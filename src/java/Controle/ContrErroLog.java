/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author RICADINHO
 */
public class ContrErroLog {

    public static int inserir(String local, String tipo, String sql_query, String mensagem) {
//        Connection conn = Conexao.conectarSCC4();
//        String sql = "INSERT INTO erro_log (local, tipo, sql_query, mensagem, data_hora) VALUES(?,?,?,?,NOW())";
//        try {
//            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//            valores.setString(1, local);
//            valores.setString(2, tipo);
//            valores.setString(3, sql_query);
//            valores.setString(4, mensagem);
//            valores.executeUpdate();
//            int autoIncrementKey = 0;
//            ResultSet rs = valores.getGeneratedKeys();
//            if (rs.next()) {
//                autoIncrementKey = rs.getInt(1);
//            }
//            valores.close();
//            return autoIncrementKey;
//        } catch (SQLException e) {
//            Logger.getLogger(ContrErroLog.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return 0;
//        } finally {
//            Conexao.desconectar(conn);
//        }
    }

}
