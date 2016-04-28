package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoConciliado;
import com.portalpostal.model.LancamentoTransferencia;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.dd.TipoLancamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoConciliadoHandler extends GenericHandler implements ResultSetHandler<LancamentoConciliado> {
        
    public LancamentoConciliadoHandler() {
        super("lancamento_conciliado");
    }
    
    public LancamentoConciliadoHandler(String table) {
        super(table);
    }

    public LancamentoConciliado handle(ResultSet result) throws SQLException {
        LancamentoConciliado lancamento = new LancamentoConciliado();
        lancamento.setIdLancamentoConciliado(getInt(result, "idLancamentoConciliado"));
        lancamento.setConta(getConta(result));
        lancamento.setPlanoConta(getPlanoConta(result));
        lancamento.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamento.setCompetencia(getDate(result, "competencia"));
        lancamento.setDataEmissao(getDate(result, "dataEmissao"));
        lancamento.setDataLancamento(getDate(result, "dataLancamento"));
        lancamento.setValor(getDouble(result, "valor"));
        return lancamento;
    }
    
    private Conta getConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "conta.idConta")) return null;
        return new ContaHandler().handle(result); 
    }
    
    private PlanoConta getPlanoConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "plano_conta.idPlanoConta")) return null;
        return new PlanoContaHandler().handle(result); 
    }
    
}
