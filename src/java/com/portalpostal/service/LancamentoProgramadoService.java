package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import java.util.List;

public class LancamentoProgramadoService {
    
    private final String nomeBD;
    
    private LancamentoProgramadoDAO lancamentoProgramadoDAO;    
    private LancamentoService lancamentoService;

    public LancamentoProgramadoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
    }
    
    public List<LancamentoProgramado> findAll() throws Exception {
        init();
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoDAO.findAll();
        for (LancamentoProgramado lancamentoProgramado : lancamentoProgramados) {
        List<Lancamento> lancamentos = lancamentoService.findByLancamentoProgramado(lancamentoProgramado.getIdLancamentoProgramado());
            lancamentoProgramado.setLancamentos(lancamentos);
        }
        return lancamentoProgramados;
    }  
    
    public LancamentoProgramado find(Integer idLancamentoProgramado) throws Exception {
        init();
        LancamentoProgramado lancamentoProgramado = lancamentoProgramadoDAO.find(idLancamentoProgramado);
        List<Lancamento> lancamentos = lancamentoService.findByLancamentoProgramado(idLancamentoProgramado);
        lancamentoProgramado.setLancamentos(lancamentos);
        return lancamentoProgramado;
    } 
    
    public LancamentoProgramado findLancamento(Integer idLancamentoProgramado) throws Exception {
        init();
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        lancamentoProgramado.setLancamentos(lancamentoService.findByLancamentoProgramado(idLancamentoProgramado));
        return lancamentoProgramado;
    } 
    
    public List<LancamentoProgramado> findByConta(Integer idConta) throws Exception {
        init();
        return lancamentoProgramadoDAO.findByConta(idConta);
    } 

    public List<LancamentoProgramado> findByPlanoConta(Integer idPlanoConta) throws Exception {
        init();
        return lancamentoProgramadoDAO.findByPlanoConta(idPlanoConta);
    }

    public List<LancamentoProgramado> findByCentroCusto(Integer idCentroCusto) throws Exception {
        init();
        return lancamentoProgramadoDAO.findByCentroCusto(idCentroCusto);
    }
    
    public List<LancamentoProgramado> findAllAtivo() throws Exception {
        init();
        return lancamentoProgramadoDAO.findAllAtivo();
    } 
    
    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoDAO.save(lancamentoProgramado);
    } 
    
    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoDAO.update(lancamentoProgramado);
    } 

    public LancamentoProgramado updateNumeroParcela(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoDAO.updateNumeroParcela(lancamentoProgramado);
    }
    
    public LancamentoProgramado delete(Integer idLancamentoProgramado) throws Exception {
        init();
        if(!podeExcluir(idLancamentoProgramado)) throw new Exception("Este lançamento não pode ser excluída!"); 
        return lancamentoProgramadoDAO.remove(idLancamentoProgramado);
    }   
    
    public boolean podeExcluir(Integer idLancamentoProgramado) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoService.findByLancamentoProgramado(idLancamentoProgramado);
        if(!lancamentos.isEmpty()) return false;
        return true;                
    }  
    
    public LancamentoProgramado createLancamento(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoProgramado.getLancamentos();
        if(lancamentoProgramado.getIdLancamentoProgramado() == null) { lancamentoProgramado = save(lancamentoProgramado); }
        lancamentoProgramado.setLancamentos(null);
        for (Lancamento lancamento : lancamentos) { 
            lancamento.setLancamentoProgramado(lancamentoProgramado);
            lancamentoService.save(lancamento); 
        } 
        return update(lancamentoProgramado);   
    }   
    
//    private Lancamento getLancamento(LancamentoProgramado lancamentoProgramado) {
//        Lancamento lancamento = LancamentoConverter.converter(lancamentoProgramado);
//        return lancamento;
//    }
    
}
