package com.portalpostal.dao.builder;

import java.sql.ResultSet;
import com.portalpostal.model.Veiculo;

public class VeiculoRowMapper implements RowMapper<Veiculo> {
    
    @Override
    public Veiculo mapRow(ResultSet result, int rowNum) throws Exception {
        return new Veiculo(
                result.getInt("idVeiculo"),
                result.getString("tipo"),
                result.getString("marca"),
                result.getString("modelo"),
                result.getString("placa"),
                result.getInt("anoFabricacao"),
                result.getInt("anoModelo"),
                result.getString("chassis"),
                result.getString("renavam"),
                result.getInt("quilometragem"),
                result.getString("combustivel"),
                result.getString("status"),
                result.getString("situacao")
        );
    }
}
