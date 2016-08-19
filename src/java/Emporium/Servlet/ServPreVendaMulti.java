/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.ContrAmarracao;
import Controle.ContrClienteEtiquetas;
import Controle.ContrServicoECT;
import Controle.contrCliente;
import Emporium.Controle.ContrPreVenda;
import Emporium.Controle.ContrPreVendaDest;
import Entidade.Amarracao;
import Entidade.Clientes;
import Entidade.ServicoECT;
import Util.FormataString;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardinho
 */
public class ServPreVendaMulti extends HttpServlet {

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
            out.println("<title>Servlet ServPreVendaMulti</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServPreVendaMulti at " + request.getContextPath() + "</h1>");
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

        //DADOS GERAIS DA POSTAGEM
        String nomeBD = request.getParameter("nomeBD");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        String nomeUser = request.getParameter("nomeUser");
        
        String obs = "";
        String tipo = "SERVICO";
        String conteudo = "";
        int peso = 0;//Integer.parseInt(request.getParameter("peso"));
        int altura = 0;//Integer.parseInt(request.getParameter("altura"));
        int largura = 0;//Integer.parseInt(request.getParameter("largura"));
        int comprimento = 0;//Integer.parseInt(request.getParameter("comprimento"));
        int idRemetente = 0;
        float vlrCobrar = 0;

        //DADOS DO DEPARTAMENTO
        int idDepartamento = 0;
        String cartaoPostagem = "";
        String departamento = request.getParameter("departamento");
        if (!departamento.equals("")) {
            String aux[] = departamento.split(";");
            idDepartamento = Integer.parseInt(aux[0]);
            departamento = aux[1];
            cartaoPostagem = aux[2];
        }

        //DADOS DO CLIENTE
        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
        String contrato = "";
        if (cli != null && cli.getTemContrato() == 1) {
            contrato = cli.getNumContrato();
            if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                cartaoPostagem = cli.getCartaoPostagem();
            }
        }

        //INICIO FOR
        String[] ids = request.getParameterValues("multi_id");
        for (String id : ids) {

            String s = request.getParameter("multi_serv_"+id);
            String[] serv = s.split(";");
            String servico = serv[1];
            //SERVICOS ADICIONAIS
            int registro = 0;
            if (servico.equals("CARTA")) {
                registro = 1;
            }

            int codECT = Integer.parseInt(serv[0]);
            String tipoPost = serv[2];

            String notaFiscal = request.getParameter("multi_nf_"+id);
            int mp = Integer.parseInt(request.getParameter("multi_mp_"+id));
            int ar = Integer.parseInt(request.getParameter("multi_ar_"+id));
            float vd = Float.parseFloat(request.getParameter("multi_vd_"+id));
            if (vd > 0 && vd < 12) {
                vd = 12;
            }

            //DADOS DO DESTINATARIO
            String email_destinatario = "";
            String celular = "";
            String nome = FormataString.removeSpecialChars(request.getParameter("multi_nome_"+id));
            String empresa = request.getParameter("multi_empresa_"+id);
            String cpf = request.getParameter("multi_cpf_"+id);
            String cep = request.getParameter("multi_cep_"+id);
            String endereco = request.getParameter("multi_endereco_"+id);
            String numero = request.getParameter("multi_numero_"+id);
            String complemento = request.getParameter("multi_compl_"+id);
            String bairro = request.getParameter("multi_bairro_"+id);
            String cidade = request.getParameter("multi_cidade_"+id);
            String uf = request.getParameter("multi_uf_"+id);
            String aosCuidados = "";
            String siglaPais = "";
            String pais = "";
            /*if (tipoPost.equals("INT")) {
                String[] auxPais = request.getParameter("pais").split(";");
                siglaPais = auxPais[0];
                pais = auxPais[1];
            }*/            
            
            //PESQUISA QUAL AMARRACAO UTILIZADA
            Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
            String siglaAmarracao = "- - -";
            if (am != null) {
                siglaAmarracao = am.getSiglaAmarracao();
            } else if (tipoPost.equals("INT")) {
                siglaAmarracao = "INT";
            }

            int qtdPostagem = Integer.parseInt(request.getParameter("multi_qtd_"+id));
            for (int i = 0; i < qtdPostagem; i++) {

                int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email_destinatario, celular, pais, nomeBD);

                //VERIFICA OS DADOS E PEGA O NUM DO OBJETO
                String numObjeto = "avista";
                String tipoEtiqueta = "SEM_REGISTRO";
                //VERIFICA A QTD DE ETIQUETA
                int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);
                if (codECT != 0 && qtdEtq == 0) {
                    if(ContrClienteEtiquetas.solicitarEtiquetasSigepWEB(codECT, cli, nomeBD)) {
                        String etq = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, nomeBD);
                        if (etq != null) {
                            String aux[] = etq.split(";");
                            numObjeto = aux[0];
                            tipoEtiqueta = aux[1];
                        }
                    }
                } else if (codECT != 0 && qtdEtq != 0) {
                    String etq = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, nomeBD);
                    if (etq != null) {
                        String aux[] = etq.split(";");
                        numObjeto = aux[0];
                        tipoEtiqueta = aux[1];
                    }
                } else if (codECT == 0) {
                    ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                    codECT = se.getCodECT();
                    contrato = "";
                }

                //VERIFICA A EXISTENCIA DE COMBO PARA O SERVIÇO
                //codECT = ContrServicoCombo.consultaCodCombo(codECT, ar, mp, vd);
                int resgistro_modico = 0;
                int posta_restante = 0;
                //INSERE PRE VENDA         
                ContrPreVenda.inserir(idCliente, numObjeto, idDestinatario, idRemetente, codECT, contrato, departamento, aosCuidados, obs, conteudo, peso, altura, largura, comprimento, vd, ar, mp, siglaAmarracao, servico, notaFiscal, vlrCobrar, tipo, idDepartamento, cartaoPostagem, idUser, registro, nomeUser, email_destinatario, tipoEtiqueta, siglaPais, tipoPost, nomeBD, posta_restante, resgistro_modico, "");
            }
        }

        response.sendRedirect("Cliente/Servicos/pre_postagem.jsp");
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
