/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Nivel;
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
public class contrNivel {

    public static ArrayList consultaTodosNiveis(String nomeBD) throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT * FROM nivel";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idNivel = result.getInt("idNivel");
                String nivel = result.getString("nivel");
                Entidade.Nivel st = new Nivel(idNivel, nivel);
                listaStatus.add(st);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaNomeByIdNivel(int idNivel, String nomeBD) {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT nivel FROM nivel WHERE idNivel = " + idNivel;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("nivel");
            } else {
                return "sem nivel!";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
