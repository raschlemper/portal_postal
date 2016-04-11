package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.dd.TipoLancamento;
import com.portalpostal.model.dd.TipoSituacao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoHandler extends GenericHandler implements ResultSetHandler<Lancamento> {
    
    public LancamentoHandler() {
        super("lancamento");
    }
    
    public LancamentoHandler(String table) {
        super(table);
    }

    public Lancamento handle(ResultSet result) throws SQLException {
        Lancamento lancamento = new Lancamento();
        lancamento.setIdLancamento(getInt(result, "idLancamento"));
        lancamento.setConta(getConta(result));
        lancamento.setPlanoConta(getPlanoConta(result));
        lancamento.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamento.setFavorecido(getString(result, "favorecido"));
        lancamento.setNumero(getString(result, "numero"));
        lancamento.setData(getDate(result, "data"));
        lancamento.setValor(getDouble(result, "valor"));
        lancamento.setSituacao(TipoSituacao.values()[getInt(result, "situacao")]);
        lancamento.setHistorico(getString(result, "historico"));
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
