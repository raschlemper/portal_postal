package com.portalpostal.dao.handler;

import com.portalpostal.model.CentroCusto;
import com.portalpostal.model.Conta;
import com.portalpostal.model.Favorecido;
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
        lancamentoProgramado.setCentroCusto(getCentroCusto(result));
        lancamentoProgramado.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamentoProgramado.setFavorecido(getFavorecido(result));
        lancamentoProgramado.setNumero(getString(result, "numero"));
        lancamentoProgramado.setDocumento(getTipoDocumento(result));
        lancamentoProgramado.setFormaPagamento(getTipoFormaPagamento(result));
        lancamentoProgramado.setFrequencia(TipoFrequencia.values()[getInt(result, "frequencia")]);
        lancamentoProgramado.setQuantidadeParcela(getInt(result, "quantidadeParcela"));
        lancamentoProgramado.setNumeroParcela(getInt(result, "numeroParcela"));
        lancamentoProgramado.setDataCompetencia(getDate(result, "dataCompetencia"));
        lancamentoProgramado.setDataEmissao(getDate(result, "dataEmissao"));
        lancamentoProgramado.setDataVencimento(getDate(result, "dataVencimento"));
        lancamentoProgramado.setValor(getDouble(result, "valor"));
        lancamentoProgramado.setSituacao(TipoSituacaoLancamentoProgramado.values()[getInt(result, "situacao")]);
        lancamentoProgramado.setHistorico(getString(result, "historico"));
        lancamentoProgramado.setObservacao(getString(result, "observacao"));
        lancamentoProgramado.setUsuario(getString(result, "usuario"));
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
    
    private CentroCusto getCentroCusto(ResultSet result) throws SQLException {
        if(!existColumn(result, "centro_custo.idCentroCusto")) return null;
        return new CentroCustoHandler().handle(result); 
    }
    
    private TipoDocumento getTipoDocumento(ResultSet result) throws SQLException {
        if(!existColumn(result, "tipo_documento.idTipoDocumento")) return null;
        return new TipoDocumentoHandler().handle(result); 
    }
    
    private TipoFormaPagamento getTipoFormaPagamento(ResultSet result) throws SQLException {
        if(!existColumn(result, "tipo_forma_pagamento.idTipoFormaPagamento")) return null;
        return new TipoFormaPagamentoHandler().handle(result); 
    }
    
    private Favorecido getFavorecido(ResultSet result) throws SQLException {
        if(!existColumn(result, "favorecido.idFavorecido")) return null;
        return new FavorecidoHandler().handle(result); 
    }     
    
}
