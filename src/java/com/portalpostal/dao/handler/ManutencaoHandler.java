package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoManutencaoVeiculo;
import com.portalpostal.model.VeiculoManutencao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ManutencaoHandler implements ResultSetHandler<VeiculoManutencao> {
    
    private String table = "veiculo_manutencao";
        
    public ManutencaoHandler() { }
    
    public ManutencaoHandler(String table) {
        this.table = table;      
    }
    
    @Override
    public VeiculoManutencao handle(ResultSet result) throws SQLException {
        VeiculoManutencao manutencao = new VeiculoManutencao();
        manutencao.setIdVeiculoManutencao(result.getInt(table  + ".idVeiculoManutencao"));
        manutencao.setTipo(TipoManutencaoVeiculo.values()[result.getInt(table  + ".tipo")]);
        manutencao.setQuilometragem((Integer) result.getObject(table  + ".quilometragem"));
        Double valor = result.getDouble(table  + ".valor");
        if (result.wasNull()) { valor = null; }
        manutencao.setValor(valor);
        manutencao.setDataManutencao(result.getDate(table  + ".dataManutencao"));
        manutencao.setDataAgendamento(result.getDate(table  + ".dataAgendamento"));
        manutencao.setDescricao(result.getString(table  + ".descricao"));
        return manutencao;
    }
    
}
