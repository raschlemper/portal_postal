package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoRateioHandler;
import com.portalpostal.model.LancamentoRateio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoRateioDAO extends GenericDAO { 
    
    private final LancamentoRateioHandler lancamentoRateioHandler;

    public LancamentoRateioDAO(String nameDB) { 
        super(nameDB, LancamentoRateioDAO.class);
        lancamentoRateioHandler = new LancamentoRateioHandler();
    } 

    public List<LancamentoRateio> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_rateio "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_rateio.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_rateio.idCentroCusto = centro_custo.idCentroCusto) "
                   + "ORDER BY lancamento_rateio.idLancamentoRateio";        
        return findAll(sql, null, lancamentoRateioHandler);
    }

    public LancamentoRateio find(Integer idLancamentoRateio) throws Exception {
        String sql = "SELECT * FROM lancamento_rateio "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_rateio.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_rateio.idCentroCusto = centro_custo.idCentroCusto) "
                   + "WHERE lancamento_rateio.idLancamentoRateio = :idLancamentoRateio";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoRateio", idLancamentoRateio);
        return (LancamentoRateio) find(sql, params, lancamentoRateioHandler);
    }

    public List<LancamentoRateio> findByLancamento(Integer idLancamento) throws Exception {
        String sql = "SELECT * FROM lancamento_rateio "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_rateio.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_rateio.idCentroCusto = centro_custo.idCentroCusto) "
                   + "WHERE lancamento_rateio.idLancamento = :idLancamento";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", idLancamento);
        return findAll(sql, params, lancamentoRateioHandler);
    }

    public LancamentoRateio save(LancamentoRateio lancamentoRateio) throws Exception {  
        String sql = "INSERT INTO lancamento_rateio (idPlanoConta, idCentroCusto, idLancamento, valor, observacao) "
                   + "VALUES(:idPlanoConta, :idCentroCusto, :idLancamento, :valor, :observacao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idPlanoConta", (lancamentoRateio.getPlanoConta() == null ? null : lancamentoRateio.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoRateio.getCentroCusto()== null ? null : lancamentoRateio.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamento", (lancamentoRateio.getLancamento() == null ? null : lancamentoRateio.getLancamento().getIdLancamento()));
        params.put("valor", lancamentoRateio.getValor());      
        params.put("observacao", lancamentoRateio.getObservacao()); 
        Integer idLancamentoRateio = save(sql, params, lancamentoRateioHandler);
        return find(idLancamentoRateio);
    }

    public LancamentoRateio update(LancamentoRateio lancamentoRateio) throws Exception {
        String sql = "UPDATE lancamento_rateio "
                   + "SET idPlanoConta = :idPlanoConta, idCentroCusto = :idCentroCusto, idLancamento = :idLancamento, "
                   + "valor = :valor, observacao = :observacao "
                   + "WHERE idLancamentoRateio = :idLancamentoRateio ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoRateio", lancamentoRateio.getIdLancamentoRateio());
        params.put("idPlanoConta", (lancamentoRateio.getPlanoConta() == null ? null : lancamentoRateio.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoRateio.getCentroCusto()== null ? null : lancamentoRateio.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamento", (lancamentoRateio.getLancamento() == null ? null : lancamentoRateio.getLancamento().getIdLancamento()));
        params.put("valor", lancamentoRateio.getValor());      
        params.put("observacao", lancamentoRateio.getObservacao()); 
        update(sql, params, lancamentoRateioHandler);
        return lancamentoRateio;  
    }

    public LancamentoRateio remove(Integer idLancamentoRateio) throws Exception { 
        String sql = "DELETE FROM lancamento_rateio WHERE idLancamentoRateio = :idLancamentoRateio ";
        LancamentoRateio lancamentoRateio = find(idLancamentoRateio);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoRateio", idLancamentoRateio);
        remove(sql, params, lancamentoRateioHandler);
        return lancamentoRateio;
    }
    
}
