/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.ContrClientePrefixoAR;
import Controle.contrCliente;
import Emporium.Controle.ContrArDigital;
import Emporium.Controle.ContrPreVenda;
import Entidade.Clientes;
import Entidade.PreVenda;
import br.com.correios.bsb.sigep.master.bean.cliente.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardinho
 */
public class ServArDigital extends HttpServlet {

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
        
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nomeBD = request.getParameter("nomeBD");
        String[] ids = request.getParameterValues("ids");
        String idsa = "";
        for (String id : ids) {
            idsa += "," + id;
        }
        
        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
        ArrayList<PreVenda> listaPV = ContrPreVenda.consultaVendaByIds(idsa.substring(1), nomeBD);
        Map<String,String> mapPref = ContrClientePrefixoAR.consultaMapPorCliente(idCliente, nomeBD);
        Map<String,String> mapArquivos = new HashMap<String, String>();
        
        for (int i = 0; i < listaPV.size(); i++) {
            PreVenda pv = listaPV.get(i);
            if(pv.getArquivo_ar().equals("")){
                String pref = mapPref.get(pv.getNomeServico());
                String nomeArquivo;
                if(mapArquivos.containsKey(pref)){
                    nomeArquivo = mapArquivos.get(pref);
                }else{
                    nomeArquivo = ContrArDigital.inserir(idCliente, pref, cli.getNome(), cli.getAr_digital(), nomeBD);
                    mapArquivos.put(pref, nomeArquivo);
                }
                ContrArDigital.updatePreVenda(pv.getId(), nomeArquivo, nomeBD);
            }
        }
        
        response.sendRedirect("Cliente/Servicos/ar_digital_arquivo.jsp");
        
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
