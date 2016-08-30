/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Destinatario;
import Util.Conexao;
import Util.FormataString;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fernando
 */
public class ContrlRemetenteLR {
    
     public static boolean deletarEndLR(int idDestinatario, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM cliente_remetente WHERE idCliente = " + idCliente + " AND idRemetente = " + idDestinatario;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("ContrlRemetenteLR.deletar", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static boolean editarEnderecoLR(int idRemetente, int idCliente, String nome, String cpf_cnpj, String empresa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf,String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_remetente SET nome = ?, cpf_cnpj = ?, empresa = ?, cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? WHERE idRemetente = ? AND idCliente = ? ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, FormataString.removeSpecialChars(nome));
            valores.setString(2, cpf_cnpj);
            valores.setString(3, empresa);
            valores.setString(4, cep);
            valores.setString(5, endereco);
            valores.setString(6, numero);
            valores.setString(7, complemento);
            valores.setString(8, bairro);
            valores.setString(9, cidade);
            valores.setString(10, uf);
            valores.setInt(11, idRemetente);
            valores.setInt(12, idCliente);
            System.out.println(valores.toString());
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("ContrlRemetenteLR.editarEnderecoLR", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    
      public static Destinatario consultaRetenteById(int idDes, int idCli, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_remetente WHERE idRemetente = " + idDes + " AND idCliente = " + idCli;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                int idDestinatario = result.getInt("idRemetente");
                int idCliente = result.getInt("idCliente");
                String nom = result.getString("nome");
                String empresa = result.getString("empresa");
                String cepp = result.getString("cep");
                String end = result.getString("endereco");
                String bair = result.getString("bairro");
                String cid = result.getString("cidade");
                String uf = result.getString("uf");
                String numero = result.getString("numero");
                String cnpjj = result.getString("cpf_cnpj");
                String complemento = result.getString("complemento");
                Destinatario des = new Destinatario(idDestinatario, idCliente, nom, cnpjj, empresa, cepp, end, numero, complemento, bair, cid, uf);

                return des;
            } else {
                return null;
            }
        } catch (Exception e) {
            ContrErroLog.inserir("ContrlRemetenteLR.consultaRetenteById", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    
    
   public static int inserirEndLR(int idCliente, String nome, String cpf_cnpj, String empresa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_remetente (idCliente, nome, cpf_cnpj, empresa, cep, endereco, numero, complemento, bairro, cidade, uf) values(?,?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setString(2, FormataString.removeSpecialChars(nome));
            valores.setString(3, cpf_cnpj);
            valores.setString(4, empresa);
            valores.setString(5, cep);
            valores.setString(6, endereco);
            valores.setString(7, numero);
            valores.setString(8, complemento);
            valores.setString(9, bairro);
            valores.setString(10, cidade);
            valores.setString(11, uf);
            valores.executeUpdate();
           
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            ContrErroLog.inserir("ContrlRemetenteLR.inserirEndLR", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static ArrayList<Destinatario> pesquisaLR(int idCli, String codigo, String nome, String cpf_cnpj, String bairro, String cidade, String cep, String emp, String end, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        
              
        
        String sql = "SELECT * FROM cliente_remetente"
                + " WHERE idCliente = " + idCli
                + " AND idRemetente LIKE '%" + codigo + "%'"
                + " AND nome LIKE '%" + nome + "%'"
                + " AND empresa LIKE '%" + emp + "%'"
                + " AND cpf_cnpj LIKE '%" + cpf_cnpj + "%'"
                + " AND (bairro LIKE '%" + bairro + "%'"
                + " OR cidade LIKE '%" + cidade + "%'"
                + " OR endereco LIKE '%" + end + "%')"
                + " AND cep LIKE '%" + cep + "%'"               
                + " ORDER BY nome";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Destinatario> lista = new ArrayList<Destinatario>();
            for (int i = 0; result.next(); i++) {
                int idDestinatario = result.getInt("idRemetente");
                int idCliente = result.getInt("idCliente");
                String nom = result.getString("nome");
                String empresa = result.getString("empresa");
                String cepp = result.getString("cep");
                String ende = result.getString("endereco");
                String bair = result.getString("bairro");
                String cid = result.getString("cidade");
                String uf = result.getString("uf");
                String numero = result.getString("numero");
                String cnpjj = result.getString("cpf_cnpj");
                String complemento = result.getString("complemento");             
                Destinatario des = new Destinatario(idDestinatario, idCliente, nom, cnpjj, empresa, cepp, ende, numero, complemento, bair, cid, uf);
                lista.add(des);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("ContrlRemetenteLR.pesquisaLR", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
