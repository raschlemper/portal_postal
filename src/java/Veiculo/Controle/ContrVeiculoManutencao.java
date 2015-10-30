package Veiculo.Controle;

import Controle.ContrErroLog;
import Veiculo.Entidade.VeiculoManutencao;
import Util.Conexao;
import Veiculo.builder.VeiculoManutencaoBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        } finally {
            Conexao.desconectar(con);
        }
        return new ArrayList();
    }

    public static VeiculoManutencao consulta(String nomeBD, VeiculoManutencao veiculoManutencao) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_manutencao, veiculo WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo AND idVeiculoManutencao = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculoManutencao.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculoManutencao(result); }
            return null;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculoManutencao.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return null;
    }

    public static VeiculoManutencao inserir(String nomeBD, VeiculoManutencao veiculoManutencao) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo_manutencao (idVeiculo, tipo, quilometragem, valor, data, dataAgendamento, dataEntrega, descricao) "
                   + "VALUES(?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, veiculoManutencao.getVeiculo().getId());
            ps.setString(2, veiculoManutencao.getTipo());
            ps.setInt(3, veiculoManutencao.getQuilometragem());
            ps.setDouble(4, veiculoManutencao.getValor());
            ps.setDate(5, new java.sql.Date(veiculoManutencao.getData().getTime()));
            if(veiculoManutencao.getDataAgendamento() == null) { ps.setNull(6, Types.DATE); }
            else { ps.setDate(6, new java.sql.Date(veiculoManutencao.getDataAgendamento().getTime())); }
            if(veiculoManutencao.getDataEntrega() == null) { ps.setNull(7, Types.DATE); }
            else { ps.setDate(7, new java.sql.Date(veiculoManutencao.getDataEntrega().getTime())); }
            ps.setString(8, veiculoManutencao.getDescricao());
            ps.executeUpdate();
            veiculoManutencao.setId(getLastId(ps));
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoManutencao);
    }
    
    public static VeiculoManutencao alterar(String nomeBD, VeiculoManutencao veiculoManutencao) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo_manutencao "
                   + "SET tipo = ?, quilometragem = ?, valor = ?, data = ?, dataAgendamento = ?, dataEntrega = ?, descricao = ? "
                   + "WHERE idVeiculoManutencao = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, veiculoManutencao.getTipo());
            ps.setInt(2, veiculoManutencao.getQuilometragem());
            ps.setDouble(3, veiculoManutencao.getValor());
            ps.setDate(4, new java.sql.Date(veiculoManutencao.getData().getTime()));            
            if(veiculoManutencao.getDataAgendamento() == null) { ps.setNull(5, Types.DATE); }
            else { ps.setDate(5, new java.sql.Date(veiculoManutencao.getDataAgendamento().getTime())); }
            if(veiculoManutencao.getDataEntrega() == null) { ps.setNull(6, Types.DATE); }
            else { ps.setDate(6, new java.sql.Date(veiculoManutencao.getDataEntrega().getTime())); }
            ps.setString(7, veiculoManutencao.getDescricao());
            ps.setInt(8, veiculoManutencao.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoManutencao);
    }
    
    public static VeiculoManutencao limpar(String nomeBD, VeiculoManutencao veiculoManutencao) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo_manutencao WHERE idVeiculoManutencao = ? ";
        try {
            veiculoManutencao = consulta(nomeBD, veiculoManutencao);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculoManutencao.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return veiculoManutencao;
    }
    
    private static Integer getLastId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
    
    private static VeiculoManutencao criarVeiculoManutencao(ResultSet result) throws SQLException {
        VeiculoManutencaoBuilder builder = new VeiculoManutencaoBuilder();
        return builder.toEntidade(result);
    }
    
}
