package Veiculo.Controle;

import Controle.ContrErroLog;
import Veiculo.Entidade.VeiculoCombustivel;
import Util.Conexao;
import Veiculo.Entidade.Veiculo;
import Veiculo.builder.VeiculoCombustivelBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContrVeiculoCombustivel {

    public static List<VeiculoCombustivel> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_combustivel, veiculo WHERE veiculo.idVeiculo = veiculo_combustivel.idVeiculo ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            List<VeiculoCombustivel> listaVeiculos = new ArrayList<VeiculoCombustivel>();
            while(result.next()) {
                listaVeiculos.add(criarVeiculoCombustivel(result));                        
            }
            return listaVeiculos;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return new ArrayList();
    }

    public static VeiculoCombustivel consulta(String nomeBD, VeiculoCombustivel veiculoCombustivel) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_combustivel, veiculo WHERE veiculo.idVeiculo = veiculo_combustivel.idVeiculo AND idVeiculoCombustivel = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculoCombustivel.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculoCombustivel(result); }
            return null;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculoCombustivel.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return null;
    }

    public static VeiculoCombustivel inserir(String nomeBD, VeiculoCombustivel veiculoCombustivel) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo_combustivel (idVeiculo, tipo, quantidade, valorUnitario, data, media, valorTotal, "
                   + "quilometragemInicial, quilometragemFinal, quilometragemPercorrida) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, veiculoCombustivel.getVeiculo().getId());
            ps.setString(2, veiculoCombustivel.getTipo());
            ps.setInt(3, veiculoCombustivel.getQuantidade());
            ps.setDouble(4, veiculoCombustivel.getValorUnitario());
            ps.setDate(5, new java.sql.Date(veiculoCombustivel.getData().getTime()));
            ps.setInt(6, veiculoCombustivel.getMedia());
            ps.setDouble(7, veiculoCombustivel.getValorTotal());
            ps.setInt(8, veiculoCombustivel.getQuilometragemInicial());
            ps.setInt(9, veiculoCombustivel.getQuilometragemFinal());
            ps.setInt(10, veiculoCombustivel.getQuilometragemPercorrida());
            ps.executeUpdate();
            veiculoCombustivel.setId(getLastId(ps));
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoCombustivel);
    }
    
    public static VeiculoCombustivel alterar(String nomeBD, VeiculoCombustivel veiculoCombustivel) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo_combustivel "
                   + "SET tipo = ?, quantidade = ?, valorUnitario = ?, data = ?, media = ?, valorTotal = ?, quilometragemInicial = ?, "
                   + "quilometragemFinal = ?, quilometragemPercorrida = ? "
                   + "WHERE idVeiculoCombustivel = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, veiculoCombustivel.getTipo());
            ps.setInt(2, veiculoCombustivel.getQuantidade());
            ps.setDouble(3, veiculoCombustivel.getValorUnitario());
            ps.setDate(4, new java.sql.Date(veiculoCombustivel.getData().getTime()));
            ps.setInt(5, veiculoCombustivel.getMedia());
            ps.setDouble(6, veiculoCombustivel.getValorTotal());
            ps.setInt(7, veiculoCombustivel.getQuilometragemInicial());
            ps.setInt(8, veiculoCombustivel.getQuilometragemFinal());
            ps.setInt(9, veiculoCombustivel.getQuilometragemPercorrida());
            ps.setInt(10, veiculoCombustivel.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoCombustivel);
    }
    
    public static VeiculoCombustivel limpar(String nomeBD, VeiculoCombustivel veiculoCombustivel) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo_combustivel WHERE idVeiculoCombustivel = ? ";
        try {
            veiculoCombustivel = consulta(nomeBD, veiculoCombustivel);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculoCombustivel.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return veiculoCombustivel;
    }
    
    public static VeiculoCombustivel consultaUltimoCombustivelCadastrado(String nomeBD, Veiculo veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT MAX(idVeiculoCombustivel) as idVeiculoCombustivel FROM veiculo_combustivel WHERE idVeiculo = ? ";  
        VeiculoCombustivel veiculoCombustivel = new VeiculoCombustivel();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculo.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { 
                veiculoCombustivel.setId(result.getInt("idVeiculoCombustivel"));
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoCombustivel);
    }
    
    private static Integer getLastId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
    
    private static VeiculoCombustivel criarVeiculoCombustivel(ResultSet result) throws SQLException {
        VeiculoCombustivelBuilder builder = new VeiculoCombustivelBuilder();
        return builder.toEntidade(result);
    }
    
}
