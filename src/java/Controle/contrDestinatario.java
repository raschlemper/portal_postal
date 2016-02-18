/*
 * To change this template, choose Tools | Templates
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
 * @author Administrador
 */
public class contrDestinatario {

    public static int inserir(int idCliente, String nome, String cpf_cnpj, String empresa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String pais, String email, String celular, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_destinatario (idCliente, nome, cpf_cnpj, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, pais, email, celular) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            valores.setString(12, pais);
            valores.setString(13, email);
            valores.setString(14, celular);
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

    public static boolean editar(int idDestinatario, int idCliente, String nome, String cpf_cnpj, String empresa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String pais, String email, String celular, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_destinatario SET nome = ?, cpf_cnpj = ?, empresa = ?, cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, pais=?, email=?, celular=? WHERE idDestinatario = ? AND idCliente = ? ";
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
            valores.setString(11, pais);
            valores.setString(12, email);
            valores.setString(13, celular);
            valores.setInt(14, idDestinatario);
            valores.setInt(15, idCliente);
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

    public static boolean deletar(int idDestinatario, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM cliente_destinatario WHERE idCliente = " + idCliente + " AND idDestinatario = " + idDestinatario;
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

    public static ArrayList<Destinatario> pesquisa(int idCli, String codigo, String nome, String cpf_cnpj, String bairro, String cidade, String cep, String emp, String end, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_destinatario"
                + " WHERE idCliente = " + idCli
                + " AND idDestinatario LIKE '%" + codigo + "%'"
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
                int idDestinatario = result.getInt("idDestinatario");
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
                String email = result.getString("email");
                String celular = result.getString("celular");
                Destinatario des = new Destinatario(idDestinatario, idCliente, nom, cnpjj, empresa, cepp, ende, numero, complemento, bair, cid, uf, email, celular);
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

    public static String consultaDestinatarioAutoComplete(int idCli, String nomePesquisa, String destino, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String where = " AND pais = 'Brasil' ";
        if(destino.equals("INT")){
            where = " AND pais <> 'Brasil' ";
        }
        String sql = "SELECT *, UPPER(TRIM(nome)) AS nomep"
                + " FROM cliente_destinatario"
                + " WHERE idCliente = " + idCli + ""
                + " AND nome LIKE '%" + nomePesquisa + "%'"
                + where
                + " GROUP BY nomep"
                + " ORDER BY LOCATE('" + nomePesquisa + "', nomep), nomep"
                + " LIMIT 0, 7";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            String ret = "";
            while (result.next()) {

                int idDestinatario = result.getInt("idDestinatario");
                String nome = result.getString("nomep");
                String empresa = result.getString("empresa");
                String cepp = result.getString("cep");
                String ende = result.getString("endereco");
                String bair = result.getString("bairro");
                String cid = result.getString("cidade");
                String uf = result.getString("uf");
                String numero = result.getString("numero");
                String cnpjj = result.getString("cpf_cnpj");
                String complemento = result.getString("complemento");
                String pais = result.getString("pais");
                

                ret += ",{\"value\": \"" + idDestinatario + "\", "
                        + "\"label\": \"" + Util.FormataString.removeAccentsToUpper(nome) + "\", "
                        + "\"endereco\": \"" + Util.FormataString.removeAccentsToUpper(ende) + "\", "
                        + "\"numero\":\"" + numero + "\", "
                        + "\"complemento\":\"" + Util.FormataString.removeAccentsToUpper(complemento) + "\", "
                        + "\"bairro\":\"" + Util.FormataString.removeAccentsToUpper(bair) + "\", "
                        + "\"cidade\":\"" + Util.FormataString.removeAccentsToUpper(cid) + "\", "
                        + "\"uf\":\"" + uf + "\", "
                        + "\"cep\":\"" + cepp + "\", "
                        + "\"aoscuidados\":\"\", "
                        + "\"empresa\":\"" + Util.FormataString.removeAccentsToUpper(empresa) + "\", "
                        + "\"email_destinatario\":\"\", "
                        + "\"celular\":\"\", "
                        + "\"cpf_cnpj\":\"" + cnpjj + "\", "
                        + "\"destino\":\"" + destino + "\", "
                        + "\"pais\":\"" + pais + "\" "
                        + "}";
            }
            valores.close();
            if (!ret.equals("")) {
                ret = "[" + ret.substring(1) + "]";
            }else{
                ret = "[]";
            }
            return ret;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Destinatario consultaDestinatarioById(int idDes, int idCli, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_destinatario WHERE idDestinatario = " + idDes + " AND idCliente = " + idCli;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                int idDestinatario = result.getInt("idDestinatario");
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
                String email = result.getString("email");
                String celular = result.getString("celular");
                Destinatario des = new Destinatario(idDestinatario, idCliente, nom, cnpjj, empresa, cepp, end, numero, complemento, bair, cid, uf, email, celular);

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
