package com.portalpostal.service;

import com.portalpostal.dao.LancamentoAnexoDAO;
import com.portalpostal.model.LancamentoAnexo;
import java.util.List;

public class LancamentoAnexoService {
    
    private final String nomeBD;    
    
    private LancamentoAnexoDAO lancamentoAnexoDAO;

    public LancamentoAnexoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoAnexoDAO = new LancamentoAnexoDAO(nomeBD);
    }
    
    public List<LancamentoAnexo> findAll() throws Exception {
        init();
        return lancamentoAnexoDAO.findAll();
    }  
    
    public LancamentoAnexo find(Integer idLancamentoAnexo) throws Exception {
        init();
        return lancamentoAnexoDAO.find(idLancamentoAnexo);
    } 
    
    public List<LancamentoAnexo> findByLancamento(Integer idLancamento) throws Exception {
        init();
        return lancamentoAnexoDAO.findByLancamento(idLancamento);
    } 
    
    public LancamentoAnexo save(LancamentoAnexo lancamentoAnexo) throws Exception {
        init();
        return lancamentoAnexoDAO.save(lancamentoAnexo);
    } 
    
    public LancamentoAnexo update(LancamentoAnexo lancamentoAnexo) throws Exception {
        init();
        return lancamentoAnexoDAO.update(lancamentoAnexo);
    } 
    
    public LancamentoAnexo delete(Integer idLancamentoAnexo) throws Exception {
        init();
        return lancamentoAnexoDAO.remove(idLancamentoAnexo);
    } 
    
    public LancamentoAnexo deleteByLancamento(Integer idLancamento) throws Exception {
        init();
        return lancamentoAnexoDAO.removeByLancamento(idLancamento);
    } 
    
}
