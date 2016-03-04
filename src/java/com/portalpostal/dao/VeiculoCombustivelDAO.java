package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.VeiculoCombustivelHandler;
import com.portalpostal.model.VeiculoCombustivel;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoCombustivelDAO {   
    
    private Connection connection;
    private VeiculoCombustivelHandler handler;

    public VeiculoCombustivelDAO(String nomeBD) {
        connection = Sql2oConnexao.conect(nomeBD);
        handler = new VeiculoCombustivelHandler();
    } 

    public List<VeiculoCombustivel> findAll() {
        String sql = "SELECT * FROM veiculo_combustivel, veiculo WHERE veiculo.idVeiculo = veiculo_combustivel.idVeiculo "
                   + "ORDER BY veiculo_combustivel.idVeiculo, veiculo_combustivel.data";
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(handler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return new ArrayList<>();
    }

    public VeiculoCombustivel find(Integer idVeiculoCombustivel) throws Exception {
        String sql = "SELECT * FROM veiculo_combustivel, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_combustivel.idVeiculo AND idVeiculoCombustivel = :idVeiculoCombustivel";
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculoCombustivel", idVeiculoCombustivel)
                    .executeAndFetchFirst(handler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoCombustivel save(VeiculoCombustivel veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_combustivel (idVeiculo, tipo, quantidade, valorUnitario, data, valorTotal, quilometragem) "
                   + "VALUES(:idVeiculo, :tipo, :quantidade, :valorUnitario, :data, :valorTotal, :quilometragem) ";
        try {
            Integer idVeiculo = connection.createQuery(sql, true)
                    .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                    .addParameter("tipo", veiculo.getTipo().ordinal())
                    .addParameter("quantidade", veiculo.getQuantidade())
                    .addParameter("valorUnitario", veiculo.getValorUnitario())
                    .addParameter("data", veiculo.getData())
                    .addParameter("valorTotal", veiculo.getValorTotal())
                    .addParameter("quilometragem", veiculo.getQuilometragem())
                    .executeUpdate().getKey(Integer.class); 
            return find(idVeiculo);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoCombustivel update(VeiculoCombustivel veiculo) throws Exception {        
        String sql = "UPDATE veiculo_combustivel "
                   + "SET idVeiculo = :idVeiculo, tipo = :tipo, quantidade = :quantidade, valorUnitario = :valorUnitario, data = :data, "
                   + "valorTotal = :valorTotal, quilometragem = :quilometragem "
                   + "WHERE idVeiculoCombustivel = :idVeiculoCombustivel ";
        try {
            connection.createQuery(sql)
                .addParameter("idVeiculoCombustivel", veiculo.getIdVeiculoCombustivel())
                .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                .addParameter("tipo", veiculo.getTipo().ordinal())
                .addParameter("quantidade", veiculo.getQuantidade())
                .addParameter("valorUnitario", veiculo.getValorUnitario())
                .addParameter("data", veiculo.getData())
                .addParameter("valorTotal", veiculo.getValorTotal())
                .addParameter("quilometragem", veiculo.getQuilometragem())
                .executeUpdate();          
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoCombustivel remove(Integer idVeiculoCombustivel) throws Exception { 
        String sql = "DELETE FROM veiculo_combustivel WHERE idVeiculoCombustivel = :idVeiculoCombustivel ";
        try {
            VeiculoCombustivel veiculo = find(idVeiculoCombustivel);
            connection.createQuery(sql)
                .addParameter("idVeiculoCombustivel", idVeiculoCombustivel)
                .executeUpdate();  
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoCombustivel findLastCombustivelByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT MAX(idVeiculoCombustivel) as idVeiculoCombustivel "
                   + "FROM veiculo_combustivel WHERE idVeiculo = :idVeiculo";
        try {              
            Integer IdVeiculoCombustivel = connection.createQuery(sql)                
                    .addParameter("idVeiculo", idVeiculo)
                    .executeAndFetchFirst(Integer.class);
            return find(IdVeiculoCombustivel);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoCombustivel", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }
}
