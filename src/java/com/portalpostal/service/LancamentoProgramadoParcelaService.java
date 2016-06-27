package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoParcelaDAO;
import com.portalpostal.model.LancamentoProgramadoParcela;
import java.util.List;

public class LancamentoProgramadoParcelaService {
    
    private final String nomeBD;   
    
    private LancamentoProgramadoParcelaDAO lancamentoProgramadoParcelaDAO;   

    public LancamentoProgramadoParcelaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoProgramadoParcelaDAO = new LancamentoProgramadoParcelaDAO(nomeBD);
    }
    
    public List<LancamentoProgramadoParcela> findAll() throws Exception {
        init();
        return lancamentoProgramadoParcelaDAO.findAll();
    }  
    
    public LancamentoProgramadoParcela find(Integer idLancamentoProgramadoParcela) throws Exception {
        init();
        return lancamentoProgramadoParcelaDAO.find(idLancamentoProgramadoParcela);
    } 
    
    public List<LancamentoProgramadoParcela> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoParcelaDAO.findByLancamentoProgramado(idLancamentoProgramado);
    } 
    
    public LancamentoProgramadoParcela save(LancamentoProgramadoParcela lancamentoProgramadoParcela) throws Exception {
        init();
        return lancamentoProgramadoParcelaDAO.save(lancamentoProgramadoParcela);
    } 
    
    public LancamentoProgramadoParcela update(LancamentoProgramadoParcela lancamentoProgramadoParcela) throws Exception {
        init();
        return lancamentoProgramadoParcelaDAO.update(lancamentoProgramadoParcela);
    } 
    
    public LancamentoProgramadoParcela delete(Integer idLancamentoProgramadoParcela) throws Exception {
        init();
        return lancamentoProgramadoParcelaDAO.remove(idLancamentoProgramadoParcela);
    }    
    
}
