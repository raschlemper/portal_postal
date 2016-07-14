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
 * @author RICARDINHO
 */
public class ServServicoAbrangencia extends HttpServlet {

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
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServServicoAbrangencia</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServServicoAbrangencia at " + request.getContextPath() + "</h1>");
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
        String nomeBD = (String) sessao.getAttribute("nomeBD");
        
        ContrServicoAbrangencia.excluirByServico("SEDEX10", nomeBD);
        int contador = Integer.parseInt(request.getParameter("contador"));
        for(int i=1; i<=contador;i++){
            if(request.getParameter("cepIni"+i) != null && request.getParameter("cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("suspenso_"+i));
                ContrServicoAbrangencia.inserir("SEDEX10", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }
        
        ContrServicoAbrangencia.excluirByServico("SEDEX12", nomeBD);
        int d_contador = Integer.parseInt(request.getParameter("d_contador"));
        for(int i=1; i<=d_contador;i++){
            if(request.getParameter("d_cepIni"+i) != null && request.getParameter("d_cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("d_cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("d_cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("d_suspenso_"+i));
                ContrServicoAbrangencia.inserir("SEDEX12", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }   
        
        ContrServicoAbrangencia.excluirByServico("SEDEXHJ", nomeBD);
        int sedexhj_contador = Integer.parseInt(request.getParameter("sedexhj_contador"));
        for(int i=1; i<=sedexhj_contador;i++){
            if(request.getParameter("sedexhj_cepIni"+i) != null && request.getParameter("sedexhj_cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("sedexhj_cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("sedexhj_cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("sedexhj_suspenso_"+i));
                ContrServicoAbrangencia.inserir("SEDEXHJ", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }   
        
        ContrServicoAbrangencia.excluirByServico("PAX", nomeBD);
        int pax_contador = Integer.parseInt(request.getParameter("pax_contador"));
        for(int i=1; i<=pax_contador;i++){
            if(request.getParameter("pax_cepIni"+i) != null && request.getParameter("pax_cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("pax_cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("pax_cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("pax_suspenso_"+i));
                ContrServicoAbrangencia.inserir("PAX", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }   
        
        ContrServicoAbrangencia.excluirByServico("ESEDEX", nomeBD);
        int e_contador = Integer.parseInt(request.getParameter("e_contador"));
        for(int i=1; i<=e_contador;i++){
            if(request.getParameter("e_cepIni"+i) != null && request.getParameter("e_cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("e_cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("e_cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("e_suspenso_"+i));
                ContrServicoAbrangencia.inserir("ESEDEX", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }        
        ContrServicoAbrangencia.excluirByServico("MDPB_L", nomeBD);
        int ml_contador = Integer.parseInt(request.getParameter("mdpbl_contador"));
        for(int i=1; i<=ml_contador;i++){
            if(request.getParameter("mdpbl_cepIni"+i) != null && request.getParameter("mdpbl_cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("mdpbl_cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("mdpbl_cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("mdpbl_suspenso_"+i));
                ContrServicoAbrangencia.inserir("MDPB_L", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }        
        ContrServicoAbrangencia.excluirByServico("MDPB_E", nomeBD);
        int me_contador = Integer.parseInt(request.getParameter("mdpbe_contador"));
        for(int i=1; i<=me_contador;i++){
            if(request.getParameter("mdpbe_cepIni"+i) != null && request.getParameter("mdpbe_cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("mdpbe_cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("mdpbe_cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("mdpbe_suspenso_"+i));
                ContrServicoAbrangencia.inserir("MDPB_E", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }        
        ContrServicoAbrangencia.excluirByServico("MDPB_N", nomeBD);
        int mn_contador = Integer.parseInt(request.getParameter("mdpbn_contador"));
        for(int i=1; i<=mn_contador;i++){
            if(request.getParameter("mdpbn_cepIni"+i) != null && request.getParameter("mdpbn_cepFim"+i) != null){
                int cepInicial = Integer.parseInt(request.getParameter("mdpbn_cepIni"+i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("mdpbn_cepFim"+i).replaceAll("-", ""));
                int suspenso = Integer.parseInt(request.getParameter("mdpbn_suspenso_"+i));
                ContrServicoAbrangencia.inserir("MDPB_N", cepInicial, cepFinal, suspenso, nomeBD);
            }
        }        
        
        sessao.setAttribute("msg", "Abrangência dos Serviços Alterados com Sucesso!");
        //response.sendRedirect("Agencia/Configuracao/servicos_abrangencia.jsp");
        response.sendRedirect(request.getHeader("referer"));
        
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
