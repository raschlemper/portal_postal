package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoCombustivelVeiculo;
import com.portalpostal.model.VeiculoCombustivel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CombustivelHandler implements ResultSetHandler<VeiculoCombustivel> {
    
    private String table = "veiculo_combustivel";
    
    public CombustivelHandler() {}
    
    public CombustivelHandler(String table) {
        this.table = table;      
    }

    
    @Override
    public VeiculoCombustivel handle(ResultSet result) throws SQLException {
        VeiculoCombustivel combustivel = new VeiculoCombustivel();
        combustivel.setIdVeiculoCombustivel(result.getInt(table  + ".idVeiculoCombustivel"));
        combustivel.setTipo(TipoCombustivelVeiculo.values()[result.getInt(table  + ".tipo")]);
        combustivel.setQuantidade(result.getInt(table  + ".quantidade"));
        combustivel.setValorUnitario(result.getDouble(table  + ".valorUnitario"));
        combustivel.setData(result.getDate(table  + ".data"));
        combustivel.setValorTotal(result.getDouble(table  + ".valorTotal"));
        combustivel.setQuilometragem(result.getInt(table  + ".quilometragem"));
        return combustivel;
    }
    
}
