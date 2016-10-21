/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RICARDINHO
 */
public class ServEditarCartaoDep extends HttpServlet {

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
            out.println("<title>Servlet ServEditarCartaoDep</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServEditarCartaoDep at " + request.getContextPath() + "</h1>");
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
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                int idCli = Integer.parseInt(request.getParameter("idCliente"));
                int idDep = Integer.parseInt(request.getParameter("idDepartamento"));
                
                String nome = request.getParameter("nome").trim();                
                String cartao = request.getParameter("cartao").trim();
                
                int temEndereco = 0;
                String nomeEndereco = "";
                String logradouro = "";
                String numero = "";
                String complemento = "";
                String bairro = "";
                String cidade = "";
                String uf = "";
                String cep = "";
                if(request.getParameter("temEndereco") != null){
                    temEndereco = 1;
                    nomeEndereco = request.getParameter("nomeEndereco").trim();
                    logradouro = request.getParameter("logradouro").trim();
                    numero = request.getParameter("numero").trim();
                    complemento = request.getParameter("complemento").trim();
                    bairro = request.getParameter("bairro").trim();
                    cidade = request.getParameter("cidade").trim();
                    uf = request.getParameter("uf").trim();
                    cep = request.getParameter("cep").trim();
                }
                
                if(!cartao.equals("")){
                    try{
                        int cart = Integer.parseInt(cartao);
                        if(cart > 0){
                            cartao = cart+"";                    
                        }else{
                            cartao = "";
                        }
                    } catch (NumberFormatException ex) {
                        cartao = "";
                    }
                }

                Controle.ContrClienteDeptos.alterarDepto(nomeBD, idCli, idDep, nome, cartao, temEndereco, nomeEndereco, logradouro, numero, complemento, bairro, cidade, uf, cep);

                sessao.setAttribute("msg", "Departamento alterado com sucesso!");
                                
                response.sendRedirect(request.getHeader("referer"));

            } catch (IOException ex) {
                int idErro = ContrErroLog.inserir("Portal Postal - ServEditarUsuario", "Exception", null, ex.toString());
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
