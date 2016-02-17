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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RICARDINHO
 */
public class ServPreVendaConfirmaImp extends HttpServlet {

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
            out.println("<title>Servlet ServPreVendaConfirmaImp</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServPreVendaConfirmaImp at " + request.getContextPath() + "</h1>");
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
        
        String nomeBD = request.getParameter("nomeBD");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nomeUser = request.getParameter("nomeUser");
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        String[] ids = request.getParameterValues("id");
        
        
        Map<String, Integer> lista = new HashMap<String, Integer>();        
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            String servico = request.getParameter("servico"+id);
            if(lista.containsKey(servico)){
                lista.put(servico, lista.get(servico)+1);
            }else{
                lista.put(servico, 1);
            }
        }        
        
        
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            int vid = Integer.parseInt(id);
            int idDest = Integer.parseInt(request.getParameter("idDestinatario"+id));
            String nObj = request.getParameter("nObj"+id);
            String nome = request.getParameter("nome"+id);
            String empresa = request.getParameter("empresa"+id);
            String cpf = request.getParameter("cpf"+id);
            String cep = request.getParameter("cep"+id);
            String endereco = request.getParameter("endereco"+id);
            String numero = request.getParameter("numero"+id);
            if(request.getParameter("sn") != null){
                numero = "S/N";
            }
            String complemento = request.getParameter("complemento"+id);
            String bairro = request.getParameter("bairro"+id);
            String cidade = request.getParameter("cidade"+id);
            String uf = request.getParameter("uf"+id);
            String aosCuidados = request.getParameter("aoscuidados"+id);
            String email = request.getParameter("email"+id);
            String celular = request.getParameter("celular"+id);
            
            ContrPreVendaDest.editar(idDest, idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, nomeBD);
            
            String servico = request.getParameter("servico"+id);
            int idDepartamento = 0;
            String cartaoPostagem = "";
            String departamento = request.getParameter("departamento"+id);
            if(!departamento.equals("")){
                String aux[] = departamento.split(";");
                idDepartamento = Integer.parseInt(aux[0]);
                departamento = aux[1];
                cartaoPostagem = aux[2];
            }
            
            //VERIFICAR SE CEP POSSUI ESEDEX CASO O SERVICO ESCOLHIDO SEJA ESEDEX.
            //SE NAO POSSUIR O ESEDEX TROCAR PARA SEDEX.
            if (servico.equals("ESEDEX")) {
                int cep2 = Integer.parseInt(cep.replace("-", "").trim());
                if (!ContrServicoAbrangencia.verificaByCepServico(cep2, servico, nomeBD)) {
                    servico = "SEDEX";
                }
            }

            Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
            String siglaAmarracao = "- - -";
            if(am != null){
                siglaAmarracao = am.getSiglaAmarracao();
            }
        
            String obs = request.getParameter("obs"+id);
            String tipo = "SERVICO";
            String conteudo = request.getParameter("conteudo"+id);
            String notaFiscal = request.getParameter("nota"+id);

            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            String contrato = "";
            if(cli != null && cli.getTemContrato() == 1){
                contrato = cli.getNumContrato();
                if(cartaoPostagem.equals("0") || cartaoPostagem.equals("")){
                    cartaoPostagem = cli.getCartaoPostagem();
                }
            }        

            int peso = 0;//Integer.parseInt(request.getParameter("peso"));
            int altura = 0;//Integer.parseInt(request.getParameter("altura"));
            int largura = 0;//Integer.parseInt(request.getParameter("largura"));
            int comprimento = 0;//Integer.parseInt(request.getParameter("comprimento"));

            int mp = Integer.parseInt(request.getParameter("mp"+id));
            int ar = Integer.parseInt(request.getParameter("ar"+id));
            float vd = Float.parseFloat(request.getParameter("vd"+id));
            float vlrCobrar = 0;                        

            int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
            int registro = 0;

            String numObjeto = "avista";
            String tipoEtiqueta = "MANUAL";
            if(nObj != null){
                numObjeto = nObj;
            }
            
            if(codECT != 0 && nObj == null){
                String etq = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, nomeBD);  
                if(etq != null){
                    String aux[] = etq.split(";");
                    numObjeto = aux[0];
                    tipoEtiqueta = aux[1];
                }              
            } else if ( codECT == 0){
                ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                codECT = se.getCodECT();
                if(servico.equals("CARTA") && codECT == 10014){
                    registro = 1;
                }
                contrato = "";
            }
            
            //VERIFICA A EXISTENCIA DE COMBO PARA O SERVIÇO
            //codECT = ContrServicoCombo.consultaCodCombo(codECT, ar, mp, vd);
            
            ContrPreVenda.alterar(idCliente, numObjeto, idDest, 0, codECT, contrato, departamento, aosCuidados, obs, conteudo, peso, altura, largura, comprimento, vd, ar, mp, siglaAmarracao, servico, notaFiscal, vlrCobrar, tipo, idDepartamento, cartaoPostagem, idUser, vid, registro, nomeUser, tipoEtiqueta, email, nomeBD);
                    
        }
        
        response.sendRedirect("Cliente/Servicos/pre_postagem.jsp?msg=Postagens importadas com sucesso!");
        
        
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
