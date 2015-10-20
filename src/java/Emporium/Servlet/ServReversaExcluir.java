/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Servlet;

import Emporium.Controle.ContrLogisticaReversa;
import br.com.correios.scol.webservice.RetornoCancelamentoTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardo
 */
public class ServReversaExcluir extends HttpServlet {

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
        
        String nomeBD = request.getParameter("nomeBD");
        String usuario = request.getParameter("loginRev");
        String senha = request.getParameter("senhaRev");
        String codAdm = request.getParameter("codAdm").trim();
        String codigoAP = request.getParameter("codAP").trim();
        int idRev = Integer.parseInt(request.getParameter("idRev").trim());
        
        RetornoCancelamentoTO ret = cancelarPedido(usuario, senha, Integer.parseInt(codAdm), codigoAP, "A");
        if(ret.getCodErro() == null || Integer.parseInt(ret.getCodErro().trim())==0){
            ContrLogisticaReversa.alterarCancelado(1, idRev, nomeBD);
            response.sendRedirect("Cliente/Servicos/lista_reversa.jsp?msg=Reversa Excluida com Sucesso!");
        }else{
            response.sendRedirect("Cliente/Servicos/lista_reversa.jsp?msg=Falha ao Excluir Reversa!");
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
        processRequest(request, response);
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

    private static RetornoCancelamentoTO cancelarPedido(java.lang.String usuario, java.lang.String senha, java.lang.Integer codAdministrativo, java.lang.String numeroPedido, java.lang.String tipo) {
        br.com.correios.scol.webservice.WebServiceScol_Service service = new br.com.correios.scol.webservice.WebServiceScol_Service();
        br.com.correios.scol.webservice.WebServiceScol port = service.getWebServiceScolPort();
        return port.cancelarPedido(usuario, senha, codAdministrativo, numeroPedido, tipo);
    }

}
