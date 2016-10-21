/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import Entidade.ClienteSMTP;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Fernando
 */
public class ServCriaSMTP extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServCriaSMTP</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServCriaSMTP at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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

            try {
                String nomeBD = (String) sessao.getAttribute("empresa");
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));

                String idDepartamento;
                String envia_remetente;
                int envia_destinatario = 0;
                int tipo_servidor;
                String smtp = "smtp.mandrillapp.com";
                int porta = 587;
                int is_secure = 1;
                String user = "fernando@scc4.com.br";
                String senha = "1QPjJ6mHdMJZCEK2jgH9CQ";
                String tipo_seguranca = "SSL"; //You can use SSL on port 465 for sending through Mandrill
                int porta_ssl = 465;

                tipo_servidor = Integer.parseInt(request.getParameter("is_smtp_client"));
                //verifica se o servidor smtp é do cliente e troca os atributos smtp
                if (tipo_servidor == 1) {
                    System.out.println("entrou if tipo_servidor "+tipo_servidor);
                    user = "";
                    senha = "";
                    is_secure = 0;
                    porta = 0;
                    smtp = request.getParameter("smtp");
                    System.out.println("porta_smtp "+ request.getParameter("porta_smtp"));
                    
                    if (!request.getParameter("porta_smtp").equals("")) {
                        System.out.println("entrou porta_smtp");
                        porta = Integer.parseInt(request.getParameter("porta_smtp"));
                    }
                    System.out.println("is_autenticacao "+ request.getParameter("is_autenticacao"));
                    if (request.getParameter("is_autenticacao") != null) {
                        System.out.println("entrou is_autenticacao");
                        
                        is_secure = Integer.parseInt(request.getParameter("is_autenticacao"));
                        user = request.getParameter("usuario");
                        System.out.println(user);
                        senha = request.getParameter("senha");
                        System.out.println(senha);
                        tipo_seguranca = request.getParameter("tipo_seg");
                        System.out.println(tipo_seguranca);
                        System.out.println("porta_ssl "+request.getParameter("porta_ssl"));
                        if (!request.getParameter("porta_ssl").equals("")) {
                            System.out.println("entrou porta_ssl");
                            porta_ssl = Integer.parseInt(request.getParameter("porta_ssl"));
                        }
                    }
                }
                System.out.println("is_destinatario " +request.getParameter("is_destinatario"));
                if (request.getParameter("is_destinatario") != null) {
                    System.out.println("entrou is_destinatario");
                    envia_destinatario = Integer.parseInt(request.getParameter("is_destinatario"));
                }

                envia_remetente = request.getParameter("add_mail");
                System.out.println(envia_remetente);
                
                String departamentos[] = request.getParameterValues("departamentos");
                idDepartamento = "";
                if (departamentos != null) {
                    for (int i = 0; i < departamentos.length; i++) {
                        if (i == 0) {
                            idDepartamento += departamentos[i];
                        } else {
                            idDepartamento += ";" + departamentos[i];
                        }
                    }
                }

                ClienteSMTP clSMTP = new ClienteSMTP(idCliente, idDepartamento, tipo_servidor, envia_remetente, envia_destinatario, smtp, porta, is_secure, tipo_seguranca, porta_ssl, user, senha);
                System.out.println("toString - " + clSMTP.toString());
                Controle.ContrClienteSMTP.insereClienteSMTP(clSMTP, nomeBD);
                sessao.setAttribute("msg", "Cadastro Inserido com Sucesso!");
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("SCC4 - ServCriarLoginPortal", "Exception", null, ex.toString());

                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + ex.toString() + "; Ocorreu um erro inesperado!");

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
