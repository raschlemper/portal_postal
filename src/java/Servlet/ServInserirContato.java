/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

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
 * @author RICADINHO
 */
public class ServInserirContato extends HttpServlet {

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
            out.println("<title>Servlet ServInserirContato</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirContato at " + request.getContextPath () + "</h1>");
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
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                String contato = request.getParameter("contatoNome");
                String email = request.getParameter("contatoEmail");
                if (email.equals("")) {
                    email = "- - -";
                }
                String fone = request.getParameter("contatoFone");
                if (fone.equals("")) {
                    fone = "- - -";
                }
                String setor = request.getParameter("contatoSetor");
                if (setor.equals("")) {
                    setor = "- - -";
                }

                Controle.contrContato.inserir(idCliente, contato, email, fone, setor, nomeBD);
                sessao.setAttribute("msg", "Contato Inserido com Sucesso!");
                response.sendRedirect("Agencia/Configuracao/cliente_contatos.jsp?idCliente=" + idCliente);

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("Portal Postal - ServInserirContato", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                response.sendRedirect("Agencia/Configuracao/cliente_contatos.jsp?idCliente=" + idCliente);
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
