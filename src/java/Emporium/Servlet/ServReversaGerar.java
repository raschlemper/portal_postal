/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Emporium.Controle.ContrLogisticaReversa;
import Util.FormataString;
import br.com.correios.scol.webservice.ColetaReversaTO;
import br.com.correios.scol.webservice.ObjetoTO;
import br.com.correios.scol.webservice.PessoaTO;
import br.com.correios.scol.webservice.ProdutoTO;
import br.com.correios.scol.webservice.RemetenteTO;
import br.com.correios.scol.webservice.ResultadoSolicitacaoTO;
import br.com.correios.scol.webservice.RetornoDigitoVerificadorTO;
import br.com.correios.scol.webservice.RetornoFaixaNumericaTO;
import br.com.correios.scol.webservice.RetornoPostagemTO;
import java.io.IOException;
import java.util.ArrayList;
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
public class ServReversaGerar extends HttpServlet {

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

        HttpSession sessao = request.getSession();

        String usuario = request.getParameter("login");
        String senha = request.getParameter("senha");

        String codServ = request.getParameter("servico_1").trim();//"41076";
        String codAdm = request.getParameter("codAdm").trim();
        String contrato = request.getParameter("contratoEct").trim();
        String cartao = request.getParameter("cartaoPostagem").trim();
        String validade = request.getParameter("dataAgendamento").trim();
        String caixa = request.getParameter("caixa").trim();
        String[] prod = null;
        if (!caixa.equals("0")) {
            prod = caixa.split(";");
        }

        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int qtdObjetos = Integer.parseInt(request.getParameter("qtdObjetos"));
        if (qtdObjetos <= 0) {
            qtdObjetos = 1;
        }
        String nomeBD = request.getParameter("nomeBD");
        String nomeUser = request.getParameter("nomeUser");
        String email_remetente = request.getParameter("email_destinatario");
        String celular_remetente = request.getParameter("celular");
        String nome_remetente = FormataString.removeSpecialChars(request.getParameter("nome"));
        String cep_remetente = request.getParameter("cep").trim().replace("-", "").replace(".", "");
        String endereco_remetente = request.getParameter("endereco");
        String numero_remetente = request.getParameter("numero");
        if (request.getParameter("sn") != null) {
            numero_remetente = "S/N";
        }
        String complemento_remetente = request.getParameter("complemento");
        String bairro_remetente = request.getParameter("bairro");
        String cidade_remetente = request.getParameter("cidade");
        String uf_remetente = request.getParameter("uf");

        String nome_destinatario = FormataString.removeSpecialChars(request.getParameter("nomeCli"));
        String cep_destinatario = request.getParameter("cepCli").trim().replace("-", "").replace(".", "");
        String endereco_destinatario = request.getParameter("enderecoCli");
        String numero_destinatario = request.getParameter("numeroCli");
        if (request.getParameter("sn2") != null) {
            numero_destinatario = "S/N";
        }
        String complemento_destinatario = request.getParameter("complementoCli");
        String bairro_destinatario = request.getParameter("bairroCli");
        String cidade_destinatario = request.getParameter("cidadeCli");
        String uf_destinatario = request.getParameter("ufCli");
        String cklist = request.getParameter("cklist");

        /*
         AP = Autorização Postagem
         LR = Logistica Reversa 
         LE - Logistica Reversa PAC 
         LS - Logistiva Reversa Sedex
         LV - Logistiva Reversa e-Sedex
         */
        String tipo_ap = "AP";
        //SOLICITA NUMERO PARA SOLICITACAO DE REVERSA
        RetornoFaixaNumericaTO range = solicitarRange(usuario, senha, Integer.parseInt(codAdm), new Long(contrato), tipo_ap, "", 1);
        if (range.getCodErro().equals("0")) {

            //CALCULA O DIGITO VERIFICADOR DO NUMERO SOLICITADO
            RetornoDigitoVerificadorTO dv = calcularDigitoVerificador(usuario, senha, Integer.parseInt(codAdm), range.getFaixaFinal());
            if (dv.getCodErro().equals("0")) {

                //DEFINE DESTINATARIO DA REVERSA
                PessoaTO d = new PessoaTO();
                d.setNome(nome_destinatario);
                d.setLogradouro(endereco_destinatario);
                d.setNumero(numero_destinatario);
                d.setComplemento(complemento_destinatario);
                d.setBairro(bairro_destinatario);
                d.setCidade(cidade_destinatario);
                d.setUf(uf_destinatario);
                d.setCep(cep_destinatario);
                //d.setEmail("");

                //DEFINE TIPO DA SOLICITACAO E O NUMERO DA AUTORIZACAO DE POSTAGEM
                List<ColetaReversaTO> lista = new ArrayList<ColetaReversaTO>();
                ColetaReversaTO c = new ColetaReversaTO();
                /*
                 CA = Coleta domiciliar. Caso não exista coleta domiciliar na localidade o sistema transforma automaticamente o pedido em uma autorização de postagem.
                 C = Coleta domiciliária. Caso não exista a coleta no local indicado, o sistema ignora a solicitação
                 A = Autorização de Postagem
                 */
                String tipo_serv = "A";
                c.setTipo(tipo_serv);
                c.setNumero(dv.getNumero());//SOMENTE PARA OS TIPOS 'A', 'AC'
                float vd = Float.parseFloat(request.getParameter("vd"));
                if (vd > 0) {
                    if (vd < 12) {
                        vd = 12;
                    }
                    c.setValorDeclarado(vd + "");
                }
                int ar = Integer.parseInt(request.getParameter("ar"));
                c.setAg(validade);
                c.setAr(ar);
                if(!cklist.equals("0")){
                     c.setCklist(cklist);
                     if(cklist.equals("5")){
                        String[] pedidos = request.getParameterValues("ckPedido");
                        String adicional = "";
                        for (int i = 0; i < pedidos.length; i++) {
                            adicional += ","+pedidos[i];                             
                        }
                        if(!adicional.equals("")){
                            adicional = adicional.substring(1);
                        }
                        c.setServicoAdicional(adicional);
                     }
                }
               

                //DEFINE O REMETENTE DA REVERSA
                RemetenteTO rem = new RemetenteTO();
                rem.setNome(nome_remetente);
                rem.setLogradouro(endereco_remetente);
                rem.setNumero(numero_remetente);
                rem.setComplemento(complemento_remetente);
                rem.setBairro(bairro_remetente);
                rem.setCidade(cidade_remetente);
                rem.setUf(uf_remetente);
                rem.setEmail(email_remetente);
                rem.setCep(cep_remetente);
                //rem.setDddCelular("48");
                //rem.setCelular("88334437");
                rem.setSms("N");
                c.setRemetente(rem);

                int idRev = ContrLogisticaReversa.inserir(idCliente, dv.getNumero(), 0, "", tipo_ap, tipo_serv, ar, vd, nome_destinatario, endereco_destinatario, numero_destinatario, complemento_destinatario, bairro_destinatario, cidade_destinatario, uf_destinatario, cep_destinatario, "", nome_remetente, endereco_remetente, numero_remetente, complemento_remetente, bairro_remetente, cidade_remetente, uf_remetente, cep_remetente, email_remetente, "", "", "N", nomeUser, qtdObjetos, nomeBD);

                //DEFINE QUANTIDADE DE OBJETOS DA SOLICITACAO
                for (int i = 1; i <= qtdObjetos; i++) {
                    ObjetoTO obj = new ObjetoTO();
                    obj.setId(idRev + "_" + i);
                    obj.setItem("1");
                    c.getObjCol().add(obj);
                }

                if (prod != null) {
                    //INSERE CAIXA NA SOLICITACAO CASO TENHA
                    ProdutoTO produtos = new ProdutoTO();
                    produtos.setCodigo(new Long(prod[0]));
                    produtos.setTipo(new Long(prod[1]));
                    produtos.setQtd(new Long("1"));
                    c.getProduto().add(produtos);
                }

                //ADICIONA NA LISTA
                lista.add(c);

                //FAZ A SOLICIACAO DA REVERSA                
                RetornoPostagemTO ap = solicitarPostagemReversa(usuario, senha, Integer.parseInt(codAdm), new Long(contrato), Integer.parseInt(codServ), new Long(cartao), d, lista);
                if (Integer.parseInt(ap.getCodErro()) == 0) {
                    List<ResultadoSolicitacaoTO> listaR = ap.getResultadoSolicitacao();
                    for (ResultadoSolicitacaoTO res : listaR) {
                        ContrLogisticaReversa.alterarCodigoAP(Integer.parseInt(res.getNumeroColeta()), 0, idRev, nomeBD);
                    }
                    sessao.setAttribute("msg", "Autorização solicitada com Sucesso!<br/>Para visualizar as autorizações solicitadas vá em 'Logística Reversa >> Autorizações Geradas'.");
                    response.sendRedirect("Cliente/Servicos/logistica_reversa.jsp");
                } else {
                    sessao.setAttribute("msg", "Falha ao solicitar Autorizacao de Postagem!<br/><br/>Mensagem dos Correios:<br/>" + ap.getMsgErro());
                    response.sendRedirect("Cliente/Servicos/logistica_reversa.jsp");
                }
            } else {
                sessao.setAttribute("msg", "Falha ao calcular Digito Verificador!<br/><br/>Mensagem dos Correios:<br/>" + dv.getMsgErro());
                response.sendRedirect("Cliente/Servicos/logistica_reversa.jsp");
            }
        } else {
            sessao.setAttribute("msg", "Falha ao solicitar range!<br/><br/>Mensagem dos Correios:<br/>" + range.getMsgErro());
            response.sendRedirect("Cliente/Servicos/logistica_reversa.jsp");
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

    private static RetornoFaixaNumericaTO solicitarRange(java.lang.String usuario, java.lang.String senha, java.lang.Integer codAdministrativo, java.lang.Long contrato, java.lang.String tipo, java.lang.String servico, java.lang.Integer quantidade) {
        br.com.correios.scol.webservice.WebServiceScol_Service service = new br.com.correios.scol.webservice.WebServiceScol_Service();
        br.com.correios.scol.webservice.WebServiceScol port = service.getWebServiceScolPort();
        return port.solicitarRange(usuario, senha, codAdministrativo, contrato, tipo, servico, quantidade);
    }

    private static RetornoDigitoVerificadorTO calcularDigitoVerificador(java.lang.String usuario, java.lang.String senha, java.lang.Integer codAdministrativo, java.lang.Integer numero) {
        br.com.correios.scol.webservice.WebServiceScol_Service service = new br.com.correios.scol.webservice.WebServiceScol_Service();
        br.com.correios.scol.webservice.WebServiceScol port = service.getWebServiceScolPort();
        return port.calcularDigitoVerificador(usuario, senha, codAdministrativo, numero);
    }

    private static RetornoPostagemTO solicitarPostagemReversa(java.lang.String usuario, java.lang.String senha, java.lang.Integer codAdministrativo, java.lang.Long contrato, java.lang.Integer codigoServico, java.lang.Long cartao, br.com.correios.scol.webservice.PessoaTO destinatario, java.util.List<br.com.correios.scol.webservice.ColetaReversaTO> coletasSolicitadas) {
        br.com.correios.scol.webservice.WebServiceScol_Service service = new br.com.correios.scol.webservice.WebServiceScol_Service();
        br.com.correios.scol.webservice.WebServiceScol port = service.getWebServiceScolPort();
        return port.solicitarPostagemReversa(usuario, senha, codAdministrativo, contrato, codigoServico, cartao, destinatario, coletasSolicitadas);
    }

}
