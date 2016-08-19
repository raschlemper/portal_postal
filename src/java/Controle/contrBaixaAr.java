/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.HistoricoImport;
import Util.Conexao;
import java.io.InputStream;
import java.sql.Blob;
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
 * @author SCC4
 */
public class contrBaixaAr {

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static void importarAR(ArrayList<String> listaIDS, ArrayList<String> listaValues, String sqlBase, String sqlDuplicate, Date dataIni, Date dataFim, String nomeBD, int idUsuario) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            //DELETA OS OBJETOS QUE NAO ESTAVAM NO ARQUIVO
            String sql = "DELETE FROM movimentacao_ar WHERE numObjeto NOT IN (";
            for (int i = 0; i < listaIDS.size(); i++) {
                if (i == 0) {
                    sql += "\"" + listaIDS.get(i) + "\"";
                } else {
                    sql += ", \"" + listaIDS.get(i) + "\"";
                }
            }
            sql += ") AND daraRecebRem BETWEEN '" + sdf.format(dataIni) + "' AND '" + sdf.format(dataFim) + "'";
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
            contrBaixaAr.inserirHistoricoAR(dataIni, dataFim, nomeBD, idUsuario, qtdExcluida);
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrBaixaAr", "SQLException", "", e.toString());
            throw e;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean inserirHistoricoAR(Date dataInicio, Date dataFim, String nomeBD, int idUsuario, int qtdExcluida) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_importacao_ar (dataInicio, dataFim, dataImportacao, qtdObjetos, qtdClientes, idUsuario, qtdExcluido) values(?,?,NOW(),?,?,?,?);";
        String sqlSelect = "SELECT COUNT(codCliente) as qtd, COUNT(DISTINCT codCliente) as qtdCliente from movimentacao_ar WHERE daraRecebRem BETWEEN '" + dataInicio + "' AND '" + dataFim + "' LIMIT 1;";
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
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaHistoricoImportAr(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM log_importacao_ar ORDER BY dataImportacao DESC LIMIT 50;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaHist = new ArrayList();
            for (int i = 0; result.next(); i++) {
                Timestamp dataimportacao = result.getTimestamp("dataImportacao");
                Date dataInicio = result.getDate("dataInicio");
                Date dataFim = result.getDate("dataFim");
                int tamanho = result.getInt("qtdObjetos");
                int qtClientes = result.getInt("qtdClientes");
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

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String salvarAR(InputStream inputStream, Date dataRec, String numObjeto, String nomeRec, int idUsuario, String nomeBD, int idCliente) throws SQLException {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sqlBase = "REPLACE INTO movimentacao_ar (numObjeto, dataBaixaAr, nomeRecebAr, daraRecebRem, tipoRetorno, codCliente"
                + ", codigoSTO, valorReceber, destinatarioDoDoc, loteDevolucao, imagemAr) VALUES (?,?,?,NOW(),?,0,0,0,0,0,?) ";

        try {
            //IMPORTA OS OBJETOS DO ARQUIVO
            PreparedStatement valores1 = conn.prepareStatement(sqlBase);
            valores1.setString(1, numObjeto);
            valores1.setDate(2, (java.sql.Date) dataRec);
            valores1.setString(3, nomeRec);
            valores1.setInt(4, idCliente);
            valores1.setBlob(5, inputStream);

            valores1.executeUpdate();
            valores1.close();

            //INSERE O LOG DA IMPORTACAO
            contrBaixaAr.inserirHistoricoAR(dataRec, dataRec, nomeBD, idUsuario, 0);
            return "";
        } catch (SQLException e) {
            ContrErroLog.inserir("contrBaixaAr", "SQLException", "", e.toString());
            throw e;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static byte[] consultaImgAR(String numObjeto, String nomeBD) throws SQLException {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sqlBase = "SELECT imagemAr FROM movimentacao_ar WHERE numObjeto = ? ;";
        try {
            //IMPORTA OS OBJETOS DO ARQUIVO
            PreparedStatement valores1 = conn.prepareStatement(sqlBase);
            valores1.setString(1, numObjeto);
            ResultSet rs = valores1.executeQuery();
            byte[] imageBytes = null;
            if(rs.next()){
                Blob imageBlob = rs.getBlob("imagemAr");
                if(imageBlob != null){
                    imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
                }
            }
            valores1.close();

            return imageBytes;
        } catch (SQLException e) {
            ContrErroLog.inserir("contrBaixaAr", "SQLException", "", e.toString());
            throw e;
        } finally {
            Conexao.desconectar(conn);
        }
    }

}
