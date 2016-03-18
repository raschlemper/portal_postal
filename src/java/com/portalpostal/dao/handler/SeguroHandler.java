package com.portalpostal.dao.handler;

import com.portalpostal.model.dd.TipoSeguroVeiculo;
import com.portalpostal.model.VeiculoSeguro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class SeguroHandler implements ResultSetHandler<VeiculoSeguro> {
    
    private String table = "veiculo_seguro";
        
    public SeguroHandler() { }
    
    public SeguroHandler(String table) {
        this.table = table;      
    }

    @Override
    public VeiculoSeguro handle(ResultSet result) throws SQLException {
        VeiculoSeguro seguro = new VeiculoSeguro();
        seguro.setIdVeiculoSeguro(result.getInt(table  + ".idVeiculoSeguro"));
        seguro.setNumeroApolice(result.getInt(table  + ".numeroApolice"));
        seguro.setCorretora(result.getString(table  + ".corretora"));
        seguro.setAssegurado(result.getString(table  + ".assegurado"));
        seguro.setValorFranquia(result.getDouble(table  + ".valorFranquia"));
        seguro.setIndenizacao(TipoSeguroVeiculo.values()[result.getInt(table  + ".indenizacao")]);
        seguro.setDataInicioVigencia(result.getDate(table  + ".dataInicioVigencia"));
        seguro.setDataFimVigencia(result.getDate(table  + ".dataFimVigencia"));
        return seguro;
    }
    
}
