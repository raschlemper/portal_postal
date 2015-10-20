/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Coleta.Controle;

import Controle.ContrErroLog;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author SCC4
 */
public class contrLogRotaFixaCarregada {

    public static void inserir(int idUsuario, String nomeBD) throws SQLException{
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_coleta_fixa (idUsuario, dataHoraCarregada) values(?,NOW());";
        try{
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1,idUsuario);
            valores.executeUpdate();
            valores.close();
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrLogRotaFixaCarregada", "SQLException", sql, e.toString());
        }finally{
            Conexao.desconectar(conn);
        }
    }

    public static Timestamp consultaUltimaRotaCarregada(String nomeBD){
       Connection conn = Conexao.conectar(nomeBD);
       String sql =  "SELECT dataHoraCarregada FROM log_coleta_fixa ORDER BY dataHoraCarregada DESC LIMIT 1;";
       try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if(result.next()){
                return result.getTimestamp("dataHoraCarregada");
            }else{
                return null;
            }
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrLogRotaFixaCarregada", "SQLException", sql, e.toString());
            return null;
        }finally{
            Conexao.desconectar(conn);
        }
    }
}
