package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoMulta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class MultaHandler implements ResultSetHandler<VeiculoMulta> {
        
    public MultaHandler() { }

    @Override
    public VeiculoMulta handle(ResultSet result) throws SQLException {
        VeiculoMulta multa = new VeiculoMulta();
        multa.setIdVeiculoMulta(result.getInt("veiculo_multa.idVeiculoMulta"));
        multa.setCondutor(result.getString("veiculo_multa.condutor"));
        multa.setNumero(result.getInt("veiculo_multa.numero"));
        multa.setValor(result.getDouble("veiculo_multa.valor"));
        multa.setData(result.getDate("veiculo_multa.data"));
        multa.setValor(result.getDouble("veiculo_multa.valor"));
        multa.setDescontada(result.getBoolean("veiculo_multa.descontada"));
        multa.setLocal(result.getString("veiculo_multa.local"));
        multa.setDescricao(result.getString("veiculo_multa.descricao"));
        return multa;
    }
    
}
