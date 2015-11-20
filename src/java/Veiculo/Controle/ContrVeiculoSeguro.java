package Veiculo.Controle;

import Controle.ContrErroLog;
import Veiculo.Entidade.VeiculoSeguro;
import Util.Conexao;
import Veiculo.builder.VeiculoSeguroBuilder;
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

public class ContrVeiculoSeguro {

    public static List<VeiculoSeguro> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_seguro, veiculo WHERE veiculo.idVeiculo = veiculo_seguro.idVeiculo ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            List<VeiculoSeguro> listaVeiculos = new ArrayList<VeiculoSeguro>();
            while(result.next()) {
                listaVeiculos.add(criarVeiculoSeguro(result));                        
            }
            return listaVeiculos;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return new ArrayList();
    }

    public static VeiculoSeguro consulta(String nomeBD, VeiculoSeguro veiculoSeguro) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_seguro, veiculo WHERE veiculo.idVeiculo = veiculo_seguro.idVeiculo AND idVeiculoSeguro = ?"
                + "ORDER BY veiculo_seguro.idVeiculo";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculoSeguro.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculoSeguro(result); }
            return null;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculoSeguro.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return null;
    }

    public static VeiculoSeguro inserir(String nomeBD, VeiculoSeguro veiculoSeguro) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo_seguro (idVeiculo, numeroSeguro, assegurado, valorFranquia, indenizacao) "
                   + "VALUES(?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, veiculoSeguro.getVeiculo().getId());
            ps.setInt(2, veiculoSeguro.getNumeroSeguro());
            if(veiculoSeguro.getAssegurado() == null) { ps.setNull(3, Types.VARCHAR); }
            else { ps.setString(3, veiculoSeguro.getAssegurado()); }
            if(veiculoSeguro.getValorFranquia() == null) { ps.setNull(4, Types.DOUBLE); }
            else { ps.setDouble(4, veiculoSeguro.getValorFranquia()); }
            if(veiculoSeguro.getIndenizacao() == null) { ps.setNull(5, Types.VARCHAR); }
            else { ps.setString(5, veiculoSeguro.getIndenizacao()); }
            ps.executeUpdate();
            veiculoSeguro.setId(getLastId(ps));
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoSeguro);
    }
    
    public static VeiculoSeguro alterar(String nomeBD, VeiculoSeguro veiculoSeguro) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo_seguro "
                   + "SET numeroSeguro = ?, assegurado = ?, valorFranquia = ?, indenizacao = ? "
                   + "WHERE idVeiculoSeguro = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculoSeguro.getNumeroSeguro());
            if(veiculoSeguro.getAssegurado() == null) { ps.setNull(2, Types.VARCHAR); }
            else { ps.setString(2, veiculoSeguro.getAssegurado()); }
            if(veiculoSeguro.getValorFranquia() == null) { ps.setNull(3, Types.DOUBLE); }
            else { ps.setDouble(3, veiculoSeguro.getValorFranquia()); }
            if(veiculoSeguro.getIndenizacao() == null) { ps.setNull(4, Types.VARCHAR); }
            else { ps.setString(4, veiculoSeguro.getIndenizacao()); }
            ps.setInt(5, veiculoSeguro.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoSeguro);
    }
    
    public static VeiculoSeguro limpar(String nomeBD, VeiculoSeguro veiculoSeguro) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo_seguro WHERE idVeiculoSeguro = ? ";
        try {
            veiculoSeguro = consulta(nomeBD, veiculoSeguro);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculoSeguro.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return veiculoSeguro;
    }
    
    private static Integer getLastId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
    
    private static VeiculoSeguro criarVeiculoSeguro(ResultSet result) throws SQLException {
        VeiculoSeguroBuilder builder = new VeiculoSeguroBuilder();
        return builder.toEntidade(result);
    }
    
}
