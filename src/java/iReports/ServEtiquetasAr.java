/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iReports;

import Controle.contrCliente;
import Emporium.Controle.ContrPreVenda;
import Entidade.Clientes;
import Util.Conexao;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author RICARDINHO
 */
public class ServEtiquetasAr extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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

            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = (Integer) sessao.getAttribute("idCliente");
            String param = request.getParameter("ids");

            try {
                Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                String num = "";
                if (cli.getNumero() != null && !cli.getNumero().trim().equals("") && !cli.getNumero().trim().equals("null")) {
                    num = ", n " + cli.getNumero();
                }
                String comp = "";
                if (cli.getComplemento() != null && !cli.getComplemento().trim().equals("") && !cli.getComplemento().trim().equals("null")) {
                    comp = ", " + cli.getComplemento();
                }

                // mapa de parâmetros do relatório (ainda vamos aprender a usar)
                Map parametros = new HashMap();
                parametros.put("idCliente", idCliente);
                parametros.put("enderecoCli", cli.getEndereco() + num + comp); //MAX DE CARACTERES '40'
                parametros.put("bairroCli", cli.getBairro());
                parametros.put("cidadeCli", cli.getCep() + " - " + cli.getCidade() + " - " + cli.getUf());
                parametros.put("nomeCli", cli.getNomeFantasia());
                parametros.put("nomeBD", nomeBD);
                parametros.put("img", "http://localhost:8080/PortalPostal/imagensNew/etq_ar.jpg");
                
                
                String ordem = request.getParameter("ordem");
                String formato = request.getParameter("formato");
                String url_jrxml = "etiquetas_ar_reimp_1.jrxml";
                if(formato.equals("A4")){
                    url_jrxml = "etiquetas_ar_reimp_1.jrxml";
                }else if(formato.equals("ETQ_16x10")){
                    url_jrxml = "etiquetas_ar_16x10.jrxml";
                }

                byte[] bytes = null;
                Connection conn = Conexao.conectar(nomeBD);
                try {
                    InputStream in = getClass().getResourceAsStream(url_jrxml);
                    JasperDesign jasperDesign = JRXmlLoader.load(in);                    
                    
                    JRDesignQuery query = new JRDesignQuery();                    
                    query.setText("SELECT " +
                                        " IF(numObjeto = 'avista', '', numObjeto) AS nObj, " +
                                        " nomeServico, " +
                                        " siglaAmarracao, " +
                                        " IF(aos_cuidados IS NULL OR aos_cuidados = '', d.nome, CONCAT(d.nome, ' A/C ', aos_cuidados)) AS nomeDes, " +
                                        " IF(valor_declarado = 0, '', 'VD') AS vd, " +
                                        " IF(aviso_recebimento = 0, '', 'AR') AS ar, " +
                                        " IF(mao_propria = 0, '', 'MP') AS mp, " +
                                        " d.cep, " +
                                        " d.endereco, " +
                                        " d.numero, " +
                                        " d.complemento, " +
                                        " d.bairro, " +
                                        " d.cidade, " +
                                        " d.uf, " +
                                        " conteudo, " +
                                        " departamento " +
                                " FROM " +
                                        " pre_venda AS p " +
                                " LEFT JOIN " +
                                        " pre_venda_destinatario AS d " +
                                " ON " +
                                        " p.idDestinatario = d.idDestinatario " +
                                " WHERE " +
                                        " p.idCliente = " + idCliente + " AND impresso_ar = 0 AND aviso_recebimento = 1 AND id IN (" + param + ")"
                            + " ORDER BY "+ordem+";");
                    jasperDesign.setQuery(query);
                    
                    JasperReport jr = JasperCompileManager.compileReport(jasperDesign); 
                    JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, conn);
                    Conexao.desconectar(conn);
                    bytes = JasperExportManager.exportReportToPdf(impressao);

                } catch (Exception e) {
                    Conexao.desconectar(conn);
                    e.printStackTrace();
                    return;
                }

                //  
                if (bytes != null && bytes.length > 0) {
                    ContrPreVenda.setarImpressoAr(nomeBD, param);
                    
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
