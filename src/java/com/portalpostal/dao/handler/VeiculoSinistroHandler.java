package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoResponsavel;
import com.portalpostal.model.TipoSeguro;
import com.portalpostal.model.TipoSinistro;
import com.portalpostal.model.VeiculoSinistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoSinistroHandler implements ResultSetHandler<VeiculoSinistro> {
    
    private final VeiculoHandler veiculo;
    
    public VeiculoSinistroHandler() {
        veiculo = new VeiculoHandler();
    }

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
        sinistro.setVeiculo(veiculo.handle(result));
        return sinistro;
    }
    
}
