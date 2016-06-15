package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoProgramadoRateioHandler;
import com.portalpostal.model.LancamentoProgramadoRateio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoProgramadoRateioDAO extends GenericDAO { 
    
    private final LancamentoProgramadoRateioHandler lancamentoProgramadoRateioHandler;

    public LancamentoProgramadoRateioDAO(String nameDB) { 
        super(nameDB, LancamentoProgramadoRateioDAO.class);
        lancamentoProgramadoRateioHandler = new LancamentoProgramadoRateioHandler();
    } 

    public List<LancamentoProgramadoRateio> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_programado_rateio "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado_rateio.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado_rateio.idCentroCusto = centro_custo.idCentroCusto) "
                   + "ORDER BY lancamento_programado_rateio.idLancamentoProgramadoRateio";        
        return findAll(sql, null, lancamentoProgramadoRateioHandler);
    }

    public LancamentoProgramadoRateio find(Integer idLancamentoProgramadoRateio) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_rateio "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado_rateio.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado_rateio.idCentroCusto = centro_custo.idCentroCusto) "
                   + "WHERE lancamento_programado_rateio.idLancamentoProgramadoRateio = :idLancamentoProgramadoRateio";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoRateio", idLancamentoProgramadoRateio);
        return (LancamentoProgramadoRateio) find(sql, params, lancamentoProgramadoRateioHandler);
    }

    public List<LancamentoProgramadoRateio> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_rateio "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado_rateio.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado_rateio.idCentroCusto = centro_custo.idCentroCusto) "
                   + "WHERE lancamento_programado_rateio.idLancamentoProgramado = :idLancamentoProgramado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        return findAll(sql, params, lancamentoProgramadoRateioHandler);
    }

    public LancamentoProgramadoRateio save(LancamentoProgramadoRateio lancamentoProgramadoRateio) throws Exception {  
        String sql = "INSERT INTO lancamento_programado_rateio (idPlanoConta, idCentroCusto, idLancamentoProgramado, valor, observacao) "
                   + "VALUES(:idPlanoConta, :idCentroCusto, :idLancamentoProgramado, :valor, :observacao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idPlanoConta", (lancamentoProgramadoRateio.getPlanoConta() == null ? null : lancamentoProgramadoRateio.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoProgramadoRateio.getCentroCusto()== null ? null : lancamentoProgramadoRateio.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamentoProgramado", (lancamentoProgramadoRateio.getLancamentoProgramado()== null ? null : lancamentoProgramadoRateio.getLancamentoProgramado().getIdLancamentoProgramado()));
        params.put("valor", lancamentoProgramadoRateio.getValor());      
        params.put("observacao", lancamentoProgramadoRateio.getObservacao()); 
        Integer idLancamentoProgramadoRateio = save(sql, params, lancamentoProgramadoRateioHandler);
        return find(idLancamentoProgramadoRateio);
    }

    public LancamentoProgramadoRateio update(LancamentoProgramadoRateio lancamentoProgramadoRateio) throws Exception {
        String sql = "UPDATE lancamento_programado_rateio "
                   + "SET idPlanoConta = :idPlanoConta, idCentroCusto = :idCentroCusto, idLancamentoProgramado = :idLancamentoProgramado, "
                   + "valor = :valor, observacao = :observacao "
                   + "WHERE idLancamentoProgramadoRateio = :idLancamentoProgramadoRateio ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoRateio", lancamentoProgramadoRateio.getIdLancamentoProgramadoRateio());
        params.put("idPlanoConta", (lancamentoProgramadoRateio.getPlanoConta() == null ? null : lancamentoProgramadoRateio.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoProgramadoRateio.getCentroCusto()== null ? null : lancamentoProgramadoRateio.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamentoProgramado", (lancamentoProgramadoRateio.getLancamentoProgramado()== null ? null : lancamentoProgramadoRateio.getLancamentoProgramado().getIdLancamentoProgramado()));
        params.put("valor", lancamentoProgramadoRateio.getValor());      
        params.put("observacao", lancamentoProgramadoRateio.getObservacao()); 
        update(sql, params, lancamentoProgramadoRateioHandler);
        return lancamentoProgramadoRateio;  
    }

    public LancamentoProgramadoRateio remove(Integer idLancamentoProgramadoRateio) throws Exception { 
        String sql = "DELETE FROM lancamento_programado_rateio WHERE idLancamentoProgramadoRateio = :idLancamentoProgramadoRateio ";
        LancamentoProgramadoRateio lancamentoProgramadoRateio = find(idLancamentoProgramadoRateio);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoProgramadoRateio", idLancamentoProgramadoRateio);
        remove(sql, params, lancamentoProgramadoRateioHandler);
        return lancamentoProgramadoRateio;
    }
    
}
