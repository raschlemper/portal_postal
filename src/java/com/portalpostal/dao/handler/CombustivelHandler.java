package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoCombustivelVeiculo;
import com.portalpostal.model.VeiculoCombustivel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CombustivelHandler implements ResultSetHandler<VeiculoCombustivel> {
        
    public CombustivelHandler() {}
    
    @Override
    public VeiculoCombustivel handle(ResultSet result) throws SQLException {
        VeiculoCombustivel combustivel = new VeiculoCombustivel();
        combustivel.setIdVeiculoCombustivel(result.getInt("veiculo_combustivel.idVeiculoCombustivel"));
        combustivel.setTipo(TipoCombustivelVeiculo.values()[result.getInt("veiculo_combustivel.tipo")]);
        combustivel.setQuantidade(result.getInt("veiculo_combustivel.quantidade"));
        combustivel.setValorUnitario(result.getDouble("veiculo_combustivel.valorUnitario"));
        combustivel.setData(result.getDate("veiculo_combustivel.data"));
        combustivel.setValorTotal(result.getDouble("veiculo_combustivel.valorTotal"));
        combustivel.setQuilometragem(result.getInt("veiculo_combustivel.quilometragem"));
        return combustivel;
    }
    
}
