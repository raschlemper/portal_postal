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
public class ServEditarLoginPortal extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
            /*
             * TODO output your page here out.println("<html>");
             * out.println("<head>"); out.println("<title>Servlet
             * ServEditarLoginPortal</title>"); out.println("</head>");
             * out.println("<body>"); out.println("<h1>Servlet
             * ServEditarLoginPortal at " + request.getContextPath () +
             * "</h1>"); out.println("</body>"); out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HttpServlet. Clique no sinal de + à esquerda para editar o código.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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

                int id = Integer.parseInt(request.getParameter("id"));
                int nivel = Integer.parseInt(request.getParameter("nivel"));
                String login = request.getParameter("login");
                String loginOld = request.getParameter("loginOld");
                String senhaOld = request.getParameter("senhaOld");
                String senha = request.getParameter("senha");
                if (senha == null || senha.equals("")) {
                    senha = senhaOld;
                }

                String acessos[] = request.getParameterValues("acessos");
                String acc = "";
                if (acessos != null) {
                    for (int i = 0; i < acessos.length; i++) {
                        if (i == 0) {
                            acc += acessos[i];
                        } else {
                            acc += ";" + acessos[i];
                        }
                    }
                }
                if (request.getParameter("visualizarPrazos") != null && !request.getParameter("visualizarPrazos").equals("")) {
                    acc += ";" + request.getParameter("visualizarPrazos");
                }
                
                String departamentos[] = request.getParameterValues("departamentos");
                String dep = "";
                if (departamentos != null) {
                    for (int i = 0; i < departamentos.length; i++) {
                        if (i == 0) {
                            dep += departamentos[i];
                        } else {
                            dep += ";" + departamentos[i];
                        }
                    }
                }
                String servicos[] = request.getParameterValues("servicos");
                String ser = "";
                if (servicos != null) {
                    for (int i = 0; i < servicos.length; i++) {
                        if (i == 0) {
                            ser += servicos[i];
                        } else {
                            ser += ";" + servicos[i];
                        }
                    }
                }

                Controle.contrSenhaCliente.alterar(id, login, senha, nivel, acc, dep, ser, nomeBD);

                sessao.setAttribute("msg", "Usuário do Cliente Alterado com Sucesso!");
                /*if (request.getParameter("local") != null && request.getParameter("local").equals("1")) {
                    response.sendRedirect("Agencia/Configuracao/cliente_usuarios.jsp?idCliente=" + codigo);
                } else {
                    response.sendRedirect("Cliente/Cadastros/usuario_lista.jsp");
                }*/
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                System.out.println(ex);
                int idErro = ContrErroLog.inserir("HOITO - ServEditarLoginPortal", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Configuracao/cliente_usuarios.jsp?idCliente=" + codigo);
                response.sendRedirect(request.getHeader("referer"));
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
