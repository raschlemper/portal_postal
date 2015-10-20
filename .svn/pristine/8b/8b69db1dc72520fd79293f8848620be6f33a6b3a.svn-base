/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SCC4
 */
public class ServPesquisaPrazo extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServPesquisaPrazo</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServPesquisaPrazo at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HttpServlet. Clique no sinal de + à esquerda para editar o código.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        String nCdEmpresa = request.getParameter("codEmpresa");
        String sDsSenha = request.getParameter("senha");
        String nCdServico = request.getParameter("servico");
        String sCepOrigem = request.getParameter("cepOrigem");
        String sCepDestino = request.getParameter("cepDestino");
        String nVlPeso = request.getParameter("peso");
        if (nVlPeso.equals("")) {
            nVlPeso = "0";
        }
        String nCdFormato = request.getParameter("formato");
        String nVlComprimento = request.getParameter("comprimento");
        if (nVlComprimento.equals("")) {
            nVlComprimento = "0";
        }
        String nVlAltura = request.getParameter("altura");
        if (nVlAltura.equals("")) {
            nVlAltura = "0";
        }
        String nVlLargura = request.getParameter("largura");
        if (nVlLargura.equals("")) {
            nVlLargura = "0";
        }
        String nVlDiametro = request.getParameter("diametro");
        if (nVlDiametro.equals("")) {
            nVlDiametro = "0";
        }
        String sCdMaoPropria = request.getParameter("maopropria");
        String nVlValorDeclarado = request.getParameter("vlDeclarado");
        String sCdAvisoRecebimento = request.getParameter("aviso");
        String endereco = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?nCdEmpresa=" + nCdEmpresa + ""
                + "&sDsSenha=" + sDsSenha + "&nCdServico=" + nCdServico + "&sCepOrigem=" + sCepOrigem + "&sCepDestino=" + sCepDestino + "&nVlPeso=" + nVlPeso + ""
                + "&nCdFormato=" + nCdFormato + "&nVlComprimento=" + nVlComprimento + "&nVlAltura=" + nVlAltura + ""
                + "&nVlLargura=" + nVlLargura + "&nVlDiametro=" + nVlDiametro + "&sCdMaoPropria=" + sCdMaoPropria + "&nVlValorDeclarado=" + nVlValorDeclarado + ""
                + "&sCdAvisoRecebimento=" + sCdAvisoRecebimento + "&StrRetorno=http://shopping.correios.com.br/wbm/shopping/script/popuptarifa.aspx";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript' language='javascript'>");
        out.println("window.open('" + endereco + "','','STATUS=NO, TOOLBAR=NO, LOCATION=NO, DIRECTORIES=NO, RESISABLE=NO, SCROLLBARS=YES, TOP=110, LEFT=310, WIDTH=400, HEIGHT=350');");
        out.println("window.location = 'Cliente/Servicos/precos_prazos.jsp'");
        out.println("</script>");

    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
