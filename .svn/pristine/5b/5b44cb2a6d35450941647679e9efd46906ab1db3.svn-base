/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Entidade.StatusEntrega;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RICADINHO
 */
public class ContrStatusEntrega {

    public static ArrayList<StatusEntrega> consultaTodosStatus(String nomeBD){
       Connection conn = Conexao.conectar(nomeBD);
       String sql =  "SELECT * FROM coleta_status";
       try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<StatusEntrega> listaStatus = new ArrayList<StatusEntrega>();
            for(int i=0; result.next(); i++){
                int id = result.getInt("id");
                int isCancelar = result.getInt("isCancelar");
                String nome = result.getString("nome");
                StatusEntrega st = new StatusEntrega(id, nome, isCancelar);
                listaStatus.add(st);
            }
            valores.close();
            return listaStatus;
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrStatusEntrega", "SQLException", sql, e.toString());
            return null;
        }finally{
            Conexao.desconectar(conn);
        }
    }

    public static String consultaNomeStatus(int id, String nomeBD){
       Connection conn = Conexao.conectar(nomeBD);
       String sql =  "SELECT * FROM coleta_status WHERE id = "+id;
       try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if(result.next()){
                return result.getString("nome");
            }else{
                return "";
            }
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrStatusEntrega", "SQLException", sql, e.toString());
            return "";
        }finally{
            Conexao.desconectar(conn);
        }
    }

}
