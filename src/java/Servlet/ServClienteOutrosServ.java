/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrClienteContrato;
import Controle.ContrClientePrefixoAR;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Fernando
 */
public class ServClienteOutrosServ extends HttpServlet {

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

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String nomeBD = (String) sessao.getAttribute("empresa");

            /**
             * ***************************** SALVA DADOS DO CONTRATO ECT
             * ******************************
             */
            ContrClienteContrato.limparOutrosServicos(idCliente, nomeBD);
            ArrayList<Integer> listaSG = new ArrayList<Integer>();
            if (!request.getParameter("servicos").equals("")) {
                String aux[] = request.getParameter("servicos").split("@");
                for (String aux2 : aux) {
                    String[] aux1 = aux2.split(";");
                    int codECT = Integer.parseInt(aux1[0]);
                    String grupoServ = aux1[1];
                    if (!listaSG.contains(codECT)) {
                        ContrClienteContrato.insereOutrosServicos(idCliente, codECT, grupoServ, nomeBD);
                    }
                }
            }
            
            ContrClientePrefixoAR.updateClienteAR(idCliente, 0, nomeBD);
            ContrClientePrefixoAR.excluir(idCliente, nomeBD);
            if(request.getParameter("toggleBtn")!=null){
                try{
                    int ar_digital = Integer.parseInt(request.getParameter("ar_digital"));
                    ContrClientePrefixoAR.updateClienteAR(idCliente, ar_digital, nomeBD);
                    for (String srv : request.getParameterValues("habilitados")) {
                        String pref = request.getParameter("prefixo_"+srv);
                        ContrClientePrefixoAR.inserir(idCliente, srv, pref.toUpperCase().trim(), nomeBD);
                    }            
                }catch(Exception e){}            
            }            

            sessao.setAttribute("msg", "Serviços do Cliente Alterado com Sucesso!");
            //response.sendRedirect("Agencia/Configuracao/cliente_outros_serv.jsp?idCliente=" + idCliente);
            response.sendRedirect(request.getHeader("referer"));

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

}
