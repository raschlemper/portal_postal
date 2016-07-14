package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoRateioDAO;
import com.portalpostal.model.LancamentoProgramadoRateio;
import java.util.List;

public class LancamentoProgramadoRateioService {
    
    private final String nomeBD;   
    
    private LancamentoProgramadoRateioDAO lancamentoProgramadoRateioDAO;   

    public LancamentoProgramadoRateioService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoProgramadoRateioDAO = new LancamentoProgramadoRateioDAO(nomeBD);
    }
    
    public List<LancamentoProgramadoRateio> findAll() throws Exception {
        init();
        return lancamentoProgramadoRateioDAO.findAll();
    }  
    
    public LancamentoProgramadoRateio find(Integer idLancamentoProgramadoRateio) throws Exception {
        init();
        return lancamentoProgramadoRateioDAO.find(idLancamentoProgramadoRateio);
    } 
    
    public List<LancamentoProgramadoRateio> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoRateioDAO.findByLancamentoProgramado(idLancamentoProgramado);
    } 
    
    public LancamentoProgramadoRateio save(LancamentoProgramadoRateio lancamentoProgramadoRateio) throws Exception {
        init();
        return lancamentoProgramadoRateioDAO.save(lancamentoProgramadoRateio);
    } 
    
    public LancamentoProgramadoRateio update(LancamentoProgramadoRateio lancamentoProgramadoRateio) throws Exception {
        init();
        return lancamentoProgramadoRateioDAO.update(lancamentoProgramadoRateio);
    } 
    
    public LancamentoProgramadoRateio delete(Integer idLancamentoProgramadoRateio) throws Exception {
        init();
        return lancamentoProgramadoRateioDAO.remove(idLancamentoProgramadoRateio);
    }    
    
}
