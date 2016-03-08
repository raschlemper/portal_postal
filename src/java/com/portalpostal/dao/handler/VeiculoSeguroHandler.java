package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoSeguro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoSeguroHandler implements ResultSetHandler<VeiculoSeguro> {
    
    private final VeiculoHandler veiculoHandler;    
    private final SeguroHandler seguroHandler;
    
    public VeiculoSeguroHandler() {
        veiculoHandler = new VeiculoHandler();
        seguroHandler = new SeguroHandler();
    }

    @Override
    public VeiculoSeguro handle(ResultSet result) throws SQLException {
        VeiculoSeguro seguro = seguroHandler.handle(result);
        seguro.setVeiculo(veiculoHandler.handle(result));
        return seguro;
    }
    
}
