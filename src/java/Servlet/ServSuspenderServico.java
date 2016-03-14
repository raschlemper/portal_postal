/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrServicoAbrangencia;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Correios
 */
public class ServSuspenderServico extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServSuspenderServico</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServSuspenderServico at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

            String nomeBD = request.getParameter("nomeBD");
            String servico = request.getParameter("sus_servico");
            if(servico.equals("ESEDEX")){        
                int status = Integer.parseInt(request.getParameter("sus_esedex"));
                if(status == 0){
                    ContrServicoAbrangencia.suspenderServico(servico, 1, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" suspenso!");
                }else{
                    ContrServicoAbrangencia.suspenderServico(servico, 0, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" reativado!");                
                }
            }else if(servico.equals("SEDEX10")){
                int status = Integer.parseInt(request.getParameter("sus_sedex10"));
                if(status == 0){
                    ContrServicoAbrangencia.suspenderServico(servico, 1, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" suspenso!");
                }else{
                    ContrServicoAbrangencia.suspenderServico(servico, 0, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" reativado!");                
                }
            }else if(servico.equals("SEDEX12")){
                int status = Integer.parseInt(request.getParameter("sus_sedex12"));
                if(status == 0){
                    ContrServicoAbrangencia.suspenderServico(servico, 1, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" suspenso!");
                }else{
                    ContrServicoAbrangencia.suspenderServico(servico, 0, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" reativado!");                
                }
            }else if(servico.equals("SEDEXHJ")){
                int status = Integer.parseInt(request.getParameter("sus_sedexhj"));
                if(status == 0){
                    ContrServicoAbrangencia.suspenderServico(servico, 1, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" suspenso!");
                }else{
                    ContrServicoAbrangencia.suspenderServico(servico, 0, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" reativado!");                
                }
            }else if(servico.equals("PAX")){
                int status = Integer.parseInt(request.getParameter("sus_pax"));
                if(status == 0){
                    ContrServicoAbrangencia.suspenderServico(servico, 1, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" suspenso!");
                }else{
                    ContrServicoAbrangencia.suspenderServico(servico, 0, nomeBD);
                    sessao.setAttribute("msg", ""+servico+" reativado!");                
                }
            }else{        
                sessao.setAttribute("msg", "Falha ao suspender o servico: " + servico +"!");
            }
            
            response.sendRedirect(request.getHeader("referer"));
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
