/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrClienteContrato;
import Controle.ContrLogAtualizacaoContrato;
import Entidade.Clientes;
import Entidade.Usuario;
import Entidade.empresas;
import Util.FormataString;
import Util.XTrustProvider;
import br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException;
import br.com.correios.bsb.sigep.master.bean.cliente.CartaoPostagemERP;
import br.com.correios.bsb.sigep.master.bean.cliente.ClienteERP;
import br.com.correios.bsb.sigep.master.bean.cliente.ContratoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.ServicoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author scc4
 */
public class ServAtualizaContrato extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServAtualizaContrato</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServAtualizaContrato at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            String nomeBD = (String) sessao.getAttribute("empresa");
            String nomeUsuario = (String) sessao.getAttribute("nome");
            int idUsuario = (Integer) sessao.getAttribute("idUsuario");
            int comErro = Integer.parseInt(request.getParameter("comErro"));
            boolean flagFalha = false;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            StringBuilder codOk = new StringBuilder();
            StringBuilder codFalha = new StringBuilder();
            StringBuilder servicosOk = new StringBuilder();
            StringBuilder msgFalha = new StringBuilder();

            ArrayList<Clientes> listaCliente = null;
            if (comErro == 1) {
                listaCliente = Controle.contrCliente.getClientesComErroAtualizacao(nomeBD);
            } else {
                listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD);
            }

            for (Clientes clie : listaCliente) {

                int temContrato = clie.getTemContrato();
                String numContrato = clie.getNumContrato();
                String cartaoPostagem = clie.getCartaoPostagem();

                try {
                    if (temContrato == 1
                            && clie.getLogin_sigep() != null && !clie.getLogin_sigep().trim().equals("")
                            && clie.getSenha_sigep() != null && !clie.getSenha_sigep().trim().equals("")) {

                        numContrato = Long.parseLong(numContrato) + "";
                        cartaoPostagem = Long.parseLong(cartaoPostagem) + "";
                        String cartao = FormataString.preencheCom(cartaoPostagem, "0", 10, 1);

                        XTrustProvider.install();
                        //ClienteERP cli = buscaCliente(numContrato, cartao, emp.getLogin_ws_sigep(), emp.getSenha_ws_sigep());
                        ClienteERP cli = buscaCliente(numContrato, cartao, clie.getLogin_sigep(), clie.getSenha_sigep());

                        List<ServicoERP> listaServico = null;
                        String cnpjSara = cli.getCnpj();
                        Date dtVgFim = null;
                        String dtVg = "---";
                        int statusC = 0;
                        String codAdm = "";
                        int anoContrato = 0;
                        String ufContrato = "";

                        for (ContratoERP c : cli.getContratos()) {

                            anoContrato = c.getDataVigenciaInicio().getYear();
                            dtVgFim = c.getDataVigenciaFim().toGregorianCalendar().getTime();
                            if (dtVgFim != null) {
                                dtVg = sdf.format(dtVgFim);
                            }
                            int codDir = Integer.parseInt(c.getCodigoDiretoria().trim());
                            ufContrato = FormataString.getUfDiretoria(codDir);

                            for (CartaoPostagemERP cp : c.getCartoesPostagem()) {
                                codAdm = cp.getCodigoAdministrativo();
                                statusC = Integer.parseInt(cp.getStatusCartaoPostagem().trim());
                                listaServico = cp.getServicos();
                            }
                        }

                        StringBuilder servicosAux = new StringBuilder();
                        if (statusC == 1) {
                            for (ServicoERP s : listaServico) {
                                int codECT = Integer.parseInt(s.getCodigo().trim());
                                String grupoServ = s.getDescricao();
                                servicosAux.append("@").append(codECT).append(" - ").append(grupoServ);
                            }
                        } else {
                            servicosAux.append("@SEM CONTRATO");
                        }

                        servicosOk.append(";").append(servicosAux.toString().substring(1));
                        codOk.append(";").append(clie.getCodigo()).append("@").append(clie.getNome()).append("@").append(cnpjSara).append("@").append(codAdm).append("@").append(statusC).append("@").append(dtVg).append("@").append(anoContrato).append("@").append(ufContrato);

                    }else if (temContrato == 1){
                        msgFalha.append(";Cliente sem senha do SigepWEB!");
                        codFalha.append(";").append(clie.getCodigo()).append("@").append(clie.getNome());
                    }
                } catch (SigepClienteException ex) {
                    msgFalha.append(";").append(ex.getMessage());
                    codFalha.append(";").append(clie.getCodigo()).append("@").append(clie.getNome());
                    flagFalha = true;
                } catch (AutenticacaoException ex) {
                    msgFalha.append(";").append(ex.getMessage());
                    codFalha.append(";").append(clie.getCodigo()).append("@").append(clie.getNome());
                    flagFalha = true;
                } catch (NumberFormatException ex) {
                    msgFalha.append(";").append(ex.getMessage());
                    codFalha.append(";").append(clie.getCodigo()).append("@").append(clie.getNome());
                    flagFalha = true;
                } catch (Exception ex) {
                    msgFalha.append(";").append(ex.getMessage());
                    codFalha.append(";").append(clie.getCodigo()).append("@").append(clie.getNome());
                    flagFalha = true;
                }

            }

            ContrLogAtualizacaoContrato.inserir(idUsuario, nomeUsuario, servicosOk.toString().substring(1), msgFalha.toString().substring(1), codOk.toString().substring(1), codFalha.toString().substring(1), nomeBD);

            if (flagFalha) {
                sessao.setAttribute("msg", "Contratos Atualizados!<br/>" + msgFalha);
            } else {
                sessao.setAttribute("msg", "Todos os Contratos foram Atualizados com Sucesso!");
            }
            //response.sendRedirect("Agencia/Configuracao/cliente_log_contrato.jsp");            
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

    private static ClienteERP buscaCliente(java.lang.String idContrato, java.lang.String idCartaoPostagem, java.lang.String usuario, java.lang.String senha) throws SigepClienteException, AutenticacaoException, Exception {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.buscaCliente(idContrato, idCartaoPostagem, usuario, senha);
    }
}
