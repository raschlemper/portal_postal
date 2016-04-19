package com.portalpostal.service;

import com.portalpostal.converter.LancamentoConverter;
import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import java.util.List;

public class LancamentoProgramadoService {
    
    private final LancamentoProgramadoDAO lancamentoProgramadoDAO;
    private final LancamentoDAO lancamentoDAO;


    public LancamentoProgramadoService(String nomeBD) {
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
        lancamentoDAO = new LancamentoDAO(nomeBD);
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
    
    public LancamentoProgramado createLancamento(LancamentoProgramado lancamentoProgramado) throws Exception {
        if(lancamentoProgramado.getIdLancamentoProgramado() == null) { 
            lancamentoProgramado = save(lancamentoProgramado); 
        }
        lancamentoDAO.save(getLancamento(lancamentoProgramado));
        return update(LancamentoProgramadoFactory.execute(lancamentoProgramado));         
    }   
    
    private Lancamento getLancamento(LancamentoProgramado lancamentoProgramado) {
        Lancamento lancamento = LancamentoConverter.converter(lancamentoProgramado);
        return lancamento;
    }
    
}
