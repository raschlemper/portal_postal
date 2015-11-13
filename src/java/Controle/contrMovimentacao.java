/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.HistoricoImport;
import Entidade.Movimentacao;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Rico
 */
public class contrMovimentacao {

    /***************************************************************************/
    /******************** IMPORTACAO DE MOVIMENTO ******************************/
    /***************************************************************************/
    public static void importarMov(ArrayList<String> listaIDS, ArrayList<String> listaValues, String sqlBase, String sqlDuplicate, Date dataIni, Date dataFim, String nomeBD, int idUsuario) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            //DELETA OS OBJETOS QUE NAO ESTAVAM NO ARQUIVO
            String sql = "DELETE FROM movimentacao WHERE id NOT IN (";
            for (int i = 0; i < listaIDS.size(); i++) {
                if (i == 0) {
                    sql += listaIDS.get(i);
                } else {
                    sql += ", " + listaIDS.get(i);
                }
            }
            sql += ") AND dataPostagem BETWEEN '" + sdf.format(dataIni) + "' AND '" + sdf.format(dataFim) + "'";
            PreparedStatement valores = conn.prepareStatement(sql);
            int qtdExcluida = valores.executeUpdate();
            valores.close();

            //IMPORTA OS OBJETOS DO ARQUIVO
            for (int i = 0; i < listaValues.size(); i++) {
                PreparedStatement valores1 = conn.prepareStatement(listaValues.get(i));
                valores1.executeUpdate();
                valores1.close();
            }

            //INSERE O LOG DA IMPORTACAO
            contrMovimentacao.inserirHistorico(dataIni, dataFim, nomeBD, idUsuario, qtdExcluida);
        } catch (SQLException e) {
            throw e;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static void importarMovVisual(ArrayList<String> listaIDS, ArrayList<String> listaQueries, String sqlBase, Date dataIni, String nomeBD, int idUsuario) throws SQLException {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            //DELETA OS OBJETOS QUE NAO ESTAVAM NO ARQUIVO            
            String sql = "DELETE FROM visual_movimento";
            PreparedStatement valores = conn.prepareStatement(sql);
            int qtdExcluida = valores.executeUpdate();
            valores.close();

            for (String query : listaQueries) {
                System.out.println("\n\n"+query);
                PreparedStatement valores1 = conn.prepareStatement(query);
                valores1.executeUpdate();
                valores1.close();
            }
            
            PreparedStatement valores2 = conn.prepareStatement("DELETE FROM movimentacao WHERE dataPostagem IN (SELECT DISTINCT(STR_TO_DATE(DATA, '%Y/%m/%d')) FROM visual_movimento  WHERE `codCliente` = '126430000');");
            valores2.executeUpdate();
            
            valores2 = conn.prepareStatement("REPLACE INTO movimentacao (id, numCaixa, numVenda, seqVenda, dataPostagem, descServico, numObjeto, destinatario, notaFiscal, peso, cep, paisDestino, valorServico, valorDestino, quantidade, valorDeclarado, departamento, codCliente, codSto, siglaServAdicionais)" +
            " (SELECT visual_movimento.id, '0','0','0', STR_TO_DATE(DATA, '%Y/%m/%d'), nomeServ, IF(numObjeto='','',CONCAT(numObjeto,'BR')), nomeDestinatario, '', 0, cep, 'BR', valor1, valor1, 1, valorAdicional, '', '2110', campo7, campo3 " +
            " FROM visual_movimento " +
            " LEFT JOIN visual_servicos " +
            " ON visual_servicos.id = codServico WHERE `codCliente` = '126430000');");
            valores2.executeUpdate();
            valores2.close();

            //INSERE O LOG DA IMPORTACAO
            contrMovimentacao.inserirHistorico(dataIni, dataIni, nomeBD, idUsuario, qtdExcluida);
        } catch (SQLException e) {
            throw e;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean inserirHistorico(Date dataInicio, Date dataFim, String nomeBD, int idUsuario, int qtdExcluida) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_importacao_mov (dataInicio, dataFim, dataImportacao, tamanho, qtdCliente, idUsuario, qtdExcluido) values(?,?,NOW(),?,?,?,?);";
        String sqlSelect = "SELECT COUNT(codCliente) as qtd, COUNT(DISTINCT codCliente) as qtdCliente from movimentacao WHERE dataPostagem BETWEEN '" + dataInicio + "' AND '" + dataFim + "' LIMIT 1;";
        try {
            PreparedStatement valoresQTD = conn.prepareStatement(sqlSelect);
            ResultSet resultSelect = (ResultSet) valoresQTD.executeQuery();
            int qtdImp = 0, qtdCli = 0;
            if (resultSelect.next()) {
                qtdImp = resultSelect.getInt("qtd");
                qtdCli = resultSelect.getInt("qtdCliente");
            }
            valoresQTD.close();

            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setDate(1, (java.sql.Date) dataInicio);
            valores.setDate(2, (java.sql.Date) dataFim);
            valores.setInt(3, qtdImp);
            valores.setInt(4, qtdCli);
            valores.setInt(5, idUsuario);
            valores.setInt(6, qtdExcluida);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaHistoricoImport(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM log_importacao_mov ORDER BY dataImportacao DESC LIMIT 50;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaHist = new ArrayList();
            for (int i = 0; result.next(); i++) {
                Timestamp dataimportacao = result.getTimestamp("dataImportacao");
                Date dataInicio = result.getDate("dataInicio");
                Date dataFim = result.getDate("dataFim");
                int tamanho = result.getInt("tamanho");
                int qtClientes = result.getInt("qtdCliente");
                int idUsuario = result.getInt("idUsuario");
                int qtdExcluido = result.getInt("qtdExcluido");
                HistoricoImport hst = new HistoricoImport(dataInicio, dataFim, dataimportacao, tamanho, qtClientes, idUsuario, qtdExcluido);
                listaHist.add(hst);
            }
            valores.close();
            return listaHist;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /***************************************************************************/
    /***************************************************************************/
    /***************************************************************************/
    public static Date getPrimeiraDataDoCliente(String idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT dataPostagem FROM movimentacao WHERE codCliente = ? ORDER BY dataPostagem LIMIT 1;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            Date data = null;
            if (result.next()) {
                data = result.getDate("dataPostagem");
            }
            valores.close();
            return data;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean darBaixaAr(int baixa, String numObjeto, String nome, String data, int codCli, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO movimentacao_ar (numObjeto, nomeRecebAr, dataBaixaAr, daraRecebRem, codCliente) VALUES ('"+numObjeto+"', '"+nome+"', DATE(NOW()), '"+data+"', "+codCli+")";
        if(baixa == 0){
            sql = "DELETE FROM movimentacao_ar WHERE numObjeto = '"+numObjeto+"' ;";
        }
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoAR(String dataInicio, String dataFinal, int idCliente, int ar, String departamentoPesq, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT m.numObjeto, m.dataPostagem, m.destinatario, m.cep, m.departamento, m.status, a.nomeRecebAr, a.dataBaixaAr"
                + " FROM movimentacao AS m"
                + " LEFT JOIN movimentacao_ar AS a ON a.numObjeto = m.numObjeto"
                + " WHERE m.siglaServAdicionais LIKE '%AR%'"
                + " AND m.codCliente = " + idCliente
                + " AND (m.dataPostagem BETWEEN '"+dataInicio+"' AND '"+dataFinal+"')";
        if (ar == 1) {
            sql += " AND a.nomeRecebAr IS NOT NULL";
        }
        if (ar == 0) {
            sql += " AND a.nomeRecebAr IS NULL";
        }
        if (!departamentoPesq.equals("0")) {
            sql += " AND m.departamento LIKE '" + departamentoPesq + "'";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();
            for (int i = 0; result.next(); i++) {
                Date dataPostagem = result.getDate("m.dataPostagem");
                String numObjeto = result.getString("m.numObjeto");
                String destinatario = result.getString("m.destinatario");
                String cep = result.getString("m.cep");
                String departamento = result.getString("m.departamento");
                String status = result.getString("m.status");
                String nomeRecebAr = result.getString("a.nomeRecebAr");
                String dataBaixaAr = result.getString("a.dataBaixaAr");

                int baixaAr = 1;
                if (nomeRecebAr == null) {
                    baixaAr = 0;
                    nomeRecebAr = "";
                    dataBaixaAr = "";
                }

                Movimentacao mov = new Movimentacao(dataPostagem, numObjeto, destinatario, cep, departamento, status, baixaAr, nomeRecebAr, dataBaixaAr);
                movimentacao.add(mov);
            }
            valores.close();
            return movimentacao;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /*********************************** PESQUISAS DOS RELATÓRIOS SINTETICO ***************************************/
    public static ArrayList getConsultaSintetica(String sql, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            
            ArrayList movimentacao = new ArrayList();
            for (int i = 0; result.next(); i++) {
                Date dataPostagem = result.getDate("dataPostagem");
                String descServico = result.getString("descServico");
                String numObjeto = result.getString("numObjeto");
                String destinatario1 = result.getString("destinatario");
                float peso = result.getFloat("peso");
                String cep1 = result.getString("cep");
                float valorServico = result.getFloat("valorServico");
                float quantidade = result.getFloat("quantidade");
                String departamentos = result.getString("departamento");
                String status = result.getString("status");
                String notaFiscal = result.getString("notaFiscal");
                Date dataEntrega = result.getDate("dataEntrega");
                String numVenda = result.getString("numVenda");
                String numCaixa = result.getString("numCaixa");
                int codStatus = result.getInt("codStatus");

                Movimentacao mov = new Movimentacao(dataPostagem, descServico, numObjeto, destinatario1, peso, cep1, valorServico, quantidade, departamentos, status, dataEntrega, notaFiscal, numVenda, numCaixa, codStatus);
                movimentacao.add(mov);
            }
            valores.close();
            return movimentacao;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /*********************************** PESQUISAS DOS RELATÓRIOS ANALITICO ***************************************/
    public static ArrayList getConsultaAnalitica(String sql, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();
            for (int i = 0; result.next(); i++) {
                Date dataPostagem = result.getDate("dataPostagem");
                String descServico = result.getString("descServico");
                String numObjeto = result.getString("numObjeto");
                String destinatario1 = result.getString("destinatario");
                float peso = result.getFloat("peso");
                String cep1 = result.getString("cep");
                float valorServico = result.getFloat("valorServico");
                float quantidade = result.getFloat("quantidade");
                String departamentos = result.getString("departamento");
                String status = result.getString("status");
                String notaFiscal = result.getString("notaFiscal");
                Date dataEntrega = result.getDate("dataEntrega");
                String numVenda = result.getString("numVenda");
                String numCaixa = result.getString("numCaixa");
                String siglaServAdicionais = result.getString("siglaServAdicionais");
                String conteudoObjeto = result.getString("conteudoObjeto");
                String paisDestino = result.getString("paisDestino");
                String contratoEct = result.getString("contratoEct");
                float valorDestino = result.getFloat("valorDestino");
                float valorDeclarado = result.getFloat("valorDeclarado");
                int codStatus = result.getInt("codStatus");
                float altura = result.getFloat("altura");
                float largura = result.getFloat("largura");
                float comprimento = result.getFloat("comprimento");

                Movimentacao mov = new Movimentacao(dataPostagem, descServico, numObjeto, destinatario1, peso, cep1, valorServico, quantidade, departamentos, status, dataEntrega, notaFiscal, numVenda, numCaixa, valorDeclarado, valorDestino, paisDestino, contratoEct, conteudoObjeto, siglaServAdicionais, codStatus, altura, largura, comprimento);
                movimentacao.add(mov);
            }
            valores.close();
            return movimentacao;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**************************************************** PESQUISAS DO TICKET *************************************************************/
    public static ArrayList getMovimentacaoByNumCaixaENumVenda(int idCliente, int numCaixa, int numVenda, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT contratoEct, seqVenda, dataPostagem, descServico, numObjeto, valorServico, valorDeclarado, valorDestino,"
                + " peso, quantidade, altura, largura, comprimento, destinatario, cep, siglaServAdicionais, notaFiscal, paisDestino"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente
                + " AND numCaixa = " + numCaixa
                + " AND numVenda = " + numVenda + " ORDER BY dataPostagem DESC;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();
            for (int i = 0; result.next(); i++) {
                String contrato = result.getString("contratoEct");
                String seqVenda = result.getString("seqVenda");
                Date dataPostagem = result.getDate("dataPostagem");
                String descServico = result.getString("descServico");
                String numObjeto = result.getString("numObjeto");
                float valorServico = result.getFloat("valorServico");
                float valorDeclarado = result.getFloat("valorDeclarado");
                float valorDestino = result.getFloat("valorDestino");
                float peso = result.getFloat("peso");
                float quantidade = result.getFloat("quantidade");
                float altura = result.getFloat("altura");
                float largura = result.getFloat("largura");
                float comprimento = result.getFloat("comprimento");
                String destinatario = result.getString("destinatario");
                String cep = result.getString("cep");
                String siglaServAdicionais = result.getString("SiglaServAdicionais");
                String notaFiscal = result.getString("notaFiscal");
                String paisDestino = result.getString("paisDestino");

                Movimentacao mov = new Movimentacao(seqVenda, dataPostagem, descServico, numObjeto, destinatario, notaFiscal, peso, cep, valorServico, valorDestino, quantidade, valorDeclarado, contrato, altura, largura, comprimento, siglaServAdicionais, paisDestino);
                movimentacao.add(mov);
            }
            valores.close();
            return movimentacao;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
