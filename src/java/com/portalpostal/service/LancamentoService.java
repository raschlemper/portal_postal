package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.PlanoContaSaldo;
import java.util.Date;
import java.util.List;

public class LancamentoService {
    
    private final LancamentoDAO lancamentoDAO;

    public LancamentoService(String nomeBD) {
        lancamentoDAO = new LancamentoDAO(nomeBD);
    }
    
    public List<Lancamento> findAll() throws Exception {
        return lancamentoDAO.findAll();
    }  
    
    public Lancamento find(Integer idLancamento) throws Exception {
        return lancamentoDAO.find(idLancamento);
    } 
    
    public List<Lancamento> findByConta(Integer idConta) throws Exception {
        return lancamentoDAO.findByConta(idConta);
    } 
    
    public List<Lancamento> findSaldo(Date dataInicio, Date dataFim) throws Exception {
        return lancamentoDAO.findSaldo(dataInicio, dataFim);
    } 

    public List<PlanoContaSaldo> findPlanoContaSaldo(Integer ano, Integer mesInicio, Integer mesFim) throws Exception {
        return lancamentoDAO.findPlanoContaSaldo(ano, mesInicio, mesFim); 
    }
    
    public List<Lancamento> findSaldoByTipo(Integer tipo, Integer ano, Integer mesInicio, Integer mesFim) throws Exception {
        return lancamentoDAO.findSaldoByTipo(tipo, ano, mesInicio, mesFim);
    } 
    
    public List<Integer> findYearFromLancamento() throws Exception {
        return lancamentoDAO.findYearFromLancamento();
    } 
    
    public Lancamento save(Lancamento lancamento) throws Exception {
        return lancamentoDAO.save(lancamento);
    } 
    
    public Lancamento update(Lancamento lancamento) throws Exception {
        return lancamentoDAO.update(lancamento);
    } 
    
    public Lancamento delete(Integer idLancamento) throws Exception {
        return lancamentoDAO.remove(idLancamento);
    }   
    
}
