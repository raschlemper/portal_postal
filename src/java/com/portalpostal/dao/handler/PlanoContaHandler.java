package com.portalpostal.dao.handler;

import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.type.TipoPlanoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class PlanoContaHandler implements ResultSetHandler<PlanoConta> {

    @Override
    public PlanoConta handle(ResultSet result) throws SQLException {
        PlanoConta planoConta = new PlanoConta();
        planoConta.setIdPlanoConta(result.getInt("plano_conta.idPlanoConta"));
        planoConta.setTipo(TipoPlanoConta.values()[result.getInt("plano_conta.tipo")]);
        planoConta.setNome(result.getString("plano_conta.nome"));
        planoConta.setCodigo(result.getInt("plano_conta.codigo"));
        planoConta.setGrupo(getGrupo(result));
        return planoConta;    
    }
    
    private PlanoConta getGrupo(ResultSet result) throws SQLException {
        Integer grupo = (Integer) result.getObject("plano_conta.grupo");
        if(grupo == null) return null;
        PlanoConta planoConta = new PlanoConta();
        planoConta.setIdPlanoConta(grupo);
        return planoConta;
    }
    
}
