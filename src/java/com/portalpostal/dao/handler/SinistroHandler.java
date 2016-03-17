package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoResponsavel;
import com.portalpostal.model.dd.TipoSinistro;
import com.portalpostal.model.VeiculoSinistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class SinistroHandler implements ResultSetHandler<VeiculoSinistro> {
        
    public SinistroHandler() { }

    @Override
    public VeiculoSinistro handle(ResultSet result) throws SQLException {
        VeiculoSinistro sinistro = new VeiculoSinistro();
        sinistro.setIdVeiculoSinistro(result.getInt("veiculo_sinistro.idVeiculoSinistro"));
        sinistro.setTipo(TipoSinistro.values()[result.getInt("veiculo_sinistro.tipo")]);
        sinistro.setBoletimOcorrencia(result.getInt("veiculo_sinistro.boletimOcorrencia"));
        sinistro.setData(result.getDate("veiculo_sinistro.data"));
        sinistro.setLocal(result.getString("veiculo_sinistro.local"));
        sinistro.setResponsavel(TipoResponsavel.values()[result.getInt("veiculo_sinistro.responsavel")]);
        sinistro.setDescricao(result.getString("veiculo_sinistro.descricao"));
        return sinistro;
    }
    
}
