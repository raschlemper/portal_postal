/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.ContrServicoECT;
import Emporium.Controle.ContrImpressaoPLP;
import Entidade.Endereco;
import Util.XTrustProvider;
import br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Fernando
 */
public class ServImportaImpressaoPLP extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession sessao = request.getSession();
        String loginSigep = request.getParameter("login_sigep");
        String senhaSigep = request.getParameter("senha_sigep");
        int idClientePLP = Integer.parseInt(request.getParameter("idCliente"));
        int idDeptoPLP = Integer.parseInt(request.getParameter("departamento"));
        String nomeBD = request.getParameter("nomeBD");
        String idPLP = request.getParameter("idPLP");
        String numObj = request.getParameter("numObjeto");
        
        try {
            //IMPORTA OBJETOS DA PLP
            XTrustProvider.install();
            String xmlPLP = solicitaPLP(new Long(idPLP), numObj, loginSigep, senhaSigep);
            String ret = leXmlPLP(xmlPLP, idPLP, idClientePLP, idDeptoPLP, nomeBD);
            
            if(ret.equals("")){
                sessao.setAttribute("msg", "PLP nº " + idPLP + " IMPORTADA COM SUCESSO !");
            }else{
                sessao.setAttribute("msg", ret);
            }                
        } catch (NumberFormatException ex) {
            //MOSTRA MENSAGEM DE ERRO
            sessao.setAttribute("msg", "O número da PLP deve conter apenas números!<br/>"
                    + "Número Digitado: " + idPLP + "<br/><br/>"
                    + "Msg. do Sistema: " + ex.getMessage());
        } catch (AutenticacaoException ex) {
            //MOSTRA MENSAGEM DE ERRO
            sessao.setAttribute("msg", "Não foi possível efetuar o Login no SigepWEB!<br/>"
                    + "Confira o Login e Senha do SigepWEB do Cliente!<br/><br/>"
                    + "Msg. do SigepWEB: " + ex.getMessage());
        } catch (SigepClienteException ex) {
            //MOSTRA MENSAGEM DE ERRO
            sessao.setAttribute("msg", "Não foi possível requisitar a PLP do SigepWEB!<br/>"
                    + "Confira se a PLP já foi vendida ou excluida do SARA!<br/><br/>"
                    + "Msg. do SigepWEB: " + ex.getMessage());
        } catch (HeadlessException e) {
            //MOSTRA MENSAGEM DE ERRO
            sessao.setAttribute("msg", "FALHA AO IMPORTAR PLP nº " + idPLP + "!<br/><br/>Erro: " + e.getMessage());
        } catch (DocumentException e) {
            //MOSTRA MENSAGEM DE ERRO
            sessao.setAttribute("msg", "FALHA AO IMPORTAR PLP nº " + idPLP + "!<br/><br/>Erro: " + e.getMessage());
        } catch (Exception ex) {
            //MOSTRA MENSAGEM DE ERRO
            sessao.setAttribute("msg", "Não foi possível requisitar a PLP do SigepWEB!<br/><br/>"
                    + "Msg. do SigepWEB: " + ex.getMessage());
        }
        
        response.sendRedirect("Cliente/Servicos/importa_plp.jsp");

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

    public static String leXmlPLP(String xml, String idPLP, int idCliente, int idDepto, String nomeBD) throws DocumentException, HeadlessException {
        String ret = "";
        xml = xml.replaceAll("&", "E");
        //System.out.println(xml);
        if (xml != null && !xml.isEmpty()) {
            
            SAXReader reader = new SAXReader();
            StringReader sr = new StringReader(xml);
            Document doc = reader.read(sr);
            List<Node> eventos = (List<Node>) doc.selectNodes("//correioslog");

            // processa eventos
            for (Node node : eventos) {
                String contrato = node.valueOf("remetente/numero_contrato");
                String cartaoPostagem = "";//node.valueOf("");
                String codigoAdministrativo = node.valueOf("remetente/codigo_administrativo");

                String nomeRemetente = node.valueOf("remetente/nome_remetente");
                String cepRemetente = node.valueOf("remetente/cep_remetente");
                String enderecoRemetente = node.valueOf("remetente/logradouro_remetente");
                String numeroRemetente = node.valueOf("remetente/numero_remetente");
                String complementoRemetente = node.valueOf("remetente/complemento_remetente");
                String bairroRemetente = node.valueOf("remetente/bairro_remetente");
                String cidadeRemetente = node.valueOf("remetente/cidade_remetente");
                String ufRemetente = node.valueOf("remetente/uf_remetente");
                Endereco endRemetente = new Endereco(nomeRemetente, enderecoRemetente, numeroRemetente, complementoRemetente, bairroRemetente, cidadeRemetente, ufRemetente, cepRemetente);                    

                List<Node> evnt = (List<Node>) node.selectNodes("objeto_postal");
                for (Node nd : evnt) {
                    
                    String notaFiscal = nd.valueOf("nacional/numero_nota_fiscal");
                    int codECT = Integer.parseInt(nd.valueOf("codigo_servico_postagem").trim());
                    String servico = ContrServicoECT.consultaGrupoServicoByCodECT(codECT);

                    String nomeDestinatario = nd.valueOf("destinatario/nome_destinatario");
                    String cepDestinatario = nd.valueOf("nacional/cep_destinatario");
                    String enderecoDestinatario = nd.valueOf("destinatario/logradouro_destinatario");
                    String numeroDestinatario = nd.valueOf("destinatario/numero_end_destinatario");
                    String complementoDestinatario = nd.valueOf("destinatario/complemento_destinatario");                    
                    String bairroDestinatario = nd.valueOf("nacional/bairro_destinatario");
                    String cidadeDestinatario = nd.valueOf("nacional/cidade_destinatario");
                    String ufDestinatario = nd.valueOf("nacional/uf_destinatario");
                    Endereco endDestinatario = new Endereco(nomeDestinatario, enderecoDestinatario, numeroDestinatario, complementoDestinatario, bairroDestinatario, cidadeDestinatario, ufDestinatario, cepDestinatario);

                    int ar = 0;
                    int mp = 0;
                    float vd = 0;
                    List<Node> evtAdicionais = (List<Node>) node.selectNodes("servico_adicional");
                    for (Node nda : evtAdicionais) {
                        int codAd = Integer.parseInt(nda.valueOf("codigo_servico_adicional").trim());
                        if (codAd == 1) {
                            ar = 1;
                        } else if (codAd == 2) {
                            mp = 2;
                        } else if (codAd == 19) {
                            vd = Float.parseFloat(nda.valueOf("valor_declarado").trim().replace(",", "."));
                        }
                    }

                    
                    String sro = nd.valueOf("numero_etiqueta");

                    ContrImpressaoPLP.inserePLP(sro, idPLP, 0, contrato, cartaoPostagem, codigoAdministrativo, codECT, servico, idCliente, idDepto, ar, mp, vd, notaFiscal, endRemetente, endDestinatario, nomeBD);

                }
            }

        } else {
            ret = "XML da PLP está vazio!";
        }
        
        return ret;
    }

    private static String solicitaPLP(java.lang.Long idPlpMaster, java.lang.String numEtiqueta, java.lang.String usuario, java.lang.String senha) throws AutenticacaoException, SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.solicitaPLP(idPlpMaster, numEtiqueta, usuario, senha);
    }
}
