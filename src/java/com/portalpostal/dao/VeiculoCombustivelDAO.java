package com.portalpostal.dao;

import com.portalpostal.dao.handler.CombustivelHandler;
import com.portalpostal.model.VeiculoCombustivel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoCombustivelDAO extends GenericDAO { 
    
    private CombustivelHandler combustivelHandler;

    public VeiculoCombustivelDAO(String nameDB) { 
        super(nameDB, VeiculoCombustivelDAO.class);
        combustivelHandler = new CombustivelHandler();
    } 

    public List<VeiculoCombustivel> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo_combustivel, veiculo WHERE veiculo.idVeiculo = veiculo_combustivel.idVeiculo "
                   + "ORDER BY veiculo_combustivel.idVeiculo, veiculo_combustivel.data";        
        return findAll(sql, null, combustivelHandler);
    }

    public VeiculoCombustivel find(Integer idVeiculoCombustivel) throws Exception {
        String sql = "SELECT * FROM veiculo_combustivel, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_combustivel.idVeiculo AND idVeiculoCombustivel = :idVeiculoCombustivel";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoCombustivel", idVeiculoCombustivel);
        return (VeiculoCombustivel) find(sql, params, combustivelHandler);
    }

    public VeiculoCombustivel save(VeiculoCombustivel veiculo) throws Exception {  
        String sql = "INSERT INTO veiculo_combustivel (idVeiculo, tipo, quantidade, valorUnitario, data, valorTotal, quilometragem) "
                   + "VALUES(:idVeiculo, :tipo, :quantidade, :valorUnitario, :data, :valorTotal, :quilometragem) ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("quantidade", veiculo.getQuantidade());
        params.put("valorUnitario", veiculo.getValorUnitario());
        params.put("data", veiculo.getData());
        params.put("valorTotal", veiculo.getValorTotal());
        params.put("quilometragem", veiculo.getQuilometragem());        
        Integer idVeiculoCombustivel = save(sql, params, combustivelHandler);
        return find(idVeiculoCombustivel);
    }

    public VeiculoCombustivel update(VeiculoCombustivel veiculo) throws Exception {
        String sql = "UPDATE veiculo_combustivel "
                   + "SET idVeiculo = :idVeiculo, tipo = :tipo, quantidade = :quantidade, valorUnitario = :valorUnitario, data = :data, "
                   + "valorTotal = :valorTotal, quilometragem = :quilometragem "
                   + "WHERE idVeiculoCombustivel = :idVeiculoCombustivel ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoCombustivel", veiculo.getIdVeiculoCombustivel());
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("quantidade", veiculo.getQuantidade());
        params.put("valorUnitario", veiculo.getValorUnitario());
        params.put("data", veiculo.getData());
        params.put("valorTotal", veiculo.getValorTotal());
        params.put("quilometragem", veiculo.getQuilometragem());        
        update(sql, params, combustivelHandler);
        return veiculo;  
    }

    public VeiculoCombustivel remove(Integer idVeiculoCombustivel) throws Exception { 
        String sql = "DELETE FROM veiculo_combustivel WHERE idVeiculoCombustivel = :idVeiculoCombustivel ";
        VeiculoCombustivel veiculo = find(idVeiculoCombustivel);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idVeiculoCombustivel", idVeiculoCombustivel);
        remove(sql, params, combustivelHandler);
        return veiculo;
    }

    public List<VeiculoCombustivel> findByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo_combustivel WHERE idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", idVeiculo);
        return findAll(sql, params, combustivelHandler);
    }

    public VeiculoCombustivel findLastCombustivelByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo_combustivel, veiculo "
                   + "WHERE idVeiculoCombustivel = (SELECT MAX(idVeiculoCombustivel) as idVeiculoCombustivel "
                                                   + "FROM veiculo_combustivel vc WHERE vc.idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", idVeiculo);
        return (VeiculoCombustivel) find(sql, params, combustivelHandler);
    }
}
