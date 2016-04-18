package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoProgramadoHandler;
import com.portalpostal.model.LancamentoProgramado;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoProgramadoDAO extends GenericDAO { 
    
    private final LancamentoProgramadoHandler lancamentoProgramadoHandler;

    public LancamentoProgramadoDAO(String nameDB) { 
        super(nameDB, LancamentoProgramadoDAO.class);
        lancamentoProgramadoHandler = new LancamentoProgramadoHandler();
    } 

    public List<LancamentoProgramado> findAll() throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_programado "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE lancamento_programado.idConta = conta.idConta "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento" 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_documento.idTipoFormaPagamento" 
                   + "ORDER BY lancamento_programado.data";        
        return findAll(sql, null, lancamentoProgramadoHandler);
    }

    public LancamentoProgramado find(Integer idLancamentoProgramado) throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_programado "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE lancamento_programado.idConta = conta.idConta "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento" 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_documento.idTipoFormaPagamento" 
                   + "AND lancamento_programado.idLancamentoProgramado = :idLancamentoProgramado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        return (LancamentoProgramado) find(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findByConta(Integer idConta) throws Exception {
        String sql = "SELECT lancamento_programado.*, plano_conta.*, tipo_documento.*, tipo_forma_pagamento.* "
                   + "FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_programado  "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE conta.idConta = lancamento_programado.idConta "
                   + "AND conta.idConta = :idConta "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento" 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_documento.idTipoFormaPagamento" 
                   + "ORDER BY lancamento_programado.data";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);       
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) throws Exception {  
        String sql = "INSERT INTO lancamento_programado (idConta, idPlanoConta, tipo, favorecido, numero, idTipoDocumento, "
                   + "idTipoFormaPagamento, frequencia, quantidadeParcela, numeroParcela, data, valor, situacao, historico) "
                   + "VALUES(:idConta, :idPlanoConta, :tipo, :favorecido, :numero, :idTipoDocumento, :idTipoFormaPagamento, "
                   + ":frequencia, :quantidadeParcela, :numeroParcela, :data, :valor, :situacao, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamentoProgramado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoProgramado.getPlanoConta() == null ? null : lancamentoProgramado.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamentoProgramado.getTipo().ordinal());     
        params.put("favorecido", lancamentoProgramado.getFavorecido());     
        params.put("numero", lancamentoProgramado.getNumero());        
        params.put("idTipoDocumento", lancamentoProgramado.getDocumento().getIdTipoDocumento());        
        params.put("idTipoformaPagamento", lancamentoProgramado.getFormaPagamento().getIdTipoFormaPagamento());          
        params.put("frequencia", lancamentoProgramado.getFrequencia().ordinal());        
        params.put("quantidadeParcela", lancamentoProgramado.getQuantidadeParcela());     
        params.put("numeroParcela", lancamentoProgramado.getNumeroParcela());     
        params.put("data", lancamentoProgramado.getData());
        params.put("valor", lancamentoProgramado.getValor());    
        params.put("situacao", lancamentoProgramado.getSituacao().ordinal());  
        params.put("historico", lancamentoProgramado.getHistorico());       
        Integer idLancamentoProgramado = save(sql, params, lancamentoProgramadoHandler);
        return find(idLancamentoProgramado);
    }

    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) throws Exception {
        String sql = "UPDATE lancamento_programado "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, tipo = :tipo, favorecido = :favorecido, "
                   + "numero = :numero, idTipoDocumento = :idTipoDocumento, idTipoformaPagamento = :idTipoformaPagamento, "
                   + "frequencia = :frequencia, quantidadeParcela = :quantidadeParcela, numeroParcela = :numeroParcela, "
                   + "data = :data, valor = :valor, historico = :historico, situacao = :situacao "
                   + "WHERE idLancamentoProgramado = :idLancamentoProgramado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", lancamentoProgramado.getIdLancamentoProgramado());
        params.put("idConta", lancamentoProgramado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoProgramado.getPlanoConta() == null ? null : lancamentoProgramado.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamentoProgramado.getTipo().ordinal());     
        params.put("favorecido", lancamentoProgramado.getFavorecido());     
        params.put("numero", lancamentoProgramado.getNumero());        
        params.put("idTipoDocumento", lancamentoProgramado.getDocumento().getIdTipoDocumento());        
        params.put("idTipoformaPagamento", lancamentoProgramado.getFormaPagamento().getIdTipoFormaPagamento());          
        params.put("frequencia", lancamentoProgramado.getFrequencia().ordinal());        
        params.put("quantidadeParcela", lancamentoProgramado.getQuantidadeParcela());    
        params.put("numeroParcela", lancamentoProgramado.getNumeroParcela());        
        params.put("data", lancamentoProgramado.getData());
        params.put("valor", lancamentoProgramado.getValor());  
        params.put("situacao", lancamentoProgramado.getSituacao().ordinal());    
        params.put("historico", lancamentoProgramado.getHistorico());     
        update(sql, params, lancamentoProgramadoHandler);
        return lancamentoProgramado;  
    }

    public LancamentoProgramado remove(Integer idLancamentoProgramado) throws Exception { 
        String sql = "DELETE FROM lancamento_programado WHERE idLancamentoProgramado = :idLancamentoProgramado ";
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        remove(sql, params, lancamentoProgramadoHandler);
        return lancamentoProgramado;
    }
}
