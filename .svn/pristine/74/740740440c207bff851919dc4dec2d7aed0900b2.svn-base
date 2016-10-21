package caixapostal.Servlet;

import caixapostal.dao.CaixaPostalDao;
import caixapostal.entity.DadosAdicionais;
import caixapostal.filter.FilterObjetos;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServAlteracaoEndereco extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Integer idObjetoInterno = Integer.parseInt(request.getParameter("idObjetoInterno"));
        FilterObjetos filter = new FilterObjetos();
        filter.setIdObjeto(idObjetoInterno);
        filter.setNomeDB(((String) request.getSession().getAttribute("empresa")));

        
        DadosAdicionais novoEndereco = montaNovoEndereco(request);
        
        try {
            CaixaPostalDao caixaPostalDao = new CaixaPostalDao(filter.getNomeDB());
            caixaPostalDao.alterarEnderecoDeEntrega(filter, novoEndereco);
            request.getSession().setAttribute("msg", "Endereço alterado com sucesso");
            response.sendRedirect("Cliente/Servicos/objetos_caixa_postal.jsp");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            request.getSession().setAttribute("msg", "Erro ao alterar o endereço.");
        }
    }

    private DadosAdicionais montaNovoEndereco(HttpServletRequest request) {
        DadosAdicionais novoEndereco = new DadosAdicionais();
        novoEndereco.setCep(request.getParameter("cep"));
        novoEndereco.setCidade(request.getParameter("cidade"));
        novoEndereco.setBairro(request.getParameter("bairro"));
        novoEndereco.setUf(request.getParameter("uf"));
        novoEndereco.setLogradouro(request.getParameter("endereco"));
        novoEndereco.setNumero(request.getParameter("numero"));
        novoEndereco.setComplemento(request.getParameter("complemento"));
        return novoEndereco;
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
