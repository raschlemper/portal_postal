/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.contrEmpresa;
import Entidade.Clientes;
import Entidade.ClientesUsuario;
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
public class ServLoginEmporium extends HttpServlet {

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
            out.println("<title>Servlet ServLoginEmporium</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServLoginEmporium at " + request.getContextPath () + "</h1>");
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
        //processRequest(request, response);
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

        int idEmpresa = Integer.parseInt(request.getParameter("agenciaHoito"));
        String nomeBD = Controle.contrEmpresa.cnpjEmpresa(idEmpresa).trim();
        if (nomeBD == null || nomeBD.equals("")) {
            response.sendRedirect("index.jsp?msg=Codigo de agencia inexistente!");
        } else {

            String senha = request.getParameter("senhaHoito");
            String login = request.getParameter("loginHoito");

            Clientes cli = Emporium.Controle.ContrLoginEmporium.login(login, senha, nomeBD);

            if (cli != null) {
                HttpSession sessao = request.getSession();
                sessao.setMaxInactiveInterval(1800);

                Controle.contrLogin.registraLoginDeCliente(idEmpresa, cli.getCodigo());
                if (Controle.contrLogin.verificaStatusEmpresa(idEmpresa)) {
                    
                    ClientesUsuario us = Controle.contrSenhaCliente.usuarioEmp(login, senha, nomeBD);
                    empresas emp = contrEmpresa.consultaEmpresa(idEmpresa);
                    
                    sessao.setAttribute("idCliente", cli.getCodigo());
                    sessao.setAttribute("empresa", nomeBD);
                    sessao.setAttribute("nomeBD", nomeBD);
                    sessao.setAttribute("idEmpresa", idEmpresa);
                    sessao.setAttribute("nomeUser", login);
                    sessao.setAttribute("nivelUsuarioEmp", us.getNivel());
                    sessao.setAttribute("idUsuarioEmp", us.getId());
                    sessao.setAttribute("acessos", us.getAcessos());
                    sessao.setAttribute("departamentos", us.getDepartamentos());
                    sessao.setAttribute("servicos", us.getServicos());
                    
                    sessao.setAttribute("nomeEmpresa", emp.getFantasia());
                    sessao.setAttribute("temColeta", emp.getColeta()); // se a agencia tem COLETA
                    sessao.setAttribute("temPV", emp.getChamada()); //se a agencia tem PRE VENDA
                    sessao.setAttribute("temETQ", cli.getUsaEtiquetador()); // Se o cliente usa o etiquetador
                    sessao.setAttribute("cliente", cli); // Se o cliente usa o etiquetador
                    sessao.setAttribute("agencia", emp); // Se o cliente usa o etiquetador
                    if(us.getNivel() != 99){
                        response.sendRedirect("Cliente/Postagens/consultas.jsp");
                    }else{
                        response.sendRedirect("Cliente/Servicos/pre_postagem.jsp");                        
                    }                    
                } else {
                    response.sendRedirect("index.jsp?msg=Esta agencia esta desativada!");
                }
            } else {
                response.sendRedirect("index.jsp?msg=O e-mail ou senha incorretos!<br><br>Verifique se o codigo de sua agencia esta correto!");
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
