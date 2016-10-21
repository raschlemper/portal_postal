/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.ContrErroLog;
import Controle.contrEmpresa;
import Util.FormatarData;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rico
 */
public class ServAgendaColeta extends HttpServlet {

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
            out.println("<title>Servlet ServAgendaColeta</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServAgendaColeta at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="M�todos HttpServlet. Clique no sinal de + � esquerda para editar o c�digo.">
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
        //processRequest(request, response);
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("nomeBD");
        if (expira == null) {
            response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faca o login novamente!");
        } else {
            try {

                int idAgf = (Integer) sessao.getAttribute("idEmpresa");
                String nomeBD = (String) sessao.getAttribute("nomeBD");
                
                int idColeta = 0, idTipo = 0, idUsuario = 0;
                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                idTipo = Integer.parseInt(request.getParameter("idTipo"));
                String obs = request.getParameter("obs");
                String dataHoraColeta = request.getParameter("dataColeta") +" "+request.getParameter("horaColeta");
                //formata a data para timestamp do tipo 'dd/MM/yyyy HH:mm'
                Timestamp vDataHoraColeta = Util.FormatarData.formataDateTime(dataHoraColeta);
                Timestamp vDataHoraAtual = new Timestamp(new Date().getTime());
                if (contrEmpresa.consultaoSemHrVerao(idAgf)) {
                    //vDataHoraColeta = FormatarData.somarHorasNaData(vDataHoraColeta, -1);
                    vDataHoraAtual = FormatarData.somarHorasNaData(vDataHoraAtual, -1);
                }


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
                if(idColetador == 0){
                    idColeta = Coleta.Controle.contrColeta.inserir(idCliente, idUsuario, idColetador, idContato, idTipo, 1, vDataHoraAtual, vDataHoraColeta, obs, 3, nomeBD);
                }else{
                    idColeta = Coleta.Controle.contrColeta.inserir(idCliente, idUsuario, idColetador, idContato, idTipo, 2, vDataHoraAtual, vDataHoraColeta, obs, 3, nomeBD);
                }
                Controle.ContrLogColeta.inserir(idColeta, idUsuario, "Solicitação WEB", "Coleta Solicitada pela WEB", nomeBD);

                sessao.setAttribute("msg", "Nova Coleta inserida com sucesso!");
                response.sendRedirect("Cliente/Servicos/coleta.jsp");

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO Emporium - ServAgendaColeta", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                response.sendRedirect("Cliente/Servicos/coleta.jsp");
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
