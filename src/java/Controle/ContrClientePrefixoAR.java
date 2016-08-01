/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Amarracao;
import Entidade.ArDigital;
import Entidade.ClientePrefixoAR;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardinho
 */
public class ContrClientePrefixoAR {
        
    public static boolean inserir(int idCliente, String grupoServico, String prefixo, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_prefix_ar (idCliente, grupoServico, prefixo) VALUES (?,?,?);";                
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setString(2, grupoServico);
            valores.setString(3, prefixo);          
            valores.executeUpdate();            
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            ContrErroLog.inserir("Contr Ar Digital", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
        
    public static boolean updateClienteAR(int idCliente, int codigoClienteECT, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET ar_digital = ? WHERE codigo = ?;";                
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, codigoClienteECT);
            valores.setInt(2, idCliente);
            valores.executeUpdate();            
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            ContrErroLog.inserir("Contr Ar Digital", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static int excluir(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "DELETE FROM cliente_prefix_ar WHERE idCliente = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idCliente);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<ClientePrefixoAR> consultaPorCliente(int idCliente, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_prefix_ar WHERE idCliente = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ClientePrefixoAR> listaAmarracao = new ArrayList<ClientePrefixoAR>();
            while(result.next()) {
                ClientePrefixoAR am = new ClientePrefixoAR(result);
                listaAmarracao.add(am);
            }
            return listaAmarracao;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static Map<String, String> consultaMapPorCliente(int idCliente, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_prefix_ar WHERE idCliente = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            Map<String,String> lista = new HashMap<String, String>();
            while(result.next()) {
                lista.put(result.getString("grupoServico"), result.getString("prefixo"));
            }
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
    
}
