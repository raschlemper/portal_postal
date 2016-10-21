package com.portalpostal.dao.handler;

import com.portalpostal.model.CentroCusto;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoProgramadoRateio;
import com.portalpostal.model.PlanoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoProgramadoRateioHandler extends GenericHandler implements ResultSetHandler<LancamentoProgramadoRateio> {
    
    public LancamentoProgramadoRateioHandler() {
        super("lancamento_programado_rateio");
    }
    
    public LancamentoProgramadoRateioHandler(String table) {
        super(table);
    }

    public LancamentoProgramadoRateio handle(ResultSet result) throws SQLException {
        LancamentoProgramadoRateio lancamentoProgramadoRateio = new LancamentoProgramadoRateio();
        lancamentoProgramadoRateio.setIdLancamentoProgramadoRateio(getInt(result, "idLancamentoProgramadoRateio"));
        lancamentoProgramadoRateio.setPlanoConta(getPlanoConta(result));
        lancamentoProgramadoRateio.setCentroCusto(getCentroCusto(result));
        lancamentoProgramadoRateio.setLancamentoProgramado(getLancamentoProgramado(result));
        lancamentoProgramadoRateio.setValor(getDouble(result, "valor"));
        lancamentoProgramadoRateio.setObservacao(getString(result, "observacao"));
        return lancamentoProgramadoRateio;
    }
    
    private PlanoConta getPlanoConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "plano_conta.idPlanoConta")) return null;
        return new PlanoContaHandler().handle(result); 
    }
    
    private CentroCusto getCentroCusto(ResultSet result) throws SQLException {
        if(!existColumn(result, "centro_custo.idCentroCusto")) return null;
        return new CentroCustoHandler().handle(result); 
    }
        
    private LancamentoProgramado getLancamentoProgramado(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento_programado.idLancamentoProgramado")) return null;
        if(!existFKValue(result, "lancamento_programado.idLancamentoProgramado")) return null;
        LancamentoProgramado lancamentoProgramado = new LancamentoProgramado();
        lancamentoProgramado.setIdLancamentoProgramado(getInt(result, "idLancamentoProgramadoRateio", "lancamento_programado"));
        return lancamentoProgramado; 
    }
    
}
