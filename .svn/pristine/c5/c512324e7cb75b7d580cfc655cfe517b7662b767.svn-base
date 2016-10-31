/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Entidade.LogColeta;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author RICADINHO
 */
public class ContrLogColeta {

    public static void inserir(int idColeta, int idUsuario, String nomeUsuario, String acao, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_coleta (idColeta, idUsuario, nomeUsuario, dataHora, acao) values(?,?,?,NOW(),?)";
        try{
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1,idColeta);
            valores.setInt(2,idUsuario);
            valores.setString(3,nomeUsuario);
            valores.setString(4,acao);
            valores.executeUpdate();
            valores.close();
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrLogColeta", "SQLException", sql, e.toString());
        }finally{
            Conexao.desconectar(conn);
        }
    }

    public static void inserirGPS(int idColeta, int idColetador, String nomeUsuario, String acao, double latitude, double longitude, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_coleta (idColeta, idColetador, nomeUsuario, dataHora, acao, latitude, longitude) values(?,?,?,NOW(),?,?,?)";
        try{
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1,idColeta);
            valores.setInt(2,idColetador);
            valores.setString(3,nomeUsuario);
            valores.setString(4,acao);
            valores.setDouble(5,latitude);
            valores.setDouble(6,longitude);
            valores.executeUpdate();
            valores.close();
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrLogColeta", "SQLException", sql, e.toString());
        }finally{
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<LogColeta> consultaLogColetaById(int idColeta, String nomeBD){
       Connection conn = Conexao.conectar(nomeBD);
       String sql =  "SELECT * FROM log_coleta WHERE idColeta = ? ORDER BY dataHora";
       try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColeta);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<LogColeta> listaStatus = new ArrayList<LogColeta>();
            for(int i=0; result.next(); i++){
                int id = result.getInt("id");
                int idUsuario = result.getInt("idUsuario");
                String nomeUsuario = result.getString("nomeUsuario");
                String acao = result.getString("acao");
                Timestamp dataHora = result.getTimestamp("dataHora");
                LogColeta lc = new LogColeta(id, idColeta, idUsuario, nomeUsuario, dataHora, acao);
                listaStatus.add(lc);
            }
            valores.close();
            return listaStatus;
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrLogColeta", "SQLException", sql, e.toString());
            return null;
        }finally{
            Conexao.desconectar(conn);
        }
    }

}
