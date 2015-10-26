/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.VeiculoManutencao;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael
 */
public class ContrVeiculoManutencao {

    public static List<VeiculoManutencao> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_manutencao";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            List<VeiculoManutencao> listaVeiculos = new ArrayList<VeiculoManutencao>();
            while(result.next()) {
                listaVeiculos.add(criarVeiculo(result));                        
            }
            return listaVeiculos;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static VeiculoManutencao consulta(String nomeBD, VeiculoManutencao veiculo) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_manutencao WHERE idVeiculoManutencao = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculo.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculo(result); }
            return null;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculoManutencao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static void inserir(String nomeBD, VeiculoManutencao veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo_manutencao (idVeiculo, tipo, quilometragem, data, dataAgendamento, dataEntrega, descricao) "
                + " values(?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculo.getIdVeiculo());
            ps.setString(2, veiculo.getTipo());
            ps.setInt(3, veiculo.getQuilometragem());
            ps.setDate(4, new java.sql.Date(veiculo.getData().getTime()));
            ps.setDate(5, new java.sql.Date(veiculo.getDataAgendamento().getTime()));
            ps.setDate(6, new java.sql.Date(veiculo.getDataEntrega().getTime()));
            ps.setString(7, veiculo.getDescricao());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterar(String nomeBD, VeiculoManutencao veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo_manutencao "
                + " SET tipo = ?, quilometragem = ?, data = ?, placa = ?, dataAgendamento = ?, dataEntrega = ?, descricao = ? "
                + "WHERE idVeiculoManutencao = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, veiculo.getTipo());
            ps.setInt(2, veiculo.getQuilometragem());
            ps.setDate(3, new java.sql.Date(veiculo.getData().getTime()));
            ps.setDate(4, new java.sql.Date(veiculo.getDataAgendamento().getTime()));
            ps.setDate(5, new java.sql.Date(veiculo.getDataEntrega().getTime()));
            ps.setString(6, veiculo.getDescricao());
            ps.setInt(7, veiculo.getId());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean limpar(String nomeBD, VeiculoManutencao veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo_manutencao WHERE idVeiculoManutencao = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculo.getId());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    private static VeiculoManutencao criarVeiculo(ResultSet result) throws SQLException {
        return new VeiculoManutencao(
            result.getInt("idVeiculoManutencao"),
            result.getInt("idVeiculo"),
            result.getString("tipo"),  
            result.getInt("quilometragem"),
            result.getDouble("valor"),
            result.getDate("data"),
            result.getDate("dataAgendamento"),
            result.getDate("dataEntrega"),
            result.getString("descricao")  
        );
    }
    
}
