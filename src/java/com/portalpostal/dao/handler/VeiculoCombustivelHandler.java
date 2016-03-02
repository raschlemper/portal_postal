package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoCombustivel;
import com.portalpostal.model.VeiculoCombustivel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoCombustivelHandler implements ResultSetHandler<VeiculoCombustivel> {
    
    private final VeiculoHandler veiculo;
    
    public VeiculoCombustivelHandler() {
        veiculo = new VeiculoHandler();
    }

    @Override
    public VeiculoCombustivel handle(ResultSet result) throws SQLException {
        VeiculoCombustivel combustivel = new VeiculoCombustivel();
        combustivel.setIdVeiculoCombustivel(result.getInt("veiculo_combustivel.idVeiculoCombustivel"));
        combustivel.setTipo(TipoCombustivel.values()[result.getInt("veiculo_combustivel.tipo")]);
        combustivel.setQuantidade(result.getInt("veiculo_combustivel.quantidade"));
        combustivel.setValorUnitario(result.getDouble("veiculo_combustivel.valorUnitario"));
        combustivel.setData(result.getDate("veiculo_combustivel.data"));
        combustivel.setValorTotal(result.getDouble("veiculo_combustivel.valorTotal"));
        combustivel.setQuilometragemInicial(result.getInt("veiculo_combustivel.quilometragemInicial"));
        combustivel.setQuilometragemFinal(result.getInt("veiculo_combustivel.quilometragemFinal"));
        combustivel.setQuilometragemPercorrida(result.getInt("veiculo_combustivel.quilometragemPercorrida"));
        combustivel.setVeiculo(veiculo.handle(result));
        return combustivel;
    }
    
}
