/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.ContrAmarracao;
import Controle.ContrErroLog;
import Entidade.ArDigital;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardinho
 */
public class ContrArDigital {
    
    public static String inserir(int idCliente, String siglaAR, String nomeCliente, int codClienteECT, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO ar_digital (idCliente,siglaAR,dataArquivo,nomeArquivo,nomeCliente, codClienteECT) VALUES (?,?,NOW(),?,?,?);";        
        String sqlUpdt = "UPDATE ar_digital SET nomeArquivo = ? WHERE idCliente = ? AND siglaAR = ? AND dataArquivo = DATE(NOW()) AND sequenciaArquivo = ?;";        
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setString(2, siglaAR);
            valores.setString(3, "");          
            valores.setString(4, nomeCliente);
            valores.setInt(5, codClienteECT);
            valores.executeUpdate();
            
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }            
            valores.close();
            
            System.out.println(autoIncrementKey);            
            SimpleDateFormat sdf = new SimpleDateFormat("ddMM");
            String seq = ArDigital.getSequenciaByNrSequencia(autoIncrementKey);
            String nomeArquivo = siglaAR+"1"+sdf.format(new Date())+seq+".SD1";
            
            PreparedStatement valores2 = conn.prepareStatement(sqlUpdt);
            valores2.setString(1, nomeArquivo);
            valores2.setInt(2, idCliente);
            valores2.setString(3, siglaAR);
            valores2.setInt(4, autoIncrementKey);
            System.out.println(valores2.toString());
            valores2.executeUpdate();            
            valores2.close();
            
            return nomeArquivo;
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            ContrErroLog.inserir("Contr Ar Digital", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static String updatePreVenda(int idPV, String nomeArquivo, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE pre_venda SET arquivo_ar = ? WHERE id = ?;";        
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, nomeArquivo);
            valores.setInt(2, idPV);
            valores.executeUpdate();            
            valores.close();
            return "nome_arquivo";
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            ContrErroLog.inserir("Contr Ar Digital", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
        public static ArDigital consultaPorNomeArquivo(String nomeArquivo, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM ar_digital WHERE nomeArquivo = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, nomeArquivo);
            System.out.println(valores.toString());
            ResultSet result = (ResultSet) valores.executeQuery();
            ArDigital ar = null;
            while(result.next()) {
                ar = new ArDigital(result);
            }
            return ar;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
    
}
