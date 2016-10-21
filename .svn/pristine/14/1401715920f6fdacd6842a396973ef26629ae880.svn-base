/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.ClientesDeptos;
import Entidade.HistoricoImport;
import Util.Conexao;
import Util.FormataString;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author RICARDINHO
 */
public class ContrClienteDeptos {
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");    
    
    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static void importarDeptos(ArrayList<String> listaValues, String sqlBase, String sqlDuplicate, String nomeBD, int idUsuario) throws SQLException {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            //DELETA OS OBJETOS QUE NAO ESTAVAM NO ARQUIVO
            String sql = "UPDATE cliente_departamentos SET ativo = 0";
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            
            //IMPORTA OS OBJETOS DO ARQUIVO
            for (int i = 0; i < listaValues.size(); i++) {
                PreparedStatement valores1 = conn.prepareStatement(listaValues.get(i));
                valores1.executeUpdate();
                valores1.close();
            }

            //INSERE O LOG DA IMPORTACAO
            inserirHistoricoDepto(nomeBD, idUsuario);
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrBaixaAr", "SQLException", "", e.toString());
            throw e;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean inserirHistoricoDepto(String nomeBD, int idUsuario) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_importacao_deptos (dataImportacao, qtdImportado, idUsuario) values(NOW(),?,?);";
        String sqlSelect = "SELECT COUNT(idDepartamento) as qtd from cliente_departamentos where ativo = 1;";
        try {
            PreparedStatement valoresQTD = conn.prepareStatement(sqlSelect);
            ResultSet resultSelect = (ResultSet) valoresQTD.executeQuery();
            int qtdCli = 0;
            if (resultSelect.next()) {
                qtdCli = resultSelect.getInt("qtd");
            }
            valoresQTD.close();

            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, qtdCli);
            valores.setInt(2, idUsuario);
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
    
    public static boolean inserirDepto(String nomeBD, int idCliente, String nome, String cartao, String cod_ref) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_departamentos (idCliente,nomeDepartamento,cartaoPostagem,codReferencia,ativo) \n" +
                    " VALUES (?,?,?,?,1) ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, nome);
            valores.setString(3, cartao);
            valores.setString(4, cod_ref);
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
    public static boolean desativaDepto(String idDepto, String idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_departamentos SET ativo = 0 WHERE idCliente = "+idCliente+" AND idDepartamento = "+idDepto+" ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);            
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("ContrClienteDeptos.desativaDepto", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterarDepto(String nomeBD, int idCliente, int idDepto, String nome, String cartao, int temEndereco, String nomeEndereco, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep, String codReferencia) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_departamentos SET cartaoPostagem = ?, nomeDepartamento = ?, temEndereco = ?, nomeEndereco = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, codReferencia = ? "
                + " WHERE idCliente = ? AND idDepartamento = ?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, cartao);
            valores.setString(2, nome);
            valores.setInt(3, temEndereco);
            valores.setString(4, nomeEndereco);
            valores.setString(5, logradouro);
            valores.setString(6, numero);
            valores.setString(7, complemento);
            valores.setString(8, bairro);
            valores.setString(9, cidade);
            valores.setString(10, uf);
            valores.setString(11, cep);
            valores.setString(12, codReferencia);
            valores.setInt(13, idCliente);
            valores.setInt(14, idDepto);
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
    
    
    public static ArrayList consultaHistoricoImportDeptos(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM log_importacao_deptos ORDER BY dataImportacao DESC LIMIT 50;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaHist = new ArrayList();
            for (int i = 0; result.next(); i++) {
                Timestamp dataimportacao = result.getTimestamp("dataImportacao");
                int qtClientes = result.getInt("qtdImportado");
                int idUsuario = result.getInt("idUsuario");
                HistoricoImport hst = new HistoricoImport(dataimportacao, qtClientes, idUsuario, 0);
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
    
    public static ArrayList<ClientesDeptos> consultaDeptos(int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_departamentos WHERE ativo = 1 AND idCliente = "+idCliente+" ORDER BY nomeDepartamento;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ClientesDeptos> lista = new ArrayList<ClientesDeptos>();
            for (int i = 0; result.next(); i++) {
                int idDepartamento = result.getInt("idDepartamento");
                String nomeDepartamento = result.getString("nomeDepartamento");
                String cartaoPostagem = result.getString("cartaoPostagem");
                String codReferencia = result.getString("codReferencia");
                int temEndereco = result.getInt("temEndereco");
                String nomeEndereco = result.getString("nomeEndereco");
                String logradouro = result.getString("logradouro");
                String numero = result.getString("numero");
                String complemento = result.getString("complemento");
                String bairro = result.getString("bairro");
                String cidade = result.getString("cidade");
                String uf = result.getString("uf");
                String cep = result.getString("cep");
                
                ClientesDeptos cd = new ClientesDeptos(idDepartamento, idCliente, nomeDepartamento, cartaoPostagem, codReferencia, temEndereco, nomeEndereco, logradouro, numero, complemento, bairro, cidade, uf, cep);
                lista.add(cd);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
        
    public static String consultaDeptosWherePesquisaMovimento(ArrayList<Integer> listaDptos, int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String where = "";
        if(listaDptos!=null && listaDptos.size()>0){
            String idsDepto = "";
            for (Integer idDep : listaDptos) {
                idsDepto += idDep+",";
            }
            if(!idsDepto.equals("")){
                idsDepto = idsDepto.substring(0, idsDepto.lastIndexOf(","));
                where = " AND idDepartamento IN ("+idsDepto+") ";
            }
        }
        String sql = "SELECT nomeDepartamento FROM cliente_departamentos WHERE ativo = 1 AND idCliente = "+idCliente+" "+where+" ORDER BY nomeDepartamento;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            String ret = "";
            for (int i = 0; result.next(); i++) {
                String nomeDepartamento = result.getString("nomeDepartamento");
                ret += "'"+FormataString.removeAccentsToUpper(nomeDepartamento)+"',";
            }
            if(!ret.equals("")){
                ret = " AND departamento IN (" + ret.substring(0, ret.lastIndexOf(",")) + ") ";
            }
            valores.close();
            return ret;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ClientesDeptos consultaDeptoById(int idCliente, int idDepto, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_departamentos WHERE ativo = 1 AND idCliente = "+idCliente+" AND idDepartamento = "+idDepto+" ORDER BY nomeDepartamento;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ClientesDeptos lista = null;
            if (result.next()) {
                int idDepartamento = result.getInt("idDepartamento");
                String nomeDepartamento = result.getString("nomeDepartamento");
                String cartaoPostagem = result.getString("cartaoPostagem");
                String codReferencia = result.getString("codReferencia");
                int temEndereco = result.getInt("temEndereco");
                String nomeEndereco = result.getString("nomeEndereco");
                String logradouro = result.getString("logradouro");
                String numero = result.getString("numero");
                String complemento = result.getString("complemento");
                String bairro = result.getString("bairro");
                String cidade = result.getString("cidade");
                String uf = result.getString("uf");
                String cep = result.getString("cep");
                
                lista = new ClientesDeptos(idDepartamento, idCliente, nomeDepartamento, cartaoPostagem, codReferencia, temEndereco, nomeEndereco, logradouro, numero, complemento, bairro, cidade, uf, cep);
                                
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
