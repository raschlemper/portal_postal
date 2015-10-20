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

        //DADOS GERAIS DA POSTAGEM
        String nomeBD = request.getParameter("nomeBD");
        String servico = request.getParameter("servico");
        String obs = request.getParameter("obs");
        String tipo = request.getParameter("tipo");
        String conteudo = request.getParameter("conteudo");
        String notaFiscal = request.getParameter("notaFiscal");
        int peso = 0;//Integer.parseInt(request.getParameter("peso"));
        int altura = 0;//Integer.parseInt(request.getParameter("altura"));
        int largura = 0;//Integer.parseInt(request.getParameter("largura"));
        int comprimento = 0;//Integer.parseInt(request.getParameter("comprimento"));
        int idRemetente = 0;
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        String nomeUser = request.getParameter("nomeUser");
        int flagMulti = Integer.parseInt(request.getParameter("flagMulti"));
        int qtdPostagem = Integer.parseInt(request.getParameter("quantidade"));
                        
        //DADOS DO DEPARTAMENTO
        int idDepartamento = 0;
        String cartaoPostagem = "";
        String departamento = request.getParameter("departamento");
        if(!departamento.equals("")){
            String aux[] = departamento.split(";");
            idDepartamento = Integer.parseInt(aux[0]);
            departamento = aux[1];
            cartaoPostagem = aux[2];
        }
        
        //DADOS DO CLIENTE
        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
        String contrato = "";
        if(cli != null && cli.getTemContrato() == 1){
            contrato = cli.getNumContrato();
            if(cartaoPostagem.equals("0") || cartaoPostagem.equals("")){
                cartaoPostagem = cli.getCartaoPostagem();
            }
        }        
        
        //SERVICOS ADICIONAIS
        int registro = 0;
        if(servico.equals("CARTA")){
            int reg = Integer.parseInt(request.getParameter("tipoCarta"));
            //SE O SERVICO DE CARTA NAO TEM REGISTRO ELA É CARTA SIMPLES
            if(reg == 0){
                servico = "SIMPLES";
            }
        }
        
        int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
        String tipoPost = "NAC";
        if(servico.equals("OUTROS")){
            String aux[] = request.getParameter("servico_1").split(";");
            tipoPost = aux[2];
            servico = aux[1];
            codECT = Integer.parseInt(aux[0]);
        }        
        
        int mp = Integer.parseInt(request.getParameter("mp"));
        int ar = Integer.parseInt(request.getParameter("ar"));
        float vd = Float.parseFloat(request.getParameter("vd"));
        if(vd>0 && vd<12){
            vd=12;
        }
        float vlrCobrar = Float.parseFloat(request.getParameter("valor_cobrar"));        
                
        for (int i = 0; i < qtdPostagem; i++) {
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
            if(request.getParameter("sn") != null){
                numero = "S/N";
            }
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String uf = request.getParameter("uf");
            String aosCuidados = request.getParameter("aoscuidados");
            String siglaPais = "BR";
            String pais = "Brasil";
            if(tipoPost.equals("INT")){                
                String[] auxPais = request.getParameter("pais").split(";");
                siglaPais = auxPais[0];
                pais = auxPais[1];
                uf = request.getParameter("estado");
            }            
            
            if (idDest == 0) {
                contrDestinatario.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, pais, email_destinatario, celular, nomeBD);
            } else {
                contrDestinatario.editar(idDest, idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, pais, email_destinatario, celular, nomeBD);
            }
            int idDestinatario =  ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email_destinatario, celular, pais, nomeBD);

            //PESQUISA QUAL AMARRACAO UTILIZADA
            Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
            String siglaAmarracao = "- - -";
            if(am != null){
                siglaAmarracao = am.getSiglaAmarracao();
            }else if(tipoPost.equals("INT")){
                siglaAmarracao = "INT";
            }
            
            //VERIFICA OS DADOS E PEGA O NUM DO OBJETO
            String numObjeto = "avista";
            String tipoEtiqueta = "SEM_REGISTRO";
            //VERIFICA A QTD DE ETIQUETA
            int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);
            if(codECT != 0 && qtdEtq != 0){
                String etq = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, nomeBD);  
                if(etq != null){
                    String aux[] = etq.split(";");
                    numObjeto = aux[0];
                    tipoEtiqueta = aux[1];
                }
            } else if ( codECT == 0 ){ 
                ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico, tipo);
                codECT = se.getCodECT();
                if(codECT == 10014 && servico.equals("CARTA")){
                    registro = Integer.parseInt(request.getParameter("tipoCarta"));
                }
                contrato = "";
            }
            
            //VERIFICA A EXISTENCIA DE COMBO PARA O SERVIÇO
            //codECT = ContrServicoCombo.consultaCodCombo(codECT, ar, mp, vd);

            //INSERE PRE VENDA         
            ContrPreVenda.inserir(idCliente, numObjeto, idDestinatario, idRemetente, codECT, contrato, departamento, aosCuidados, obs, conteudo, peso, altura, largura, comprimento, vd, ar, mp, siglaAmarracao, servico, notaFiscal, vlrCobrar, tipo, idDepartamento, cartaoPostagem, idUser, registro, nomeUser, email_destinatario, tipoEtiqueta, siglaPais, tipoPost, nomeBD);
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
