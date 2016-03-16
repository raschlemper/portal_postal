package com.portalpostal.dao.handler;

import com.portalpostal.model.type.TipoSeguro;
import com.portalpostal.model.VeiculoSeguro;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class SeguroHandler implements ResultSetHandler<VeiculoSeguro> {
    
    public SeguroHandler() { }

    @Override
    public VeiculoSeguro handle(ResultSet result) throws SQLException {
        VeiculoSeguro seguro = new VeiculoSeguro();
        seguro.setIdVeiculoSeguro(result.getInt("veiculo_seguro.idVeiculoSeguro"));
        seguro.setNumeroApolice(result.getInt("veiculo_seguro.numeroApolice"));
        seguro.setCorretora(result.getString("veiculo_seguro.corretora"));
        seguro.setAssegurado(result.getString("veiculo_seguro.assegurado"));
        seguro.setValorFranquia(result.getDouble("veiculo_seguro.valorFranquia"));
        seguro.setIndenizacao(TipoSeguro.values()[result.getInt("veiculo_seguro.indenizacao")]);
        seguro.setDataInicioVigencia(result.getDate("veiculo_seguro.dataInicioVigencia"));
        seguro.setDataFimVigencia(result.getDate("veiculo_seguro.dataFimVigencia"));
        return seguro;
    }
    
}
