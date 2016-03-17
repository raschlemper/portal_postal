package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoManutencao;
import com.portalpostal.model.VeiculoManutencao;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ManutencaoHandler implements ResultSetHandler<VeiculoManutencao> {
        
    public ManutencaoHandler() { }
    
    @Override
    public VeiculoManutencao handle(ResultSet result) throws SQLException {
        VeiculoManutencao manutencao = new VeiculoManutencao();
        manutencao.setIdVeiculoManutencao(result.getInt("veiculo_manutencao.idVeiculoManutencao"));
        manutencao.setTipo(TipoManutencao.values()[result.getInt("veiculo_manutencao.tipo")]);
        manutencao.setQuilometragem((Integer) result.getObject("veiculo_manutencao.quilometragem"));
        Double valor = result.getDouble("veiculo_manutencao.valor");
        if (result.wasNull()) { valor = null; }
        manutencao.setValor(valor);
        manutencao.setDataManutencao(result.getDate("veiculo_manutencao.dataManutencao"));
        manutencao.setDataAgendamento(result.getDate("veiculo_manutencao.dataAgendamento"));
        manutencao.setDescricao(result.getString("veiculo_manutencao.descricao"));
        return manutencao;
    }
    
}
