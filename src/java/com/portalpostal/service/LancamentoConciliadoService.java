package com.portalpostal.service;

import com.portalpostal.dao.LancamentoConciliadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoConciliado;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class LancamentoConciliadoService {
    
    private final LancamentoConciliadoDAO lancamentoConciliadoDAO;    
    private final LancamentoService lancamentoService;

    public LancamentoConciliadoService(String nomeBD) {
        lancamentoConciliadoDAO = new LancamentoConciliadoDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
    }
    
    public List<LancamentoConciliado> findAll() throws Exception {
        return lancamentoConciliadoDAO.findAll();
    }  
    
    public List<LancamentoConciliado> findByData(Date data) throws Exception {
        return lancamentoConciliadoDAO.findByData(data);
    } 

    public LancamentoConciliado findByLote(Integer numeroLote) throws Exception {
        return lancamentoConciliadoDAO.findByLote(numeroLote);
    }
    
    public LancamentoConciliado find(Integer idLancamentoConciliado) throws Exception {
        return lancamentoConciliadoDAO.find(idLancamentoConciliado);
    } 
    
    public LancamentoConciliado save(LancamentoConciliado lancamentoConciliado) throws Exception {
        return lancamentoConciliadoDAO.save(lancamentoConciliado);
    } 
    
    public LancamentoConciliado update(LancamentoConciliado lancamentoConciliado) throws Exception {
        return lancamentoConciliadoDAO.update(lancamentoConciliado);
    } 
    
    public LancamentoConciliado delete(Integer idLancamentoConciliado) throws Exception {
        return lancamentoConciliadoDAO.remove(idLancamentoConciliado);
    }   
    
    public LancamentoConciliado createLancamento(LancamentoConciliado lancamentoConciliado) throws Exception {
        Lancamento lancamentoConciliacao = lancamentoService.save(lancamentoConciliado.getLancamento());
        lancamentoConciliado.setLancamento(lancamentoConciliacao);
        lancamentoConciliado.setNumeroLote(getLastLote());
        lancamentoConciliado = save(lancamentoConciliado);
        List<Lancamento> lancamentos = lancamentoService.findLancamentoNotConciliadoByDataLancamento(
                        getDate(lancamentoConciliado.getDataLancamento()));
        for (Lancamento lancamento : lancamentos) {
            lancamento.setNumeroLoteConciliado(lancamentoConciliado.getNumeroLote());
            lancamentoService.updateNumeroLoteConciliado(lancamento);
        }
        return lancamentoConciliado;
    } 
    
    private Date getDate(Date dataLancamento) {
        Calendar data = new GregorianCalendar();
        data.setTime(dataLancamento);
        data.set(Calendar.HOUR, 23);
        data.set(Calendar.MINUTE, 59);
        data.set(Calendar.SECOND, 59);
        return data.getTime();
    }
    
    private Double calculateValorLancamento(Lancamento lancamento) {
        return (lancamento.getValor() + lancamento.getValorJuros() + lancamento.getValorMulta()) - lancamento.getValorDesconto();
    } 
    
    private Integer getLastLote() throws Exception {
        Integer numeroLote = lancamentoConciliadoDAO.findLastLote();
        if(numeroLote == null || numeroLote < 1) return 1;
        return numeroLote + 1;
    }
}
