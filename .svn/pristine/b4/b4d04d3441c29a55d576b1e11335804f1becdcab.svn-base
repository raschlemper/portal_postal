/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Servlet;

import Emporium.Controle.ContrTelegramaPostal;
import Entidade.Endereco;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fernando
 */
public class ServTelegramaNovo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nomeBD = request.getParameter("nomeBD");
        String nomeUser = request.getParameter("nomeUser");
        
        String nomeRem = request.getParameter("nomeCli");
        String enderecoRem = request.getParameter("enderecoCli");
        String numeroRem = request.getParameter("numeroCli");
        String complementoRem = request.getParameter("complementoCli");
        String bairroRem = request.getParameter("bairroCli");
        String cidadeRem = request.getParameter("cidadeCli");
        String ufRem = request.getParameter("ufCli");
        String cepRem = request.getParameter("cepCli");
        Endereco endRem = new Endereco(nomeRem, enderecoRem, numeroRem, complementoRem, bairroRem, cidadeRem, ufRem, cepRem);
        
        String nomeDes = request.getParameter("nome");
        String enderecoDes = request.getParameter("endereco");
        String numeroDes = request.getParameter("numero");
        String complementoDes = request.getParameter("complemento");
        String bairroDes = request.getParameter("bairro");
        String cidadeDes = request.getParameter("cidade");
        String ufDes = request.getParameter("uf");
        String cepDes = request.getParameter("cep");
        Endereco endDes = new Endereco(nomeDes, enderecoDes, numeroDes, complementoDes, bairroDes, cidadeDes, ufDes, cepDes);
        
        String mensagem = request.getParameter("obs");        
        String dep = request.getParameter("departamento");        
        int idDepartamento = 0;
        if(!dep.equals("")){
            String aux[] = dep.split(";");
            dep = aux[1];
            idDepartamento = Integer.parseInt(aux[0]);
        }
        
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int envio = Integer.parseInt(request.getParameter("ckEnvio"));
        String dataAgendado = sdf.format(new Date());
        if(envio == 0){
            String dt = request.getParameter("dataColeta");
            String aux[] = dt.split("/");
            String hr = request.getParameter("horaColeta");
            dataAgendado = aux[2] + "-" + aux[1] + "-" + aux[0] + " " + hr;
        }
        
        String adicionais = "";
        String tipoRet = "";
        if(request.getParameter("ar") != null){
            adicionais += request.getParameter("ar");
        }
        String email = "";
        if(request.getParameter("copiaMsg") != null){
            adicionais += request.getParameter("copiaMsg");
            tipoRet = request.getParameter("tipoRet");
            email = request.getParameter("email");
        }
        
        ContrTelegramaPostal.inserir(idCliente, idDepartamento, dep, envio, dataAgendado, adicionais, tipoRet, mensagem, endRem, endDes, nomeUser, email, nomeBD);
        
        response.sendRedirect("Cliente/Servicos/telegrama_postal.jsp?msg=Telegrama solicitado com sucesso!");
                    
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
