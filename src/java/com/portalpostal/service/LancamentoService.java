package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.model.Lancamento;
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
