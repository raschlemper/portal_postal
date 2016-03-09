/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Servlet;

import Controle.contrCliente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RICADINHO
 */
public class ServEditarCadastro extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        HttpSession sessao = request.getSession();
        String nomeBD = (String) sessao.getAttribute("empresa");
        System.out.println("chego");
        //VERIFICA SE ESTA LOGADO NA SESSAO
        if (nomeBD == null) {
        System.out.println("chego1");
            response.sendRedirect("index.jsp?msgLog=3");
        } else {      
        System.out.println("chego2");
            //PEGA OS PARAMETROS DO FORM HTML
            int nome_etq = Integer.parseInt(request.getParameter("nome_etq"));
        System.out.println("chego3" +  nome_etq);
            int idCli = Integer.parseInt(request.getParameter("idCliente"));
        System.out.println("chego4" + idCli);
            String url_logo = request.getParameter("logo_img_url");            
        System.out.println("chego5" + url_logo);
            //ALTERA URL NO BANCO
            contrCliente.alterarLogo(url_logo, idCli, nomeBD);
        System.out.println("chego5");
            contrCliente.editarNomeEtq(idCli, nome_etq, nomeBD);
        System.out.println("chego6");
            response.sendRedirect("Cliente/Cadastros/cadastro.jsp?msg=Cadastro Atualizado!");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
