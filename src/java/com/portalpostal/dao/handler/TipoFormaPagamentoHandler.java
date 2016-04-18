package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoFormaPagamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

class TipoFormaPagamentoHandler extends GenericHandler implements ResultSetHandler<TipoFormaPagamento> {

    public TipoFormaPagamentoHandler() {
        super("tipo_forma_pagamento");
    }
    
    public TipoFormaPagamentoHandler(String table) {
        super(table);
    }

    public TipoFormaPagamento handle(ResultSet result) throws SQLException {
        TipoFormaPagamento tipoFormaPagamento = new TipoFormaPagamento();
        tipoFormaPagamento.setIdTipoFormaPagamento(getInt(result, "idTipoFormaPagamento"));
        tipoFormaPagamento.setDescricao(getString(result, "descricao"));
        return tipoFormaPagamento;
    }
    
}
