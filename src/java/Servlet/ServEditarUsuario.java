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
public class ServEditarUsuario extends HttpServlet {

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
            out.println("<title>Servlet ServEditarUsuario</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServEditarUsuario at " + request.getContextPath () + "</h1>");
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
                int idEmpresa = (Integer) sessao.getAttribute("idEmpresa");
                String nome = request.getParameter("nome");
                String login = request.getParameter("login");
                String senha = request.getParameter("senha");
                String email = request.getParameter("email");
                int nivel = Integer.parseInt(request.getParameter("nivel"));
                int idUs = Integer.parseInt(request.getParameter("idUsuario"));

                if (senha == null || senha.equals("")) {
                    senha = Controle.contrUsuario.consultaSenhaById(idUs, nomeBD);
                } else {
                    senha = Util.FormataString.encodeSenha(senha);
                }
                
                int usaConsolidador = 0;
                if(request.getParameter("usaConsolidador")!=null){
                    usaConsolidador = Integer.parseInt(request.getParameter("usaConsolidador"));
                }
                int usaPortalPostal = 0;
                if(request.getParameter("usaPortalPostal")!=null){
                    usaPortalPostal = Integer.parseInt(request.getParameter("usaPortalPostal"));
                }
                
                String acessosPortalPostal = "";
                String acessosConsolidador = "";
                
                if(usaPortalPostal == 1){
                    String pp[] = request.getParameterValues("acessos_pp_edit");
                    if(pp != null){
                        for (String acesso : pp) {
                            acessosPortalPostal += ";" + acesso;
                        }
                    }
                }
                
                if(usaConsolidador == 1){              
                    String con_conf[] = request.getParameterValues("acessos_cons_edit");
                    if(con_conf != null){
                        for (String acesso : con_conf) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                }
                
                if(!acessosPortalPostal.equals("")){
                    acessosPortalPostal = acessosPortalPostal.substring(1);
                }
                if(!acessosConsolidador.equals("")){
                    acessosConsolidador = acessosConsolidador.substring(1);
                }
                Controle.contrUsuario.alterar(nome, nivel, login, senha, idEmpresa, idUs, email, usaPortalPostal, acessosPortalPostal, usaConsolidador, acessosConsolidador);

                sessao.setAttribute("msg", "Usuário Alterado com sucesso!");
                //response.sendRedirect("Agencia/Configuracao/usuario_lista.jsp");
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("Portal Postal - ServEditarUsuario", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Configuracao/usuario_lista.jsp");
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
