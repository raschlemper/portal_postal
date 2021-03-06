/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.ContrClienteEtiquetas;
import Emporium.Controle.ContrPreVenda;
import Entidade.PreVenda;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RICARDINHO
 */
public class ServPreVendaExcluir extends HttpServlet {

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
            out.println("<title>Servlet ServPreVendaExcluir</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServPreVendaExcluir at " + request.getContextPath() + "</h1>");
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
        
        String idVenda = request.getParameter("idVenda");
        String nomeBD = request.getParameter("nomeBD");
        String[] idEtiquetas = idVenda.split(",");
        for (String idEtiqueta : idEtiquetas) {
            deletaEtiquetaUnica(Integer.parseInt(idEtiqueta),nomeBD);
        }

        response.sendRedirect("Cliente/Servicos/pre_postagem.jsp");

    }

    private void deletaEtiquetaUnica(Integer idVenda, String nomeBD) {
        if(!ContrPreVenda.verificaImpresso(idVenda, nomeBD)){
            PreVenda preVenda = ContrPreVenda.consultaVendaById(idVenda, nomeBD);
            ContrPreVenda.excluir(idVenda, nomeBD);
            if(!preVenda.getNumObjeto().equals("avista")){
                ContrClienteEtiquetas.alteraUtilizadaEtiqueta(preVenda.getNumObjeto(), 0, nomeBD);
            }
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
