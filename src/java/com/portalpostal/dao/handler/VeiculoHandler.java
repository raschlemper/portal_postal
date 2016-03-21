package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoCombustivelVeiculo;
import com.portalpostal.model.dd.TipoSituacaoVeiculo;
import com.portalpostal.model.dd.TipoStatusVeiculo;
import com.portalpostal.model.dd.TipoVeiculo;
import com.portalpostal.model.Veiculo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class VeiculoHandler implements ResultSetHandler<Veiculo> {
    
    private String table = "veiculo";
    
    public VeiculoHandler() { }
    
    public VeiculoHandler(String table) {
        this.table = table;
    }

    @Override
    public Veiculo handle(ResultSet result) throws SQLException {        
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(result.getInt(table + ".idVeiculo"));
        veiculo.setTipo(TipoVeiculo.values()[result.getInt(table + ".tipo")]);  
        veiculo.setIdMarca(result.getInt(table + ".idMarca"));
        veiculo.setMarca(result.getString(table + ".marca"));
        veiculo.setIdModelo(result.getInt(table + ".idModelo"));
        veiculo.setModelo(result.getString(table + ".modelo")); 
        veiculo.setIdVersao(result.getString(table + ".idVersao"));
        veiculo.setVersao(result.getString(table + ".versao"));  
        veiculo.setPlaca(result.getString(table + ".placa"));
        veiculo.setAnoModelo(result.getInt(table + ".anoModelo"));
        veiculo.setChassis(result.getString(table + ".chassis"));
        veiculo.setRenavam(result.getString(table + ".renavam"));
        veiculo.setQuilometragem(result.getInt(table + ".quilometragem"));
        veiculo.setCombustivel(TipoCombustivelVeiculo.values()[result.getInt(table + ".combustivel")]);
        veiculo.setStatus(TipoStatusVeiculo.values()[result.getInt(table + ".status")]);
        veiculo.setSituacao(TipoSituacaoVeiculo.values()[result.getInt(table + ".situacao")]);
        veiculo.setDataCadastro(result.getDate(table + ".dataCadastro"));
        return veiculo;
    }
    
}
