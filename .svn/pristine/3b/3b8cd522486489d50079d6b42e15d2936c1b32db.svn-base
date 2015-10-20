/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Servlet;

import Controle.ContrErroLog;
import java.io.*;
import java.net.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author SCC4
 */
public class ServInserirColeta extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServInserirColeta</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirColeta at " + request.getContextPath () + "</h1>");
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
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("/index.jsp?msgLog=3");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");

                //pega os dados da coleta
                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                int idTipo = Integer.parseInt(request.getParameter("idTipo"));
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuario, nomeBD);
                String obs = request.getParameter("obs");
                String dataHoraColeta = request.getParameter("dataColeta") +" "+request.getParameter("horaColeta");
                //formata a data para timestamp do tipo 'dd/MM/yyyy HH:mm'
                Timestamp vDataHoraColeta = Util.FormatarData.formataDateTime(dataHoraColeta);

                //status padrao para uma nova coleta = 2 (repassado)
                int status = 2;

                //pega os dados do contato
                String contato = request.getParameter("contato");
                int idContato = 0;
                try {
                    idContato = Integer.parseInt(contato);
                } catch (Exception e) {
                    if(contato != null && !contato.equals("")){
                        String email = request.getParameter("email");
                        String fone = request.getParameter("fone");
                        String setor = request.getParameter("setor");
                        Controle.contrContato.inserir(idCliente, contato, email, fone, setor, nomeBD);
                    }
                }

                //insere uma nova coleta no bd
                int idColeta = Coleta.Controle.contrColeta.inserir(idCliente, idUsuario, idColetador, idContato, idTipo, status, vDataHoraColeta, obs, 1, nomeBD);
                Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Coleta Solicitada por Telefone", nomeBD);

                sessao.setAttribute("msg", "Nova Coleta inserida com sucesso!");
                //response.sendRedirect("Agencia/Coleta/acompanhamento.jsp?idColetador="+idColetador);
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("Portal Postal - ServInserirColeta", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Coleta/novaColeta.jsp");
                response.sendRedirect(request.getHeader("referer"));
            }
        }
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
