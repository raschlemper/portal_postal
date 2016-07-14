package com.portalpostal.dao.handler;

import com.portalpostal.model.Veiculo;
import com.portalpostal.model.VeiculoMulta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class MultaHandler extends GenericHandler implements ResultSetHandler<VeiculoMulta> {
                
    public MultaHandler() { 
        super("veiculo_multa");
    }
    
    public MultaHandler(String table) {
        super(table);
    }

    @Override
    public VeiculoMulta handle(ResultSet result) throws SQLException {
        VeiculoMulta multa = new VeiculoMulta();
        multa.setIdVeiculoMulta(getInt(result, "idVeiculoMulta"));
        multa.setCondutor(getString(result, "condutor"));
        multa.setNumero(getInt(result, "numero"));
        multa.setValor(getDouble(result, "valor"));
        multa.setData(getDate(result, "data"));
        multa.setValor(getDouble(result, "valor"));
        multa.setDescontada(getBoolean(result, "descontada"));
        multa.setLocal(getString(result, "local"));
        multa.setDescricao(getString(result, "descricao"));
        multa.setVeiculo(getVeiculo(result));
        return multa;
    }
    
    private Veiculo getVeiculo(ResultSet result) throws SQLException {
        if(!existColumn(result, "veiculo.idVeiculo")) return null;
        return new VeiculoHandler().handle(result); 
    }
    
}
