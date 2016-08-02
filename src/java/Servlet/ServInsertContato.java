/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import static Controle.contrContato.deletar;
import Entidade.empresas;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Fernando
 */
public class ServInsertContato extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServInsertContato</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInsertContato at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        empresas agf_empresa = (empresas) sessao.getAttribute("agf_empresa");
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            try {

                String nome = request.getParameter("nome");
                 System.out.println(nome);
                String email = request.getParameter("email");
                 System.out.println(email);
                String telefone = request.getParameter("telefone");
                 System.out.println(telefone);
                String setor = request.getParameter("setor");
                 System.out.println(setor);
                String aniversario = request.getParameter("aniversario");
                System.out.println(aniversario);
                aniversario = Util.FormatarData.DateToBD(aniversario);
                 System.out.println(aniversario +" F");
                String site = request.getParameter("site");
                System.out.println(site);
                String observ = request.getParameter("observ");
                 System.out.println(observ);
                
                int idCli = Integer.parseInt(request.getParameter("idCli"));
                 System.out.println(idCli);
                int nivel = Integer.parseInt(request.getParameter("nivel"));
                 System.out.println(nivel);
                System.out.println(idCli+" - "+nome+" - "+ email+" - "+ telefone+" - "+ setor+" - "+ aniversario+" - "+ site+" - "+ observ+" - "+ nivel+" - "+ agf_empresa.getCnpj());
                Controle.contrContato.inserirContato(idCli, nome, email, telefone, setor, aniversario, site, observ, nivel, agf_empresa.getCnpj());
                
                sessao.setAttribute("msg", "Contato incluido com sucesso!");

                response.sendRedirect(request.getHeader("referer"));
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("Portal Postal - ServInsertContato", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                response.sendRedirect(request.getHeader("referer"));
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
