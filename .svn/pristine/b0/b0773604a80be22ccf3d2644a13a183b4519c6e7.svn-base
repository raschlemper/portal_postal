/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iReports;

import Entidade.Clientes;
import Entidade.DadosEtiqueta;
import Util.Conexao;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
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
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Fernando
 */
public class LinkImpressaoSRO extends HttpServlet {

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

        String codAgencia = request.getParameter("codAgencia");
        int cod = Integer.parseInt(codAgencia);
        String nomeBD = Controle.contrLogin.cnpjEmpresa(cod).trim();

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        Clientes cli = Controle.contrLogin.loginWS(login, senha, nomeBD);

        if (cli == null) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<erro>");
            out.println("login ou senha ou codigo da agencia invalidos");
            out.println("</erro>");

        } else {
            String sro = request.getParameter("sro");
            if (sro.contains(",")) {
                sro = sro.replace(",", "','");
            }
            sro = "'" + sro + "'";

            int idCliente = cli.getCodigo();

            String formato = request.getParameter("formato");
            String url_jrxml = "etiqueta_A4_4_por_folha.jasper";
            if (formato.equals("A4") || formato.equals("PIMACO_6288_1")) {
                url_jrxml = "etiqueta_A4_4_por_folha.jasper";
            } else if (formato.equals("A4_6")) {
                url_jrxml = "etiqueta_A4_6_por_folha.jasper";
            } else if (formato.equals("ENV_DL")) {
                url_jrxml = "etiqueta_envelope_DL.jasper";
            } else if (formato.equals("ENV_DL_ESQ")) {
                url_jrxml = "etiqueta_envelope_DL_ESQ.jasper";
            } else if (formato.equals("ENV_C5_ESQ")) {
                url_jrxml = "etiqueta_envelope_C5_ESQ.jasper";
            } else if (formato.equals("ENV_C5")) {
                url_jrxml = "etiqueta_envelope_C5.jasper";
            } else if (formato.equals("ENV_B4")) {
                url_jrxml = "etiqueta_envelope_B4.jasper";
            } else if (formato.equals("ETQ_10x10")) {
                url_jrxml = "etiqueta_10x10.jasper";
            } else if (formato.equals("ETQ_16x10")) {
                url_jrxml = "etiqueta_16x10.jasper";
            }

            try {
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

                String url_base = "http://www.portalpostal.com.br";
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
                        + " IF(numObjeto = 'avista', id, numObjeto) AS nObj, "
                        + " IF(contrato = '', '0', '1') AS temContrato,"
                        + " IF(contrato = '' || nomeServico = 'PPI' || nomeServico = 'CARTA' || nomeServico = 'SIMPLES', "
                        + "CONCAT('" + url_base + "/imagensNew/chancelas/', nomeServico, '.png'), "
                        + "CONCAT('" + url_base + "/imagensNew/chancelas/CHANCELA_', nomeServico, '.png')"
                        + ") AS imgChancela,"
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
                        + " IF(valor_declarado = 0, '', 'VD') AS vd,"
                        + " IF(aviso_recebimento = 0, '', 'AR') AS ar,"
                        + " IF(mao_propria = 0, '', 'MP') AS mp,"
                        + " valor_declarado,"
                        + " aviso_recebimento,"
                        + " mao_propria,"
                        + " notaFiscal, "
                        + " departamento, "
                        + " valor_cobrar, "
                        + " conteudo, "
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
                        + " p.idCliente = " + idCliente + " AND numObjeto IN (" + sro + ")"
                        + " ORDER BY id;";

                //System.out.println(sql_query);
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
                        if (r.getString("temContrato").equals("1")) {
                            d.setContrato_ect(contrato);
                        } else {
                            d.setContrato_ect("");
                        }

                        try {
                            if (r.getString("imgChancela").contains("CHANCELA_")) {
                                Image IMG_CHANCELA = new ImageIcon(getClass().getResource("Images/CHANCELA_" + r.getString("nomeServico") + ".png")).getImage();
                                d.setChancela_img(IMG_CHANCELA);
                            } else {
                                Image IMG_CHANCELA = new ImageIcon(getClass().getResource("Images/" + r.getString("nomeServico") + ".png")).getImage();
                                d.setChancela_img(IMG_CHANCELA);
                            }
                        } catch (Exception e) {
                            d.setChancela_img(null);
                        }
                        d.setUrl_chancela(r.getString("imgChancela"));
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

                if (bytes != null && bytes.length > 0) {
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e);
                //e.printStackTrace();
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
