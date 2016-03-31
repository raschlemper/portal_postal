package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoHandler;
import com.portalpostal.model.Lancamento;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoDAO extends GenericDAO { 
    
    private LancamentoHandler lancamentoHandler;

    public LancamentoDAO(String nameDB) { 
        super(nameDB, LancamentoDAO.class);
        lancamentoHandler = new LancamentoHandler();
    } 

    public List<Lancamento> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento, conta, plano_conta "
                   + "WHERE lancamento.idConta = conta.idConta "
                   + "AND lancamento.idPlanoConta = plano_conta.idPlanoConta "
                   + "ORDER BY lancamento.idLancamento";        
        return findAll(sql, null, lancamentoHandler);
    }

    public Lancamento find(Integer idLancamento) throws Exception {
        String sql = "SELECT * FROM lancamento, conta, plano_conta "
                   + "WHERE lancamento.idConta = conta.idConta "
                   + "AND lancamento.idPlanoConta = plano_conta.idPlanoConta "
                   + "AND lancamento.idLancamento = :idLancamento";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", idLancamento);
        return (Lancamento) find(sql, params, lancamentoHandler);
    }

    public Lancamento save(Lancamento lancamento) throws Exception {  
        String sql = "INSERT INTO lancamento (idConta, idPlanoConta, data, valor, historico) "
                   + "VALUES(:idConta, :idPlanoConta, :data, :valor, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamento.getConta().getIdConta());
        params.put("idPlanoConta", lancamento.getPlanoConta().getIdPlanoConta());
        params.put("data", lancamento.getData());      
        params.put("valor", lancamento.getValor());    
        params.put("historico", lancamento.getHistorico());       
        Integer idLancamento = save(sql, params, lancamentoHandler);
        return find(idLancamento);
    }

    public Lancamento update(Lancamento lancamento) throws Exception {
        String sql = "UPDATE lancamento "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, "
                   + "data = :data, valor = :valor, historico = :historico "
                   + "WHERE idLancamento = :idLancamento ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", lancamento.getIdLancamento());
        params.put("idConta", lancamento.getConta().getIdConta());
        params.put("idPlanoConta", lancamento.getPlanoConta().getIdPlanoConta());
        params.put("data", lancamento.getData());      
        params.put("valor", lancamento.getValor());    
        params.put("historico", lancamento.getHistorico());       
        update(sql, params, lancamentoHandler);
        return lancamento;  
    }

    public Lancamento remove(Integer idLancamento) throws Exception { 
        String sql = "DELETE FROM lancamento WHERE idLancamento = :idLancamento ";
        Lancamento lancamento = find(idLancamento);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamento", idLancamento);
        remove(sql, params, lancamentoHandler);
        return lancamento;
    }
}
