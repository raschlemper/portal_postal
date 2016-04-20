package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.LancamentoParcelado;
import com.portalpostal.model.LancamentoParcelado;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.TipoDocumento;
import com.portalpostal.model.TipoFormaPagamento;
import com.portalpostal.model.dd.TipoFrequencia;
import com.portalpostal.model.dd.TipoLancamento;
import com.portalpostal.model.dd.TipoSituacao;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

class LancamentoParceladoHandler extends GenericHandler implements ResultSetHandler<LancamentoParcelado> {
    
    public LancamentoParceladoHandler() {
        super("lancamento_parcelado");
    }
    
    public LancamentoParceladoHandler(String table) {
        super(table);
    }

    public LancamentoParcelado handle(ResultSet result) throws SQLException {
        LancamentoParcelado lancamentoParcelado = new LancamentoParcelado();
        lancamentoParcelado.setIdLancamentoParcelado(getInt(result, "idLancamentoParcelado"));
        lancamentoParcelado.setConta(getConta(result));
        lancamentoParcelado.setPlanoConta(getPlanoConta(result));
        lancamentoParcelado.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamentoParcelado.setFavorecido(getString(result, "favorecido"));
        lancamentoParcelado.setNumero(getString(result, "numero"));
        lancamentoParcelado.setDocumento(getTipoDocumento(result));
        lancamentoParcelado.setFormaPagamento(getTipoFormaPagamento(result));
        lancamentoParcelado.setFrequencia(TipoFrequencia.values()[getInt(result, "frequencia")]);
        lancamentoParcelado.setQuantidadeParcela(getInt(result, "quantidadeParcela"));
        lancamentoParcelado.setDataEmissao(getDate(result, "dataEmissao"));
        lancamentoParcelado.setValorTotal(getDouble(result, "valorTotal"));
        lancamentoParcelado.setHistorico(getString(result, "historico"));
        return lancamentoParcelado;
    }
    
    private Conta getConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "conta.idConta")) return null;
        return new ContaHandler().handle(result); 
    }
    
    private PlanoConta getPlanoConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "plano_conta.idPlanoConta")) return null;
        return new PlanoContaHandler().handle(result); 
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

