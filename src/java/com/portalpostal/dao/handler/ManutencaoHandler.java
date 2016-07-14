package com.portalpostal.dao.handler;

import com.portalpostal.model.Veiculo;
import com.portalpostal.model.dd.TipoManutencaoVeiculo;
import com.portalpostal.model.VeiculoManutencao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ManutencaoHandler extends GenericHandler implements ResultSetHandler<VeiculoManutencao> {
    
    public ManutencaoHandler() { 
        super("veiculo_manutencao");
    }
    
    public ManutencaoHandler(String table) {
        super(table);
    }
    
    @Override
    public VeiculoManutencao handle(ResultSet result) throws SQLException {
        VeiculoManutencao manutencao = new VeiculoManutencao();
        manutencao.setIdVeiculoManutencao(getInt(result, "idVeiculoManutencao"));
        manutencao.setTipo(TipoManutencaoVeiculo.values()[getInt(result, "tipo")]);
        manutencao.setQuilometragem(getInt(result, "quilometragem"));
        manutencao.setValor(getDouble(result, "valor"));
        manutencao.setDataManutencao(getDate(result, "dataManutencao"));
        manutencao.setDataAgendamento(getDate(result, "dataAgendamento"));
        manutencao.setDescricao(getString(result, "descricao"));
        manutencao.setVeiculo(getVeiculo(result));
        return manutencao;
    }
    
    private Veiculo getVeiculo(ResultSet result) throws SQLException {
        if(!existColumn(result, "veiculo.idVeiculo")) return null;
        return new VeiculoHandler().handle(result); 
    }
    
}
