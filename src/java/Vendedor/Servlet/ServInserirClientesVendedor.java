/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vendedor.Servlet;

import Controle.ContrErroLog;
import Vendedor.Controle.contrVendedores;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ServInserirClientesVendedor extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServInserirClientesVendedor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirClientesVendedor at " + request.getContextPath() + "</h1>");
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
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            try {
                String nomeBD = (String) sessao.getAttribute("empresa");
                //contador de itens na tabela da clientes
                int cont = Integer.parseInt(request.getParameter("contador"));
                //id do vendedor
                int idVendedor = Integer.parseInt(request.getParameter("idVendedor"));

                ArrayList<String> list_idCli_percent = new ArrayList<>();
                ArrayList<String> list_idCliente = new ArrayList<>();
                boolean flag = true;
                //for que percorre a quantidade de itens que contem na tabela da clientes e verifica se tem algum duplicado
                for (int i = 0; i < cont; i++) {
                    String auxIdCliente = request.getParameter("cliente" + i);
                    String percentual = request.getParameter("percent" + i);
                    if (!list_idCli_percent.contains(auxIdCliente+";"+percentual)) {
                        System.out.println("## "+auxIdCliente+";"+percentual);
                        list_idCli_percent.add(auxIdCliente+";"+percentual);
                    } else {
                        flag = false;
                    }
                }

                // Deleta as lista antiga 
                contrVendedores.deletarListaCliente(idVendedor, nomeBD);
                //for que percorre a quantidade de itens que contem na tabela da clientes insere no banco
                for (int i = 0; i < list_idCli_percent.size(); i++) {
                    String vIdCliente = list_idCli_percent.get(i).split(";")[0];
                    if (vIdCliente != null && !vIdCliente.equals("")) {
                        int idCliente = Integer.parseInt(vIdCliente);
                        String percentual = list_idCli_percent.get(i).split(";")[1];
                        if (percentual.trim().equals("")) {
                            percentual = "0";
                        }
                        //Insere lista atual sem os duplicados                     
                        contrVendedores.inserirClientesLista(idVendedor, idCliente, percentual, nomeBD);
                    }
                }
                if (flag) {
                    sessao.setAttribute("msg", "Lista clientes salva com sucesso!");
                } else {
                    sessao.setAttribute("msg", "<b>Erro !!!</b><br/>Havia(m) Cliente(s) duplicado(s) na lista. <br/> Cliente(s)duplicado(s) excluido(s)!");
                }

                //response.sendRedirect("Agencia/Coleta/coletador_lista.jsp");
                response.sendRedirect(request.getHeader("referer"));

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServInserirCleintesVendedor", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Coleta/coletador_lista.jsp");
                response.sendRedirect(request.getHeader("referer"));
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
