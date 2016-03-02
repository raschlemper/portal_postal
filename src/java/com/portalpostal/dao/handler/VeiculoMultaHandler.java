package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoMulta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoMultaHandler implements ResultSetHandler<VeiculoMulta> {
    
    private final VeiculoHandler veiculo;
    
    public VeiculoMultaHandler() {
        veiculo = new VeiculoHandler();
    }

    @Override
    public VeiculoMulta handle(ResultSet result) throws SQLException {
        VeiculoMulta multa = new VeiculoMulta();
        multa.setIdVeiculoMulta(result.getInt("veiculo_multa.idVeiculoMulta"));
        multa.setNumeroMulta(result.getInt("veiculo_multa.numeroMulta"));
        multa.setValor(result.getDouble("veiculo_multa.valor"));
        multa.setData(result.getDate("veiculo_multa.data"));
        multa.setValor(result.getDouble("veiculo_multa.valor"));
        multa.setLocal(result.getString("veiculo_multa.local"));
        multa.setDescricao(result.getString("veiculo_multa.descricao"));
        multa.setVeiculo(veiculo.handle(result));
        return multa;
    }
    
}
