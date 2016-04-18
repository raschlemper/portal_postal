package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.model.LancamentoProgramado;
import java.util.List;

public class LancamentoProgramadoService {
    
    private final LancamentoProgramadoDAO lancamentoProgramadoDAO;

    public LancamentoProgramadoService(String nomeBD) {
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
    }
    
    public List<LancamentoProgramado> findAll() throws Exception {
        return lancamentoProgramadoDAO.findAll();
    }  
    
    public LancamentoProgramado find(Integer idLancamentoProgramado) throws Exception {
        return lancamentoProgramadoDAO.find(idLancamentoProgramado);
    } 
    
    public List<LancamentoProgramado> findByConta(Integer idConta) throws Exception {
        return lancamentoProgramadoDAO.findByConta(idConta);
    } 
    
    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) throws Exception {
        return lancamentoProgramadoDAO.save(lancamentoProgramado);
    } 
    
    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) throws Exception {
        return lancamentoProgramadoDAO.update(lancamentoProgramado);
    } 
    
    public LancamentoProgramado delete(Integer idLancamentoProgramado) throws Exception {
        return lancamentoProgramadoDAO.remove(idLancamentoProgramado);
    }   
    
}
