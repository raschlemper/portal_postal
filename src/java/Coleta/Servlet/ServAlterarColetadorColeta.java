/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Servlet;

import Controle.ContrErroLog;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SCC4
 */
public class ServAlterarColetadorColeta extends HttpServlet {

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
            out.println("<title>Servlet ServAlterarColetadorColeta</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServAlterarColetadorColeta at " + request.getContextPath () + "</h1>");
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

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                int cont = Integer.parseInt(request.getParameter("contador"));
                int idUsuario = (Integer) sessao.getAttribute("idUsuario");
                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuario, nomeBD);
                String nomeColetador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
                String pagina = request.getParameter("pagina");

                for (int i = 1; i <= cont; i++) {
                    String vIdColeta = request.getParameter("idColeta" + i);
                    if (vIdColeta != null && !vIdColeta.equals("")) {
                        int idColeta = Integer.parseInt(vIdColeta);
                        Coleta.Controle.contrColeta.alterarColetador(idColeta, idColetador, nomeBD);
                        Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Alterado o Coletador da Coleta para " + nomeColetador, nomeBD);
                    }
                }

                sessao.setAttribute("msg", "Coletados da Coleta Alterado com Sucesso!");
                //response.sendRedirect("Agencia/Coleta/"+pagina+"?idColetador=" + idColetador);
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServAlterarColetadorColeta", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Coleta/acompanhamento.jsp");
                response.sendRedirect(request.getHeader("referer"));
            }
        }

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
