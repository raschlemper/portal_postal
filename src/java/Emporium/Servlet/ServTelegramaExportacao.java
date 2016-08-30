
package Emporium.Servlet;

import exportacao.telegrama.TelegramaExportacao;
import exportacao.telegrama.TelegramaUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ServTelegramaExportacao extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            String idsTelegrama = request.getParameter("inputTelegramasMarcados");
            String nomeBD = (String) sessao.getAttribute("nomeBD");

            TelegramaExportacao exportacao = new TelegramaExportacao(nomeBD);
            exportacao.setIdsTelegrama(idsTelegrama);

            response.setContentType("application/force-download;charset=UTF-8");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition","attachment; filename=\""+TelegramaUtils.gerarNomeDoArquivoExportacao()+"\"");
            response.getWriter().print(exportacao.exportar());
            sessao.setAttribute("msg", "Telegrama exportado com sucesso!");
            
        }
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
