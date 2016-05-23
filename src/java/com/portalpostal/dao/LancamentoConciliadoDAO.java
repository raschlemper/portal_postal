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
        String sql = "SELECT * FROM conta, plano_conta, lancamento, lancamento_conciliado "
                   + "WHERE lancamento_conciliado.idConta = conta.idConta "
                   + "AND lancamento_conciliado.idPlanoConta = plano_conta.idPlanoConta "
                   + "AND lancamento_conciliado.idLancamento = lancamento.idLancamento "
                   + "ORDER BY lancamento_conciliado.dataLancamento";        
        return findAll(sql, null, lancamentoConciliadoHandler);
    }

    public LancamentoConciliado find(Integer idLancamentoConciliado) throws Exception {
        String sql = "SELECT * FROM conta, plano_conta, lancamento, lancamento_conciliado "
                   + "WHERE lancamento_conciliado.idConta = conta.idConta "
                   + "AND lancamento_conciliado.idPlanoConta = plano_conta.idPlanoConta "
                   + "AND lancamento_conciliado.idLancamento = lancamento.idLancamento "
                   + "AND lancamento_conciliado.idLancamentoConciliado = :idLancamentoConciliado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoConciliado", idLancamentoConciliado);
        return (LancamentoConciliado) find(sql, params, lancamentoConciliadoHandler);
    }

    public Integer findLastLote() throws Exception {
        String sql = "SELECT max(numeroLote) FROM lancamento_conciliado ";
        return (Integer) find(sql, null, Integer.class);
    }

    public LancamentoConciliado save(LancamentoConciliado lancamentoConciliado) throws Exception {  
        String sql = "INSERT INTO lancamento_conciliado (idConta, idPlanoConta, idLancamento, tipo, numeroLote, "
                   + "dataCompetencia, dataEmissao, dataLancamento, valor, historico) "
                   + "VALUES(:idConta, :idPlanoConta, :idLancamento, :tipo, :numeroLote, :dataCompetencia, :dataEmissao, "
                   + ":dataLancamento, :valor, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamentoConciliado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoConciliado.getPlanoConta() == null ? null : lancamentoConciliado.getPlanoConta().getIdPlanoConta()));
        params.put("idLancamento", lancamentoConciliado.getLancamento().getIdLancamento());
        params.put("tipo", lancamentoConciliado.getTipo().ordinal()); 
        params.put("numeroLote", lancamentoConciliado.getNumeroLote()); 
        params.put("dataCompetencia", lancamentoConciliado.getDataCompetencia());       
        params.put("dataEmissao", lancamentoConciliado.getDataEmissao());    
        params.put("dataLancamento", lancamentoConciliado.getDataLancamento());
        params.put("valor", lancamentoConciliado.getValor());  
        params.put("historico", lancamentoConciliado.getHistorico());              
        Integer idLancamentoConciliado = save(sql, params, lancamentoConciliadoHandler);
        return find(idLancamentoConciliado);
    }

    public LancamentoConciliado update(LancamentoConciliado lancamentoConciliado) throws Exception {
        String sql = "UPDATE lancamento_conciliado "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, idLancamento = :idLancamento, "
                   + "tipo = :tipo, numeroLote = :numeroLote, dataCompetencia = :dataCompetencia, dataEmissao = :dataEmissao, "
                   + "dataLancamento = :dataLancamento, valor = :valor, historico= :historico "
                   + "WHERE idLancamentoConciliado = :idLancamentoConciliado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoConciliado", lancamentoConciliado.getIdLancamentoConciliado());
        params.put("idConta", lancamentoConciliado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoConciliado.getPlanoConta() == null ? null : lancamentoConciliado.getPlanoConta().getIdPlanoConta()));
        params.put("idLancamento", lancamentoConciliado.getLancamento().getIdLancamento());
        params.put("tipo", lancamentoConciliado.getTipo().ordinal()); 
        params.put("numeroLote", lancamentoConciliado.getNumeroLote()); 
        params.put("dataCompetencia", lancamentoConciliado.getDataCompetencia());       
        params.put("dataEmissao", lancamentoConciliado.getDataEmissao());    
        params.put("dataLancamento", lancamentoConciliado.getDataLancamento());  
        params.put("valor", lancamentoConciliado.getValor());   
        params.put("historico", lancamentoConciliado.getHistorico());          
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
