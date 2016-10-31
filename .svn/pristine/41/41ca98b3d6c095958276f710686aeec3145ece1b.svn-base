/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Usuario;
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
public class contrUsuario {

    public static void inserir(String nome, int nivel, String login, String senha, int idEmpresa, String email, int usaPortalPostal, String acessosPortalPostal, int usaConsolidador, String acessosConsolidador) {
        Connection conn = Conexao.conectarGeral();
        String sql = "insert into usuarios (nome, idNivel, login, senha, idEmpresa, email, ativo, usaPortalPostal, acessosPortalPostal, usaConsolidador, acessosConsolidador) values(?,?,?,?,?,?,1,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setInt(2, nivel);
            valores.setString(3, login);
            valores.setString(4, senha);
            valores.setInt(5, idEmpresa);
            valores.setString(6, email);
            valores.setInt(7, usaPortalPostal);
            valores.setString(8, acessosPortalPostal);
            valores.setInt(9, usaConsolidador);
            valores.setString(10, acessosConsolidador);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterar(String nome, int nivel, String login, String senha, int idEmpresa, int idUsuario, String email, int usaPortalPostal, String acessosPortalPostal, int usaConsolidador, String acessosConsolidador) {
        Connection conn = Conexao.conectarGeral();
        String sql = "UPDATE usuarios SET nome=?, idNivel=?, login=?, senha=?, idEmpresa=?, email=?, usaPortalPostal=?, acessosPortalPostal=?, usaConsolidador=?, acessosConsolidador=? WHERE idUsuario = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setInt(2, nivel);
            valores.setString(3, login);
            valores.setString(4, senha);
            valores.setInt(5, idEmpresa);
            valores.setString(6, email);
            valores.setInt(7, usaPortalPostal);
            valores.setString(8, acessosPortalPostal);
            valores.setInt(9, usaConsolidador);
            valores.setString(10, acessosConsolidador);
            valores.setInt(11, idUsuario);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarSenha(String senha, int idUsuario) {
        Connection conn = Conexao.conectarGeral();
        String sql = "UPDATE usuarios SET senha=? WHERE idUsuario = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, senha);
            valores.setInt(2, idUsuario);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluir(int idUsuario, String nomeBD) {
        Connection conn = Conexao.conectarGeral();
        String sql = "update usuarios set ativo = 0 where idUsuario = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaSenhaById(int idUsuario, String nomeBD) {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT senha FROM usuarios WHERE idUsuario=?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idUsuario);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("senha");
            } else {
                return "";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaNomeUsuarioById(int idUsuario, String nomeBD) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT nome FROM usuarios WHERE idUsuario = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idUsuario);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String login = result.getString("nome");
                return login;
            } else {
                return "";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static ArrayList consultaTodosUsuariosByIdEmpresa(String nomeBD, int idEmpresa) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM usuarios WHERE ativo = 1 AND idEmpresa = ? ORDER BY nome";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaUsuarios = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idUsuario = result.getInt("idUsuario");
                String nome = result.getString("nome");
                String login = result.getString("login");
                String senha = result.getString("senha");
                String email = result.getString("email");
                String cpf = result.getString("cpf");
                int idNivel = result.getInt("idNivel");
                int ativo = result.getInt("ativo");
                int usaPortalPostal = result.getInt("usaPortalPostal");
                String acessosPortalPostal = result.getString("acessosPortalPostal");
                int usaConsolidador = result.getInt("usaConsolidador");
                String acessosConsolidador = result.getString("acessosConsolidador");
                listaUsuarios.add(new Usuario(idUsuario, idEmpresa, nome, login, senha, email, cpf, ativo, idNivel, usaPortalPostal, acessosPortalPostal, usaConsolidador, acessosConsolidador));
            }
            return listaUsuarios;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static Usuario consultaUsuarioById(int idUsuario, String nomeBD) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM usuarios WHERE idUsuario = ? ORDER BY login";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idUsuario);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                int idEmpresa = result.getInt("idEmpresa");
                String nome = result.getString("nome");
                String login = result.getString("login");
                String senha = result.getString("senha");
                String email = result.getString("email");
                String cpf = result.getString("cpf");
                int idNivel = result.getInt("idNivel");
                int ativo = result.getInt("ativo");
                int usaPortalPostal = result.getInt("usaPortalPostal");
                String acessosPortalPostal = result.getString("acessosPortalPostal");
                int usaConsolidador = result.getInt("usaConsolidador");
                String acessosConsolidador = result.getString("acessosConsolidador");
                return new Usuario(idUsuario, idEmpresa, nome, login, senha, email, cpf, ativo, idNivel, usaPortalPostal, acessosPortalPostal, usaConsolidador, acessosConsolidador);
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static Usuario consultaUsuarioByLogin(String login) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, login);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                int idUsuario = result.getInt("idUsuario");
                int idEmpresa = result.getInt("idEmpresa");
                String nome = result.getString("nome");
                String senha = result.getString("senha");
                String email = result.getString("email");
                String cpf = result.getString("cpf");
                int idNivel = result.getInt("idNivel");
                int ativo = result.getInt("ativo");
                int usaPortalPostal = result.getInt("usaPortalPostal");
                String acessosPortalPostal = result.getString("acessosPortalPostal");
                int usaConsolidador = result.getInt("usaConsolidador");
                String acessosConsolidador = result.getString("acessosConsolidador");
                return new Usuario(idUsuario, idEmpresa, nome, login, senha, email, cpf, ativo, idNivel, usaPortalPostal, acessosPortalPostal, usaConsolidador, acessosConsolidador);
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrUsuario", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
}
