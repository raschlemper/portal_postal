package com.portalpostal.dao.handler;

import com.portalpostal.model.VeiculoMulta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class MultaHandler implements ResultSetHandler<VeiculoMulta> {
    
    private String table = "veiculo_multa";
        
    public MultaHandler() { }
    
    public MultaHandler(String table) {
        this.table = table;      
    }

    @Override
    public VeiculoMulta handle(ResultSet result) throws SQLException {
        VeiculoMulta multa = new VeiculoMulta();
        multa.setIdVeiculoMulta(result.getInt(table  + ".idVeiculoMulta"));
        multa.setCondutor(result.getString(table  + ".condutor"));
        multa.setNumero(result.getInt(table  + ".numero"));
        multa.setValor(result.getDouble(table  + ".valor"));
        multa.setData(result.getDate(table  + ".data"));
        multa.setValor(result.getDouble(table  + ".valor"));
        multa.setDescontada(result.getBoolean(table  + ".descontada"));
        multa.setLocal(result.getString(table  + ".local"));
        multa.setDescricao(result.getString(table  + ".descricao"));
        return multa;
    }
    
}
