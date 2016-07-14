package com.portalpostal.service;

import com.portalpostal.dao.LancamentoRateioDAO;
import com.portalpostal.model.LancamentoRateio;
import java.util.List;

public class LancamentoRateioService {
    
    private final String nomeBD;   
    
    private LancamentoRateioDAO lancamentoRateioDAO;   

    public LancamentoRateioService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoRateioDAO = new LancamentoRateioDAO(nomeBD);
    }
    
    public List<LancamentoRateio> findAll() throws Exception {
        init();
        return lancamentoRateioDAO.findAll();
    }  
    
    public LancamentoRateio find(Integer idLancamentoRateio) throws Exception {
        init();
        return lancamentoRateioDAO.find(idLancamentoRateio);
    } 
    
    public List<LancamentoRateio> findByLancamento(Integer idLancamento) throws Exception {
        init();
        return lancamentoRateioDAO.findByLancamento(idLancamento);
    } 
    
    public LancamentoRateio save(LancamentoRateio lancamentoRateio) throws Exception {
        init();
        return lancamentoRateioDAO.save(lancamentoRateio);
    } 
    
    public LancamentoRateio update(LancamentoRateio lancamentoRateio) throws Exception {
        init();
        return lancamentoRateioDAO.update(lancamentoRateio);
    } 
    
    public LancamentoRateio delete(Integer idLancamentoRateio) throws Exception {
        init();
        return lancamentoRateioDAO.remove(idLancamentoRateio);
    }    
    
}
