package com.portalpostal.dao.handler;

import com.portalpostal.model.CentroCusto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CentroCustoHandler extends GenericHandler implements ResultSetHandler<CentroCusto> {
            
    public CentroCustoHandler() { 
        super("centro_custo");
    }
    
    public CentroCustoHandler(String table) {
        super(table);
    }

    @Override
    public CentroCusto handle(ResultSet result) throws SQLException {
        CentroCusto centroCusto = new CentroCusto();
        centroCusto.setIdCentroCusto(getInt(result, "idCentroCusto"));
        centroCusto.setNome(getString(result, "nome"));
        centroCusto.setCodigo(getInt(result, "codigo"));
        centroCusto.setGrupo(getGrupo(result));
        return centroCusto;    
    }
    
    private CentroCusto getGrupo(ResultSet result) throws SQLException {
        if(!existColumn(result, "centro_custo.grupo")) return null;
        Integer idCentroCusto = getInt(result, "grupo");
        if(idCentroCusto == null) return null;
        CentroCusto plano = new CentroCusto();
        plano.setIdCentroCusto(idCentroCusto);
        return plano;
    }
    
}
