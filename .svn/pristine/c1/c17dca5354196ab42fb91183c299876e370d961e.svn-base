/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.contrEmpresa;
import Controle.contrLogin;
import Entidade.Usuario;
import Entidade.empresas;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrador
 */
public class ServLogin extends HttpServlet {


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
            out.println("<title>Servlet ServLogin</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServLogin at " + request.getContextPath () + "</h1>");
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


        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        Usuario user = contrLogin.login(login, senha);

        if (user != null) {
            Controle.contrLogin.registraLoginDeAgencia(user.getIdEmpresa(), user.getIdUsuario());
            if (Controle.contrLogin.verificaStatusEmpresa(user.getIdEmpresa())) {

                HttpSession sessao = request.getSession();
                sessao.setMaxInactiveInterval(30600);

                sessao.setAttribute("usuario", login);
                sessao.setAttribute("nome", user.getNome());
                sessao.setAttribute("nivel", user.getIdNivel());
                sessao.setAttribute("idUsuario", user.getIdUsuario());
                sessao.setAttribute("idEmpresa", user.getIdEmpresa());
                sessao.setAttribute("acessosAgencia", user.getListaAcessosPortalPostal());
                
                empresas emp = contrEmpresa.consultaEmpresa(user.getIdEmpresa());
                
                sessao.setAttribute("emp", emp);
                sessao.setAttribute("nomeBD", emp.getCnpj());
                sessao.setAttribute("empresa", emp.getCnpj());
                
                if(emp.getChamada() == 1){
                    //response.sendRedirect("./Agencia/Relatorio/painel_etiquetas.jsp");
                    response.sendRedirect("NewTemplate/Dashboard");
                }else{
                    //response.sendRedirect("./Agencia/Importacao/imp_movimento.jsp");
                    response.sendRedirect("NewTemplate/Importacao/imp_movimento_b.jsp");
                }
                
            } else {
                response.sendRedirect("index.jsp?msg=Login ou Senha incorreta ou inexistente!");
            }

        } else {
            response.sendRedirect("index.jsp?msg=Login ou Senha incorreta ou inexistente!");
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
