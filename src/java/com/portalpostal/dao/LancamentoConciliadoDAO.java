package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoConciliadoHandler;
import com.portalpostal.model.LancamentoConciliado;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoConciliadoDAO extends GenericDAO { 
    
    private final LancamentoConciliadoHandler lancamentoConciliadoHandler;

    public LancamentoConciliadoDAO(String nameDB) { 
        super(nameDB, LancamentoConciliadoDAO.class);
        lancamentoConciliadoHandler = new LancamentoConciliadoHandler();
    } 

    public List<LancamentoConciliado> findAll() throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_conciliado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE .idConta = conta.idConta "
                   + "ORDER BY lancamento_conciliado.dataVencimento";        
        return findAll(sql, null, lancamentoConciliadoHandler);
    }

    public LancamentoConciliado find(Integer idLancamentoConciliado) throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_conciliado "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_conciliado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE lancamento_conciliado.idConta = conta.idConta "
                   + "AND lancamento_conciliado.idLancamentoConciliado = :idLancamentoConciliado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoConciliado", idLancamentoConciliado);
        return (LancamentoConciliado) find(sql, params, lancamentoConciliadoHandler);
    }

    public LancamentoConciliado save(LancamentoConciliado lancamentoConciliado) throws Exception {  
        String sql = "INSERT INTO lancamento_conciliado (idConta, idPlanoConta, tipo, competencia, "
                   + "dataEmissao, dataLancamento, valor) "
                   + "VALUES(:idConta, :idPlanoConta, :tipo, :competencia, :dataEmissao, "
                   + ":dataLancamento, :valor)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamentoConciliado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoConciliado.getPlanoConta() == null ? null : lancamentoConciliado.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamentoConciliado.getTipo().ordinal()); 
        params.put("competencia", lancamentoConciliado.getCompetencia());       
        params.put("dataEmissao", lancamentoConciliado.getDataEmissao());    
        params.put("dataLancamento", lancamentoConciliado.getDataLancamento());
        params.put("valor", lancamentoConciliado.getValor());          
        Integer idLancamentoConciliado = save(sql, params, lancamentoConciliadoHandler);
        return find(idLancamentoConciliado);
    }

    public LancamentoConciliado update(LancamentoConciliado lancamentoConciliado) throws Exception {
        String sql = "UPDATE lancamento_conciliado "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, "
                   + "tipo = :tipo, competencia = :competencia, dataEmissao = :dataEmissao, "
                   + "dataLancamento = :dataLancamento, valor = :valor "
                   + "WHERE idLancamentoConciliado = :idLancamentoConciliado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoConciliado", lancamentoConciliado.getIdLancamentoConciliado());
        params.put("idConta", lancamentoConciliado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoConciliado.getPlanoConta() == null ? null : lancamentoConciliado.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamentoConciliado.getTipo().ordinal()); 
        params.put("competencia", lancamentoConciliado.getCompetencia());       
        params.put("dataEmissao", lancamentoConciliado.getDataEmissao());    
        params.put("dataLancamento", lancamentoConciliado.getDataLancamento());
        params.put("valor", lancamentoConciliado.getValor());           
        update(sql, params, lancamentoConciliadoHandler);
        return lancamentoConciliado;  
    }

    public LancamentoConciliado remove(Integer idLancamentoConciliado) throws Exception { 
        String sql = "DELETE FROM lancamento_conciliado WHERE idLancamentoConciliado = :idLancamentoConciliado ";
        LancamentoConciliado lancamentoConciliado = find(idLancamentoConciliado);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoConciliado", idLancamentoConciliado);
        remove(sql, params, lancamentoConciliadoHandler);
        return lancamentoConciliado;
    }
}
