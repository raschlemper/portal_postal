package com.portalpostal.dao.handler;

import com.portalpostal.model.Veiculo;
import com.portalpostal.model.dd.TipoSeguroVeiculo;
import com.portalpostal.model.VeiculoSeguro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class SeguroHandler extends GenericHandler implements ResultSetHandler<VeiculoSeguro> {
        
    public SeguroHandler() { 
        super("veiculo_seguro");
    }
    
    public SeguroHandler(String table) {
        super(table);
    }

    @Override
    public VeiculoSeguro handle(ResultSet result) throws SQLException {
        VeiculoSeguro seguro = new VeiculoSeguro();
        seguro.setIdVeiculoSeguro(getInt(result, "idVeiculoSeguro"));
        seguro.setNumeroApolice(getInt(result, "numeroApolice"));
        seguro.setCorretora(getString(result, "corretora"));
        seguro.setAssegurado(getString(result, "assegurado"));
        seguro.setValorFranquia(getDouble(result, "valorFranquia"));
        seguro.setIndenizacao(TipoSeguroVeiculo.values()[getInt(result, "indenizacao")]);
        seguro.setDataInicioVigencia(getDate(result, "dataInicioVigencia"));
        seguro.setDataFimVigencia(getDate(result, "dataFimVigencia"));
        seguro.setVeiculo(getVeiculo(result));
        return seguro;
    }
    
    private Veiculo getVeiculo(ResultSet result) throws SQLException {
        if(!existColumn(result, "veiculo.idVeiculo")) return null;
        return new VeiculoHandler().handle(result); 
    }
    
}
