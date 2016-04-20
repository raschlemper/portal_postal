package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.dd.TipoLancamento;
import com.portalpostal.model.dd.TipoModeloLancamento;
import com.portalpostal.model.dd.TipoSituacaoLancamento;
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
        lancamento.setLancamentoProgramado(getLancamentoProgramado(result));
        lancamento.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamento.setFavorecido(getString(result, "favorecido"));
        lancamento.setNumero(getString(result, "numero"));
        lancamento.setDataEmissao(getDate(result, "dataEmissao"));
        lancamento.setDataVencimento(getDate(result, "dataVencimento"));
        lancamento.setDataLancamento(getDate(result, "dataLancamento"));
        lancamento.setDataCompensacao(getDate(result, "dataCompensacao"));
        lancamento.setValor(getDouble(result, "valor"));
        lancamento.setSituacao(TipoSituacaoLancamento.values()[getInt(result, "situacao")]);
        lancamento.setModelo(TipoModeloLancamento.values()[getInt(result, "modelo")]);
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
        
    private LancamentoProgramado getLancamentoProgramado(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento_programado.idLancamentoProgramado")) return null;
        return new LancamentoProgramadoHandler().handle(result); 
    }
    
}
