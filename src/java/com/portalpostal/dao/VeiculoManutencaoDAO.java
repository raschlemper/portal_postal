package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.VeiculoManutencaoHandler;
import com.portalpostal.model.VeiculoManutencao;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoManutencaoDAO {   
    
    private Connection connection;
    private VeiculoManutencaoHandler handler;

    public VeiculoManutencaoDAO(String nomeBD) {
        connection = Sql2oConnexao.conect(nomeBD);
        handler = new VeiculoManutencaoHandler();
    } 

    public List<VeiculoManutencao> findAll() {
        String sql = "SELECT * FROM veiculo_manutencao, veiculo WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo "
                   + "ORDER BY veiculo_manutencao.idVeiculo, veiculo_manutencao.dataManutencao";
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(handler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return new ArrayList<>();
    }

    public VeiculoManutencao find(Integer idVeiculoManutencao) throws Exception {
        String sql = "SELECT * FROM veiculo_manutencao, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo AND idVeiculoManutencao = :idVeiculoManutencao";
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculoManutencao", idVeiculoManutencao)
                    .executeAndFetchFirst(handler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoManutencao save(VeiculoManutencao veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_manutencao (idVeiculo, tipo, quilometragem, valor, dataManutencao, dataAgendamento, dataEntrega, descricao) "
                   + "VALUES(:idVeiculo, :tipo, :quilometragem, :valor, :dataManutencao, :dataAgendamento, :dataEntrega, :descricao)";
        try {
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
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoManutencao update(VeiculoManutencao veiculo) throws Exception {        
        String sql = "UPDATE veiculo_manutencao "
                   + "SET tipo = :tipo, quilometragem = :quilometragem, valor = :valor, data = :data, dataAgendamento = :dataAgendamento, "
                   + "dataEntrega = :dataEntrega, descricao = :descricao "
                   + "WHERE idVeiculoManutencao = :idVeiculoManutencao ";
        try {
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
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoManutencao remove(Integer idVeiculoManutencao) throws Exception { 
        String sql = "DELETE FROM veiculo_manutencao WHERE idVeiculoManutencao = :idVeiculoManutencao ";
        try {
            VeiculoManutencao veiculo = find(idVeiculoManutencao);
            connection.createQuery(sql)
                .addParameter("idVeiculoManutencao", idVeiculoManutencao)
                .executeUpdate();  
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }
}
