package com.portalpostal.dao.handler;

import com.portalpostal.model.CentroCusto;
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
        lancamento.setCentroCusto(getCentroCusto(result));
        lancamento.setLancamentoProgramado(getLancamentoProgramado(result));
        lancamento.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamento.setFavorecido(getString(result, "favorecido"));
        lancamento.setNumero(getString(result, "numero"));
        lancamento.setNumeroParcela(getInt(result, "numeroParcela"));
        lancamento.setDataCompetencia(getDate(result, "dataCompetencia"));
        lancamento.setDataEmissao(getDate(result, "dataEmissao"));
        lancamento.setDataVencimento(getDate(result, "dataVencimento"));
        lancamento.setDataLancamento(getDate(result, "dataLancamento"));
        lancamento.setDataCompensacao(getDate(result, "dataCompensacao"));
        lancamento.setValor(getDouble(result, "valor"));
        lancamento.setValorDesconto(getDouble(result, "valorDesconto"));
        lancamento.setValorJuros(getDouble(result, "valorJuros"));
        lancamento.setValorMulta(getDouble(result, "valorMulta"));
        lancamento.setSituacao(TipoSituacaoLancamento.values()[getInt(result, "situacao")]);
        lancamento.setModelo(TipoModeloLancamento.values()[getInt(result, "modelo")]);
        lancamento.setNumeroLoteConciliado(getInt(result, "numeroLoteConciliado"));
        lancamento.setAutenticacao(getString(result, "autenticacao"));
        lancamento.setHistorico(getString(result, "historico"));
        lancamento.setObservacao(getString(result, "observacao"));
        lancamento.setUsuario(getString(result, "usuario"));
        lancamento.setAnexos(getAnexos(result));
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
    
    private CentroCusto getCentroCusto(ResultSet result) throws SQLException {
        if(!existColumn(result, "centro_custo.idCentroCusto")) return null;
        return new CentroCustoHandler().handle(result); 
    }
        
    private LancamentoProgramado getLancamentoProgramado(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento_programado.idLancamentoProgramado")) return null;
        if(!existFKValue(result, "lancamento_programado.idLancamentoProgramado")) return null;
        return new LancamentoProgramadoHandler().handle(result); 
    }
        
    private boolean getAnexos(ResultSet result) throws SQLException {
        if(!existColumn(result, "anexos")) return false;
        Long quantidade = getLong(result, "anexos", null);
        if(quantidade > 0) return true;
        return false;
    }
    
}
