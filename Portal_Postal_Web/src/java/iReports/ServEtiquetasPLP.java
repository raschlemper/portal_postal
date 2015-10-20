/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iReports;

import Controle.contrCliente;
import Emporium.Controle.ContrImpressaoPLP;
import Entidade.Clientes;
import Util.Conexao;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Fernando
 */
@WebServlet(name = "ServEtiquetasPLP", urlPatterns = {"/ServEtiquetasPLP"})
public class ServEtiquetasPLP extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {

            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = (Integer) sessao.getAttribute("idCliente");
            String SROs = request.getParameter("ids");
            
            String formato = request.getParameter("formato");
            String url_jrxml = "etiqueta_plp.jrxml";
            if(formato.equals("A4")){
                url_jrxml = "etiqueta_plp.jrxml";
            }else if(formato.equals("ETQ_16x10")){
                url_jrxml = "etiqueta_plp_16x10.jrxml";
            }

            try {
                Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                String url_base = getServletContext().getRealPath("");
                String url = "http://localhost:8080/PortalPostal/" + cli.getUrl_logo();
                if (!urlExist(url)) {
                    url = "";
                }

                // mapa de parâmetros do relatório (ainda vamos aprender a usar)
                Map parametros = new HashMap();
                parametros.put("nomeBD", nomeBD);
                parametros.put("urlLogoCli", url);                   

                byte[] bytes = null;
                try {
                    InputStream in = getClass().getResourceAsStream(url_jrxml);
                    JasperDesign jasperDesign = JRXmlLoader.load(in);                    
                    
                    String sqlQuery = "SELECT *,"
                            + " IF(servico = 'CARTA' || servico = 'SIMPLES', CONCAT('"+url_base+"/imagensNew/chancelas/', servico, '.png'), CONCAT('http://localhost:8080/PortalPostal/ServGeraChancelaEtq?servico=', servico, '&contrato=' , contrato, '&nome=' , REPLACE(REPLACE(nomeRemetente, ' ', '_'), '&', 'E'), '&ano=2015&uf=', ufRemetente)) AS imgChancela"
                            + " FROM me_plp"
                            + " WHERE idCliente = " + idCliente + " AND status = 0 AND sro IN (" + SROs + ");";
                    
                    JRDesignQuery query = new JRDesignQuery();
                    query.setText(sqlQuery);
                    jasperDesign.setQuery(query);
                    
                    JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, Conexao.conectar(nomeBD));
                    bytes = JasperExportManager.exportReportToPdf(impressao);
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                    return;
                }

                //  
                if (bytes != null && bytes.length > 0) {
                    ContrImpressaoPLP.setarImpresso(nomeBD, SROs);
                    
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public static boolean urlExist(String vurl) {
        try {
            URL url = new URL(vurl);
            url.openStream();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
