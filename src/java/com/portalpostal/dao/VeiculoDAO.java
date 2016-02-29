package com.portalpostal.dao;

import com.portalpostal.model.Veiculo;
import com.portalpostal.dao.builder.VeiculoRowMapper;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoDAO extends GenericDAO {   

    public VeiculoDAO(String nomeBD) {
        super(nomeBD);
    } 

    public List<Veiculo> consultaTodos(String nmCache) throws Exception {
        String sql = "SELECT * FROM veiculo ORDER BY tipo, marca, modelo, placa";
        return connection.createQuery(sql).executeAndFetch(Veiculo.class);
    }

    public Veiculo consulta(String nmCache, Integer idVeiculo) throws Exception {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("idVeiculo", idVeiculo);
//        List<Veiculo> veiculos = executeQuery("SELECT * FROM veiculo WHERE idVeiculo = :idVeiculo", paramMap, new VeiculoRowMapper());
//        if(veiculos != null && !veiculos.isEmpty()) return (Veiculo) veiculos.get(0);
        return null;
    }

    public Veiculo inserir(String nmCache, Veiculo veiculo) throws Exception {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("tipo", veiculo.getTipo());
//        paramMap.put("marca", veiculo.getMarca());
//        paramMap.put("modelo", veiculo.getModelo());
//        paramMap.put("placa", veiculo.getPlaca());
//        paramMap.put("anoFabricacao", veiculo.getAnoFabricacao());
//        paramMap.put("anoModelo", veiculo.getAnoModelo());
//        paramMap.put("chassis", veiculo.getChassis());
//        paramMap.put("renavam", veiculo.getRenavam());
//        paramMap.put("quilometragem", veiculo.getQuilometragem());
//        paramMap.put("combustivel", veiculo.getCombustivel());
//        paramMap.put("status", veiculo.getStatus());
//        paramMap.put("situacao", veiculo.getSituacao());        
//        Integer id = executeUpdate("INSERT INTO veiculo (tipo, marca, modelo, placa, anoFabricacao, anoModelo, chassis, renavam, quilometragem, combustivel, status, situacao) "
//                   + "VALUES(:tipo, :marca, :modelo, :placa, :anoFabricacao, :anoModelo, :chassis, :renavam, :quilometragem, :combustivel, :status, :situacao)", paramMap);
//        if(id != null) return consulta(nmCache, id);
        return null;
    }
}
