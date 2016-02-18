/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iReports;

import Controle.contrCliente;
import Emporium.Controle.ContrPreVenda;
import Entidade.Clientes;
import Util.Conexao;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author RICARDINHO
 */
@WebServlet(name = "ServEtiquetas", urlPatterns = {"/ServEtiquetas"})
public class ServEtiquetas extends HttpServlet {

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

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {

            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = (Integer) sessao.getAttribute("idCliente");
            int idUser = (Integer) sessao.getAttribute("idUsuarioEmp");
            String nomeUser = (String) sessao.getAttribute("nomeUser");
            String param = request.getParameter("ids");
            
            String formato = request.getParameter("formato");
            String ordem = request.getParameter("ordem");
            String url_jrxml = "etiquetas_reimp.jrxml";
            if(formato.equals("A4")){
                url_jrxml = "etiquetas_reimp.jrxml";
            }else if(formato.equals("ENV_DL")){
                url_jrxml = "etiquetas_envelope_DL.jrxml";
            }else if(formato.equals("ENV_DL_ESQ")){
                url_jrxml = "etiquetas_envelope_DL_ESQ.jrxml";
            }else if(formato.equals("ENV_C5_ESQ")){
                url_jrxml = "etiquetas_envelope_C5_ESQ.jrxml";
            }else if(formato.equals("ENV_C5")){
                url_jrxml = "etiquetas_envelope_C5.jrxml";
            }else if(formato.equals("ENV_B4")){
                url_jrxml = "etiquetas_envelope_B4.jrxml";
            }else if(formato.equals("ETQ_10x10")){
                url_jrxml = "etiquetas_10x10.jrxml";
            }else if(formato.equals("ETQ_16x10")){
                url_jrxml = "etiquetas_16x10.jrxml";
            }

            try {
                Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                String num = "";
                if (cli.getNumero() != null && !cli.getNumero().trim().equals("") && !cli.getNumero().trim().equals("null")) {
                    num = ", n " + cli.getNumero();
                }
                String comp = "";
                if (cli.getComplemento() != null && !cli.getComplemento().trim().equals("") && !cli.getComplemento().trim().equals("null")) {
                    comp = ", " + cli.getComplemento();
                }

                String url_base = getServletContext().getRealPath("");
                //String url = "http://localhost:8084/portalpostal/" + cli.getUrl_logo();
                String url = "http://localhost:8080/PortalPostal/" + cli.getUrl_logo();
                if (!urlExist(url)) {
                    url = "";
                }

                // mapa de parâmetros do relatório (ainda vamos aprender a usar)
                Map parametros = new HashMap();
                parametros.put("idCliente", idCliente);
                parametros.put("ids", param);
                parametros.put("enderecoCli", cli.getEndereco() + num + comp); //MAX DE CARACTERES '40'
                parametros.put("bairroCli", cli.getBairro());
                parametros.put("cidadeCli", cli.getCidade() + " / " + cli.getUf());
                parametros.put("cepCli", cli.getCep() + "");
                String contrato = "";
                if(cli.getTemContrato() == 1){
                    contrato = cli.getNumContrato();                    
                }
                parametros.put("contratoCli", contrato);
                String nome = cli.getNome();
                if(cli.getNome_etq() == 1){
                    nome = cli.getNomeFantasia();
                }
                parametros.put("nomeCli", nome);
                parametros.put("nomeBD", nomeBD);
                parametros.put("urlLogoCli", url);    
                parametros.put("responsavel", ""); 
                

                byte[] bytes = null;
                Connection conn = Conexao.conectar(nomeBD);
                try {
                    InputStream in = getClass().getResourceAsStream(url_jrxml);
                    JasperDesign jasperDesign = JRXmlLoader.load(in);                    
                    
                    JRDesignQuery query = new JRDesignQuery();
                    query.setText("SELECT" + 
                                        " IF(numObjeto = 'avista', id, numObjeto) AS nObj, " +
                                        " IF(contrato = '', '0', '1') AS temContrato," +
                                        //" IF(contrato = '' || nomeServico = 'CARTA' || nomeServico = 'SIMPLES', CONCAT('http://localhost:8084/PortalPostal_Web/imagensNew/chancelas/', nomeServico, '.png'), CONCAT('http://localhost:8084/PortalPostal_Web/ServGeraChancela?servico=', nomeServico, '&codCliente=' , p.idCliente, '&nomeBD=', $P{nomeBD})) AS imgChancela," +
                                        " IF(contrato = '' || nomeServico = 'PPI' || nomeServico = 'CARTA' || nomeServico = 'SIMPLES', CONCAT('"+url_base+"/imagensNew/chancelas/', nomeServico, '.png'), CONCAT('http://localhost:8080/PortalPostal/ServGeraChancela?servico=', nomeServico, '&codCliente=' , p.idCliente, '&nomeBD=', $P{nomeBD})) AS imgChancela," +
                                        " nomeServico, " +
                                        " siglaAmarracao, " +
                                        " cartaoPostagem, " +
                                        " observacoes, " +
                                        " peso, " +
                                        " destino_postagem, " +
                                        " d.nome AS nomeDes, " +
                                        " IF(aos_cuidados IS NULL OR aos_cuidados = '', '', CONCAT('A/C ', aos_cuidados)) AS nomeAc, " +
                                        " IF(valor_declarado = 0, '', 'VD') AS vd," +
                                        " IF(aviso_recebimento = 0, '', 'AR') AS ar," +
                                        " IF(mao_propria = 0, '', 'MP') AS mp," +
                                        " notaFiscal, " +
                                        " departamento, " +
                                        " valor_cobrar, " +
                                        " SUBSTRING(d.nome_sa, 1, 30) AS nomeDesCompacto, " +
                                        " d.cep, " +
                                        " d.cpf_cnpj, " +
                                        " d.endereco, " +
                                        " d.numero, " +
                                        " d.complemento, " +
                                        " d.bairro, " +
                                        " d.cidade, " +
                                        " d.uf, " +
                                        " d.celular, " +
                                        " d.pais " +
                                " FROM" +
                                        " pre_venda AS p" +
                                " LEFT JOIN" +
                                        " pre_venda_destinatario AS d " +
                                " ON" +
                                        " p.idDestinatario = d.idDestinatario" +
                                " WHERE" +
                                        " p.idCliente = " + idCliente + " AND impresso = 0 AND id IN (" + param + ")"
                            + " ORDER BY "+ordem+";");
                    jasperDesign.setQuery(query);
                    
                    JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, conn);
                    Conexao.desconectar(conn);
                    bytes = JasperExportManager.exportReportToPdf(impressao);

                } catch (Exception e) {                    
                    Conexao.desconectar(conn);
                    System.out.println(e);
                    e.printStackTrace();
                    return;
                }

                //  
                if (bytes != null && bytes.length > 0) {
                    ContrPreVenda.setarImpresso(nomeBD, param, idUser, nomeUser);
                    
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public static boolean urlExist(String vurl) {
        try {
            URL url = new URL(vurl);
            url.openStream();
            return true;
        } catch (Exception e) {
            return false;
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
}
