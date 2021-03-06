/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Servlet;

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
 * @author RICADINHO
 */
public class ServAlterarStatusColeta extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServAlterarStatusColeta</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServAlterarStatusColeta at " + request.getContextPath () + "</h1>");
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
                int idUsuario = (Integer) sessao.getAttribute("idUsuario");
                int idEmpresa = (Integer) sessao.getAttribute("idEmpresa");
                String statusEntrega = request.getParameter("statusEntrega");

                if (statusEntrega != null && !statusEntrega.equals("0")) {

                    int idStatusEnt = Integer.parseInt(statusEntrega);

                    String nomeStatus = Controle.ContrStatusEntrega.consultaNomeStatus(idStatusEnt, nomeBD);
                    String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuario, nomeBD);
                    String idsColetas = request.getParameter("coletas");
                    idsColetas = idsColetas.substring(1);
                    Timestamp vDataHoraAtual = new Timestamp(new Date().getTime());
                    if (contrEmpresa.consultaoSemHrVerao(idEmpresa)) {
                        vDataHoraAtual = FormatarData.somarHorasNaData(vDataHoraAtual, -1);
                    }
                    Coleta.Controle.contrColeta.darBaixaColetas(idsColetas, idStatusEnt, idStatusEnt, vDataHoraAtual, nomeBD);

                    if (idsColetas.contains(",")) {
                        String ids[] = idsColetas.split(",");
                        for (String id : ids) {
                            int idColeta = Integer.parseInt(id);
                            Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Alterado o Status da Coleta para " + nomeStatus, nomeBD);

                        }
                    } else { // caso seja uma unica coleta
                        int idColeta = Integer.parseInt(idsColetas);
                        Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Alterado o Status da Coleta para " + nomeStatus, nomeBD);

                    }
                    PrintWriter out = response.getWriter();
                    out.println("Status das coletas selecionadas alterado com Sucesso");

                }

            } catch (Exception ex) {
                PrintWriter out = response.getWriter();
                out.println("SYSTEM ERROR: Ocorreu um erro inesperado");
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
