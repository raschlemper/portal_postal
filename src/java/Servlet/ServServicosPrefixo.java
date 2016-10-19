/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrLogAcoes;
import Entidade.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author scc4
 */
public class ServServicosPrefixo extends HttpServlet {

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
        String nomeBD = (String) sessao.getAttribute("nomeBD");
        Usuario user = (Usuario) sessao.getAttribute("agf_usuario");
        String servico = request.getParameter("servico").toUpperCase();
        int contador = Integer.parseInt(request.getParameter("contador"));

        Controle.ContrServicoPrefixo.excluirByServico(servico, nomeBD);

        for (int i = 1; i <= contador; i++) {
            if (request.getParameter("prefixo" + i) != null) {
                String prefixo = request.getParameter("prefixo" + i);
                int status = Integer.parseInt(request.getParameter("suspenso" + i));

                if (request.getParameter("avista" + i) != null) {
                    Controle.ContrServicoPrefixo.inserir(servico, "", prefixo, 1, status, nomeBD);
                    ContrLogAcoes.inserir(nomeBD, "Cadastro Prefixo Etiqueta", "CAD_EDIT", "Cadastro de Prefixo - servico = " + servico + " - prefixo = " + prefixo + " - tipo = fisica - ativo = " + status, user.getIdUsuario(), user.getNome());
                } else {
                    Controle.ContrServicoPrefixo.inserir(servico, "", prefixo, 0, status, nomeBD);
                    ContrLogAcoes.inserir(nomeBD, "Cadastro Prefixo Etiqueta", "CAD_EDIT", "Cadastro de Prefixo - servico = " + servico + " - prefixo = " + prefixo + " - tipo = logica - ativo = " + status, user.getIdUsuario(), user.getNome());
                }
            }
        }

        sessao.setAttribute("msg", "Prefixos Atualizados com Sucesso!");
        //response.sendRedirect("Agencia/Configuracao/servicos_prefixos.jsp");
        response.sendRedirect(request.getHeader("referer"));

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
