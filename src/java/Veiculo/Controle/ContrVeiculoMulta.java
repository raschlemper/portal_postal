package Veiculo.Controle;

import Controle.ContrErroLog;
import Veiculo.Entidade.VeiculoMulta;
import Util.Conexao;
import Veiculo.builder.VeiculoMultaBuilder;
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

public class ContrVeiculoMulta {

    public static List<VeiculoMulta> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_multa, veiculo WHERE veiculo.idVeiculo = veiculo_multa.idVeiculo "
                + "ORDER BY veiculo_multa.idVeiculo, veiculo_multa.data";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            List<VeiculoMulta> listaVeiculos = new ArrayList<VeiculoMulta>();
            while(result.next()) {
                listaVeiculos.add(criarVeiculoMulta(result));                        
            }
            return listaVeiculos;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return new ArrayList();
    }

    public static VeiculoMulta consulta(String nomeBD, VeiculoMulta veiculoMulta) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_multa, veiculo WHERE veiculo.idVeiculo = veiculo_multa.idVeiculo AND idVeiculoMulta = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculoMulta.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculoMulta(result); }
            return null;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculoMulta.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return null;
    }

    public static VeiculoMulta inserir(String nomeBD, VeiculoMulta veiculoMulta) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo_multa (idVeiculo, numeroMulta, data, valor, local, descricao) "
                   + "VALUES(?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, veiculoMulta.getVeiculo().getId());
            ps.setInt(2, veiculoMulta.getNumeroMulta());
            ps.setDate(3, new java.sql.Date(veiculoMulta.getData().getTime()));
            ps.setDouble(4, veiculoMulta.getValor());
            if(veiculoMulta.getLocal() == null) { ps.setNull(5, Types.VARCHAR); }
            else { ps.setString(5, veiculoMulta.getLocal()); }
            if(veiculoMulta.getDescricao() == null) { ps.setNull(6, Types.VARCHAR); }
            else { ps.setString(6, veiculoMulta.getDescricao()); }
            ps.executeUpdate();
            veiculoMulta.setId(getLastId(ps));
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoMulta);
    }
    
    public static VeiculoMulta alterar(String nomeBD, VeiculoMulta veiculoMulta) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo_multa "
                   + "SET numeroMulta = ?, data = ?, valor = ?, local = ?, descricao = ? "
                   + "WHERE idVeiculoMulta = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculoMulta.getNumeroMulta());
            ps.setDate(2, new java.sql.Date(veiculoMulta.getData().getTime()));
            ps.setDouble(3, veiculoMulta.getValor());
            if(veiculoMulta.getLocal() == null) { ps.setNull(4, Types.VARCHAR); }
            else { ps.setString(4, veiculoMulta.getLocal()); }
            if(veiculoMulta.getDescricao() == null) { ps.setNull(5, Types.VARCHAR); }
            else { ps.setString(5, veiculoMulta.getDescricao()); }
            ps.setInt(6, veiculoMulta.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoMulta);
    }
    
    public static VeiculoMulta limpar(String nomeBD, VeiculoMulta veiculoMulta) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo_multa WHERE idVeiculoMulta = ? ";
        try {
            veiculoMulta = consulta(nomeBD, veiculoMulta);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculoMulta.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return veiculoMulta;
    }
    
    private static Integer getLastId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
    
    private static VeiculoMulta criarVeiculoMulta(ResultSet result) throws SQLException {
        VeiculoMultaBuilder builder = new VeiculoMultaBuilder();
        return builder.toEntidade(result);
    }
    
}
