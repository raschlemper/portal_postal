package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoResponsavelVeiculo;
import com.portalpostal.model.dd.TipoSinistroVeiculo;
import com.portalpostal.model.VeiculoSinistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class SinistroHandler implements ResultSetHandler<VeiculoSinistro> {
    
    private String table = "veiculo_sinistro";
        
    public SinistroHandler() { }
    
    public SinistroHandler(String table) {
        this.table = table;      
    }

    @Override
    public VeiculoSinistro handle(ResultSet result) throws SQLException {
        VeiculoSinistro sinistro = new VeiculoSinistro();
        sinistro.setIdVeiculoSinistro(result.getInt(table  + ".idVeiculoSinistro"));
        sinistro.setTipo(TipoSinistroVeiculo.values()[result.getInt(table  + ".tipo")]);
        sinistro.setBoletimOcorrencia(result.getInt(table  + ".boletimOcorrencia"));
        sinistro.setData(result.getDate(table  + ".data"));
        sinistro.setLocal(result.getString(table  + ".local"));
        sinistro.setResponsavel(TipoResponsavelVeiculo.values()[result.getInt(table  + ".responsavel")]);
        sinistro.setDescricao(result.getString(table  + ".descricao"));
        return sinistro;
    }
    
}
