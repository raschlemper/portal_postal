package com.portalpostal.dao.handler;

import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.dd.TipoPlanoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class PlanoContaHandler implements ResultSetHandler<PlanoConta> {
    
    private String table = "plano_conta";
        
    public PlanoContaHandler() { }
    
    public PlanoContaHandler(String table) {
        this.table = table;      
    }

    @Override
    public PlanoConta handle(ResultSet result) throws SQLException {
        PlanoConta planoConta = new PlanoConta();
        planoConta.setIdPlanoConta(result.getInt(table  + ".idPlanoConta"));
        planoConta.setTipo(TipoPlanoConta.values()[result.getInt(table  + ".tipo")]);
        planoConta.setNome(result.getString(table  + ".nome"));
        planoConta.setCodigo(result.getInt(table  + ".codigo"));
        planoConta.setGrupo(getGrupo(result));
        return planoConta;    
    }
    
    private PlanoConta getGrupo(ResultSet result) throws SQLException {
        Integer grupo = (Integer) result.getObject(table  + ".grupo");
        if(grupo == null) return null;
        PlanoConta planoConta = new PlanoConta();
        planoConta.setIdPlanoConta(grupo);
        return planoConta;
    }
    
}
