package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoManutencao;
import com.portalpostal.model.VeiculoManutencao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoManutencaoHandler implements ResultSetHandler<VeiculoManutencao> {
    
    private final VeiculoHandler veiculo;
    
    public VeiculoManutencaoHandler() {
        veiculo = new VeiculoHandler();
    }

    @Override
    public VeiculoManutencao handle(ResultSet result) throws SQLException {
        VeiculoManutencao manutencao = new VeiculoManutencao();
        manutencao.setIdVeiculoManutencao(result.getInt("veiculo_manutencao.idVeiculoManutencao"));
        manutencao.setTipo(TipoManutencao.values()[result.getInt("veiculo_manutencao.tipo")]);
        manutencao.setQuilometragem(result.getInt("veiculo_manutencao.quilometragem"));
        manutencao.setValor(result.getDouble("veiculo_manutencao.valor"));
        manutencao.setDataManutencao(result.getDate("veiculo_manutencao.dataManutencao"));
        manutencao.setDataAgendamento(result.getDate("veiculo_manutencao.dataAgendamento"));
        manutencao.setDataEntrega(result.getDate("veiculo_manutencao.dataEntrega"));
        manutencao.setDescricao(result.getString("veiculo_manutencao.descricao"));
        manutencao.setVeiculo(veiculo.handle(result));
        return manutencao;
    }
    
}
