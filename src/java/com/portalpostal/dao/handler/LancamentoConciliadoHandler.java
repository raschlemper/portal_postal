package com.portalpostal.dao.handler;

import com.portalpostal.model.CentroCusto;
import com.portalpostal.model.Conta;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoConciliado;
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
        LancamentoConciliado lancamentoConciliado = new LancamentoConciliado();
        lancamentoConciliado.setIdLancamentoConciliado(getInt(result, "idLancamentoConciliado"));
        lancamentoConciliado.setConta(getConta(result));
        lancamentoConciliado.setPlanoConta(getPlanoConta(result));
        lancamentoConciliado.setCentroCusto(getCentroCusto(result));
        lancamentoConciliado.setLancamento(getLancamento(result));
        lancamentoConciliado.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamentoConciliado.setNumeroLote(getInt(result, "numeroLote"));
        lancamentoConciliado.setDataCompetencia(getDate(result, "dataCompetencia"));
        lancamentoConciliado.setDataEmissao(getDate(result, "dataEmissao"));
        lancamentoConciliado.setDataLancamento(getDate(result, "dataLancamento"));
        lancamentoConciliado.setValor(getDouble(result, "valor"));
        lancamentoConciliado.setHistorico(getString(result, "historico"));
        lancamentoConciliado.setUsuario(getString(result, "usuario"));
        return lancamentoConciliado;
    }
    
    private Conta getConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "conta.idConta")) return null;
        return new ContaHandler().handle(result); 
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
        return new LancamentoHandler().handle(result); 
    }
    
}
