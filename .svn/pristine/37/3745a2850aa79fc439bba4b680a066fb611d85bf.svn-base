/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrClienteDeptos;
import Controle.contrSenhaCliente;
import Entidade.ClientesDeptos;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ricardinho
 */
public class ServClienteLoginMassa extends HttpServlet {

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
            String nomeBD = (String) sessao.getAttribute("empresa");
            
            String ids[] = request.getParameterValues("idCliente");
            for (int i = 0; i < ids.length; i++) {
                try{
                    
                    int idCliente = Integer.parseInt(ids[i]);
                    String login = request.getParameter("login"+idCliente);
                    String senha = request.getParameter("senha"+idCliente);
                    
                    ArrayList<ClientesDeptos> lst = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
                    String deptoAcessos = "";
                    for (int j = 0; j < lst.size(); j++) {
                        if(j == 0){
                            deptoAcessos += "" + lst.get(j).getIdDepartamento();
                        }else{
                            deptoAcessos += "," + lst.get(j).getIdDepartamento();
                        }
                    }
                    
                    contrSenhaCliente.inserir(idCliente, login, senha, 1, "1;2;3;4;5;6;7", deptoAcessos, "1;2;3;4;5;6;7", nomeBD);
                    
                }catch(NumberFormatException e){                    
                }                
            }
            
            
            sessao.setAttribute("msg", "Logins criados com sucesso!");      
        }
                  
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
