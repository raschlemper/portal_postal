/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iReports;

import Controle.contrCliente;
import Entidade.Clientes;
import Util.Conexao;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ServRelatorioLogEtq extends HttpServlet {

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
            try {
                String idCliente = request.getParameter("idCliente");
                String nomeBD = (String) sessao.getAttribute("empresa");
                Map parametros = new HashMap();
                parametros.put("nomeBD", nomeBD);
                parametros.put("msg", "mensagem");
                parametros.put("idCliente", idCliente);

                byte[] bytes = null;
                InputStream in = getClass().getResourceAsStream("relatorio_log_etiquetas.jrxml");
                JasperDesign jasperDesign = JRXmlLoader.load(in);
                JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, Conexao.conectar(nomeBD));
                bytes = JasperExportManager.exportReportToPdf(impressao);


                if (bytes != null && bytes.length > 0) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-disposition", "attachment; filename=relatorio_etiquetas_inseridas.pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
                
            } catch (JRException ex) {
                response.sendRedirect("/Agencia/Configuracao/cliente_etiquetas.jsp?idCliente=83&msg="+ex);
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
