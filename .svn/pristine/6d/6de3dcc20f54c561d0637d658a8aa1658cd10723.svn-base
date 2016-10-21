package com.portalpostal.service;

import com.portalpostal.dao.LancamentoAnexoDAO;
import com.portalpostal.model.LancamentoAnexo;
import java.util.List;

public class LancamentoAnexoService {
    
    private final LancamentoAnexoDAO lancamentoAnexoDAO;

    public LancamentoAnexoService(String nomeBD) {
        lancamentoAnexoDAO = new LancamentoAnexoDAO(nomeBD);
    }
    
    public List<LancamentoAnexo> findAll() throws Exception {
        return lancamentoAnexoDAO.findAll();
    }  
    
    public LancamentoAnexo find(Integer idLancamentoAnexo) throws Exception {
        return lancamentoAnexoDAO.find(idLancamentoAnexo);
    } 
    
    public LancamentoAnexo save(LancamentoAnexo lancamentoAnexo) throws Exception {
        return lancamentoAnexoDAO.save(lancamentoAnexo);
    } 
    
    public LancamentoAnexo update(LancamentoAnexo lancamentoAnexo) throws Exception {
        return lancamentoAnexoDAO.update(lancamentoAnexo);
    } 
    
    public LancamentoAnexo delete(Integer idLancamentoAnexo) throws Exception {
        return lancamentoAnexoDAO.remove(idLancamentoAnexo);
    } 
    
}
