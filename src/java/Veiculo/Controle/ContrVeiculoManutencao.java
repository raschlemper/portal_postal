/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Veiculo.Controle;

import Controle.ContrErroLog;
import Veiculo.Entidade.Veiculo;
import Veiculo.Entidade.VeiculoManutencao;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
        String sql = "SELECT * FROM veiculo_manutencao, veiculo WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            List<VeiculoManutencao> listaVeiculos = new ArrayList<VeiculoManutencao>();
            while(result.next()) {
                listaVeiculos.add(criarVeiculoManutencao(result));                        
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
        String sql = "SELECT * FROM veiculo_manutencao, veiculo WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo AND idVeiculoManutencao = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculo.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculoManutencao(result); }
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
        String sql = "INSERT INTO veiculo_manutencao (idVeiculo, tipo, quilometragem, valor, data, dataAgendamento, dataEntrega, descricao) "
                + " values(?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculo.getVeiculo().getId());
            ps.setString(2, veiculo.getTipo());
            ps.setInt(3, veiculo.getQuilometragem());
            ps.setDouble(4, veiculo.getValor());
            ps.setDate(5, new java.sql.Date(veiculo.getData().getTime()));
            if(veiculo.getDataAgendamento() == null) { ps.setNull(6, Types.DATE); }
            else { ps.setDate(6, new java.sql.Date(veiculo.getDataAgendamento().getTime())); }
            if(veiculo.getDataEntrega() == null) { ps.setNull(7, Types.DATE); }
            else { ps.setDate(7, new java.sql.Date(veiculo.getDataEntrega().getTime())); }
            ps.setString(8, veiculo.getDescricao());
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
    
    private static VeiculoManutencao criarVeiculoManutencao(ResultSet result) throws SQLException {
        return new VeiculoManutencao(
            result.getInt("veiculo_manutencao.idVeiculoManutencao"),
            criarVeiculo(result),
            result.getString("veiculo_manutencao.tipo"),  
            result.getInt("veiculo_manutencao.quilometragem"),
            result.getDouble("veiculo_manutencao.valor"),
            result.getDate("veiculo_manutencao.data"),
            result.getDate("veiculo_manutencao.dataAgendamento"),
            result.getDate("veiculo_manutencao.dataEntrega"),
            result.getString("veiculo_manutencao.descricao")  
        );
    }
    
    private static Veiculo criarVeiculo(ResultSet result) throws SQLException {
        return new Veiculo(
            result.getInt("veiculo.idVeiculo"),
            result.getString("veiculo.tipo"),  
            result.getString("veiculo.marca"),  
            result.getString("veiculo.modelo"),  
            result.getString("veiculo.placa"),
            result.getInt("veiculo.anoFabricacao"),
            result.getInt("veiculo.anoModelo"),
            result.getString("veiculo.chassis"),
            result.getString("veiculo.renavam"),
            result.getInt("veiculo.quilometragem"),
            result.getString("veiculo.combustivel"),
            result.getString("veiculo.status"),
            result.getString("veiculo.situacao")
        );
    }
    
}
