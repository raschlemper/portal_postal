package com.portalpostal.dao;

import com.portalpostal.dao.handler.MultaHandler;
import com.portalpostal.model.VeiculoMulta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoMultaDAO extends GenericDAO {   
    
    private final MultaHandler multaHandler;

    public VeiculoMultaDAO(String nameDB) { 
        super(nameDB, VeiculoMultaDAO.class);
        multaHandler = new MultaHandler();
    } 

    public List<VeiculoMulta> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo_multa, veiculo WHERE veiculo.idVeiculo = veiculo_multa.idVeiculo "
                   + "ORDER BY veiculo_multa.idVeiculo, veiculo_multa.data";
        return findAll(sql, null, multaHandler);
    }

    public VeiculoMulta find(Integer idVeiculoMulta) throws Exception {
        String sql = "SELECT * FROM veiculo_multa, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_multa.idVeiculo AND idVeiculoMulta = :idVeiculoMulta";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoMulta", idVeiculoMulta);
        return (VeiculoMulta) find(sql, params, multaHandler);
    }

    public VeiculoMulta save(VeiculoMulta veiculo) throws Exception { 
        String sql = "INSERT INTO veiculo_multa (idVeiculo, condutor, numero, data, valor, descontada, local, descricao) "
                   + "VALUES(:idVeiculo, :condutor, :numero, :data, :valor, :descontada, :local, :descricao)";       
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("condutor", veiculo.getCondutor());
        params.put("numero", veiculo.getNumero());
        params.put("data", veiculo.getData());
        params.put("valor", veiculo.getValor());
        params.put("descontada", veiculo.getDescontada());
        params.put("local", veiculo.getLocal());
        params.put("descricao", veiculo.getDescricao());
        Integer idVeiculoMulta = save(sql, params, multaHandler);
        return find(idVeiculoMulta);
    }

    public VeiculoMulta update(VeiculoMulta veiculo) throws Exception {  
        String sql = "UPDATE veiculo_multa "
                   + "SET idVeiculo = :idVeiculo, condutor = :condutor, numero = :numero, data = :data, valor = :valor, descontada = :descontada, "
                   + "local = :local, descricao = :descricao "
                   + "WHERE idVeiculoMulta = :idVeiculoMulta "; 
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoMulta", veiculo.getIdVeiculoMulta());
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("condutor", veiculo.getCondutor());
        params.put("numero", veiculo.getNumero());
        params.put("data", veiculo.getData());
        params.put("valor", veiculo.getValor());
        params.put("descontada", veiculo.getDescontada());
        params.put("local", veiculo.getLocal());
        params.put("descricao", veiculo.getDescricao());
        update(sql, params, multaHandler);
        return veiculo;  
    }

    public VeiculoMulta remove(Integer idVeiculoMulta) throws Exception { 
        String sql = "DELETE FROM veiculo_multa WHERE idVeiculoMulta = :idVeiculoMulta ";
        VeiculoMulta veiculo = find(idVeiculoMulta);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoMulta", idVeiculoMulta);
        remove(sql, params, multaHandler);
        return veiculo;
    }

    public List<VeiculoMulta> findByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo_multa WHERE idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", idVeiculo);
        return findAll(sql, params, multaHandler);
    }
}
