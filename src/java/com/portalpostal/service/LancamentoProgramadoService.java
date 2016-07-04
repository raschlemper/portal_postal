package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoProgramadoParcela;
import com.portalpostal.model.LancamentoProgramadoRateio;
import java.util.ArrayList;
import java.util.List;

public class LancamentoProgramadoService {
    
    private final String nomeBD;
    
    private LancamentoProgramadoDAO lancamentoProgramadoDAO;    
    private LancamentoService lancamentoService;  
    private LancamentoProgramadoParcelaService lancamentoProgramadoParcelaService;
    private LancamentoProgramadoRateioService lancamentoProgramadoRateioService;

    public LancamentoProgramadoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoParcelaService = new LancamentoProgramadoParcelaService(nomeBD);
        lancamentoProgramadoRateioService = new LancamentoProgramadoRateioService(nomeBD);
    }
    
    public List<LancamentoProgramado> findAll() throws Exception {
        init();
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoDAO.findAll();
        for (LancamentoProgramado lancamentoProgramado : lancamentoProgramados) {
            lancamentoProgramado = setParcelas(lancamentoProgramado);
            lancamentoProgramado = setRateios(lancamentoProgramado);
            lancamentoProgramado = setLancamentos(lancamentoProgramado);
        }
        return lancamentoProgramados;
    }  
    
    public LancamentoProgramado find(Integer idLancamentoProgramado) throws Exception {
        init();
        LancamentoProgramado lancamentoProgramado = lancamentoProgramadoDAO.find(idLancamentoProgramado);
        lancamentoProgramado = setParcelas(lancamentoProgramado);
        lancamentoProgramado = setRateios(lancamentoProgramado);
        lancamentoProgramado = setLancamentos(lancamentoProgramado);
        return lancamentoProgramado;
    } 
    
    public LancamentoProgramado findLancamento(Integer idLancamentoProgramado) throws Exception {
        init();
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        lancamentoProgramado = setParcelas(lancamentoProgramado);
        lancamentoProgramado = setRateios(lancamentoProgramado);
        lancamentoProgramado = setLancamentos(lancamentoProgramado);
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
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoDAO.findAllAtivo();
        for (LancamentoProgramado lancamentoProgramado : lancamentoProgramados) {
            lancamentoProgramado = setParcelas(lancamentoProgramado);
        }
        return lancamentoProgramados;
    }  
    
    public List<LancamentoProgramado> findByFavorecido(Integer idFavorecido) throws Exception {
        init();
        return lancamentoProgramadoDAO.findByFavorecido(idFavorecido);
    } 
    
    public List<LancamentoProgramado> findByColaborador(Integer idColaborador) throws Exception {
        init();
        return lancamentoProgramadoDAO.findByColaborador(idColaborador);
    } 
    
    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
//        LancamentoProgramado lancamentoProgramadoResult = lancamentoProgramadoDAO.save(ajustaLancamento(lancamentoProgramado));
        LancamentoProgramado lancamentoProgramadoResult = lancamentoProgramadoDAO.save(lancamentoProgramado);
        lancamentoProgramadoResult.setParcelas(lancamentoProgramado.getParcelas());
        lancamentoProgramadoResult.setParcelas(saveOrUpdateParcela(lancamentoProgramadoResult));
        lancamentoProgramadoResult.setRateios(lancamentoProgramado.getRateios());
        lancamentoProgramadoResult.setRateios(saveOrUpdateRateio(lancamentoProgramadoResult));
        return lancamentoProgramadoResult;
    } 
    
    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
//        LancamentoProgramado lancamentoProgramadoResult = lancamentoProgramadoDAO.update(ajustaLancamento(lancamentoProgramado));
        LancamentoProgramado lancamentoProgramadoResult = lancamentoProgramadoDAO.update(lancamentoProgramado);
        lancamentoProgramadoResult.setParcelas(saveOrUpdateParcela(lancamentoProgramado));
        lancamentoProgramadoResult.setRateios(saveOrUpdateRateio(lancamentoProgramado));
        return lancamentoProgramadoResult;
    } 

    public LancamentoProgramado updateNumeroParcela(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoDAO.updateNumeroParcela(lancamentoProgramado);
    }

    public LancamentoProgramado updateSituacao(LancamentoProgramado lancamentoProgramado) throws Exception {
        init();
        return lancamentoProgramadoDAO.updateSituacao(lancamentoProgramado);
    }
    
    public LancamentoProgramado delete(Integer idLancamentoProgramado) throws Exception {
        init();
        if(!podeExcluir(idLancamentoProgramado)) throw new Exception("Este lançamento não pode ser excluído!"); 
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        removerLancamentoParcela(lancamentoProgramado, null);
        removerLancamentoRateio(lancamentoProgramado, null);
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
        lancamentoProgramado = setLancamentoNaProgramacao(lancamentoProgramado);
        lancamentoProgramado = setLancamentoNaParcela(lancamentoProgramado);
        return update(lancamentoProgramado);   
    }    
    
    private LancamentoProgramado setLancamentoNaProgramacao(LancamentoProgramado lancamentoProgramado) throws Exception {
        if(lancamentoProgramado.getIdLancamentoProgramado() == null) { lancamentoProgramado = save(lancamentoProgramado); }
        List<Lancamento> lancamentos = new ArrayList<Lancamento>();
        for (Lancamento lancamento : lancamentoProgramado.getLancamentos()) { 
            lancamento.setLancamentoProgramado(lancamentoProgramado);
            lancamentos.add(lancamentoService.save(lancamento)); 
        }   
        lancamentoProgramado.setLancamentos(lancamentos);
        return lancamentoProgramado;
    }
    
    private LancamentoProgramado setLancamentoNaParcela(LancamentoProgramado lancamentoProgramado) {
        List<LancamentoProgramadoParcela> parcelasLancamento = new ArrayList<LancamentoProgramadoParcela>();
        for (LancamentoProgramadoParcela parcela : lancamentoProgramado.getParcelas()) {
            for (Lancamento lancamento : lancamentoProgramado.getLancamentos()) {
                if(lancamento.getNumeroParcela() == parcela.getNumero()) { parcela.setLancamento(lancamento); }
            }
            parcelasLancamento.add(parcela);
        }
        lancamentoProgramado.setParcelas(parcelasLancamento);
        return lancamentoProgramado;
    }
    
    private LancamentoProgramado ajustaLancamento(LancamentoProgramado lancamentoProgramado) {
        lancamentoProgramado = ajustaLancamentoParcela(lancamentoProgramado);
        lancamentoProgramado = ajustaLancamentoRateio(lancamentoProgramado);
        return lancamentoProgramado;
    }
    
    // ***** PARCELAS ***** //
    
    private LancamentoProgramado setLancamentos(LancamentoProgramado lancamentoProgramado) throws Exception {
        if(lancamentoProgramado.getParcelas() != null && !lancamentoProgramado.getParcelas().isEmpty()) return lancamentoProgramado;
        lancamentoProgramado.setLancamentos(getLancamentos(lancamentoProgramado.getIdLancamentoProgramado()));
        return lancamentoProgramado;
    }
    
    private List<Lancamento> getLancamentos(Integer idLancamentoProgramado) throws Exception {
        return lancamentoService.findByLancamentoProgramado(idLancamentoProgramado);
    }
    
    // ***** PARCELAS ***** //
    
    private LancamentoProgramado setParcelas(LancamentoProgramado lancamentoProgramado) throws Exception {
        lancamentoProgramado.setParcelas(getParcelas(lancamentoProgramado.getIdLancamentoProgramado()));
        return lancamentoProgramado;
    }
    
    private List<LancamentoProgramadoParcela> getParcelas(Integer idLancamentoProgramado) throws Exception {
        return lancamentoProgramadoParcelaService.findByLancamentoProgramado(idLancamentoProgramado);
    }
    
    private List<LancamentoProgramadoParcela> saveOrUpdateParcela(LancamentoProgramado lancamentoProgramado) throws Exception {
        removerLancamentoParcela(lancamentoProgramado, lancamentoProgramado.getParcelas());
        List<LancamentoProgramadoParcela> parcelasLista = new ArrayList<LancamentoProgramadoParcela>();
        if(lancamentoProgramado.getParcelas() == null || lancamentoProgramado.getParcelas().isEmpty()) return parcelasLista;
        for (LancamentoProgramadoParcela parcela : lancamentoProgramado.getParcelas()) {
            parcela.setLancamentoProgramado(getLancamentoToParcela(lancamentoProgramado));
            if(parcela.getIdLancamentoProgramadoParcela() != null) {
                parcelasLista.add(lancamentoProgramadoParcelaService.update(parcela));
            } else {
                parcelasLista.add(lancamentoProgramadoParcelaService.save(parcela));
            }
        }
        return parcelasLista;
    }
    
    private LancamentoProgramado getLancamentoToParcela(LancamentoProgramado lancamentoProgramado) {
        LancamentoProgramado parcela = new LancamentoProgramado();
        parcela.setIdLancamentoProgramado(lancamentoProgramado.getIdLancamentoProgramado());
        return parcela;
    }
    
    private LancamentoProgramado ajustaLancamentoParcela(LancamentoProgramado lancamentoProgramado) {
        if(lancamentoProgramado.getParcelas() == null || lancamentoProgramado.getParcelas().isEmpty()) { return lancamentoProgramado; }
        lancamentoProgramado.setPlanoConta(null);
        lancamentoProgramado.setCentroCusto(null);
        lancamentoProgramado.setDataVencimento(null);
        return lancamentoProgramado;
    }
    
    private void removerLancamentoParcela(LancamentoProgramado lancamentoProgramado, List<LancamentoProgramadoParcela> parcelasLancamento) throws Exception {
        List<LancamentoProgramadoParcela> parcelas = getParcelas(lancamentoProgramado.getIdLancamentoProgramado());
        for (LancamentoProgramadoParcela parcela : parcelas) {    
            if(parcelasLancamento == null || parcelasLancamento.isEmpty() || !parcelasLancamento.contains(parcela)) {
                lancamentoProgramadoParcelaService.delete(parcela.getIdLancamentoProgramadoParcela());
            } 
        }
    }
    
    // ***** RATEIOS ***** //
    
    private LancamentoProgramado setRateios(LancamentoProgramado lancamentoProgramado) throws Exception {
        lancamentoProgramado.setRateios(getRateios(lancamentoProgramado.getIdLancamentoProgramado()));
        return lancamentoProgramado;
    }
    
    private List<LancamentoProgramadoRateio> getRateios(Integer idLancamentoProgramado) throws Exception {
        return lancamentoProgramadoRateioService.findByLancamentoProgramado(idLancamentoProgramado);
    }
    
    private List<LancamentoProgramadoRateio> saveOrUpdateRateio(LancamentoProgramado lancamentoProgramado) throws Exception {
        removerLancamentoRateio(lancamentoProgramado, lancamentoProgramado.getRateios());
        List<LancamentoProgramadoRateio> parcelasLista = new ArrayList<LancamentoProgramadoRateio>();
        if(lancamentoProgramado.getRateios() == null) return parcelasLista;
        for (LancamentoProgramadoRateio rateio : lancamentoProgramado.getRateios()) {
            rateio.setLancamentoProgramado(getLancamentoToRateio(lancamentoProgramado));
            if(rateio.getIdLancamentoProgramadoRateio() != null) {
                parcelasLista.add(lancamentoProgramadoRateioService.update(rateio));
            } else {
                parcelasLista.add(lancamentoProgramadoRateioService.save(rateio));
            }
        }
        return parcelasLista;
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
    
//    private Lancamento createLancamentoToRateio(Lancamento lancamento) {        
//        Lancamento lancamentoRateio = new Lancamento();
//        lancamentoRateio.setIdLancamento(lancamento.getIdLancamento());
//        return lancamentoRateio;
//    }
    
    private void removerLancamentoRateio(LancamentoProgramado lancamentoProgramado, List<LancamentoProgramadoRateio> parcelasLancamento) throws Exception {
        List<LancamentoProgramadoRateio> parcelas = getRateios(lancamentoProgramado.getIdLancamentoProgramado());
        for (LancamentoProgramadoRateio parcela : parcelas) {    
            if(parcelasLancamento == null || parcelasLancamento.isEmpty() || !parcelasLancamento.contains(parcela)) {
                lancamentoProgramadoRateioService.delete(parcela.getIdLancamentoProgramadoRateio());
            } 
        }
    }
    
}
