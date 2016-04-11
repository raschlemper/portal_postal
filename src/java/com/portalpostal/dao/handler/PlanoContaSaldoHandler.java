package com.portalpostal.dao.handler;

import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.PlanoContaSaldo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class PlanoContaSaldoHandler extends GenericHandler implements ResultSetHandler<PlanoContaSaldo> {
            
    public PlanoContaSaldoHandler() { 
        super(null);
    }
    
    public PlanoContaSaldoHandler(String table) {
        super(table);
    }

    @Override
    public PlanoContaSaldo handle(ResultSet result) throws SQLException {
        PlanoContaSaldo planoConta = new PlanoContaSaldo();
        planoConta.setPlanoConta(getPlanoConta(result));
        planoConta.setAno(getInt(result, "ano"));
        planoConta.setMes(getInt(result, "mes"));
        planoConta.setValor(getDouble(result, "valor"));
        return planoConta;    
    }
    
    private PlanoConta getPlanoConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "idPlanoConta")) return null;
        PlanoConta plano = new PlanoConta();
        plano.setIdPlanoConta(getInt(result, "idPlanoConta"));
        return plano;
    }
    
}
