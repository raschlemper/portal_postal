package com.portalpostal.dao.handler;

import com.portalpostal.model.Veiculo;
import com.portalpostal.model.dd.TipoResponsavelVeiculo;
import com.portalpostal.model.dd.TipoSinistroVeiculo;
import com.portalpostal.model.VeiculoSinistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class SinistroHandler extends GenericHandler implements ResultSetHandler<VeiculoSinistro> {
            
    public SinistroHandler() {
        super("veiculo_sinistro");
    }
    
    public SinistroHandler(String table) {
        super(table);
    }

    @Override
    public VeiculoSinistro handle(ResultSet result) throws SQLException {
        VeiculoSinistro sinistro = new VeiculoSinistro();
        sinistro.setIdVeiculoSinistro(getInt(result, "idVeiculoSinistro"));
        sinistro.setTipo(TipoSinistroVeiculo.values()[getInt(result, "tipo")]);
        sinistro.setBoletimOcorrencia(getLong(result, "boletimOcorrencia"));
        sinistro.setData(getDate(result, "data"));
        sinistro.setLocal(getString(result, "local"));
        sinistro.setResponsavel(TipoResponsavelVeiculo.values()[getInt(result, "responsavel")]);
        sinistro.setDescricao(getString(result, "descricao"));
        sinistro.setVeiculo(getVeiculo(result));
        return sinistro;
    }
    
    private Veiculo getVeiculo(ResultSet result) throws SQLException {
        if(!existColumn(result, "veiculo.idVeiculo")) return null;
        return new VeiculoHandler().handle(result); 
    }
    
}
