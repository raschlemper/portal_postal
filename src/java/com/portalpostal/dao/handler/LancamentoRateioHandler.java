package com.portalpostal.dao.handler;

import com.portalpostal.model.CentroCusto;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoRateio;
import com.portalpostal.model.PlanoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoRateioHandler extends GenericHandler implements ResultSetHandler<LancamentoRateio> {
    
    public LancamentoRateioHandler() {
        super("lancamento_rateio");
    }
    
    public LancamentoRateioHandler(String table) {
        super(table);
    }

    public LancamentoRateio handle(ResultSet result) throws SQLException {
        LancamentoRateio lancamentoRateio = new LancamentoRateio();
        lancamentoRateio.setIdLancamentoRateio(getInt(result, "idLancamentoRateio"));
        lancamentoRateio.setPlanoConta(getPlanoConta(result));
        lancamentoRateio.setCentroCusto(getCentroCusto(result));
        lancamentoRateio.setLancamento(getLancamento(result));
        lancamentoRateio.setValor(getDouble(result, "valor"));
        lancamentoRateio.setObservacao(getString(result, "observacao"));
        return lancamentoRateio;
    }
    
    private PlanoConta getPlanoConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "plano_conta.idPlanoConta")) return null;
        return new PlanoContaHandler().handle(result); 
    }
    
    private CentroCusto getCentroCusto(ResultSet result) throws SQLException {
        if(!existColumn(result, "centro_custo.idCentroCusto")) return null;
        return new CentroCustoHandler().handle(result); 
    }
        
    private Lancamento getLancamento(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento.idLancamento")) return null;
        if(!existFKValue(result, "lancamento.idLancamento")) return null;
        Lancamento lancamento = new Lancamento();
        lancamento.setIdLancamento(getInt(result, "idLancamentoRateio", "lancamento"));
        return lancamento; 
    }
    
}
