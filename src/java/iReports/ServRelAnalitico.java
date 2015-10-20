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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author RICARDINHO
 */
public class ServRelAnalitico extends HttpServlet {

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

        String nomeBD = request.getParameter("nomeBD");
        int idCliente = Integer.parseInt(request.getParameter("idCli"));

        try {
            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            
            // mapa de parâmetros do relatório (ainda vamos aprender a usar)
            Map parametros = new HashMap();
            //parametros.put("idCliente", 283);
            //parametros.put("enderecoCli", cli.getEndereco() + ", n " + cli.getNumero() + ", " + cli.getComplemento()); //MAX DE CARACTERES '40'
            //parametros.put("bairroCli", cli.getBairro());
            //parametros.put("cidadeCli", cli.getCidade() + " / " + cli.getUf());
            //parametros.put("cepCli", cli.getCep() + "");
            //parametros.put("contratoCli", "Contrato: " + cli.getTemContrato());
            parametros.put("nomeCli", cli.getNomeFantasia());
            //parametros.put("urlLogoCli", "www.portalpostal.com.br"+ cli.getUrl_logo());
            parametros.put("urlLogoCli", "http://localhost:8084" + cli.getUrl_logo());

            byte[] bytes = null;
            try {
                InputStream in = getClass().getResourceAsStream("relatorio_analitico.jrxml");
                JasperDesign jasperDesign = JRXmlLoader.load(in);
                JRDesignQuery query = new JRDesignQuery();
                query.setText("SELECT nome FROM cliente LIMIT 10");
                jasperDesign.setQuery(query);
                JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

                JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, Conexao.conectar(nomeBD));
                bytes = JasperExportManager.exportReportToPdf(impressao);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            //  
            if (bytes != null && bytes.length > 0) {
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=relatorio_analitico.pdf");
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
