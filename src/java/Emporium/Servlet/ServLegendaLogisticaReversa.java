/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Emporium.Controle.ContrLogisticaReversa;
import Entidade.LegendaLogisticaReversa;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Viviane
 */
public class ServLegendaLogisticaReversa extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nomeBD = (String) request.getSession().getAttribute("empresa");
        
        LegendaLogisticaReversa starWhite = new LegendaLogisticaReversa();
        starWhite.setId(1);
        starWhite.setNome(getParameter(request,"starWhite"));
        LegendaLogisticaReversa starBlue = new LegendaLogisticaReversa();
        starBlue.setId(2);
        starBlue.setNome(getParameter(request,"starBlue"));
        LegendaLogisticaReversa starGrey = new LegendaLogisticaReversa();
        starGrey.setId(3);
        starGrey.setNome(getParameter(request,"starGrey"));
        LegendaLogisticaReversa starRed = new LegendaLogisticaReversa();
        starRed.setId(4);
        starRed.setNome(getParameter(request,"starRed"));
        LegendaLogisticaReversa starYellow = new LegendaLogisticaReversa();
        starYellow.setId(5);
        starYellow.setNome(getParameter(request,"starYellow"));

        List<LegendaLogisticaReversa> legendas = new ArrayList<LegendaLogisticaReversa>(Arrays.asList(starWhite,starBlue,starGrey,starRed,starYellow));


        ContrLogisticaReversa dao = new ContrLogisticaReversa();
        PrintWriter out = response.getWriter();
     
        try{
            dao.atualizaLegenda(nomeBD, legendas);            
            out.println("%js alert('Alteração realizada com sucesso!'); js%");
            
        }catch(Exception ex){
            ex.printStackTrace();
            out.println("%js alert('Falha ao salvar os dados!'); js%");
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


    private String getParameter(HttpServletRequest request,String name) throws UnsupportedEncodingException{
        return new String(request.getParameter(name).getBytes(),"UTF-8");
    }
}
