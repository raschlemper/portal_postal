/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrAmarracaoServico;
import Controle.ContrClienteContrato;
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
public class ServInserirAmarracao extends HttpServlet {

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
            out.println("<title>Servlet ServInserirAmarracao</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirAmarracao at " + request.getContextPath() + "</h1>");
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
        String nomeBD = (String) sessao.getAttribute("nomeBD");

        String nome = request.getParameter("nome");
        String sigla = request.getParameter("sigla").toUpperCase();
        String nomeCT = request.getParameter("nomeCTE").toUpperCase();
        String cepCT = request.getParameter("cepCTE").toUpperCase();
        int contador = Integer.parseInt(request.getParameter("contador"));

        int idAmarracao = Controle.ContrAmarracao.inserir(nome, sigla, cepCT, nomeCT, nomeBD);
        
        String servicos = "";
        if (!request.getParameter("servicos").equals("")) {
            String aux[] = request.getParameter("servicos").split(";");
            for (int i = 0; i < aux.length; i++) {
                String serv = aux[i];
                ContrAmarracaoServico.inserir(idAmarracao, serv, nomeBD);
                if(i == 0){
                    servicos += "'"+serv+"'";
                }else{
                    servicos += ", '"+serv+"'";
                }
            }
        }

        String sqlAmar = ContrAmarracaoServico.consultaIdAmarracaoByServico(idAmarracao, servicos, nomeBD);

        for (int i = 1; i <= contador; i++) {
            if (request.getParameter("cepIni" + i) != null && request.getParameter("cepFim" + i) != null) {
                int cepInicial = Integer.parseInt(request.getParameter("cepIni" + i).replaceAll("-", ""));
                int cepFinal = Integer.parseInt(request.getParameter("cepFim" + i).replaceAll("-", ""));
                if (!Controle.ContrAmarracaoCep.verificaExistenciaFaixaCep(cepInicial, cepFinal, 0, sqlAmar, nomeBD)) {
                    Controle.ContrAmarracaoCep.inserir(idAmarracao, cepInicial, cepFinal, nomeBD);
                }
            }
        }

        sessao.setAttribute("msg", "Amarração Inserida com Sucesso!");
        //response.sendRedirect("Agencia/Configuracao/amarracao_lista.jsp");
        
        sessao.setAttribute("msg", "Amarração Alterada com Sucesso!");
        if(request.getHeader("referer").contains("NewTemplate")){
            response.sendRedirect("NewTemplate/Cadastros/amarracao_lista_b.jsp");
        }else{
            response.sendRedirect("Agencia/Configuracao/amarracao_lista.jsp");            
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
