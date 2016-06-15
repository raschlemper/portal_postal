package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoProgramadoRateio;
import java.util.ArrayList;
import java.util.List;

public class LancamentoProgramadoService {
    
    private final String nomeBD;
    
    private LancamentoProgramadoDAO lancamentoProgramadoDAO;    
    private LancamentoService lancamentoService;  
    private LancamentoProgramadoRateioService lancamentoProgramadoRateioService;

    public LancamentoProgramadoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoRateioService = new LancamentoProgramadoRateioService(nomeBD);
    }
    
    public List<LancamentoProgramado> findAll() throws Exception {
        init();
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoDAO.findAll();
        for (LancamentoProgramado lancamentoProgramado : lancamentoProgramados) {
            List<Lancamento> lancamentos = lancamentoService.findByLancamentoProgramado(lancamentoProgramado.getIdLancamentoProgramado());
            lancamentoProgramado.setLancamentos(lancamentos);
            lancamentoProgramado = setRateio(lancamentoProgramado);
        }
        return lancamentoProgramados;
    }  
    
    public LancamentoProgramado find(Integer idLancamentoProgramado) throws Exception {
        init();
        LancamentoProgramado lancamentoProgramado = lancamentoProgramadoDAO.find(idLancamentoProgramado);
        List<Lancamento> lancamentos = lancamentoService.findByLancamentoProgramado(idLancamentoProgramado);
        lancamentoProgramado.setLancamentos(lancamentos);
        lancamentoProgramado = setRateio(lancamentoProgramado);
        return lancamentoProgramado;
    } 
    
    public LancamentoProgramado findLancamento(Integer idLancamentoProgramado) throws Exception {
        init();
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        lancamentoProgramado.setLancamentos(lancamentoService.findByLancamentoProgramado(idLancamentoProgramado));
        lancamentoProgramado = setRateio(lancamentoProgramado);
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
        LancamentoProgramado lancamentoProgramadoResult = lancamentoProgramadoDAO.save(ajustaLancamentoRateio(lancamentoProgramado));
        lancamentoProgramadoResult.setRateios(lancamentoProgramado.getRateios());
        lancamentoProgramadoResult.setRateios(saveOrUpdateRateio(lancamentoProgramadoResult));
        return lancamentoProgramadoResult;
    } 
    
    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        LancamentoProgramado lancamentoProgramadoResult = lancamentoProgramadoDAO.update(ajustaLancamentoRateio(lancamentoProgramado));
        lancamentoProgramadoResult.setRateios(saveOrUpdateRateio(lancamentoProgramado));
        return lancamentoProgramadoResult;
    } 

    public LancamentoProgramado updateNumeroParcela(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoDAO.updateNumeroParcela(lancamentoProgramado);
    }
    
    public LancamentoProgramado delete(Integer idLancamentoProgramado) throws Exception {
        init();
        if(!podeExcluir(idLancamentoProgramado)) throw new Exception("Este lançamento não pode ser excluída!"); 
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        removerLancamentoRateio(lancamentoProgramado, null);
        return lancamentoProgramadoDAO.remove(idLancamentoProgramado);
    } 
    
    private LancamentoProgramado setRateio(LancamentoProgramado lancamentoProgramado) throws Exception {
        lancamentoProgramado.setRateios(getRateios(lancamentoProgramado.getIdLancamentoProgramado()));
        return lancamentoProgramado;
    }
    
    private List<LancamentoProgramadoRateio> getRateios(Integer idLancamentoProgramado) throws Exception {
        return lancamentoProgramadoRateioService.findByLancamentoProgramado(idLancamentoProgramado);
    }
    
    private List<LancamentoProgramadoRateio> saveOrUpdateRateio(LancamentoProgramado lancamentoProgramado) throws Exception {
        removerLancamentoRateio(lancamentoProgramado, lancamentoProgramado.getRateios());
        List<LancamentoProgramadoRateio> rateiosLista = new ArrayList<LancamentoProgramadoRateio>();
        if(lancamentoProgramado.getRateios() == null) return rateiosLista;
        for (LancamentoProgramadoRateio rateio : lancamentoProgramado.getRateios()) {
            rateio.setLancamentoProgramado(getLancamentoToRateio(lancamentoProgramado));
            if(rateio.getIdLancamentoProgramadoRateio() != null) {
                rateiosLista.add(lancamentoProgramadoRateioService.update(rateio));
            } else {
                rateiosLista.add(lancamentoProgramadoRateioService.save(rateio));
            }
        }
        return rateiosLista;
    }
    
    private LancamentoProgramado getLancamentoToRateio(LancamentoProgramado lancamentoProgramado) {
        LancamentoProgramado rateio = new LancamentoProgramado();
        rateio.setIdLancamentoProgramado(lancamentoProgramado.getIdLancamentoProgramado());
        return rateio;
    }
    
    private LancamentoProgramado ajustaLancamentoRateio(LancamentoProgramado lancamentoProgramado) {
        if(lancamentoProgramado.getRateios() == null || lancamentoProgramado.getRateios().isEmpty()) { return lancamentoProgramado; }
        lancamentoProgramado.setPlanoConta(null);
        lancamentoProgramado.setCentroCusto(null);
        lancamentoProgramado.setObservacao(null);
        return lancamentoProgramado;
    }
    
    private Lancamento createLancamentoToRateio(Lancamento lancamento) {        
        Lancamento lancamentoRateio = new Lancamento();
        lancamentoRateio.setIdLancamento(lancamento.getIdLancamento());
        return lancamentoRateio;
    }
    
    private void removerLancamentoRateio(LancamentoProgramado lancamentoProgramado, List<LancamentoProgramadoRateio> rateiosLancamento) throws Exception {
        List<LancamentoProgramadoRateio> rateios = getRateios(lancamentoProgramado.getIdLancamentoProgramado());
        for (LancamentoProgramadoRateio rateio : rateios) {    
            if(rateiosLancamento == null || rateiosLancamento.isEmpty() || !rateiosLancamento.contains(rateio)) {
                lancamentoProgramadoRateioService.delete(rateio.getIdLancamentoProgramadoRateio());
            } 
        }
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
