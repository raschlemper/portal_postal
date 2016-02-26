package com.portalpostal.dao;

import com.portalpostal.model.Veiculo;
import com.portalpostal.dao.builder.VeiculoRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoDAO extends GenericDAO {   

    public VeiculoDAO(String nomeBD) {
        super(nomeBD);
    } 

    public List<Veiculo> consultaTodos(String nmCache) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        VeiculoRowMapper mapper = new VeiculoRowMapper();
        return execute("SELECT * FROM veiculo ORDER BY tipo, marca, modelo, placa", paramMap, mapper);
    }
}
