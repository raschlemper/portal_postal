/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrServicoPrefixo;
import Controle.contrCliente;
import Entidade.Clientes;
import Util.CalculoEtiqueta;
import Util.FormataString;
import Util.XTrustProvider;
import br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
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
public class ServInserirFaixaEtiquetaWS extends HttpServlet {

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
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServInserirFaixaEtiquetaWS</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirFaixaEtiquetaWS at " + request.getContextPath() + "</h1>");
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
            int idUsuario = (Integer) sessao.getAttribute("idUsuario");
            String nomeUsuario = (String) sessao.getAttribute("nome");
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            int uso = Integer.parseInt(request.getParameter("uso"));
            String cpnj = request.getParameter("cnpj");

            Clientes c = contrCliente.consultaClienteById(idCliente, nomeBD);

            try {

                String aux[] = request.getParameter("servico").split(";");
                int servico = Integer.parseInt(aux[0]);
                String grupoServ = aux[1];
                int idServico = Integer.parseInt(aux[2]);

                int qtd = Integer.parseInt(request.getParameter("qtd"));

                String cartao = "";//request.getParameter("cartao");

                if (!c.getLogin_sigep().equals("") && !c.getSenha_sigep().equals("")) {
                    String login = c.getLogin_sigep();
                    String senha = c.getSenha_sigep();

                    String tipoUso = "PARA PORTAL POSTAL";
                    if (uso == 1) {
                        tipoUso = "FORNECIDA PARA CLIENTE";
                    }

                    if (!cpnj.equals("")) {
                        cpnj = cpnj.replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "").replaceAll(" ", "");
                        if (FormataString.isCNPJ(cpnj)) {

                            XTrustProvider.install();
                            
                            String a = solicitaEtiquetas("C", cpnj, new Long(idServico), qtd, login, senha);
                            
                            if (a != null && !a.equals("")) {
                                if (a != null && a.contains(",") && a.trim().length() == 27) {
                                    String prefixo = a.substring(0, 2);
                                    String inicial = a.substring(2, 10);
                                    String fim = a.substring(16, 24);

                                    String dgInicial = Util.CalculoEtiqueta.calculaDigito(Integer.parseInt(inicial));
                                    String dgFim = Util.CalculoEtiqueta.calculaDigito(Integer.parseInt(fim));

                                    String finicial = prefixo + inicial + dgInicial + "BR";
                                    String ffim = prefixo + fim + dgFim + "BR";

                                    //INSERE LOG DE ETIQUETAS
                                    int idImportacao = Controle.ContrClienteEtiquetas.insereLog(finicial, ffim, idCliente, idUsuario, nomeUsuario, qtd, grupoServ, "SigepWEB", tipoUso, nomeBD);

                                    //INSERE FAIXAS NO BANCO
                                    int nIni = Integer.parseInt(inicial);
                                    int nFim = Integer.parseInt(fim);
                                    String sql = "INSERT INTO cliente_etiquetas (seqLogica, idImportacao, idCliente, codECT, grupoServico, cartaoPostagem, utilizada) VALUES";
                                    for (int num = nIni; num <= nFim; num++) {
                                        String etiqueta = prefixo + "" + CalculoEtiqueta.concertaTamanhoNum(num) + "" + CalculoEtiqueta.calculaDigito(num) + "BR";
                                        if (num == nFim) {
                                            sql += " ('" + etiqueta + "', " + idImportacao + ", " + idCliente + ", " + servico + ", '" + grupoServ + "', '" + cartao + "', " + uso + ");";
                                        } else {
                                            sql += " ('" + etiqueta + "', " + idImportacao + ", " + idCliente + ", " + servico + ", '" + grupoServ + "', '" + cartao + "', " + uso + "),";
                                        }
                                    }
                                    
                                    //INSERE ETIQUETAS NO BANCO DE DADOS PARA O CLIENTE
                                    Controle.ContrClienteEtiquetas.insereEtiquetas(sql, nomeBD);
                                    //EXCLUIR O PREFIXO SOLICITADO CASO EXISTA
                                    ContrServicoPrefixo.excluirByPrefixo(prefixo, nomeBD);
                                    //INSERE ESTE PREFIXO COMO PREFIXO DE SEQ. LOGICA
                                    Controle.ContrServicoPrefixo.inserir(grupoServ, "", prefixo, 0, 1, nomeBD);
                                    
                                    sessao.setAttribute("msg", "Faixa de Etiqueta inserida com sucesso!");

                                } else {
                                    sessao.setAttribute("msg", "Falha ao Solicitar Etiquetas!");
                                }
                            } else {
                                sessao.setAttribute("msg", "Verifique se o CNPJ está válido no SARA!");
                            }

                        } else {
                            sessao.setAttribute("msg", cpnj + " - CNPJ Inválido!");
                        }
                    } else {
                        sessao.setAttribute("msg", "Preencha todos os campos !");
                    }
                } else {
                    sessao.setAttribute("msg", "Este cliente não possui uma senha do SigepWEB!<br/><br/>Favor solicite e cadastre uma senha para o cliente!");
                }

            } catch (AutenticacaoException ex) {
                sessao.setAttribute("msg", "Falha ao Solicitar Etiquetas:<br/>" + ex.getMessage() + "<br/><br/>Consulte este CNPJ " + cpnj + " no SARA!<br/>E verifique se o cadastro do SECT está correto!");
            } catch (SigepClienteException ex) {
                sessao.setAttribute("msg", "Falha ao Solicitar Etiquetas:<br/>" + ex.getMessage() + "<br/><br/>Consulte este CNPJ " + cpnj + " no SARA!<br/>E verifique se o cadastro do SECT está correto!");
            }

            //response.sendRedirect("Agencia/Configuracao/cliente_etiquetas.jsp?idCliente=" + idCliente);
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

    private static String solicitaEtiquetas(java.lang.String tipoDestinatario, java.lang.String identificador, java.lang.Long idServico, java.lang.Integer qtdEtiquetas, java.lang.String usuario, java.lang.String senha) throws AutenticacaoException, SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.solicitaEtiquetas(tipoDestinatario, identificador, idServico, qtdEtiquetas, usuario, senha);
    }
}
