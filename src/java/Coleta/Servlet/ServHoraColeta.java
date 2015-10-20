/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Servlet;

import Controle.ContrErroLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SCC4
 */
public class ServHoraColeta extends HttpServlet {

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
            out.println("<title>Servlet ServHoraColeta</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServHoraColeta at " + request.getContextPath () + "</h1>");
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
            response.sendRedirect("/index.jsp?msgLog=3");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                int antecedencia = Integer.parseInt(request.getParameter("antecedencia"));
                String vHoraFimAcesso = request.getParameter("timefield1");
                String vHoraIniColeta = request.getParameter("timefield2");
                String vHoraFimColeta = request.getParameter("timefield3");

                SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");

                Time horaFimAcesso = null, horaIniColeta = null, horaFimColeta = null;
                Date aux = null;

                try {
                    aux = formatador.parse(vHoraFimAcesso);
                    horaFimAcesso = new Time(aux.getTime());
                    aux = formatador.parse(vHoraIniColeta);
                    horaIniColeta = new Time(aux.getTime());
                    aux = formatador.parse(vHoraFimColeta);
                    horaFimColeta = new Time(aux.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(ServHoraColeta.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (Coleta.Controle.contrHoraColeta.verificaExistenciaHoraColeta(nomeBD)) {
                    Coleta.Controle.contrHoraColeta.alterar(horaFimAcesso, horaFimAcesso, horaIniColeta, horaFimColeta, antecedencia, nomeBD);
                } else {
                    Coleta.Controle.contrHoraColeta.inserir(horaFimAcesso, horaFimAcesso, horaIniColeta, horaFimColeta, antecedencia, nomeBD);
                }

                sessao.setAttribute("msg", "Horários da Coleta atualizados com sucesso!");
                //response.sendRedirect("Agencia/Configuracao/config_hora_coleta.jsp?");
                response.sendRedirect(request.getHeader("referer"));
                
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServHoraColeta", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Configuracao/config_hora_coleta.jsp?");
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
