/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.contrUsuario;
import Util.EnviaEmail;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author SCC4
 */
public class ServEsqueciSenha extends HttpServlet {

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
            out.println("<title>Servlet ServEsqueciSenha</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServEsqueciSenha at " + request.getContextPath () + "</h1>");
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
        String login = request.getParameter("login");
        Entidade.Usuario user = null;
        if (login != null && !login.equals("")) {
            user = Controle.contrUsuario.consultaUsuarioByLogin(login);
        } else {
            sessao.setAttribute("msg", "Digite o seu Login!");
        }

        if (user != null) {
            String nome = user.getNome();
            String login2 = user.getLogin();
            String email2 = user.getEmail();
            String senha = EnviaEmail.geraSenha(6);
            try {
                contrUsuario.alterarSenha(senha, user.getIdUsuario());
                Util.EnviaEmail.SendMailEsqueceuSenhaHoito(nome, login2, senha, email2);
                sessao.setAttribute("msg", "Foi enviado um e-mail para '" + email2 + "' com uma nova senha de acesso!");
            } catch (EmailException ex) {
                sessao.setAttribute("msg", "Não foi possivel enviar o e-mail para '" + email2 + "'!");
            }
        } else {
            sessao.setAttribute("msg", "Usuário não encontrado!<br>Verifique se o Login foi digitado corretamente.");
        }

        response.sendRedirect("index.jsp");
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
