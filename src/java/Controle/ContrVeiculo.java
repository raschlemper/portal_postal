/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Veiculo;
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
public class ContrVeiculo {

    public static List<Veiculo> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
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

    public static Veiculo consulta(String nomeBD, Veiculo veiculo) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo WHERE idVeiculo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculo.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculo(result); }
            return null;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static void inserir(String nomeBD, Veiculo veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo (tipo, marca, modelo, placa, anoFabricacao, anoModelo, chassis, renavam, quilometragem, "
                + "combustivel, status, situacao) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, veiculo.getTipo());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());
            ps.setString(4, veiculo.getPlaca());
            if(veiculo.getAnoFabricacao() == null) { ps.setNull(5, Types.INTEGER); }
            else { ps.setInt(5, veiculo.getAnoFabricacao()); }
            if(veiculo.getAnoModelo() == null) { ps.setNull(6, Types.INTEGER); }
            else { ps.setInt(6, veiculo.getAnoModelo()); }
            if(veiculo.getChassis() == null) { ps.setNull(7, Types.VARCHAR); }
            else { ps.setString(7, veiculo.getChassis()); }
            ps.setString(8, veiculo.getRenavam());
            ps.setInt(9, veiculo.getQuilometragem());
            ps.setString(10, veiculo.getCombustivel());
            ps.setString(11, veiculo.getStatus());
            ps.setString(12, veiculo.getSituacao());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterar(String nomeBD, Veiculo veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo "
                + " SET tipo = ?, marca = ?, modelo = ?, placa = ?, anoFabricacao = ?, anoModelo = ?, chassis = ?, renavam = ?, "
                + " quilometragem = ?, combustivel = ?, status = ?, situacao = ? WHERE idVeiculo = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, veiculo.getTipo());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());
            ps.setString(4, veiculo.getPlaca());
            if(veiculo.getAnoFabricacao() == null) { ps.setNull(5, Types.INTEGER); }
            else { ps.setInt(5, veiculo.getAnoFabricacao()); }
            if(veiculo.getAnoModelo() == null) { ps.setNull(6, Types.INTEGER); }
            else { ps.setInt(6, veiculo.getAnoModelo()); }
            if(veiculo.getChassis() == null) { ps.setNull(7, Types.VARCHAR); }
            else { ps.setString(7, veiculo.getChassis()); }
            ps.setString(8, veiculo.getRenavam());
            ps.setInt(9, veiculo.getQuilometragem());
            ps.setString(10, veiculo.getCombustivel());
            ps.setString(11, veiculo.getStatus());
            ps.setString(12, veiculo.getSituacao());
            ps.setInt(13, veiculo.getId());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean limpar(String nomeBD, Veiculo veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo WHERE idVeiculo = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculo.getId());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static List<Veiculo> consultaByPlaca(String nomeBD, Veiculo veiculo) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo WHERE placa = ? ";
        if(veiculo.getId() != null) { sql += " AND idVeiculo <> ? "; }
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, veiculo.getPlaca());
            if(veiculo.getId() != null) { ps.setInt(2, veiculo.getId()); }
            ResultSet result = (ResultSet) ps.executeQuery();
            List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
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
    
    private static Veiculo criarVeiculo(ResultSet result) throws SQLException {
        return new Veiculo(
            result.getInt("idVeiculo"),
            result.getString("tipo"),  
            result.getString("marca"),  
            result.getString("modelo"),  
            result.getString("placa"),
            result.getInt("anoFabricacao"),
            result.getInt("anoModelo"),
            result.getString("chassis"),
            result.getString("renavam"),
            result.getInt("quilometragem"),
            result.getString("combustivel"),
            result.getString("status"),
            result.getString("situacao")
        );
    }
    
}
