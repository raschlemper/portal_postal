package Veiculo.Controle;

import Controle.ContrErroLog;
import Veiculo.Entidade.Veiculo;
import Util.Conexao;
import Veiculo.builder.VeiculoBuilder;
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

public class ContrVeiculo {

    public static List<Veiculo> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
        String sql = "SELECT * FROM veiculo ORDER BY tipo, marca, modelo, placa";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { listaVeiculos.add(criarVeiculo(result)); }
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return listaVeiculos;
    }

    public static Veiculo consulta(String nomeBD, Integer idVeiculo) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM veiculo WHERE idVeiculo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idVeiculo);
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { return criarVeiculo(result); }
        } catch (SQLException e) {
            Logger.getLogger(ContrVeiculo.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(con);
        }
        return null;
    }

    public static Veiculo inserir(String nomeBD, Veiculo veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO veiculo (tipo, marca, modelo, placa, anoFabricacao, anoModelo, chassis, renavam, quilometragem, combustivel, status, situacao) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
            veiculo.setId(getLastId(ps));
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculo.getId());
    }
    
    public static Veiculo alterar(String nomeBD, Veiculo veiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE veiculo "
                   + "SET tipo = ?, marca = ?, modelo = ?, placa = ?, anoFabricacao = ?, anoModelo = ?, "
                   + "chassis = ?, renavam = ?, quilometragem = ?, combustivel = ?, status = ?, situacao = ? "
                   + "WHERE idVeiculo = ? ";
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
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return consulta(nomeBD, veiculo.getId());
    }
    
    public static Veiculo limpar(String nomeBD, Integer idVeiculo) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM veiculo WHERE idVeiculo = ? ";
        Veiculo veiculo = null;
        try {
            veiculo = consulta(nomeBD, idVeiculo);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, veiculo.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
        return veiculo;
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
        } finally {
            Conexao.desconectar(con);
        }
        return null;
    }
    
    private static Integer getLastId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
    
    private static Veiculo criarVeiculo(ResultSet result) throws SQLException {
        VeiculoBuilder builder = new VeiculoBuilder();
        return builder.toEntidade(result);
    }
    
}
