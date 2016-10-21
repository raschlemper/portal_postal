/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Contato;
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
public class contrContato {

    public static boolean inserir(int idCliente, String contato, String email, String telefone, String setor, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_contato (idEmpresa, contato, email, foneRamal, setor, ativo) values(?,?,?,?,?,1)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, contato);
            valores.setString(3, email);
            valores.setString(4, telefone);
            valores.setString(5, setor);
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

    public static boolean editar(int idContato, String contato, String email, String telefone, String setor, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_contato SET contato = '" + contato + "', email = '" + email + "', foneRamal = '" + telefone + "', setor = '" + setor + "' WHERE idContato = " + idContato;
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

    public static boolean deletar(int idContato, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_contato SET ativo = 0 WHERE idContato = " + idContato;
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

    public static Contato consultaContatoPorId(int idContato, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_contato WHERE idContato = " + idContato + " ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                int idContato2 = result.getInt("idContato");
                String contato = result.getString("contato");
                int idEmpresa = result.getInt("idEmpresa");
                String email = result.getString("email");
                String fone = result.getString("foneramal");
                String setor = result.getString("setor");
                return new Contato(idContato, contato, idEmpresa, email, fone, setor);
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaContatos(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_contato WHERE ativo = 1 AND idEmpresa = " + idCliente;
        ArrayList listaContato = new ArrayList();
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            for (int i = 0; result.next(); i++) {
                int idContato = result.getInt("idContato");
                String contatoNome = result.getString("contato");
                int idEmpresa = result.getInt("idEmpresa");
                String contatoEmail = result.getString("email");
                String contatoFone = result.getString("foneramal");
                String contatoSetor = result.getString("setor");
                Contato cont = new Contato(idContato, contatoNome, idEmpresa, contatoEmail, contatoFone, contatoSetor);
                listaContato.add(cont);
            }
            return listaContato;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
