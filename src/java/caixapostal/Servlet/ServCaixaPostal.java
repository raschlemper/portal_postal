package caixapostal.Servlet;

import caixapostal.componentes.SituacaoObjetoInterno;
import caixapostal.dao.CaixaPostalDao;
import caixapostal.filter.FilterObjetos;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Viviane
 */
public class ServCaixaPostal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Integer idObjeto = Integer.parseInt(request.getParameter("idObjeto"));
        String situacao = request.getParameter("situacao");
        FilterObjetos filter = new FilterObjetos();
        filter.setNomeDB(((String) request.getSession().getAttribute("empresa")));
        filter.setIdObjeto(idObjeto);
        filter.setSituacao(situacao);
        CaixaPostalDao caixaPostalDao = new CaixaPostalDao(filter.getNomeDB());

        try {
            if (filter.getSituacao().equals(SituacaoObjetoInterno.DEVOLVER.toString())) {
                caixaPostalDao.Devolver(filter);
            }
            if(filter.getSituacao().equals(SituacaoObjetoInterno.REENVIAR.toString())){
                caixaPostalDao.Reenviar(filter);
            }

            request.getSession().setAttribute("msg", "Objeto enviado com sucesso!");
            response.sendRedirect("Cliente/Servicos/objetos_caixa_postal.jsp");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            request.getSession().setAttribute("msg", "Erro ao enviar o objeto!");
            response.sendRedirect("Cliente/Servicos/objetos_caixa_postal.jsp");
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

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
