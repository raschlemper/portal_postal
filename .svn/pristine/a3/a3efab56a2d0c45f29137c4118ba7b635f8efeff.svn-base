/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Servlet;

import Controle.ContrErroLog;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author SCC4
 */
public class ServInserirTipoColeta extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServInserirTipoColeta</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirTipoColeta at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("/index.jsp?msgLog=3");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                String tipo = request.getParameter("tipo");
                Coleta.Controle.contrTipoColeta.inserir(tipo, nomeBD);

                sessao.setAttribute("msg", "Tipo de Coleta inserido com sucesso!");
                //response.sendRedirect("Agencia/Coleta/tipo_coleta_lista.jsp");
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServInserirTipoColeta", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Coleta/tipo_coleta_lista.jsp");
                response.sendRedirect(request.getHeader("referer"));
                
            }
        }


    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
