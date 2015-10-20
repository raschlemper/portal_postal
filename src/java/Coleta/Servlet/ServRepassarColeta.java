/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Servlet;

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
public class ServRepassarColeta extends HttpServlet {

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
            out.println("<title>Servlet ServRepassarColeta</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServRepassarColeta at " + request.getContextPath () + "</h1>");
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
                int idUsuario = (Integer) sessao.getAttribute("idUsuario");
                String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuario, nomeBD);
                String vIdColetador;

                boolean flag = false;
                int idColetador = 0;
                int cont = Integer.parseInt(request.getParameter("contador"));
                for (int i = 1; i <= cont; i++) {
                    String vIdColeta = request.getParameter("idColeta" + i);
                    if (vIdColeta != null && !vIdColeta.equals("")) {
                        vIdColetador = request.getParameter("idColetador" + i);
                        if (vIdColetador == null) {
                                flag = true;
                        } else {
                            idColetador = Integer.parseInt(request.getParameter("idColetador" + i));
                            if (idColetador > 0) {
                                int idColeta = Integer.parseInt(vIdColeta);

                                int status = 2;
                                Coleta.Controle.contrColeta.alterarStatus(idColeta, status, nomeBD);
                                Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Coleta foi Confirmada", nomeBD);
                            } else {
                                flag = true;
                            }
                        }
                    }
                }

                sessao.setAttribute("msg", "Coletas confirmadas com sucesso!");
                if(flag){
                    sessao.setAttribute("msg", "Favor escolher um coletador para todas as coletas sem coletador definido!");
                }
                //response.sendRedirect("Agencia/Coleta/consultaSolicitadas.jsp");
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServRepassarColeta", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Coleta/consultaSolicitadas.jsp");
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
