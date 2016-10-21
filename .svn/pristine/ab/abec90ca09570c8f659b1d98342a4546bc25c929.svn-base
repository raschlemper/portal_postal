/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iReports;

import Controle.contrCliente;
import Entidade.Clientes;
import Entidade.DadosEtiqueta;
import Util.Conexao;
import static iReports.ServEtiquetasReimp.urlExist;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author RICARDINHO
 */
public class ServEtiquetasArReimp extends HttpServlet {

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
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {

            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = (Integer) sessao.getAttribute("idCliente");
            String param = request.getParameter("ids");
            String ordem = request.getParameter("ordem");
            String formato = request.getParameter("formato");
            String url_jrxml = "aviso_recebimento_A4.jasper";
            if (formato.equals("A4")) {
                url_jrxml = "aviso_recebimento_A4.jasper";
            } else if (formato.equals("ETQ_16x10")) {
                url_jrxml = "aviso_recebimento_16x10.jasper";
            }

            try {

                Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                String nome = cli.getNome();
                if (cli.getNome_etq() == 1) {
                    nome = cli.getNomeFantasia();
                }
                String num = "";
                String comp = "";
                String bairro = cli.getBairro();
                String cidade = cli.getCidade();
                String uf = cli.getUf();
                if (cli.getNumero() != null && !cli.getNumero().trim().equals("") && !cli.getNumero().trim().equals("null")) {
                    num = ", n " + cli.getNumero();
                }
                if (cli.getComplemento() != null && !cli.getComplemento().trim().equals("") && !cli.getComplemento().trim().equals("null")) {
                    comp = ", " + cli.getComplemento();
                }
                String logradouro = cli.getEndereco() + num + comp;
                String contrato = "";
                if (cli.getTemContrato() == 1) {
                    contrato = cli.getNumContrato();
                }

                String url_base = "http://localhost:8080/PortalPostal";
                String url = cli.getUrl_logo();
                if (cli.getUrl_logo() == null || cli.getUrl_logo().trim().equals("") || cli.getUrl_logo().equals("null")) {
                    url = "";
                } else {
                    if (!url.startsWith("http")) {
                        url = url_base + cli.getUrl_logo();
                    }
                    if (!urlExist(url)) {
                        url = "";
                    }
                }

                String sql_query = "SELECT"
                        + " IF(numObjeto = 'avista', '', numObjeto) AS nObj, "
                        + " nomeServico, "
                        + " siglaAmarracao, "
                        + " p.cartaoPostagem, "
                        + " codECT, "
                        + " observacoes, "
                        + " id, "
                        + " p.idDepartamento, "
                        + " peso, "
                        + " destino_postagem, "
                        + " d.nome AS nomeDes, "
                        + " IF(aos_cuidados IS NULL OR aos_cuidados = '', '', CONCAT('A/C ', aos_cuidados)) AS nomeAc, "
                        + " valor_declarado,"
                        + " aviso_recebimento,"
                        + " mao_propria,"
                        + " notaFiscal, "
                        + " departamento, "
                        + " valor_cobrar, "
                        + " CONCAT(notaFiscal, ' - ', conteudo) AS conteudo, "
                        + " SUBSTRING(d.nome_sa, 1, 30) AS nomeDesCompacto, "
                        + " d.*, "
                        + " dep.* "
                        + " FROM"
                        + " pre_venda AS p"
                        + " LEFT JOIN"
                        + " pre_venda_destinatario AS d "
                        + " ON"
                        + " p.idDestinatario = d.idDestinatario"
                        + " LEFT JOIN"
                        + " cliente_departamentos AS dep "
                        + " ON"
                        + " p.idDepartamento = dep.idDepartamento AND p.idCliente = dep.idCliente"
                        + " WHERE"
                        + " p.idCliente = " + idCliente + " AND aviso_recebimento = 1 AND id IN (" + param + ")"
                        + " ORDER BY " + ordem + ";";

                Connection conn = Conexao.conectar(nomeBD);
                List dados = new ArrayList();
                byte[] bytes = null;

                try {
                    PreparedStatement valores = conn.prepareStatement(sql_query);
                    ResultSet r = (ResultSet) valores.executeQuery();
                    while (r.next()) {
                        DadosEtiqueta d = new DadosEtiqueta();
                        d.setId_pp(r.getInt("id"));
                        d.setId_cliente(r.getInt("idCliente"));
                        d.setId_depto(r.getInt("idDepartamento"));

                        d.setCodido_ect(r.getInt("codECT"));
                        d.setGrupo_servico(r.getString("nomeServico"));
                        d.setSro(r.getString("nObj"));
                        d.setCartao_postagem(r.getString("p.cartaoPostagem"));
                        d.setContrato_ect(contrato);

                        d.setUrl_chancela("");
                        d.setUrl_logo(url);
                        d.setSigla_triagem(r.getString("siglaAmarracao"));
                        d.setNota_fiscal(r.getString("notaFiscal"));
                        d.setConteudo(r.getString("conteudo"));
                        d.setObservacao(r.getString("observacoes"));
                        d.setAos_cuidados(r.getString("nomeAc"));

                        d.setPeso(r.getFloat("peso"));
                        d.setAltura(0);
                        d.setLargura(0);
                        d.setComprimento(0);

                        d.setAr(r.getInt("aviso_recebimento"));
                        d.setMp(r.getInt("mao_propria"));
                        d.setVd(r.getFloat("valor_declarado"));
                        d.setValor_cobrar(r.getFloat("valor_cobrar"));

                        d.setDestinatario_nome(r.getString("d.nome"));
                        d.setDestinatario_documento(r.getString("d.cpf_cnpj"));
                        d.setDestinatario_logradouro(r.getString("d.endereco"));
                        d.setDestinatario_numero(r.getString("d.numero"));
                        d.setDestinatario_complemento(r.getString("d.complemento"));
                        d.setDestinatario_bairro(r.getString("d.bairro"));
                        d.setDestinatario_cidade(r.getString("d.cidade"));
                        d.setDestinatario_uf(r.getString("d.uf"));
                        d.setDestinatario_pais(r.getString("d.pais"));
                        d.setDestinatario_cep(r.getString("d.cep").replace("-", "").replace(".", ""));
                        d.setDestinatario_celular(r.getString("d.celular"));
                        d.setDestinatario_email(r.getString("d.email"));

                        if (r.getInt("dep.temEndereco") == 1) {
                            d.setRemetente_nome(r.getString("dep.nomeEndereco"));
                            d.setRemetente_documento("");
                            d.setRemetente_departamento(r.getString("departamento"));
                            d.setRemetente_logradouro(r.getString("dep.logradouro") + ", " + r.getString("dep.numero") + ", " + r.getString("dep.complemento"));
                            d.setRemetente_numero(r.getString("dep.numero"));
                            d.setRemetente_complemento(r.getString("dep.complemento"));
                            d.setRemetente_bairro(r.getString("dep.bairro"));
                            d.setRemetente_cidade(r.getString("dep.cidade"));
                            d.setRemetente_uf(r.getString("dep.uf"));
                            d.setRemetente_pais("BR");
                            d.setRemetente_cep(r.getString("dep.cep"));
                        } else {
                            d.setRemetente_nome(nome);
                            d.setRemetente_documento("");
                            d.setRemetente_departamento(r.getString("departamento"));
                            d.setRemetente_logradouro(logradouro);
                            d.setRemetente_numero("");
                            d.setRemetente_complemento("");
                            d.setRemetente_bairro(bairro);
                            d.setRemetente_cidade(cidade);
                            d.setRemetente_uf(uf);
                            d.setRemetente_pais("BR");
                            d.setRemetente_cep(cli.getCep() + "");
                        }
                        dados.add(d);
                    }
                    valores.close();

                    try {
                        Map parametros = new HashMap();
                        InputStream in = getClass().getResourceAsStream(url_jrxml);
                        JRDataSource jrds = new JRBeanCollectionDataSource(dados);
                        JasperPrint impressao = JasperFillManager.fillReport(in, parametros, jrds);
                        bytes = JasperExportManager.exportReportToPdf(impressao);
                    } catch (JRException e) {
                        System.out.println(e);
                    }

                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    Conexao.desconectar(conn);
                }

                /*
                Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                String num = "";
                if (cli.getNumero() != null && !cli.getNumero().trim().equals("") && !cli.getNumero().trim().equals("null")) {
                    num = ", n " + cli.getNumero();
                }
                String comp = "";
                if (cli.getComplemento() != null && !cli.getComplemento().trim().equals("") && !cli.getComplemento().trim().equals("null")) {
                    comp = ", " + cli.getComplemento();
                }
                
                // mapa de parâmetros do relatório (ainda vamos aprender a usar)
                Map parametros = new HashMap();
                parametros.put("idCliente", idCliente);
                parametros.put("enderecoCli", cli.getEndereco() + num + comp); //MAX DE CARACTERES '40'
                parametros.put("bairroCli", cli.getBairro());
                parametros.put("cidadeCli", cli.getCep() + " - " + cli.getCidade() + " - " + cli.getUf());
                parametros.put("nomeCli", cli.getNomeFantasia());
                parametros.put("nomeBD", nomeBD);
                parametros.put("img", "http://localhost:8080/PortalPostal/imagensNew/etq_ar.jpg");
                
                byte[] bytes = null;
                Connection conn = Conexao.conectar(nomeBD);
                try {
                    InputStream in = getClass().getResourceAsStream(url_jrxml);
                    JasperDesign jasperDesign = JRXmlLoader.load(in);
                    
                    
                    JRDesignQuery query = new JRDesignQuery();
                    query.setText("SELECT " +
                                        " IF(numObjeto = 'avista', '', numObjeto) AS nObj, " +
                                        " nomeServico, " +
                                        " siglaAmarracao, " +
                                        " IF(aos_cuidados IS NULL OR aos_cuidados = '', d.nome, CONCAT(d.nome, ' A/C ', aos_cuidados)) AS nomeDes, " +
                                        " IF(valor_declarado = 0, '', 'VD') AS vd, " +
                                        " IF(aviso_recebimento = 0, '', 'AR') AS ar, " +
                                        " IF(mao_propria = 0, '', 'MP') AS mp, " +
                                        " d.cep, " +
                                        " d.endereco, " +
                                        " d.numero, " +
                                        " d.complemento, " +
                                        " d.bairro, " +
                                        " d.cidade, " +
                                        " d.uf, " +
                                        " CONCAT(notaFiscal, ' - ', conteudo) AS conteudo, " +
                                        " departamento " +
                                " FROM " +
                                        " pre_venda AS p " +
                                " LEFT JOIN " +
                                        " pre_venda_destinatario AS d " +
                                " ON " +
                                        " p.idDestinatario = d.idDestinatario " +
                                " WHERE " +
                                        " aviso_recebimento = 1 AND id IN ("+param+")"
                            + " ORDER BY "+ordem+";");
                    jasperDesign.setQuery(query);
                    
                    JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

                    JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, conn);
                    Conexao.desconectar(conn);
                    bytes = JasperExportManager.exportReportToPdf(impressao);

                } catch (Exception e) {
                    Conexao.desconectar(conn);
                    e.printStackTrace();
                    return;
                }*/
                if (bytes != null && bytes.length > 0) {
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
