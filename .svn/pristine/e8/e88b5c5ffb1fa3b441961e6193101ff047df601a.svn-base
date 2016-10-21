/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Entidade.LogAtualizacaoContratos;
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
 * @author scc4
 */
public class ContrLogAtualizacaoContrato {
    
    
    public static void inserir(int idUsuario, String nomeUsuario, String msgSucesso, String msgFalha, String codSucesso, String codFalha, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_atualizacao_contratos (dataHora, idUsuario, nomeUsuario, msgSucesso, msgFalha, codSucesso, codFalha) values(NOW(),?,?,?,?,?,?)";
        try{
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1,idUsuario);
            valores.setString(2,nomeUsuario);
            valores.setString(3,msgSucesso);
            valores.setString(4,msgFalha);
            valores.setString(5,codSucesso);
            valores.setString(6,codFalha);
            valores.executeUpdate();
            valores.close();
        }catch(SQLException e){
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrLogColeta", "SQLException", sql, e.toString());
        }finally{
            Conexao.desconectar(conn);
        }
    }
    
    public static LogAtualizacaoContratos consultaUltimoLog(String nomeBD){
       Connection conn = Conexao.conectar(nomeBD);
       String sql =  "SELECT * FROM log_atualizacao_contratos ORDER BY dataHora DESC LIMIT 1";
       try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            LogAtualizacaoContratos log = null;
            if(result.next()){
                int id = result.getInt("id");
                int idUsuario = result.getInt("idUsuario");
                String nomeUsuario = result.getString("nomeUsuario");
                String msgSucesso = result.getString("msgSucesso");
                String msgFalha = result.getString("msgFalha");
                String codSucesso = result.getString("codSucesso");
                String codFalha = result.getString("codFalha");
                Timestamp dataHora = result.getTimestamp("dataHora");
                
                log = new LogAtualizacaoContratos(id, dataHora, msgSucesso, msgFalha, codSucesso, codFalha, idUsuario, nomeUsuario);
            }
            valores.close();
            return log;
        }catch(SQLException e){
            ContrErroLog.inserir("HOITO - contrLogColeta", "SQLException", sql, e.toString());
            return null;
        }finally{
            Conexao.desconectar(conn);
        }
    }
    
}
