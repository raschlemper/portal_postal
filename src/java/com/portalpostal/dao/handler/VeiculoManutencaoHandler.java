package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoManutencao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoManutencaoHandler implements ResultSetHandler<VeiculoManutencao> {
    
    private final VeiculoHandler veiculoHandler;
    private final ManutencaoHandler manutencaoHandler;
    
    public VeiculoManutencaoHandler() {
        veiculoHandler = new VeiculoHandler();
        manutencaoHandler = new ManutencaoHandler();
    }

    @Override
    public VeiculoManutencao handle(ResultSet result) throws SQLException {
        VeiculoManutencao manutencao = manutencaoHandler.handle(result);
        manutencao.setVeiculo(veiculoHandler.handle(result));
        return manutencao;
    }
    
}
