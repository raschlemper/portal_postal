package com.portalpostal.dao;

import Veiculo.Entidade.Veiculo;
import Veiculo.builder.VeiculoBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoDAO extends GenericDAO {   

    public VeiculoDAO(String nomeBD) {
        super(nomeBD);
    } 

    public List<Veiculo> consultaTodos(String nmCache) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        return execute("SELECT * FROM veiculo ORDER BY tipo, marca, modelo, placa", paramMap, veiculos);
    }
    
    public static RowMapper<Veiculo> veiculos = new RowMapper<Veiculo>() {
        public Veiculo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return criarVeiculo(rs);
	}
    };
    
    private static Veiculo criarVeiculo(ResultSet result) throws SQLException {
        VeiculoBuilder builder = new VeiculoBuilder();
        return builder.toEntidade(result);
    }
}
