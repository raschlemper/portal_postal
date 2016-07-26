package com.portalpostal.dao.handler;

import com.portalpostal.model.Veiculo;
import com.portalpostal.model.dd.TipoCombustivelVeiculo;
import com.portalpostal.model.VeiculoCombustivel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CombustivelHandler extends GenericHandler implements ResultSetHandler<VeiculoCombustivel> {
            
    public CombustivelHandler() {
        super("veiculo_combustivel");
    }
    
    public CombustivelHandler(String table) {
        super(table);
    }
    
    @Override
    public VeiculoCombustivel handle(ResultSet result) throws SQLException {
        VeiculoCombustivel combustivel = new VeiculoCombustivel();
        combustivel.setIdVeiculoCombustivel(getInt(result, "idVeiculoCombustivel"));
        combustivel.setTipo(TipoCombustivelVeiculo.values()[getInt(result, "tipo")]);
        combustivel.setQuantidade(getDouble(result, "quantidade"));
        combustivel.setValorUnitario(getDouble(result, "valorUnitario"));
        combustivel.setData(getDate(result, "data"));
        combustivel.setValorTotal(getDouble(result, "valorTotal"));
        combustivel.setQuilometragem(getInt(result, "quilometragem"));
        combustivel.setVeiculo(getVeiculo(result));
        return combustivel;
    }
    
    private Veiculo getVeiculo(ResultSet result) throws SQLException {
        if(!existColumn(result, "veiculo.idVeiculo")) return null;
        return new VeiculoHandler().handle(result); 
    }
    
}
