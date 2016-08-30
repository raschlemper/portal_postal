/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.ClienteLogEtiqueta;
import Entidade.Clientes;
import Entidade.ServicoECT;
import Util.CalculoEtiqueta;
import Util.Conexao;
import Util.FormataString;
import Util.XTrustProvider;
import br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author RICARDINHO
 */
public class ContrClienteEtiquetas {

    public static void insereEtiquetas(String sql, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean solicitarEtiquetasSigepWEB(int codigoECT, Clientes c, String nomeBD) {
        try {
            String cnpj = c.getCnpj();
            ServicoECT s = ContrServicoECT.consultaByCodigoECT(codigoECT);
            String grupoServ = s.getGrupoServico();
            int idServico = s.getIdServicoECT();

            String cartao = "";
            int uso = 0;
            int qtd = 500;

            if (idServico > 0) {
                if (c.getTemContrato() == 1 && !c.getLogin_sigep().equals("") && !c.getSenha_sigep().equals("")) {
                    String login = c.getLogin_sigep();
                    String senha = c.getSenha_sigep();

                    cnpj = cnpj.replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "").replaceAll(" ", "");
                    if (!cnpj.equals("") && FormataString.isCNPJ(cnpj)) {

                        XTrustProvider.install();

                        String a = solicitaEtiquetas("C", cnpj, new Long(idServico), qtd, login, senha);

                        if (a != null && !a.equals("") && a.contains(",") && a.trim().length() == 27) {
                            String prefixo = a.substring(0, 2);
                            String inicial = a.substring(2, 10);
                            String fim = a.substring(16, 24);

                            String dgInicial = Util.CalculoEtiqueta.calculaDigito(Integer.parseInt(inicial));
                            String dgFim = Util.CalculoEtiqueta.calculaDigito(Integer.parseInt(fim));

                            String finicial = prefixo + inicial + dgInicial + "BR";
                            String ffim = prefixo + fim + dgFim + "BR";

                            //INSERE LOG DE ETIQUETAS
                            int idImportacao = Controle.ContrClienteEtiquetas.insereLog(finicial, ffim, c.getCodigo(), 0, "Solicitação Automática", qtd, grupoServ, "SigepWEB", "PARA PORTAL POSTAL", nomeBD);

                            //INSERE FAIXAS NO BANCO
                            int nIni = Integer.parseInt(inicial);
                            int nFim = Integer.parseInt(fim);

                            String sql = "INSERT INTO cliente_etiquetas (seqLogica, idImportacao, idCliente, codECT, grupoServico, cartaoPostagem, utilizada) VALUES";
                            for (int num = nIni; num <= nFim; num++) {
                                String etiqueta = prefixo + "" + CalculoEtiqueta.concertaTamanhoNum(num) + "" + CalculoEtiqueta.calculaDigito(num) + "BR";
                                if (num == nFim) {
                                    sql += " ('" + etiqueta + "', " + idImportacao + ", " + c.getCodigo() + ", " + codigoECT + ", '" + grupoServ + "', '" + cartao + "', " + uso + ");";
                                } else {
                                    sql += " ('" + etiqueta + "', " + idImportacao + ", " + c.getCodigo() + ", " + codigoECT + ", '" + grupoServ + "', '" + cartao + "', " + uso + "),";
                                }
                            }

                            //INSERE ETIQUETAS NO BANCO DE DADOS PARA O CLIENTE
                            Connection conn = Conexao.conectar(nomeBD);
                            try {
                                PreparedStatement valores = conn.prepareStatement(sql);
                                valores.executeUpdate();
                                valores.close();
                                return true;
                            } catch (SQLException e) {
                                ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
                                return false;
                            } finally {
                                Conexao.desconectar(conn);
                            }

                        } else {
                            //System.out.println("Falha ao solicitar!");
                            return false;
                        }
                    } else {
                        //System.out.println(cnpj + " - CNPJ Inválido!");
                        return false;
                    }
                } else {
                    //System.out.println("Este cliente não possui uma senha do SigepWEB!");
                    return false;
                }
            } else {
                //System.out.println("Este cliente não possui uma senha do SigepWEB!");
                return false;
            }

        } catch (AutenticacaoException ex) {
            //System.out.println(ex);
            return false;
        } catch (SigepClienteException ex) {
            //System.out.println(ex);
            return false;
        } catch (Exception ex) {
            //System.out.println(ex);
            return false;
        }
    }

    public static void alteraUtilizadaEtiqueta(String seqLogica, int utilizada, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_etiquetas SET utilizada = " + utilizada + " WHERE seqLogica = '" + seqLogica + "' ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int insereLog(String faixaIni, String faixaFim, int idCliente, int idUsuario, String nomeUsuario, int qtd, String codECT, String tipoGeracao, String tipoUso, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO log_etiquetas_cli (idCliente, idUsuario, nomeUsuario, dataHora, faixaIni, faixaFim, qtd, servico, tipoGeracao, tipoUso) VALUES(?,?,?,NOW(),?,?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setInt(2, idUsuario);
            valores.setString(3, nomeUsuario);
            valores.setString(4, faixaIni);
            valores.setString(5, faixaFim);
            valores.setInt(6, qtd);
            valores.setString(7, codECT);
            valores.setString(8, tipoGeracao);
            valores.setString(9, tipoUso);
            valores.executeUpdate();
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void excluirEtiquetas(int idLog, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql1 = "DELETE FROM cliente_etiquetas WHERE idImportacao = " + idLog;
        String sql2 = "DELETE FROM log_etiquetas_cli WHERE id = " + idLog;
        try {
            PreparedStatement valores = conn.prepareStatement(sql1);
            valores.executeUpdate();
            valores = conn.prepareStatement(sql2);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql1, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void suspenderEtiquetas(int idLog, int utilizada, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql1 = "UPDATE cliente_etiquetas SET utilizada = " + utilizada + " WHERE utilizada = 0 AND idImportacao = " + idLog;
        try {
            PreparedStatement valores = conn.prepareStatement(sql1);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql1, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificaExistenciaFaixaEtiquetaBD(String faixaIni, String faixaFim, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT idImportacao FROM cliente_etiquetas WHERE seqLogica = '" + faixaIni + "' OR seqLogica = '" + faixaFim + "' LIMIT 1; ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return true;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int contaQtdUtilizadaPorIdLog(int idLog, int utilizada, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String ut = "utilizada = 0";
        if (utilizada != 0) {
            ut = "utilizada <> 0";
        }
        String sql = "SELECT COUNT(idImportacao) AS qtd FROM cliente_etiquetas WHERE " + ut + " AND idImportacao = " + idLog;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getInt("qtd");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int contaQtdUtilizadaPorGrupoServ(String grupoServ, int utilizada, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT COUNT(idImportacao) AS qtd FROM cliente_etiquetas WHERE idCliente = " + idCliente + " AND utilizada = " + utilizada + " AND grupoServico = '" + grupoServ + "' ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getInt("qtd");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String pegaEtiquetaNaoUtilizadaPorGrupoServ(int idCliente, String servico, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT seqLogica, utilizada FROM cliente_etiquetas WHERE utilizada = 0 AND idCliente = " + idCliente + " AND grupoServico = '" + servico + "' ORDER BY idImportacao, seqLogica LIMIT 1 FOR UPDATE"; //WHERE RETIRADO = AND cartaoPostagem = '"+cartaoPostagem+"'
        try {
            String seq = "avista";
            PreparedStatement valores = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                seq = result.getString("seqLogica");
                result.updateInt("utilizada", 1);
                result.updateRow();
            }
            result.close();
            valores.close();
            return seq;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(int idCli, String servico, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT seqLogica, utilizada, idImportacao FROM cliente_etiquetas WHERE utilizada = 0 AND idCliente = " + idCli + " AND grupoServico = '" + servico + "' ORDER BY idImportacao, seqLogica LIMIT 1 FOR UPDATE"; //WHERE RETIRADO = AND cartaoPostagem = '"+cartaoPostagem+"'

        try {
            String lista = null;
            PreparedStatement valores3 = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = (ResultSet) valores3.executeQuery();
            if (result.next()) {

                String seq = result.getString("seqLogica");
                String idImp = result.getString("idImportacao");

                result.updateInt("utilizada", 1);
                result.updateRow();

                PreparedStatement valores = conn.prepareStatement("SELECT tipoGeracao FROM log_etiquetas_cli WHERE id = " + idImp);
                valores.executeQuery();
                ResultSet rs = valores.getResultSet();
                String tipo = "SEM_REGISTRO";
                if (rs.next()) {
                    tipo = rs.getString("tipoGeracao");
                }
                valores.close();
                lista = seq + ";" + tipo;
            }
            result.close();
            valores3.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<ClienteLogEtiqueta> consultaLogFaixas(int idCli, int limit, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM log_etiquetas_cli WHERE idCliente = " + idCli + " ORDER BY dataHora DESC LIMIT " + limit;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ClienteLogEtiqueta> lista = new ArrayList<ClienteLogEtiqueta>();
            while (result.next()) {
                int idLog = result.getInt("id");
                int idCliente = result.getInt("idCliente");
                int idUsuario = result.getInt("idUsuario");
                String nomeUsuario = result.getString("nomeUsuario");
                String faixaIni = result.getString("faixaIni");
                String faixaFim = result.getString("faixaFim");
                String nomeServico = "";
                String servico = result.getString("servico");
                int qtd = result.getInt("qtd");
                Timestamp dataHora = result.getTimestamp("dataHora");

                ClienteLogEtiqueta log = new ClienteLogEtiqueta(idLog, idCliente, idUsuario, nomeUsuario, faixaIni, faixaFim, dataHora, qtd, servico, nomeServico);
                lista.add(log);
            }
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<ClienteLogEtiqueta> consultaLogFaixas(String dataIni, String dataFim, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT log_etiquetas_cli.*, nome FROM log_etiquetas_cli LEFT JOIN cliente ON codigo = idCliente WHERE DATE(dataHora) BETWEEN '" + dataIni + "' AND '" + dataFim + "' ORDER BY dataHora DESC";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ClienteLogEtiqueta> lista = new ArrayList<ClienteLogEtiqueta>();
            while (result.next()) {
                int idLog = result.getInt("id");
                int idCliente = result.getInt("idCliente");
                int idUsuario = result.getInt("idUsuario");
                String nomeUsuario = result.getString("nomeUsuario");
                String faixaIni = result.getString("faixaIni");
                String faixaFim = result.getString("faixaFim");
                String nomeServico = result.getString("nome");
                String servico = result.getString("servico");
                int qtd = result.getInt("qtd");
                Timestamp dataHora = result.getTimestamp("dataHora");
                String tipoGeracao = result.getString("tipoGeracao");
                String tipoUso = result.getString("tipoUso");
                
                ClienteLogEtiqueta log = new ClienteLogEtiqueta(idLog, idCliente, idUsuario, nomeUsuario, faixaIni, faixaFim, dataHora, qtd, servico, nomeServico, tipoGeracao, tipoUso);
                lista.add(log);
            }
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<ClienteLogEtiqueta> consultaQtdEtiquetasRestantes(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT COUNT(ce.idCliente) AS qtd, ce.idCliente, grupoServico, cliente.nome, dataHora "
                + " FROM cliente_etiquetas AS ce"
                + " LEFT JOIN cliente ON cliente.codigo = ce.idCliente"
                + " LEFT JOIN log_etiquetas_cli ON log_etiquetas_cli.id = ce.idImportacao"
                + " WHERE utilizada = 0"
                + " GROUP BY ce.idCliente, grupoServico, cliente.nome, dataHora"
                + " ORDER BY qtd;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ClienteLogEtiqueta> lista = new ArrayList<ClienteLogEtiqueta>();
            while (result.next()) {
                int idLog = 0;
                int idCliente = result.getInt("ce.idCliente");
                int idUsuario = 0;
                String nomeUsuario = result.getString("cliente.nome");
                String faixaIni = "";
                String faixaFim = "";
                String nomeServico = result.getString("grupoServico");
                String servico = "";
                int qtd = result.getInt("qtd");
                Timestamp dataHora = result.getTimestamp("dataHora");

                ClienteLogEtiqueta log = new ClienteLogEtiqueta(idLog, idCliente, idUsuario, nomeUsuario, faixaIni, faixaFim, dataHora, qtd, servico, nomeServico);
                lista.add(log);
            }
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<ClienteLogEtiqueta> consultaQtdEtiquetasRestantes(int qtde, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT COUNT(ce.idCliente) AS qtd, ce.idCliente, grupoServico, cliente.nome, dataHora "
                + " FROM cliente_etiquetas AS ce"
                + " LEFT JOIN cliente ON cliente.codigo = ce.idCliente"
                + " LEFT JOIN log_etiquetas_cli ON log_etiquetas_cli.id = ce.idImportacao"
                + " WHERE utilizada = 0 AND nome IS NOT NULL AND qtd < " + qtde
                + " GROUP BY ce.idCliente, grupoServico, cliente.nome, dataHora"
                + " ORDER BY qtd;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ClienteLogEtiqueta> lista = new ArrayList<ClienteLogEtiqueta>();
            while (result.next()) {
                int idLog = 0;
                int idCliente = result.getInt("ce.idCliente");
                int idUsuario = 0;
                String nomeUsuario = result.getString("cliente.nome");
                String faixaIni = "";
                String faixaFim = "";
                String nomeServico = result.getString("grupoServico");
                String servico = "";
                int qtd = result.getInt("qtd");
                Timestamp dataHora = result.getTimestamp("dataHora");

                ClienteLogEtiqueta log = new ClienteLogEtiqueta(idLog, idCliente, idUsuario, nomeUsuario, faixaIni, faixaFim, dataHora, qtd, servico, nomeServico);
                lista.add(log);
            }
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    private static String solicitaEtiquetas(java.lang.String tipoDestinatario, java.lang.String identificador, java.lang.Long idServico, java.lang.Integer qtdEtiquetas, java.lang.String usuario, java.lang.String senha) throws AutenticacaoException, SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.solicitaEtiquetas(tipoDestinatario, identificador, idServico, qtdEtiquetas, usuario, senha);
    }

}
