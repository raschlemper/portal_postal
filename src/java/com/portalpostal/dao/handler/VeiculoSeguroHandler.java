package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoSeguro;
import com.portalpostal.model.VeiculoSeguro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoSeguroHandler implements ResultSetHandler<VeiculoSeguro> {
    
    private final VeiculoHandler veiculo;
    
    public VeiculoSeguroHandler() {
        veiculo = new VeiculoHandler();
    }

    @Override
    public VeiculoSeguro handle(ResultSet result) throws SQLException {
        VeiculoSeguro seguro = new VeiculoSeguro();
        seguro.setIdVeiculoSeguro(result.getInt("veiculo_seguro.idVeiculoSeguro"));
        seguro.setNumeroSeguro(result.getInt("veiculo_seguro.numeroSeguro"));
        seguro.setAssegurado(result.getString("veiculo_seguro.assegurado"));
        seguro.setValorFranquia(result.getDouble("veiculo_seguro.valorFranquia"));
        seguro.setIndenizacao(TipoSeguro.values()[result.getInt("veiculo_seguro.indenizacao")]);
        seguro.setVeiculo(veiculo.handle(result));
        return seguro;
    }
    
}
