package com.portalpostal.dao.handler;

import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.dd.TipoPlanoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class PlanoContaHandler extends GenericHandler implements ResultSetHandler<PlanoConta> {
            
    public PlanoContaHandler() { 
        super("plano_conta");
    }
    
    public PlanoContaHandler(String table) {
        super(table);
    }

    @Override
    public PlanoConta handle(ResultSet result) throws SQLException {
        PlanoConta planoConta = new PlanoConta();
        planoConta.setIdPlanoConta(getInt(result, "idPlanoConta"));
        planoConta.setTipo((getInt(result, "tipo") == null ? null : TipoPlanoConta.values()[getInt(result, "tipo")]));
        planoConta.setNome(getString(result, "nome"));
        planoConta.setCodigo(getInt(result, "codigo"));
        planoConta.setGrupo(getGrupo(result));
        return planoConta;    
    }
    
    private PlanoConta getGrupo(ResultSet result) throws SQLException {
        if(!existColumn(result, "plano_conta.grupo")) return null;
        Integer idPlanoConta = getInt(result, "grupo");
        if(idPlanoConta == null) return null;
        PlanoConta plano = new PlanoConta();
        plano.setIdPlanoConta(idPlanoConta);
        return plano;
    }
    
}
