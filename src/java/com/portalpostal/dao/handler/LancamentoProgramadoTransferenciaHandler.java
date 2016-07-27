package com.portalpostal.dao.handler;

import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoProgramadoTransferencia;
import com.portalpostal.model.TipoDocumento;
import com.portalpostal.model.TipoFormaPagamento;
import com.portalpostal.model.dd.TipoFrequencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoProgramadoTransferenciaHandler extends GenericHandler implements ResultSetHandler<LancamentoProgramadoTransferencia> {
        
    public LancamentoProgramadoTransferenciaHandler() {
        super("lancamento_transferencia_programado");
    }
    
    public LancamentoProgramadoTransferenciaHandler(String table) {
        super(table);
    }

    public LancamentoProgramadoTransferencia handle(ResultSet result) throws SQLException {
        LancamentoProgramadoTransferencia lancamento = new LancamentoProgramadoTransferencia();
        lancamento.setIdLancamentoProgramadoTransferencia(getInt(result, "idLancamentoProgramadoTransferencia"));
        lancamento.setLancamentoProgramadoOrigem(getLancamentoProgramadoOrigem(result));
        lancamento.setLancamentoProgramadoDestino(getLancamentoProgramadoDestino(result));
        lancamento.setNumero(getString(result, "numero"));
        lancamento.setDocumento(getTipoDocumento(result));
        lancamento.setFormaPagamento(getTipoFormaPagamento(result));
        lancamento.setFrequencia(TipoFrequencia.values()[getInt(result, "frequencia")]);
        lancamento.setDataCompetencia(getDate(result, "dataCompetencia"));
        lancamento.setDataEmissao(getDate(result, "dataEmissao"));
        lancamento.setDataVencimento(getDate(result, "dataVencimento"));
        lancamento.setValor(getDouble(result, "valor"));
        lancamento.setHistorico(getString(result, "historico"));
        lancamento.setObservacao(getString(result, "observacao"));
        lancamento.setUsuario(getString(result, "usuario"));
        return lancamento;
    }
    
    private LancamentoProgramado getLancamentoProgramadoOrigem(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamentoProgramadoOrigem.idLancamentoProgramado")) return null;
        return new LancamentoProgramadoHandler("lancamentoProgramadoOrigem").handle(result); 
    }
    
    private LancamentoProgramado getLancamentoProgramadoDestino(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamentoProgramadoDestino.idLancamentoProgramado")) return null;
        return new LancamentoProgramadoHandler("lancamentoProgramadoDestino").handle(result); 
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
