
package Emporium.Servlet;

import exportacao.telegrama.TemegramaImportacao;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = 505000)
public class ServTelegramaImportacao extends HttpServlet {
             
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        try {
            if (expira == null) {
                response.sendRedirect("index.jsp?msgLog=3");
            } else {
                Part fileImportacao = request.getPart("fileImportacao");
                String nomeBD = (String) sessao.getAttribute("nomeBD");
                realizaImportacao(nomeBD, fileImportacao);
                sessao.setAttribute("msg", "Telegrama importado com sucesso!");
                response.sendRedirect(request.getHeader("referer"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void realizaImportacao(String nomeBD, Part fileImportacao) throws IOException, SQLException {
        TemegramaImportacao importacao = new TemegramaImportacao(nomeBD);
        importacao.importar(fileImportacao.getInputStream());
    }

    
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

   
}
