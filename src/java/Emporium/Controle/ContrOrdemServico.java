/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.ContrAmarracao;
import Entidade.OrdemServico;
import Util.Conexao;
import Util.FormatarData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Correios
 */
public class ContrOrdemServico {
    
    
    public static int inserir(int idCliente, int idUsuario, String nomeUsuario, int qtd, String ids, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO ordem_servico (idCliente, idUsuario, nomeUsuario, dataOs, qtdObjetos) VALUES(?,?,?,NOW(),?)";
        String sql2 = "UPDATE pre_venda SET idOs = ?, isSync = 0 WHERE id IN ("+ids+")";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setInt(2, idUsuario);
            valores.setString(3, nomeUsuario);
            valores.setInt(4, qtd);
            valores.executeUpdate();
            
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            
            PreparedStatement valores2 = conn.prepareStatement(sql2);
            valores2.setInt(1, autoIncrementKey);
            valores2.executeUpdate();
            valores2.close();
            
            return autoIncrementKey;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static int removerObjeto(int idPV, int idOS, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE ordem_servico SET qtdObjetos = (qtdObjetos-1) WHERE idOs = " + idOS;
        String sql2 = "UPDATE pre_venda SET idOs = 0, isSync = 0 WHERE id = " + idPV;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            
            PreparedStatement valores2 = conn.prepareStatement(sql2);
            valores2.executeUpdate();
            valores2.close();
            
            return 1;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static HashMap<String, Integer> consultaQtdServicosOS(int idOS, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT nomeServico, COUNT(nomeServico) AS qtd FROM pre_venda WHERE idOS = "+idOS+" GROUP BY nomeServico;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = valores.executeQuery();
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            while(result.next()){
                String nomeServico = result.getString("nomeServico");
                int qtd = result.getInt("qtd");
                map.put(nomeServico, qtd);
            }
            valores.close();
            return map;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static OrdemServico consultaOS(int idOS, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM ordem_servico WHERE idOS = "+idOS+";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = valores.executeQuery();
            OrdemServico os = null;
            if(result.next()){
                int idOs = result.getInt("idOs");
                int idCliente = result.getInt("idCliente");
                int idUsuario = result.getInt("idUsuario");
                String nomeUsuario = result.getString("nomeUsuario");
                Timestamp dataOs = result.getTimestamp("dataOs");
                int qtdObjetos = result.getInt("qtdObjetos");
                os = new OrdemServico(idOs, idCliente, idUsuario, nomeUsuario, dataOs, qtdObjetos);
            }
            valores.close();
            return os;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<OrdemServico> pesquisaOS(int idCliente, String idOS, String data, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        
        if(idOS == null || idOS.equals("")){
            idOS = "0";
        }
        data = FormatarData.DateToBDcomAspas(data);
        
        String sql = "SELECT * FROM ordem_servico WHERE idCliente = "+idCliente+" AND (idOS = "+idOS+" OR DATE(dataOs) = " + data + ");";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = valores.executeQuery();
            ArrayList<OrdemServico> os = new ArrayList<OrdemServico>();
            while(result.next()){
                int idOs = result.getInt("idOs");
                int idCli = result.getInt("idCliente");
                int idUsuario = result.getInt("idUsuario");
                String nomeUsuario = result.getString("nomeUsuario");
                Timestamp dataOs = result.getTimestamp("dataOs");
                int qtdObjetos = result.getInt("qtdObjetos");
                os.add(new OrdemServico(idOs, idCli, idUsuario, nomeUsuario, dataOs, qtdObjetos));
            }
            valores.close();
            return os;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
