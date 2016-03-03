package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoCombustivel;
import com.portalpostal.model.TipoSituacao;
import com.portalpostal.model.TipoStatus;
import com.portalpostal.model.TipoVeiculo;
import com.portalpostal.model.Veiculo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoHandler implements ResultSetHandler<Veiculo> {

    @Override
    public Veiculo handle(ResultSet result) throws SQLException {        
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(result.getInt("veiculo.idVeiculo"));
        veiculo.setTipo(TipoVeiculo.values()[result.getInt("veiculo.tipo")]);  
        veiculo.setIdMarca(result.getInt("veiculo.idMarca"));
        veiculo.setMarca(result.getString("veiculo.marca"));
        veiculo.setIdModelo(result.getInt("veiculo.idModelo"));
        veiculo.setModelo(result.getString("veiculo.modelo")); 
        veiculo.setIdVersao(result.getString("veiculo.idVersao"));
        veiculo.setVersao(result.getString("veiculo.versao"));  
        veiculo.setPlaca(result.getString("veiculo.placa"));
        veiculo.setAnoModelo(result.getInt("veiculo.anoModelo"));
        veiculo.setChassis(result.getString("veiculo.chassis"));
        veiculo.setRenavam(result.getString("veiculo.renavam"));
        veiculo.setQuilometragem(result.getInt("veiculo.quilometragem"));
        veiculo.setCombustivel(TipoCombustivel.values()[result.getInt("veiculo.combustivel")]);
        veiculo.setStatus(TipoStatus.values()[result.getInt("veiculo.status")]);
        veiculo.setSituacao(TipoSituacao.values()[result.getInt("veiculo.situacao")]);
        veiculo.setDataCadastro(result.getDate("veiculo.dataCadastro"));
        return veiculo;
    }
    
}
