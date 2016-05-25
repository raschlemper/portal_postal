package com.portalpostal.service;

import com.portalpostal.dao.LancamentoParceladoDAO;
import com.portalpostal.model.LancamentoParcelado;
import java.util.List;

public class LancamentoParceladoService {
    
    private final LancamentoParceladoDAO lancamentoParceladoDAO;


    public LancamentoParceladoService(String nomeBD) {
        lancamentoParceladoDAO = new LancamentoParceladoDAO(nomeBD);
    }
    
    public List<LancamentoParcelado> findAll() throws Exception {
        return lancamentoParceladoDAO.findAll();
    }  
    
    public LancamentoParcelado find(Integer idLancamentoParcelado) throws Exception {
        return lancamentoParceladoDAO.find(idLancamentoParcelado);
    } 
    
    public List<LancamentoParcelado> findByConta(Integer idConta) throws Exception {
        return lancamentoParceladoDAO.findByConta(idConta);
    } 
    
    public LancamentoParcelado save(LancamentoParcelado lancamentoParcelado) throws Exception {
        return lancamentoParceladoDAO.save(lancamentoParcelado);
    } 
    
    public LancamentoParcelado update(LancamentoParcelado lancamentoParcelado) throws Exception {
        return lancamentoParceladoDAO.update(lancamentoParcelado);
    } 
    
    public LancamentoParcelado delete(Integer idLancamentoParcelado) throws Exception {
        return lancamentoParceladoDAO.remove(idLancamentoParcelado);
    } 
    
}
