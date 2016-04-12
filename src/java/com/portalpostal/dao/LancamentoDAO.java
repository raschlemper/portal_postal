package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoHandler;
import com.portalpostal.dao.handler.LancamentoSaldoHandler;
import com.portalpostal.dao.handler.PlanoContaSaldoHandler;
import com.portalpostal.dao.handler.TipoLancamentoSaldoHandler;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.PlanoContaSaldo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoDAO extends GenericDAO { 
    
    private final LancamentoHandler lancamentoHandler;
    private final LancamentoSaldoHandler lancamentoSaldoHandler;
    private final PlanoContaSaldoHandler planoContaSaldoHandler;
    private final TipoLancamentoSaldoHandler tipoLancamentoSaldoHandler;

    public LancamentoDAO(String nameDB) { 
        super(nameDB, LancamentoDAO.class);
        lancamentoHandler = new LancamentoHandler();
        lancamentoSaldoHandler = new LancamentoSaldoHandler();
        planoContaSaldoHandler = new PlanoContaSaldoHandler();
        tipoLancamentoSaldoHandler = new TipoLancamentoSaldoHandler();
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
        String sql = "SELECT idConta, idPlanoConta, tipo, DATE(data) as data, sum(valor) as valor "
                   + "FROM lancamento "
                   + "WHERE DATE(data) BETWEEN :dataInicio AND :dataFim "
                   + "GROUP BY idConta, idPlanoConta, tipo, data "
                   + "ORDER BY idConta, idPlanoConta, tipo, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, lancamentoSaldoHandler);
    }

    public List<PlanoContaSaldo> findPlanoContaSaldo(Integer ano, Integer mesInicio, Integer mesFim) throws Exception {
        String sql = "SELECT idPlanoConta, year(data) as ano, month(data) as mes, sum(valor) as valor FROM lancamento " 
                   + "WHERE idPlanoConta IS NOT NULL AND year(data) = :ano "
                   + "AND month(data) BETWEEN :mesInicio AND :mesFim "
                   + "GROUP BY idPlanoConta, ano, mes "
                   + "ORDER BY idPlanoConta, ano, mes";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ano", ano);       
        params.put("mesInicio", mesInicio);       
        params.put("mesFim", mesFim);       
        return findAll(sql, params, planoContaSaldoHandler);
    }

    public List<Lancamento> findSaldoByTipo(Integer tipo, Integer ano, Integer mesInicio, Integer mesFim) throws Exception {
        String sql = "SELECT tipo, year(data) as ano, month(data) as mes, sum(valor) as valor FROM lancamento "
                   + "WHERE tipo = :tipo AND year(data) = :ano AND month(data) BETWEEN :mesInicio AND :mesFim "
                   + "GROUP BY tipo, ano, mes " 
                   + "ORDER BY tipo, ano, mes";     
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", tipo);       
        params.put("ano", ano);       
        params.put("mesInicio", mesInicio);       
        params.put("mesFim", mesFim);       
        return findAll(sql, params, tipoLancamentoSaldoHandler);
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
