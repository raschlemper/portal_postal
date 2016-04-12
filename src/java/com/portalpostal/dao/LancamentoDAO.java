package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoHandler;
import com.portalpostal.dao.handler.SaldoHandler;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.Saldo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoDAO extends GenericDAO { 
    
    private final LancamentoHandler lancamentoHandler;
    private final SaldoHandler saldoHandler;

    public LancamentoDAO(String nameDB) { 
        super(nameDB, LancamentoDAO.class);
        lancamentoHandler = new LancamentoHandler();
        saldoHandler = new SaldoHandler();
    } 

    public List<Lancamento> findAll() throws Exception {
        String sql = "SELECT * FROM conta, lancamento "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE lancamento.idConta = conta.idConta "
                   + "ORDER BY lancamento.data";        
        return findAll(sql, null, lancamentoHandler);
    }

    public Lancamento find(Integer idLancamento) throws Exception {
        String sql = "SELECT * FROM conta, lancamento "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE lancamento.idConta = conta.idConta "
                   + "AND lancamento.idLancamento = :idLancamento";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", idLancamento);
        return (Lancamento) find(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByConta(Integer idConta) throws Exception {
        String sql = "SELECT lancamento.*, plano_conta.* FROM conta, lancamento "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento.idPlanoConta = plano_conta.idPlanoConta) "
                   + "WHERE conta.idConta = lancamento.idConta "
                   + "AND conta.idConta = :idConta "
                   + "ORDER BY lancamento.data";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);       
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findSaldo(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT DATE(data) as data, sum( if(tipo = 0, valor, valor * -1) ) as valor "
                   + "FROM lancamento "
                   + "WHERE DATE(data) BETWEEN :dataInicio AND :dataFim "
                   + "GROUP BY data "
                   + "ORDER BY data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }

    public List<Saldo> findSaldoPlanoConta(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT idPlanoConta as id, DATE(data) as data, sum(valor) as valor "
                   + "FROM lancamento "
                   + "WHERE DATE(data) BETWEEN :dataInicio AND :dataFim "
                   + "GROUP BY idPlanoConta, data "
                   + "ORDER BY idPlanoConta, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }    

    public List<Saldo> findSaldoTipo(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT tipo as id, DATE(data) as data, sum(valor) as valor FROM lancamento "
                   + "WHERE DATE(data) BETWEEN :dataInicio AND :dataFim "
                   + "GROUP BY tipo, data " 
                   + "ORDER BY tipo, data";     
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }
    
    public List<Integer> findYearFromLancamento() throws Exception {
        String sql = "SELECT year(data) ano FROM lancamento GROUP BY ano ORDER BY ano;";  
        return findAll(sql, null, Integer.class);
    }

    public Lancamento save(Lancamento lancamento) throws Exception {  
        String sql = "INSERT INTO lancamento (idConta, idPlanoConta, tipo, favorecido, numero, data, valor, situacao, historico) "
                   + "VALUES(:idConta, :idPlanoConta, :tipo, :favorecido, :numero, :data, :valor, :situacao, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamento.getConta().getIdConta());
        params.put("idPlanoConta", (lancamento.getPlanoConta() == null ? null : lancamento.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamento.getTipo().ordinal());     
        params.put("favorecido", lancamento.getFavorecido());     
        params.put("numero", lancamento.getNumero());            
        params.put("data", lancamento.getData());      
        params.put("valor", lancamento.getValor());    
        params.put("situacao", lancamento.getSituacao().ordinal());  
        params.put("historico", lancamento.getHistorico());       
        Integer idLancamento = save(sql, params, lancamentoHandler);
        return find(idLancamento);
    }

    public Lancamento update(Lancamento lancamento) throws Exception {
        String sql = "UPDATE lancamento "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, tipo = :tipo, favorecido = :favorecido, "
                   + "numero = :numero, data = :data, valor = :valor, historico = :historico, situacao = :situacao "
                   + "WHERE idLancamento = :idLancamento ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", lancamento.getIdLancamento());
        params.put("idConta", lancamento.getConta().getIdConta());
        params.put("idPlanoConta", (lancamento.getPlanoConta() == null ? null : lancamento.getPlanoConta().getIdPlanoConta()));
        params.put("tipo", lancamento.getTipo().ordinal());     
        params.put("favorecido", lancamento.getFavorecido());     
        params.put("numero", lancamento.getNumero());            
        params.put("data", lancamento.getData());      
        params.put("valor", lancamento.getValor());   
        params.put("situacao", lancamento.getSituacao().ordinal());    
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
