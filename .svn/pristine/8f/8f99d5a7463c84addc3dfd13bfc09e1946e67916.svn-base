
package Coleta.Controle;

import Coleta.Entidade.HoraColeta;
import Controle.ContrErroLog;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class contrHoraColeta {
    
    public static void inserir(Time horaIniAcesso, Time horaFimAccesso, Time horaIniColeta, Time horaFimColeta, int  minAntecedencia, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into coleta_horarios (id, horaIniAcesso, horaFimAcesso, horaIniColeta, horaFimColeta, minAntecedencia) values(1,?,?,?,?,?);";
        try{
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setTime(1,horaIniAcesso);
            valores.setTime(2,horaFimAccesso);
            valores.setTime(3,horaIniColeta);
            valores.setTime(4,horaFimColeta);
            valores.setInt(5,minAntecedencia);
            valores.executeUpdate();
            valores.close();
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrHoraColeta", "SQLException", sql, e.toString());
        }finally{
            Conexao.desconectar(conn);
        }
    }

    public static int alterar(Time horaIniAcesso, Time horaFimAccesso, Time horaIniColeta, Time horaFimColeta, int  minAntecedencia, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta_horarios set horaIniAcesso = ?, horaFimAcesso = ?, horaIniColeta = ?, horaFimColeta = ?, minAntecedencia = ? where id = 1;";
        try{
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setTime(1,horaIniAcesso);
            valores.setTime(2,horaFimAccesso);
            valores.setTime(3,horaIniColeta);
            valores.setTime(4,horaFimColeta);
            valores.setInt(5,minAntecedencia);
            int i = valores.executeUpdate();
            return i;
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrHoraColeta", "SQLException", sql, e.toString());
            return -1;
        }finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static HoraColeta consultaHoraColetaById(String nomeBD){
       Connection conn = Conexao.conectar(nomeBD);
       String sql =  "SELECT * FROM coleta_horarios WHERE id = 1;";
       try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if(result.next()){
                int id = result.getInt("id");
                Time horaIniAcesso = result.getTime("horaIniAcesso");
                Time horaFimAcesso = result.getTime("horaFimAcesso");
                Time horaIniColeta = result.getTime("horaIniColeta");
                Time horaFimColeta = result.getTime("horaFimColeta");
                int minAntecedencia = result.getInt("minAntecedencia");   
                return new HoraColeta(id, horaIniAcesso, horaFimAcesso, horaIniColeta, horaFimColeta, minAntecedencia);
            }else{
                return null;
            }
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrHoraColeta", "SQLException", sql, e.toString());
            return null;
        }finally{
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificaExistenciaHoraColeta(String nomeBD){
       Connection conn = Conexao.conectar(nomeBD);
       String sql =  "SELECT * FROM coleta_horarios WHERE id = 1;";
       try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if(result.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrHoraColeta", "SQLException", sql, e.toString());
            return false;
        }finally{
            Conexao.desconectar(conn);
        }
    }

}
