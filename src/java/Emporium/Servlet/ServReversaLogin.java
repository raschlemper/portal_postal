/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Servlet;

import Controle.contrCliente;
//import br.com.correios.logisticareversa.service.ComponenteException;
//import br.com.correios.logisticareversa.service.RetornoFaixaNumerica;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ricardo
 */
public class ServReversaLogin extends HttpServlet {

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
        
//            HttpSession sessao = request.getSession();
//            String codAdm = request.getParameter("codAdm").trim();
//            String contrato = request.getParameter("contratoEct").trim();
//            
//            final String usuario = request.getParameter("login").trim();
//            final String senha = request.getParameter("senha").trim();
//            
//            java.net.Authenticator.setDefault(new java.net.Authenticator() {
//                @Override
//                protected java.net.PasswordAuthentication getPasswordAuthentication() {
//                    return new java.net.PasswordAuthentication(usuario, senha.toCharArray());
//                }
//            });
//            
//            String cartao = request.getParameter("cartao").trim();
//            String nomeBD = request.getParameter("nomeBD");
//            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
//            String tipo_ap = "AP";
//          
//        try {
//            //SOLICITA NUMERO PARA SOLICITACAO DE REVERSA
//            RetornoFaixaNumerica ret = solicitarRange(codAdm, tipo_ap, "", "1");
//            
//            if(ret.getCodErro().equals("0")){
//                contrCliente.alterarLoginReversa(usuario, senha, cartao, idCliente, nomeBD);
//                sessao.setAttribute("msg", ret.getMsgErro());
//                response.sendRedirect("Cliente/Servicos/logistica_reversa.jsp");
//            }else{
//                sessao.setAttribute("msg", ret.getMsgErro());
//                response.sendRedirect("Cliente/Servicos/logistica_reversa.jsp");
//            }
//        } catch (ComponenteException ex) {
//                sessao.setAttribute("msg", ex.getMessage());
//                response.sendRedirect("Cliente/Servicos/logistica_reversa.jsp");
//        }
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

//    private static RetornoFaixaNumerica solicitarRange(java.lang.String codAdministrativo, java.lang.String tipo, java.lang.String servico, java.lang.String quantidade) throws ComponenteException {
//        br.com.correios.logisticareversa.service.LogisticaReversaService service = new br.com.correios.logisticareversa.service.LogisticaReversaService();
//        br.com.correios.logisticareversa.service.LogisticaReversaWS port = service.getLogisticaReversaWSPort();
//        return port.solicitarRange(codAdministrativo, tipo, servico, quantidade);
//    }


    

}
