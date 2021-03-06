package generic.Controles;

import Controle.ContrErroLog;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mysqlGenerics {

    public static void insert_update_generic(String sql, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        sql = protegeHackSQL(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("mysqlGenerics.insert_update_generic", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }


    public static ResultSet select_generic(String sql, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        sql = protegeHackSQL(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            return (ResultSet) valores.executeQuery();
        } catch (SQLException e) {
            ContrErroLog.inserir("mysqlGenerics.select_generic", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    private static String protegeHackSQL(String sql) {
        String aux = sql.toUpperCase();
        if (aux.contains("DELETE") || aux.contains("DROP") || aux.contains("TRUNCATE")) {
            sql = "";
        }
        return sql;
    }
}
