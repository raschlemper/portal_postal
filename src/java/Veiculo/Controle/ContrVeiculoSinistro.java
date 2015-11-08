package Veiculo.Controle;

import Controle.ContrErroLog;
import Veiculo.Entidade.VeiculoSinistro;
import Util.Conexao;
import Veiculo.builder.VeiculoSinistroBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContrVeiculoSinistro {

    public static List<VeiculoSinistro> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_sinistro, veiculo WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            List<VeiculoSinistro> listaVeiculos = new ArrayList<VeiculoSinistro>();
            while(result.next()) {
                listaVeiculos.add(criarVeiculoSinistro(result));                        
            }
            return listaVeiculos;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return new ArrayList();
    }

    public static VeiculoSinistro consulta(String nomeBD, VeiculoSinistro veiculoSinistro) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo_sinistro, veiculo WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo AND idVeiculoSinistro = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, veiculoSinistro.getId());
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculoSinistro(result); }
            return null;
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculoSinistro.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return null;
    }

    public static VeiculoSinistro inserir(String nomeBD, VeiculoSinistro veiculoSinistro) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo_sinistro (idVeiculo, tipo, boletimOcorrencia, data, local, responsavel, descricao) "
                   + "VALUES(?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, veiculoSinistro.getVeiculo().getId());
            ps.setString(2, veiculoSinistro.getTipo());
            ps.setInt(3, veiculoSinistro.getBoletimOcorrencia());
            ps.setDate(4, new java.sql.Date(veiculoSinistro.getData().getTime()));
            ps.setString(5, veiculoSinistro.getLocal());
            ps.setString(6, veiculoSinistro.getResponsavel());
            ps.setString(7, veiculoSinistro.getDescricao());
            ps.executeUpdate();
            veiculoSinistro.setId(getLastId(ps));
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoSinistro);
    }
    
    public static VeiculoSinistro alterar(String nomeBD, VeiculoSinistro veiculoSinistro) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo_sinistro "
                   + "SET tipo = ?, boletimOcorrencia = ?, data = ?, local = ?, responsavel = ?, descricao = ? "
                   + "WHERE idVeiculoSinistro = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, veiculoSinistro.getTipo());
            ps.setInt(2, veiculoSinistro.getBoletimOcorrencia());
            ps.setDate(3, new java.sql.Date(veiculoSinistro.getData().getTime()));
            ps.setString(4, veiculoSinistro.getLocal());
            ps.setString(5, veiculoSinistro.getResponsavel());
            ps.setString(6, veiculoSinistro.getDescricao());
            ps.setInt(7, veiculoSinistro.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculoSinistro);
    }
    
    public static VeiculoSinistro limpar(String nomeBD, VeiculoSinistro veiculoSinistro) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo_sinistro WHERE idVeiculoSinistro = ? ";
        try {
            veiculoSinistro = consulta(nomeBD, veiculoSinistro);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculoSinistro.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return veiculoSinistro;
    }
    
    private static Integer getLastId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
    
    private static VeiculoSinistro criarVeiculoSinistro(ResultSet result) throws SQLException {
        VeiculoSinistroBuilder builder = new VeiculoSinistroBuilder();
        return builder.toEntidade(result);
    }
    
}
