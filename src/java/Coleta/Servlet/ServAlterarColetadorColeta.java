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
public class ServAlterarColetadorColeta extends HttpServlet {

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
             out.println("<title>Servlet ServAlterarColetadorColeta</title>");  
             out.println("</head>");
             out.println("<body>");
             out.println("<h1>Servlet ServAlterarColetadorColeta at " + request.getContextPath () + "</h1>");
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
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuario, nomeBD);
                String nomeColetador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
                String idsColetas = request.getParameter("coletas");
                boolean isWeb = Boolean.parseBoolean(request.getParameter("isweb"));
                System.out.println("XXXXXXXXXXX");
                System.out.println(isWeb);
                Coleta.Controle.contrColeta.alterarColetadordasColetas(idsColetas,idColetador,isWeb,nomeBD);
                
                if (idsColetas.contains(",")) {
                    
                    String ids[] = idsColetas.split(",");
                    for (String id : ids) {
                        int idColeta = Integer.parseInt(id);
                        Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Alterado o Coletador da Coleta para " + nomeColetador, nomeBD);
                    }
                } else { // caso seja uma unica coleta
                    int idColeta = Integer.parseInt(idsColetas);
                    Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Alterado o Coletador da Coleta para " + nomeColetador, nomeBD);

                }
                PrintWriter out = response.getWriter();
                out.println("Coletador das coletas selecionadas alterado com Sucesso");
              //  sessao.setAttribute("msg", "Coletados da Coleta Alterado com Sucesso!");
                //response.sendRedirect("Agencia/Coleta/"+pagina+"?idColetador=" + idColetador);
                // response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServAlterarColetadorColeta", "Exception", null, ex.toString());
                PrintWriter out = response.getWriter();
                out.println("SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado");

               // sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Coleta/acompanhamento.jsp");
                //  response.sendRedirect(request.getHeader("referer"));
            }

        }

    }
}
