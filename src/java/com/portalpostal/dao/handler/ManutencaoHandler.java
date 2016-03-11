package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoManutencao;
import com.portalpostal.model.VeiculoManutencao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.sql2o.ResultSetHandler;

public class ManutencaoHandler implements ResultSetHandler<VeiculoManutencao> {
        
    public ManutencaoHandler() { }
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public VeiculoManutencao handle(ResultSet result) throws SQLException {
        VeiculoManutencao manutencao = new VeiculoManutencao();
        manutencao.setIdVeiculoManutencao(result.getInt("veiculo_manutencao.idVeiculoManutencao"));
        manutencao.setTipo(TipoManutencao.values()[result.getInt("veiculo_manutencao.tipo")]);
        manutencao.setQuilometragem(result.getInt("veiculo_manutencao.quilometragem"));
        manutencao.setValor(result.getDouble("veiculo_manutencao.valor"));
        manutencao.setDataManutencao(result.getTimestamp("veiculo_manutencao.dataManutencao"));
        manutencao.setDataAgendamento(result.getTimestamp("veiculo_manutencao.dataAgendamento"));
        manutencao.setDescricao(result.getString("veiculo_manutencao.descricao"));
        return manutencao;
    }
    
}
