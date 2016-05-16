package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import java.util.Date;
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
        LancamentoProgramado lancamentoProgramado = lancamentoProgramadoDAO.find(idLancamentoProgramado);
        List<Lancamento> lancamentos = lancamentoDAO.findByLancamentoProgramado(idLancamentoProgramado);
        lancamentoProgramado.setLancamentos(lancamentos);
        return lancamentoProgramado;
    } 
    
    public LancamentoProgramado findLancamento(Integer idLancamentoProgramado) throws Exception {
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        lancamentoProgramado.setLancamentos(lancamentoDAO.findByLancamentoProgramado(idLancamentoProgramado));
        return lancamentoProgramado;
    } 
    
    public List<LancamentoProgramado> findByConta(Integer idConta) throws Exception {
        return lancamentoProgramadoDAO.findByConta(idConta);
    } 
    
    public List<LancamentoProgramado> findByDataVencimento(Date dataInicio, Date dataFim) throws Exception {
        return lancamentoProgramadoDAO.findByDataVencimento(dataInicio, dataFim);
    } 
    
    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) throws Exception {
        return lancamentoProgramadoDAO.save(lancamentoProgramado);
    } 
    
    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) throws Exception {
        return lancamentoProgramadoDAO.update(lancamentoProgramado);
    } 
    
    public LancamentoProgramado delete(Integer idLancamentoProgramado) throws Exception {
        if(!podeExcluir(idLancamentoProgramado)) throw new Exception("Esta conta não pode ser excluída!"); 
        return lancamentoProgramadoDAO.remove(idLancamentoProgramado);
    }   
    
    public boolean podeExcluir(Integer idLancamentoProgramado) throws Exception {
        List<Lancamento> lancamentos = lancamentoDAO.findByLancamentoProgramado(idLancamentoProgramado);
        if(!lancamentos.isEmpty()) return false;
        return true;                
    }  
    
    public LancamentoProgramado createLancamento(LancamentoProgramado lancamentoProgramado) throws Exception {
        List<Lancamento> lancamentos = lancamentoProgramado.getLancamentos();
        if(lancamentoProgramado.getIdLancamentoProgramado() == null) { lancamentoProgramado = save(lancamentoProgramado); }
        lancamentoProgramado.setLancamentos(null);
        for (Lancamento lancamento : lancamentos) { 
            lancamento.setLancamentoProgramado(lancamentoProgramado);
            lancamentoDAO.save(lancamento); 
        } 
        return update(lancamentoProgramado);   
    }   
    
//    private Lancamento getLancamento(LancamentoProgramado lancamentoProgramado) {
//        Lancamento lancamento = LancamentoConverter.converter(lancamentoProgramado);
//        return lancamento;
//    }
    
}
