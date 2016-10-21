/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Controle;

import Coleta.Entidade.TipoColeta;
import Controle.ContrErroLog;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCC4
 */
public class contrTipoColeta {

    public static void inserir(String tipo, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into coleta_tipos (tipo, ativo) values(?, 1)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, tipo);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterar(String tipo, int idTipoColeta, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta_tipos set tipo=? where idTipoColeta = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, tipo);
            valores.setInt(2, idTipoColeta);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluir(int idTipoColeta, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta_tipos set ativo = 0 where idTipoColeta = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idTipoColeta);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaTodosTipoColeta(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta_tipos WHERE ativo = 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idTipoColeta = result.getInt("idTipoColeta");
                String tipo = result.getString("tipo");
                Coleta.Entidade.TipoColeta tipoColeta = new TipoColeta(idTipoColeta, tipo);
                listaStatus.add(tipoColeta);
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

    public static int sorteiaTipoColeta(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT idTipoColeta FROM coleta_tipos WHERE ativo = 1 ORDER BY RAND() LIMIT 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getInt("idTipoColeta");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static TipoColeta consultaTipoColetaById(int idTipoColeta, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta_tipos WHERE idTipoColeta=?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idTipoColeta);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String tipo = result.getString("tipo");
                return new TipoColeta(idTipoColeta, tipo);
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

    public static String consultaNomeTipoColetaById(int idTipoColeta, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT tipo FROM coleta_tipos WHERE idTipoColeta=?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idTipoColeta);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("tipo");
            } else {
                return "";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrTipoColeta", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
