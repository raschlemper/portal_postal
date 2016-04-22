package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.LancamentoParcelado;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.TipoDocumento;
import com.portalpostal.model.TipoFormaPagamento;
import com.portalpostal.model.dd.TipoFrequencia;
import com.portalpostal.model.dd.TipoLancamento;
import com.portalpostal.model.dd.TipoSituacaoLancamentoProgramado;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoProgramadoHandler extends GenericHandler implements ResultSetHandler<LancamentoProgramado> {
    
    public LancamentoProgramadoHandler() {
        super("lancamento_programado");
    }
    
    public LancamentoProgramadoHandler(String table) {
        super(table);
    }

    public LancamentoProgramado handle(ResultSet result) throws SQLException {
        LancamentoProgramado lancamentoProgramado = new LancamentoProgramado();
        lancamentoProgramado.setIdLancamentoProgramado(getInt(result, "idLancamentoProgramado"));
        lancamentoProgramado.setConta(getConta(result));
        lancamentoProgramado.setPlanoConta(getPlanoConta(result));
        lancamentoProgramado.setLancamentoParcelado(getLancamentoParcelado(result));
        lancamentoProgramado.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamentoProgramado.setFavorecido(getString(result, "favorecido"));
        lancamentoProgramado.setNumero(getString(result, "numero"));
        lancamentoProgramado.setDocumento(getTipoDocumento(result));
        lancamentoProgramado.setFormaPagamento(getTipoFormaPagamento(result));
        lancamentoProgramado.setFrequencia(TipoFrequencia.values()[getInt(result, "frequencia")]);
        lancamentoProgramado.setNumeroParcela(getInt(result, "numeroParcela"));
        lancamentoProgramado.setDataEmissao(getDate(result, "dataEmissao"));
        lancamentoProgramado.setDataVencimento(getDate(result, "dataVencimento"));
        lancamentoProgramado.setValor(getDouble(result, "valor"));
        lancamentoProgramado.setSituacao(TipoSituacaoLancamentoProgramado.values()[getInt(result, "situacao")]);
        lancamentoProgramado.setHistorico(getString(result, "historico"));
        return lancamentoProgramado;
    }
    
    private Conta getConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "conta.idConta")) return null;
        return new ContaHandler().handle(result); 
    }
    
    private PlanoConta getPlanoConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "plano_conta.idPlanoConta")) return null;
        return new PlanoContaHandler().handle(result); 
    }
    
    private LancamentoParcelado getLancamentoParcelado(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento_parcelado.idLancamentoParcelado")) return null;
        if(!existFKValue(result, "lancamento_parcelado.idLancamentoParcelado")) return null;
        return new LancamentoParceladoHandler().handle(result); 
    }
    
    private TipoDocumento getTipoDocumento(ResultSet result) throws SQLException {
        if(!existColumn(result, "tipo_documento.idTipoDocumento")) return null;
        return new TipoDocumentoHandler().handle(result); 
    }
    
    private TipoFormaPagamento getTipoFormaPagamento(ResultSet result) throws SQLException {
        if(!existColumn(result, "tipo_forma_pagamento.idTipoFormaPagamento")) return null;
        return new TipoFormaPagamentoHandler().handle(result); 
    }
    
}
