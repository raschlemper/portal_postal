package Emporium.Servlet;

import com.google.gson.Gson;
import com.portal.componentes.nfe.CarregaDadosNFE;
import com.portal.componentes.nfe.DadosNFE;
import com.portal.componentes.nfe.LeitorConteudo;
import com.portal.componentes.nfe.ParametrosFormularioNFE;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServDadosNFE extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            PrintWriter out = response.getWriter();
            String captcha = request.getParameter("captcha");
            String keyNF = request.getParameter("keyNF");
            ParametrosFormularioNFE parametro = (ParametrosFormularioNFE) request.getSession().getAttribute("PARAMETRONFE");
            DadosNFE dados = new LeitorConteudo().ler( new CarregaDadosNFE(parametro).enviar(captcha, keyNF));
            String json = new Gson().toJson(dados);
            out.print("%js carregaResultado('"+json+"'); js%");
            
        } catch (Exception ex) {
            ex.printStackTrace();
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
