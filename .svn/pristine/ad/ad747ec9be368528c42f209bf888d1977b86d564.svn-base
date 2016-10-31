/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.GrupoFaturamento;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author SCC4
 */
public class ContrGrupoFaturamento {

    public static void inserir(String nome, String sigla, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO grupo_faturamento (nome, sigla, ativo) VALUES (?, ?, 1);";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setString(2, sigla);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterar(String nome, String sigla, int id, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE grupo_faturamento SET nome=?, sigla=? WHERE id = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setString(2, sigla);
            valores.setInt(3, id);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluir(int id, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE grupo_faturamento SET ativo = 0 WHERE id = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<GrupoFaturamento> consultaTodosTipoColeta(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM grupo_faturamento WHERE ativo = 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<GrupoFaturamento> listaStatus = new ArrayList<GrupoFaturamento>();
            while (result.next()) {
                int id = result.getInt("id");
                String nome = result.getString("nome");
                String sigla = result.getString("sigla");
                GrupoFaturamento ent = new GrupoFaturamento(id, nome, sigla);
                listaStatus.add(ent);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }


    public static GrupoFaturamento consultaById(int id, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM grupo_faturamento WHERE id = ?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, id);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String nome = result.getString("nome");
                String sigla = result.getString("sigla");
                return new GrupoFaturamento(id, nome, sigla);
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
