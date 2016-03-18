package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoConta;
import com.portalpostal.model.dd.TipoContaCategoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class TipoContaHandler implements ResultSetHandler<TipoConta> {
    
    private String table = "tipo_conta";
        
    public TipoContaHandler() { }
    
    public TipoContaHandler(String table) {
        this.table = table;      
    }

    @Override
    public TipoConta handle(ResultSet result) throws SQLException {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setIdTipoConta(result.getInt(table  + ".idTipoConta"));
        tipoConta.setCategoria(TipoContaCategoria.values()[result.getInt(table  + ".categoria")]);
        tipoConta.setDescricao(result.getString(table  + ".descricao"));
        return tipoConta;    
    }
    
}
