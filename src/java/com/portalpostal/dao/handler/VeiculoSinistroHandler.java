package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoSinistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoSinistroHandler implements ResultSetHandler<VeiculoSinistro> {
    
    private final VeiculoHandler veiculoHandler;
    private final SinistroHandler sinistroHandler;
    
    public VeiculoSinistroHandler() {
        veiculoHandler = new VeiculoHandler();
        sinistroHandler = new SinistroHandler();
    }

    @Override
    public VeiculoSinistro handle(ResultSet result) throws SQLException {
        VeiculoSinistro sinistro = sinistroHandler.handle(result);
        sinistro.setVeiculo(veiculoHandler.handle(result));
        return sinistro;
    }
    
}
