/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.*;
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
 * @author RICARDINHO
 */
public class ServPreVenda extends HttpServlet {

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
            out.println("<title>Servlet ServPreVenda</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServPreVenda at " + request.getContextPath() + "</h1>");
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
        String servico = request.getParameter("servico");
        String obs = request.getParameter("obs");
        String tipo = "SERVICO";
        String conteudo = request.getParameter("conteudo");
        String notaFiscal = request.getParameter("notaFiscal");
        int peso = 0;//Integer.parseInt(request.getParameter("peso"));
        int altura = 0;//Integer.parseInt(request.getParameter("altura"));
        int largura = 0;//Integer.parseInt(request.getParameter("largura"));
        int comprimento = 0;//Integer.parseInt(request.getParameter("comprimento"));
        int idRemetente = 0;
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        String setor = request.getParameter("setor");
        String nomeUser = request.getParameter("nomeUser");
        int qtdPostagem = Integer.parseInt(request.getParameter("quantidade"));

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

        //SERVICOS ADICIONAIS
        int posta_restante = 0;
        int registro_modico = 0;
        int registro = 0;
        String tipoRegistro = request.getParameter("tipoRg");
        if (servico.equals("CARTA")) {
            registro = Integer.parseInt(request.getParameter("tipoCarta"));
            //SE O SERVICO DE CARTA NAO TEM REGISTRO ELA É CARTA SIMPLES
            if (registro == 0) {
                servico = "SIMPLES";
            } else if (tipoRegistro.equals("1")) {
                servico = "CARTA_MOD";
                registro_modico = 1;
                registro = 0;
            }
        } else if (servico.startsWith("MDPB") || servico.equals("IMPRESSO")) {
            if (tipoRegistro != null && tipoRegistro.trim().equals("1")) {
                registro_modico = 1;
                registro = 0;
            } else {
                registro_modico = 0;
                registro = 1;
            }
        }

        int mp = Integer.parseInt(request.getParameter("mp"));
        int ar = Integer.parseInt(request.getParameter("ar"));
        float vd = Float.parseFloat(request.getParameter("vd"));
        if (vd > 0 && vd < 12) {
            vd = 12;
        }
        float vlrCobrar = Float.parseFloat(request.getParameter("valor_cobrar"));

        int codECTsolicitado = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
        String tipoPost = "NAC";
        if (servico.equals("OUTROS")) {
            String aux[] = request.getParameter("servico_1").split(";");
            tipoPost = aux[2];
            servico = aux[1];
            codECTsolicitado = Integer.parseInt(aux[0]);
            ServicoECT se = ContrServicoECT.consultaByCodigoECT(codECTsolicitado);
            if(se.getFaturar() == 0){
                contrato = "";
                cartaoPostagem = "";
            }
        }

        //DADOS DO DESTINATARIO
        int idDest = Integer.parseInt(request.getParameter("idDestinatario"));
        String email_destinatario = request.getParameter("email_destinatario");
        String celular = request.getParameter("celular");
        String nome = FormataString.removeSpecialChars(request.getParameter("nome"));
        String empresa = request.getParameter("empresa");
        String cpf = request.getParameter("cpf_cnpj");
        String cep = request.getParameter("cep");
        String endereco = request.getParameter("endereco");
        String numero = request.getParameter("numero");
        if (request.getParameter("sn") != null) {
            numero = "S/N";
        }
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String uf = request.getParameter("uf");
        String aosCuidados = request.getParameter("aoscuidados");
        String siglaPais = "BR";
        String pais = "Brasil";
        if (tipoPost.equals("INT")) {
            String[] auxPais = request.getParameter("pais").split(";");
            siglaPais = auxPais[0];
            pais = auxPais[1];
            uf = request.getParameter("estado");
        }
        if (obs.length() > 50) {
            obs = obs.substring(0, 49);
        }
        if(request.getParameter("salvarDestinatario") != null){
            if (idDest == 0) {
                contrDestinatario.inserir(idCliente, idDepartamento, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, pais, email_destinatario, celular, nomeBD, "");
            } else {
                contrDestinatario.editar(idDest, idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, pais, email_destinatario, celular, nomeBD);
            }
        }

        //PESQUISA QUAL AMARRACAO UTILIZADA
        Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
        String siglaAmarracao = "- - -";
        if (am != null) {
            siglaAmarracao = am.getSiglaAmarracao();
        } else if (tipoPost.equals("INT")) {
            siglaAmarracao = "INT";
        }

        String falha = "";
        for (int i = 0; i < qtdPostagem; i++) {

            int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email_destinatario, celular, pais, nomeBD);
            if (idDestinatario > 0) {

                //VERIFICA OS DADOS E PEGA O NUM DO OBJETO
                String numObjeto = "avista";
                String tipoEtiqueta = "SEM_REGISTRO";

                //VERIFICA SE O CLIENTE TEM CONTRATO
                int codECT = codECTsolicitado;
                //VERIFICA A QTD DE ETIQUETA
                int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);
                if (codECT != 0 && qtdEtq == 0 && !servico.equals("SIMPLES")) {
                    if(ContrClienteEtiquetas.solicitarEtiquetasSigepWEB(codECT, cli, nomeBD)) {
                        String etq = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, 0, nomeBD);
                        if (etq != null) {
                            String aux[] = etq.split(";");
                            numObjeto = aux[0];
                            tipoEtiqueta = aux[1];
                        }
                    }
                } else if (codECT != 0 && qtdEtq != 0 && !servico.equals("SIMPLES")) {
                    String etq = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, 0, nomeBD);
                    if (etq != null) {
                        String aux[] = etq.split(";");
                        numObjeto = aux[0];
                        tipoEtiqueta = aux[1];
                    }
                } else if (codECT == 0) {
                    if (servico.equals("CARTA_MOD")) {
                        servico = "CARTA";
                    }
                    ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                    codECT = se.getCodECT();
                    /*if (codECT == 10014 && servico.equals("CARTA")) {
                        registro = Integer.parseInt(request.getParameter("tipoCarta"));
                    }*/
                    String etq = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, 1, nomeBD);
                    System.out.println(etq);
                    if (etq != null) {
                        String aux[] = etq.split(";");
                        numObjeto = aux[0];
                        tipoEtiqueta = aux[1];
                    }
                    contrato = "";
                }

                //System.out.println(codECT + " | " + servico + " - RG = " + registro + " | RM = " + registro_modico);
                //VERIFICA A EXISTENCIA DE COMBO PARA O SERVIÇO
                //codECT = ContrServicoCombo.consultaCodCombo(codECT, ar, mp, vd);
                
                //INSERE PRE VENDA 
                ContrPreVenda.inserir(idCliente, numObjeto, idDestinatario, idRemetente, codECT, contrato, departamento, aosCuidados, obs, conteudo, peso, altura, largura, comprimento, vd, ar, mp, siglaAmarracao, servico, notaFiscal, vlrCobrar, tipo, idDepartamento, cartaoPostagem, idUser, registro, nomeUser, email_destinatario, tipoEtiqueta, siglaPais, tipoPost, nomeBD, posta_restante, registro_modico, setor);

            } else {
                //response.sendRedirect("Cliente/Servicos/pre_postagem.jsp?Falha ao inserir o destinatario!");                
                falha = "Falha ao inserir o destinatario! Nao use caracteres especiais!";
            }
        }

        response.sendRedirect("Cliente/Servicos/pre_postagem.jsp?msg=" + falha);

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
