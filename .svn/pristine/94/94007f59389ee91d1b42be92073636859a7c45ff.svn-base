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
 * @author SCC4
 */
public class ServCriarLoginPortal extends HttpServlet {

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
            out.println("<title>Servlet ServCriarLoginPortal</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServCriarLoginPortal at " + request.getContextPath () + "</h1>");
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
                int codigo = Integer.parseInt(request.getParameter("idCliente"));
            try {
                
                String nomeBD = (String) sessao.getAttribute("empresa");

                int nivel = Integer.parseInt(request.getParameter("nivel"));
                String login = request.getParameter("login");
                String senha = request.getParameter("senha");
                
                String acessos[] = request.getParameterValues("acessos");
                String acc = "";
                if(acessos != null){
                    for (int i = 0; i < acessos.length; i++) {
                        if(i == 0){
                            acc += acessos[i];
                        }else{
                            acc += ";"+acessos[i];
                        }
                    }
                }
                
                String departamentos[] = request.getParameterValues("departamentos");
                String dep = "";
                if(departamentos != null){
                    for (int i = 0; i < departamentos.length; i++) {
                        if(i == 0){
                            dep += departamentos[i];
                        }else{
                            dep += ";"+departamentos[i];
                        }       
                    }
                }
                String servicos[] = request.getParameterValues("servicos");
                String ser = "";
                if(servicos != null){
                    for (int i = 0; i < servicos.length; i++) {
                        if(i == 0){
                            ser += servicos[i];
                        }else{
                            ser += ";"+servicos[i];
                        }
                    }
                }

                Controle.contrSenhaCliente.inserir(codigo, login, senha, nivel, acc, dep, ser, nomeBD);

                sessao.setAttribute("msg", "Usuário do Cliente Inserido com Sucesso!");                
                response.sendRedirect(request.getHeader("referer"));
                /*if(request.getParameter("local") != null){
                    response.sendRedirect("Agencia/Configuracao/cliente_usuarios.jsp?idCliente="+codigo);
                }else{
                    response.sendRedirect("Cliente/Cadastros/usuario_lista.jsp");
                }*/

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("SCC4 - ServCriarLoginPortal", "Exception", null, ex.toString());

                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                
                response.sendRedirect(request.getHeader("referer"));
                //response.sendRedirect("Agencia/Configuracao/cliente_usuarios.jsp?idCliente="+codigo);
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
