package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoParceladoHandler;
import com.portalpostal.model.LancamentoParcelado;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoParceladoDAO extends GenericDAO { 
    
    private final LancamentoParceladoHandler lancamentoParceladoHandler;

    public LancamentoParceladoDAO(String nameDB) { 
        super(nameDB, LancamentoParceladoDAO.class);
        lancamentoParceladoHandler = new LancamentoParceladoHandler();
    } 

    public List<LancamentoParcelado> findAll() throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_parcelado "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_parcelado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE lancamento_parcelado.idConta = conta.idConta "
                   + "AND lancamento_parcelado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_parcelado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "ORDER BY lancamento_parcelado.data";        
        return findAll(sql, null, lancamentoParceladoHandler);
    }

    public LancamentoParcelado find(Integer idLancamentoParcelado) throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_parcelado "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_parcelado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE lancamento_parcelado.idConta = conta.idConta "
                   + "AND lancamento_parcelado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_parcelado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "AND lancamento_parcelado.idLancamentoParcelado = :idLancamentoParcelado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoParcelado", idLancamentoParcelado);
        return (LancamentoParcelado) find(sql, params, lancamentoParceladoHandler);
    }

    public List<LancamentoParcelado> findByConta(Integer idConta) throws Exception {
        String sql = "SELECT lancamento_parcelado.*, plano_conta.*, tipo_documento.*, tipo_forma_pagamento.* "
                   + "FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_parcelado "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_parcelado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE conta.idConta = lancamento_parcelado.idConta "
                   + "AND conta.idConta = :idConta "
                   + "AND lancamento_parcelado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_parcelado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "ORDER BY lancamento_parcelado.data";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);       
        return findAll(sql, params, lancamentoParceladoHandler);
    }

    public LancamentoParcelado save(LancamentoParcelado lancamentoParcelado) throws Exception {  
        String sql = "INSERT INTO lancamento_parcelado (idConta, idPlanoConta, tipo, favorecido, numero, idTipoDocumento, "
                   + "idTipoFormaPagamento, frequencia, quantidadeParcela, dataEmissao, valorTotal, historico) "
                   + "VALUES(:idConta, :idPlanoConta, :tipo, :favorecido, :numero, :idTipoDocumento, :idTipoFormaPagamento, "
                   + ":frequencia, :quantidadeParcela, :dataEmissao, :valorTotal, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamentoParcelado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoParcelado.getPlanoConta() == null ? null : lancamentoParcelado.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamentoParcelado.getTipo().ordinal());     
        params.put("favorecido", lancamentoParcelado.getFavorecido());     
        params.put("numero", lancamentoParcelado.getNumero());        
        params.put("idTipoDocumento", lancamentoParcelado.getDocumento().getIdTipoDocumento());        
        params.put("idTipoFormaPagamento", lancamentoParcelado.getFormaPagamento().getIdTipoFormaPagamento());          
        params.put("frequencia", lancamentoParcelado.getFrequencia().ordinal());        
        params.put("quantidadeParcela", lancamentoParcelado.getQuantidadeParcela());     
        params.put("dataEmissao", lancamentoParcelado.getDataEmissao());    
        params.put("valorTotal", lancamentoParcelado.getValorTotal());    
        params.put("historico", lancamentoParcelado.getHistorico());       
        Integer idLancamentoParcelado = save(sql, params, lancamentoParceladoHandler);
        return find(idLancamentoParcelado);
    }

    public LancamentoParcelado update(LancamentoParcelado lancamentoParcelado) throws Exception {
        String sql = "UPDATE lancamento_parcelado "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, tipo = :tipo, favorecido = :favorecido, "
                   + "numero = :numero, idTipoDocumento = :idTipoDocumento, idTipoFormaPagamento = :idTipoFormaPagamento, "
                   + "frequencia = :frequencia, quantidadeParcela = :quantidadeParcela, dataEmissao = :dataEmissao, "
                   + "valorTotal = :valorTotal, historico = :historico "
                   + "WHERE idLancamentoParcelado = :idLancamentoParcelado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoParcelado", lancamentoParcelado.getIdLancamentoParcelado());
        params.put("idConta", lancamentoParcelado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoParcelado.getPlanoConta() == null ? null : lancamentoParcelado.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamentoParcelado.getTipo().ordinal());     
        params.put("favorecido", lancamentoParcelado.getFavorecido());     
        params.put("numero", lancamentoParcelado.getNumero());   
        params.put("idTipoDocumento", lancamentoParcelado.getDocumento().getIdTipoDocumento());        
        params.put("idTipoFormaPagamento", lancamentoParcelado.getFormaPagamento().getIdTipoFormaPagamento());          
        params.put("frequencia", lancamentoParcelado.getFrequencia().ordinal());        
        params.put("quantidadeParcela", lancamentoParcelado.getQuantidadeParcela());     
        params.put("dataEmissao", lancamentoParcelado.getDataEmissao());    
        params.put("valorTotal", lancamentoParcelado.getValorTotal());    
        params.put("historico", lancamentoParcelado.getHistorico());     
        update(sql, params, lancamentoParceladoHandler);
        return lancamentoParcelado;  
    }

    public LancamentoParcelado remove(Integer idLancamentoParcelado) throws Exception { 
        String sql = "DELETE FROM lancamento_parcelado WHERE idLancamentoParcelado = :idLancamentoParcelado ";
        LancamentoParcelado lancamentoParcelado = find(idLancamentoParcelado);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoParcelado", idLancamentoParcelado);
        remove(sql, params, lancamentoParceladoHandler);
        return lancamentoParcelado;
    }
}
