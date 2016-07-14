package com.portalpostal.dao;

import com.portalpostal.dao.handler.SinistroHandler;
import com.portalpostal.model.VeiculoSinistro;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoSinistroDAO extends GenericDAO {   
    
    private final SinistroHandler sinistroHandler;    

    public VeiculoSinistroDAO(String nameDB) {   
        super(nameDB, VeiculoSinistroDAO.class);        
        sinistroHandler = new SinistroHandler();      
    } 

    public List<VeiculoSinistro> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo_sinistro, veiculo WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo "
                   + "ORDER BY veiculo_sinistro.idVeiculo, veiculo_sinistro.data";
        return findAll(sql, null, sinistroHandler);
    }

    public VeiculoSinistro find(Integer idVeiculoSinistro) throws Exception {
        String sql = "SELECT * FROM veiculo_sinistro, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo AND idVeiculoSinistro = :idVeiculoSinistro";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoSinistro", idVeiculoSinistro);
        return (VeiculoSinistro) find(sql, params, sinistroHandler);
    }

    public VeiculoSinistro save(VeiculoSinistro veiculo) throws Exception { 
        String sql = "INSERT INTO veiculo_sinistro (idVeiculo, tipo, boletimOcorrencia, data, local, responsavel, descricao) "
                   + "VALUES(:idVeiculo, :tipo, :boletimOcorrencia, :data, :local, :responsavel, :descricao)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("boletimOcorrencia", veiculo.getBoletimOcorrencia());
        params.put("data", veiculo.getData());
        params.put("local", veiculo.getLocal());
        params.put("responsavel", veiculo.getResponsavel().ordinal());
        params.put("descricao", veiculo.getDescricao());
        Integer idVeiculoSinistro = save(sql, params, sinistroHandler);
        return find(idVeiculoSinistro);
    }

    public VeiculoSinistro update(VeiculoSinistro veiculo) throws Exception { 
        String sql = "UPDATE veiculo_sinistro "
                   + "SET idVeiculo = :idVeiculo, tipo = :tipo, boletimOcorrencia = :boletimOcorrencia, data = :data, local = :local, "
                   + "responsavel = :responsavel, descricao = :descricao "
                   + "WHERE idVeiculoSinistro = :idVeiculoSinistro ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoSinistro", veiculo.getIdVeiculoSinistro());
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("boletimOcorrencia", veiculo.getBoletimOcorrencia());
        params.put("data", veiculo.getData());
        params.put("local", veiculo.getLocal());
        params.put("responsavel", veiculo.getResponsavel().ordinal());
        params.put("descricao", veiculo.getDescricao());
        update(sql, params, sinistroHandler);
        return veiculo;  
    }

    public VeiculoSinistro remove(Integer idVeiculoSinistro) throws Exception { 
        String sql = "DELETE FROM veiculo_sinistro WHERE idVeiculoSinistro = :idVeiculoSinistro ";
        VeiculoSinistro veiculo = find(idVeiculoSinistro);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoSinistro", idVeiculoSinistro);
        remove(sql, params, sinistroHandler);
        return veiculo;
    }

    public List<VeiculoSinistro> findByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo_sinistro WHERE idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", idVeiculo);
        return findAll(sql, params, sinistroHandler);
    }
}
