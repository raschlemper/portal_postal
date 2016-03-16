package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoConta;
import com.portalpostal.model.type.TipoContaCategoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class TipoContaHandler implements ResultSetHandler<TipoConta> {

    @Override
    public TipoConta handle(ResultSet result) throws SQLException {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setIdTipoConta(result.getInt("tipo_conta.idTipoConta"));
        tipoConta.setCategoria(TipoContaCategoria.values()[result.getInt("tipo_conta.categoria")]);
        tipoConta.setDescricao(result.getString("tipo_conta.descricao"));
        return tipoConta;    
    }
    
}
