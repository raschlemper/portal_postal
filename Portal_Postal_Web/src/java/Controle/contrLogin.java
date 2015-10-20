/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Usuario;
import java.sql.Connection;
import Util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrador
 */
public class contrLogin {

    public static Usuario login(String login, String senha) {
        Usuario user = null;
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ? AND ativo = 1 AND usaPortalPostal = 1";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, login);
            valores.setString(2, senha);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                int idEmpresa = result.getInt("idEmpresa");
                int idUsuario = result.getInt("idUsuario");
                String nome = result.getString("nome");
                String email = result.getString("email");
                String cpf = result.getString("cpf");
                int idNivel = result.getInt("idNivel");
                int ativo = result.getInt("ativo");
                int usaPortalPostal = result.getInt("usaPortalPostal");
                String acessosPortalPostal = result.getString("acessosPortalPostal");
                int usaConsolidador = result.getInt("usaPortalPostal");
                String acessosConsolidador = result.getString("acessosConsolidador");
                user = new Usuario(idUsuario, idEmpresa, nome, login, senha, email, cpf, ativo, idNivel, usaPortalPostal, acessosPortalPostal, usaConsolidador, acessosConsolidador);
            }
            return user;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLogin", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static boolean verificaLoginSenha(String login, String senha, int nivel, String nomeBD) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT idNivel FROM usuarios WHERE ativo = 1 AND usaPortalPostal = 1 AND login = ? AND senha = ? AND idNivel <= ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, login);
            valores.setString(2, senha);
            valores.setInt(3, nivel);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLogin", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static boolean registraLoginDeAgencia(int idEmpresa, int idUsuario) {
        Connection conn = Conexao.conectarGeral();
        String sql = "INSERT INTO logempresa (idEmpresa, idUsuario, dataLogEmp) VALUES(?,?,NOW())";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            valores.setInt(2, idUsuario);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLogin", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean registraLoginDeCliente(int idEmpresa, int idCliente) {
        Connection conn = Conexao.conectarGeral();
        String sql = "INSERT INTO logcliente (idEmpresa, idCliente, dataLog) VALUES(?,?,NOW())";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            valores.setInt(2, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLogin", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificaStatusEmpresa(int idEmpresa) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT status FROM empresas WHERE idEmpresa = ? and status = 1";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLogin", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }
}
