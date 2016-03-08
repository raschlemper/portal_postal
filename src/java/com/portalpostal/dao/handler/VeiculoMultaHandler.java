package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoMulta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoMultaHandler implements ResultSetHandler<VeiculoMulta> {
    
    private final VeiculoHandler veiculoHandler;
    private final MultaHandler multaHandler;
    
    public VeiculoMultaHandler() {
        veiculoHandler = new VeiculoHandler();
        multaHandler = new MultaHandler();
    }

    @Override
    public VeiculoMulta handle(ResultSet result) throws SQLException {
        VeiculoMulta multa = multaHandler.handle(result);
        multa.setVeiculo(veiculoHandler.handle(result));
        return multa;
    }
    
}
