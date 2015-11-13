/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Entidade.ServicoPrefixo;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author scc4
 */
public class ContrServicoPrefixo {
    
    public static int inserir(String grupoServico, String codigosECT, String prefixo, int avista, int status, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into servicos_prefixos (grupo_servico, codigos_ect, avista, prefixo, status) values(?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, grupoServico);
            valores.setString(2, codigosECT);
            valores.setInt(3, avista);
            valores.setString(4, prefixo);
            valores.setInt(5, status);
            valores.executeUpdate();
            
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static int excluirByPrefixo(String prefixo_servico, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "DELETE FROM servicos_prefixos WHERE prefixo = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prefixo_servico);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static int excluirByServico(String grupo_servico, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "DELETE FROM servicos_prefixos WHERE grupo_servico = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, grupo_servico);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<ServicoPrefixo> consultaTodosAmarracao(String grupo_servico, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM servicos_prefixos WHERE grupo_servico = ? ORDER BY prefixo";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, grupo_servico);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoPrefixo> listaAmarracao = new ArrayList<ServicoPrefixo>();
            while (result.next()) {
                int avista = result.getInt("avista");
                int status = result.getInt("status");
                String grupoServico = result.getString("grupo_servico");
                String codigosECT = result.getString("codigos_ect");
                String prefixo = result.getString("prefixo");
                ServicoPrefixo am = new ServicoPrefixo(grupoServico, codigosECT, avista, prefixo, status);
                listaAmarracao.add(am);
            }
            return listaAmarracao;
        } catch (SQLException e) {
            System.out.println(e);
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
    
}
