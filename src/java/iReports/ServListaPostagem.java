/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iReports;

import Controle.contrCliente;
import Emporium.Controle.ContrOrdemServico;
import Entidade.Clientes;
import Entidade.OrdemServico;
import Util.Conexao;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Ricardo
 */
public class ServListaPostagem extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = (Integer) sessao.getAttribute("idCliente");
            int idUser = (Integer) sessao.getAttribute("idUsuarioEmp");
            String nomeUser = (String) sessao.getAttribute("nomeUser");

            int idOs = Integer.parseInt(request.getParameter("idOs"));
            int qtd = Integer.parseInt(request.getParameter("qtd"));
            String ids = request.getParameter("ids");
            if (idOs == 0) {
                if (qtd > 0) {
                    idOs = ContrOrdemServico.inserir(idCliente, idUser, nomeUser, qtd, ids, nomeBD);
                }
            }

            if (idOs > 0) {
                try {
                    Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                    OrdemServico os = ContrOrdemServico.consultaOS(idOs, nomeBD);

                    //mapa de parâmetros do relatório (ainda vamos aprender a usar)
                    Map parametros = new HashMap();
                    parametros.put("idCliente", idCliente + "");
                    parametros.put("nomeCliente", cli.getNome());
                    parametros.put("cnpjCliente", cli.getCnpj());
                    parametros.put("cartaoCliente", cli.getCartaoPostagem());
                    parametros.put("contratoCliente", cli.getNumContrato());
                    parametros.put("userOs", os.getNomeUsuario());
                    parametros.put("dataOs", sdf.format(os.getDataOs()));
                    parametros.put("idOs", idOs + "");
                    parametros.put("TOTAL", os.getQtdObjetos() + "");

                    byte[] bytes = null;
                    Connection conn = Conexao.conectar(nomeBD);
                    try {

                        InputStream in = getClass().getResourceAsStream("lista_postagem_fat.jrxml");
                        JasperDesign jasperDesign = JRXmlLoader.load(in);
                        JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
                        JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, conn);

                        InputStream in2 = getClass().getResourceAsStream("lista_postagem_av.jrxml");
                        JasperDesign jasperDesign2 = JRXmlLoader.load(in2);
                        JasperReport jr2 = JasperCompileManager.compileReport(jasperDesign2);
                        JasperPrint impressao2 = JasperFillManager.fillReport(jr2, parametros, conn);
                        Conexao.desconectar(conn);
                        //bytes = JasperExportManager.exportReportToPdf(impressao2);
                        List pages = impressao2.getPages();
                        for (int j = 0; j < pages.size(); j++) {
                            JRPrintPage object = (JRPrintPage) pages.get(j);
                            impressao.addPage(object);

                        }
                        bytes = JasperExportManager.exportReportToPdf(impressao);

                    } catch (Exception e) {
                    Conexao.desconectar(conn);
                        System.out.println(e);
                        e.printStackTrace();
                        return;
                    }

                    //  
                    if (bytes != null && bytes.length > 0) {
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
