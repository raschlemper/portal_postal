package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoCombustivelVeiculo;
import com.portalpostal.model.dd.TipoSituacaoVeiculo;
import com.portalpostal.model.dd.TipoStatusVeiculo;
import com.portalpostal.model.dd.TipoVeiculo;
import com.portalpostal.model.Veiculo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoHandler extends GenericHandler implements ResultSetHandler<Veiculo> {
    
    public VeiculoHandler() { 
        super("veiculo");
    }
    
    public VeiculoHandler(String table) {
        super(table);
    }

    @Override
    public Veiculo handle(ResultSet result) throws SQLException {        
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(getInt(result, "idVeiculo"));
        veiculo.setTipo(TipoVeiculo.values()[getInt(result, "tipo")]);  
        veiculo.setIdMarca(getInt(result, "idMarca"));
        veiculo.setMarca(getString(result, "marca"));
        veiculo.setIdModelo(getInt(result, "idModelo"));
        veiculo.setModelo(getString(result, "modelo")); 
        veiculo.setIdVersao(getString(result, "idVersao"));
        veiculo.setVersao(getString(result, "versao"));  
        veiculo.setPlaca(getString(result, "placa"));
        veiculo.setAnoModelo(getInt(result, "anoModelo"));
        veiculo.setChassis(getString(result, "chassis"));
        veiculo.setRenavam(getString(result, "renavam"));
        veiculo.setQuilometragem(getInt(result, "quilometragem"));
        veiculo.setCombustivel(TipoCombustivelVeiculo.values()[getInt(result, "combustivel")]);
        veiculo.setStatus(TipoStatusVeiculo.values()[getInt(result, "status")]);
        veiculo.setSituacao(TipoSituacaoVeiculo.values()[getInt(result, "situacao")]);
        veiculo.setDataCadastro(getDate(result, "dataCadastro"));
        return veiculo;
    }
    
}
