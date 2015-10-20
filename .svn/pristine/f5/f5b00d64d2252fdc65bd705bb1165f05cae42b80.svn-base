/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Remetente;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class contrRemetente {

    public static int inserir(int idCliente, String nome, String cpf_cnpj, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String url_logo, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_remetente (idCliente, nome, cpf_cnpj, cep, endereco, numero, complemento, bairro, cidade, uf, url_logo) values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setString(2, nome);
            valores.setString(3, cpf_cnpj);
            valores.setString(4, cep);
            valores.setString(5, endereco);
            valores.setString(6, numero);
            valores.setString(7, complemento);
            valores.setString(8, bairro);
            valores.setString(9, cidade);
            valores.setString(10, uf);
            valores.setString(11, url_logo);
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
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean editar(int idRemetente, int idCliente, String nome, String cpf_cnpj, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_remetente SET nome = ?, cpf_cnpj = ?, cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? WHERE idRemetente = ? AND idCliente = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setString(2, cpf_cnpj);
            valores.setString(3, cep);
            valores.setString(4, endereco);
            valores.setString(5, numero);
            valores.setString(6, complemento);
            valores.setString(7, bairro);
            valores.setString(8, cidade);
            valores.setString(9, uf);
            valores.setInt(10, idRemetente);
            valores.setInt(11, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean alterarLogo( String url_logo, int idRemetente, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_remetente SET url_logo = ? WHERE idRemetente = ? AND idCliente = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, url_logo);
            valores.setInt(2, idRemetente);
            valores.setInt(3, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean deletar(int idRemetente, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM cliente_remetente WHERE idCliente = "+idCliente+" AND idRemetente = " + idRemetente;
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
    }

    public static ArrayList<Remetente> pesquisa(int idCli, String codigo, String nome, String cpf_cnpj, String bairro, String cidade, String cep, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_remetente"
                + " WHERE idCliente = " + idCli
                + " AND idRemetente LIKE '%" + codigo + "%'"
                + " AND nome LIKE '%" + nome + "%'"
                + " AND cpf_cnpj LIKE '%" + cpf_cnpj + "%'"
                + " AND bairro LIKE '%" + bairro + "%'"
                + " AND cidade LIKE '%" + cidade + "%'"
                + " AND cep LIKE '%" + cep + "%'"
                + " ORDER BY nome";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Remetente> lista = new ArrayList<Remetente>();
            for (int i = 0; result.next(); i++) {
                int idRemetente = result.getInt("idRemetente");
                int idCliente = result.getInt("idCliente");
                String nom = result.getString("nome");
                String cepp = result.getString("cep");
                String end = result.getString("endereco");
                String bair = result.getString("bairro");
                String cid = result.getString("cidade");
                String uf = result.getString("uf");
                String numero = result.getString("numero");
                String cnpjj = result.getString("cpf_cnpj");
                String complemento = result.getString("complemento");
                String url_logo = result.getString("url_logo");
                Remetente des = new Remetente(idRemetente, idCliente, nom, cnpjj, cepp, end, numero, complemento, bair, cid, uf, url_logo);
                lista.add(des);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Remetente consultaRemetenteById(int idRem, int idCli, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_remetente WHERE idRemetente = "+idRem+" AND idCliente = " + idCli;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                int idRemetente = result.getInt("idRemetente");
                int idCliente = result.getInt("idCliente");
                String nom = result.getString("nome");
                String cepp = result.getString("cep");
                String end = result.getString("endereco");
                String bair = result.getString("bairro");
                String cid = result.getString("cidade");
                String uf = result.getString("uf");
                String numero = result.getString("numero");
                String cnpjj = result.getString("cpf_cnpj");
                String complemento = result.getString("complemento");
                String url_logo = result.getString("url_logo");
                Remetente des = new Remetente(idRemetente, idCliente, nom, cnpjj, cepp, end, numero, complemento, bair, cid, uf, url_logo);

                return des;
            } else {
                return null;
            }
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

}
