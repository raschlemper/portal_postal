/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

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
 * @author RICARDINHO
 */
public class ServClienteLatLng extends HttpServlet {

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
            out.println("<title>Servlet ServClienteLatLng</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServClienteLatLng at " + request.getContextPath() + "</h1>");
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
            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            double latitude = Double.parseDouble(request.getParameter("lat"));
            double longitude = Double.parseDouble(request.getParameter("lng"));

            contrCliente.alterarLatitudeLongitude(latitude, longitude, idCliente, nomeBD);
            
            int idGrupoFat = Integer.parseInt(request.getParameter("grupo_fat"));
            String nome = request.getParameter("razao");
            String fantasia = request.getParameter("fantasia");
            String cnpj = request.getParameter("cnpj").replace("-", "").replace(".", "").replace("/", "").trim();
            String email = request.getParameter("email");
            String telefone = request.getParameter("telefone");
            String cep = request.getParameter("cep").replace("-", "").replace(".", "").trim();
            String endereco = request.getParameter("logradouro");
            String numero = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String uf = request.getParameter("uf");
            String obs = request.getParameter("obs");
            
            
            contrCliente.alterarCliente(nome, fantasia, endereco, numero, complemento, bairro, cidade, uf, cep, telefone, email, cnpj, latitude, longitude, idGrupoFat, idCliente, obs, nomeBD);
            contrCliente.sincronizarDadosComFavorecidos(idCliente, nome, nomeBD);

            //response.sendRedirect("Agencia/Configuracao/cliente_lista.jsp?msg=Latitude e Longitude alterados com sucesso!");
            
            sessao.setAttribute("msg", "Latitude e Longitude alterados com sucesso!");                
            response.sendRedirect(request.getHeader("referer"));
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
