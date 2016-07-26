/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrClienteContrato;
import Controle.contrCliente;
import br.com.correios.scol.webservice.RetornoFaixaNumericaTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RICARDINHO
 */
public class ServClienteContrato extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServClienteContrato</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServClienteContrato at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            int temContrato = Integer.parseInt(request.getParameter("contrato"));
            String nomeContrato = request.getParameter("nomeContrato").toUpperCase();
            String numContrato = request.getParameter("numContrato");
            String cartaoPostagem = request.getParameter("cartaoPostagem");
            int anoContrato = Integer.parseInt(request.getParameter("anoContrato"));
            String ufContrato = request.getParameter("ufContrato");
            String nomeSara = request.getParameter("nomeSara");
            String cnpjSenha = request.getParameter("cnpjSara");

            String data = request.getParameter("vigenciaFim");
            Date dtVgFim = new Date();
            try {
                dtVgFim = Util.FormatarData.formataData(data);
            } catch (Exception ex) {
                Logger.getLogger(ServClienteContrato.class.getName()).log(Level.SEVERE, null, ex);
            }
            int codDir = Integer.parseInt(request.getParameter("codDir"));
            int statusC = Integer.parseInt(request.getParameter("statusCartao"));
            String codAdm = request.getParameter("codAdm").trim();
            String msg = "";

            if (temContrato == 1) {

                try {
                    numContrato = Long.parseLong(numContrato) + "";
                } catch (Exception e) {
                    numContrato = "";
                }
                try {
                    cartaoPostagem = Long.parseLong(cartaoPostagem) + "";
                } catch (Exception e) {
                    cartaoPostagem = "";
                }
            } else {
                numContrato = "";
                cartaoPostagem = "";
            }

            /**
             * ***************************** SALVA DADOS DO CONTRATO ECT ******************************
             */
            ContrClienteContrato.alterarTemContrato(temContrato, nomeContrato, numContrato, anoContrato, ufContrato, idCliente, cartaoPostagem, codAdm, dtVgFim, codDir, statusC, nomeSara, codAdm, cnpjSenha, nomeBD);

            ContrClienteContrato.limparContrato(idCliente, nomeBD);
            if (temContrato == 1) {
                ArrayList<Integer> listaSG = new ArrayList<Integer>();
                if (!request.getParameter("servicos").equals("")) {
                    String aux[] = request.getParameter("servicos").split("@");
                    if (aux.length > 0) {
                        for (int i = 0; i < aux.length; i++) {
                            String aux1[] = aux[i].split(";");
                            int codECT = Integer.parseInt(aux1[0]);
                            String grupoServ = aux1[1];
                            if (!listaSG.contains(codECT)) {
                                ContrClienteContrato.insereContrato(idCliente, codECT, grupoServ, nomeBD);
                            }
                        }
                    }
                }
            }

            /**
             * ***************************** SALVA SENHA SIGEP WEB ***********************************
             */
            
            String login_sigep = request.getParameter("login_sigep").trim();
            String senha_sigep = request.getParameter("senha_sigep").trim();
            if(!login_sigep.equals("") && !senha_sigep.equals("")){            
                ContrClienteContrato.alterarLoginSigep(idCliente, login_sigep, senha_sigep, nomeBD);
            }

            /**
             * ****************************** SALVA SENHA LOGISTICA REVERSA ***************************
             */
            
            String login_reversa = request.getParameter("login_reversa").trim();
            String senha_reversa = request.getParameter("senha_reversa").trim();
            String cartao_reversa = request.getParameter("cartao_reversa").trim();

            if(!login_reversa.equals("") && !senha_reversa.equals("") && !cartao_reversa.equals("")){
                //SOLICITA NUMERO PARA SOLICITACAO DE REVERSA        
                RetornoFaixaNumericaTO ret = solicitarRange(login_reversa, senha_reversa, Integer.parseInt(codAdm), numContrato, "AP", "", 1);
                if (ret.getCodErro().equals("0")) {
                    contrCliente.alterarLoginReversa(login_reversa, senha_reversa, cartao_reversa, idCliente, nomeBD);
                } else {
                    msg += "<br/><br/>Não foi possivel salvar o login da reversa:<br/>"+ret.getMsgErro();                
                }
            }

            /**
             * *****************************************************************
             */
            sessao.setAttribute("msg", "Contrato do Cliente Alterado com Sucesso!" + msg);
            //response.sendRedirect("Agencia/Configuracao/cliente_contrato.jsp?idCliente=" + idCliente);             
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

    /*private static RetornoFaixaNumericaTO solicitarRange(java.lang.String usuario, java.lang.String senha, java.lang.Integer codAdministrativo, java.lang.Long contrato, java.lang.String tipo, java.lang.String servico, java.lang.Integer quantidade) {
        br.com.correios.scol.webservice.WebServiceScol_Service service = new br.com.correios.scol.webservice.WebServiceScol_Service();
        br.com.correios.scol.webservice.WebServiceScol port = service.getWebServiceScolPort();
        return port.solicitarRange(usuario, senha, codAdministrativo, contrato, tipo, servico, quantidade);
    }*/

    private static RetornoFaixaNumericaTO solicitarRange(java.lang.String usuario, java.lang.String senha, java.lang.Integer codAdministrativo, java.lang.String contrato, java.lang.String tipo, java.lang.String servico, java.lang.Integer quantidade) {
        br.com.correios.scol.webservice.WebServiceScol_Service service = new br.com.correios.scol.webservice.WebServiceScol_Service();
        br.com.correios.scol.webservice.WebServiceScol port = service.getWebServiceScolPort();
        return port.solicitarRange(usuario, senha, codAdministrativo, new Long(contrato), tipo, servico, quantidade); 
    }

}
