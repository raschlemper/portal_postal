package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.model.VeiculoManutencao;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoManutencaoDAO {   
    
    private Connection connection;
    private VeiculoDAO veiculoDAO;

    public VeiculoManutencaoDAO(String nomeBD) {
        connection = Sql2oConnexao.conect(nomeBD);
        veiculoDAO = new VeiculoDAO(nomeBD);
    } 

    public List<VeiculoManutencao> findAll() {
        String sql = "SELECT * FROM veiculo_manutencao ORDER BY idVeiculo, dataManutencao";
        try {              
            List<VeiculoManutencao> lista = connection.createQuery(sql).executeAndFetch(VeiculoManutencao.class);
            for (VeiculoManutencao veiculoManutencao : lista) {
                veiculoManutencao.setVeiculo(veiculoDAO.find(veiculoManutencao.getIdVeiculo()));                
            }
            return lista;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoManutencao find(Integer idVeiculoManutencao) throws Exception {
        String sql = "SELECT * FROM veiculo_manutencao WHERE idVeiculoManutencao = :idVeiculoManutencao";
        VeiculoManutencao veiculoManutencao = connection.createQuery(sql)
                .addParameter("idVeiculoManutencao", idVeiculoManutencao)
                .executeAndFetchFirst(VeiculoManutencao.class);
        veiculoManutencao.setVeiculo(veiculoDAO.find(veiculoManutencao.getIdVeiculo()));
        return veiculoManutencao;
    }

    public VeiculoManutencao save(VeiculoManutencao veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_manutencao (idVeiculo, tipo, quilometragem, valor, dataManutencao, dataAgendamento, dataEntrega, descricao) "
                   + "VALUES(:idVeiculo, :tipo, :quilometragem, :valor, :dataManutencao, :dataAgendamento, :dataEntrega, :descricao)";
        Integer idVeiculo = connection.createQuery(sql, true)
                .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                .addParameter("tipo", veiculo.getTipo().ordinal())
                .addParameter("quilometragem", veiculo.getQuilometragem())
                .addParameter("valor", veiculo.getValor())
                .addParameter("dataManutencao", veiculo.getDataManutencao())
                .addParameter("dataAgendamento", veiculo.getDataAgendamento())
                .addParameter("dataEntrega", veiculo.getDataEntrega())
                .addParameter("descricao", veiculo.getDescricao())
                .executeUpdate().getKey(Integer.class); 
        return find(idVeiculo);
    }

    public VeiculoManutencao update(VeiculoManutencao veiculo) throws Exception {        
        String sql = "UPDATE veiculo_manutencao "
                   + "SET tipo = :tipo, quilometragem = :quilometragem, valor = :valor, data = :data, dataAgendamento = :dataAgendamento, "
                   + "dataEntrega = :dataEntrega, descricao = :descricao "
                   + "WHERE idVeiculoManutencao = :idVeiculoManutencao ";
            connection.createQuery(sql)
                .addParameter("idVeiculoManutencao", veiculo.getIdVeiculoManutencao())
                .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                .addParameter("tipo", veiculo.getTipo().ordinal())
                .addParameter("quilometragem", veiculo.getQuilometragem())
                .addParameter("valor", veiculo.getValor())
                .addParameter("dataManutencao", veiculo.getDataManutencao())
                .addParameter("dataAgendamento", veiculo.getDataAgendamento())
                .addParameter("dataEntrega", veiculo.getDataEntrega())
                .addParameter("descricao", veiculo.getDescricao())  
                .executeUpdate();          
        return veiculo;
    }

    public VeiculoManutencao remove(Integer idVeiculoManutencao) throws Exception { 
        VeiculoManutencao veiculo = find(idVeiculoManutencao);
        String sql = "DELETE FROM veiculo_manutencao WHERE idVeiculoManutencao = :idVeiculoManutencao ";
            connection.createQuery(sql)
                .addParameter("idVeiculoManutencao", idVeiculoManutencao)
                .executeUpdate();  
        return veiculo;
    }
}
