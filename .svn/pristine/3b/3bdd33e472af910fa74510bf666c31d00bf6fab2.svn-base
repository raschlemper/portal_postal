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
public class ServInserirUsuario extends HttpServlet {

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
            out.println("<title>Servlet ServInserirUsuario</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirUsuario at " + request.getContextPath () + "</h1>");
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
                int idEmpresa = (Integer) sessao.getAttribute("idEmpresa");
                String nome = request.getParameter("nome");
                String login = request.getParameter("login");
                String senha = request.getParameter("senha");
                String email = request.getParameter("email");
                int nivel = Integer.parseInt(request.getParameter("nivel"));
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
                    String pp[] = request.getParameterValues("acessos_pp");
                    if(pp != null){
                        for (String acesso : pp) {
                            acessosPortalPostal += ";" + acesso;
                        }
                    }
                    /*
                    String pp_etiqueta[] = request.getParameterValues("geretq");
                    if(pp_etiqueta != null){
                        for (String acesso : pp_etiqueta) {
                            acessosPortalPostal += ";" + acesso;
                        }
                    }
                    String pp_coleta[] = request.getParameterValues("coleta");
                    if(pp_coleta != null){
                        for (String acesso : pp_coleta) {
                            acessosPortalPostal += ";" + acesso;
                        }
                    }
                    String pp_importacao[] = request.getParameterValues("importacao");
                    if(pp_importacao != null){
                        for (String acesso : pp_importacao) {
                            acessosPortalPostal += ";" + acesso;
                        }
                    }
                    String pp_cadastro[] = request.getParameterValues("cadastro");
                    if(pp_cadastro != null){
                        for (String acesso : pp_cadastro) {
                            acessosPortalPostal += ";" + acesso;
                        }
                    }*/
                }
                
                if(usaConsolidador == 1){                    
                    String con_conf[] = request.getParameterValues("acessos_cons");
                    if(con_conf != null){
                        for (String acesso : con_conf) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                    /*
                    String con_conf[] = request.getParameterValues("con_conf");
                    if(con_conf != null){
                        for (String acesso : con_conf) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                    String con_ferramenta[] = request.getParameterValues("con_ferramenta");
                    if(con_ferramenta != null){
                        for (String acesso : con_ferramenta) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                    String con_exportacao[] = request.getParameterValues("con_exportacao");
                    if(con_exportacao != null){
                        for (String acesso : con_exportacao) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                    String con_exped[] = request.getParameterValues("con_exped");
                    if(con_exped != null){
                        for (String acesso : con_exped) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                    String con_pesq[] = request.getParameterValues("con_pesq");
                    if(con_pesq != null){
                        for (String acesso : con_pesq) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                    String con_relat[] = request.getParameterValues("con_relat");
                    if(con_relat != null){
                        for (String acesso : con_relat) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }
                    String con_util[] = request.getParameterValues("con_util");
                    if(con_util != null){
                        for (String acesso : con_util) {
                            acessosConsolidador += ";" + acesso;
                        }
                    }*/
                }
                
                if(!acessosPortalPostal.equals("")){
                    acessosPortalPostal = acessosPortalPostal.substring(1);
                }
                if(!acessosConsolidador.equals("")){
                    acessosConsolidador = acessosConsolidador.substring(1);
                }

                Controle.contrUsuario.inserir(nome, nivel, login, senha, idEmpresa, email, usaPortalPostal, acessosPortalPostal, usaConsolidador, acessosConsolidador);

                sessao.setAttribute("msg", "Usuário Inserido com sucesso!");
                //response.sendRedirect("Agencia/Configuracao/usuario_lista.jsp");
                response.sendRedirect(request.getHeader("referer"));
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("Portal Postal - ServInserirUsuario", "Exception", null, ex.toString());
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
