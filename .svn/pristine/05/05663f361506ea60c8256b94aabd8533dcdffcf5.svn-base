/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Servlet;

import Controle.ContrErroLog;
import Controle.contrDestinatario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RICADINHO
 */
public class ServExcluirDestinatario extends HttpServlet {
   
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
            out.println("<title>Servlet ServExcluirDestinatario</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServExcluirDestinatario at " + request.getContextPath () + "</h1>");
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
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                String[] ids = request.getParameterValues("idDestinatario");
                String idsSelecionados = "0";
                if (ids != null && ids.length > 0) {
                    for (int i = 0; i < ids.length; i++) {
                        String id = ids[i];
                        if (id != null) {
                            idsSelecionados += "," + id;
                        }
                    }

                    contrDestinatario.deletar(idsSelecionados, idCliente, nomeBD);
                    sessao.setAttribute("msg", "Destinatário Excluido com Sucesso!");
                    response.sendRedirect("Cliente/Cadastros/destinatario_lista.jsp");
                } else {

                }

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("Portal Postal - ServInserirDestinatario", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                response.sendRedirect("Cliente/Cadastros/destinatario_lista.jsp");
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
