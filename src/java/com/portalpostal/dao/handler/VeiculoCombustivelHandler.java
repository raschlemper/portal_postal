package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoCombustivel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoCombustivelHandler implements ResultSetHandler<VeiculoCombustivel> {
    
    private final VeiculoHandler veiculoHandler;
    private final CombustivelHandler combustivelHandler;
    
    public VeiculoCombustivelHandler() {
        veiculoHandler = new VeiculoHandler();
        combustivelHandler = new CombustivelHandler();
    }

    @Override
    public VeiculoCombustivel handle(ResultSet result) throws SQLException {
        VeiculoCombustivel combustivel = combustivelHandler.handle(result);
        combustivel.setVeiculo(veiculoHandler.handle(result));
        return combustivel;
    }
    
}
