package com.portalpostal.service;

import com.portalpostal.dao.LancamentoConciliadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoConciliado;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class LancamentoConciliadoService {
    
    private final String nomeBD;
    
    private LancamentoConciliadoDAO lancamentoConciliadoDAO;    
    private LancamentoService lancamentoService;

    public LancamentoConciliadoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoConciliadoDAO = new LancamentoConciliadoDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
    }
    
    public List<LancamentoConciliado> findAll() throws Exception {
        init();
        return lancamentoConciliadoDAO.findAll();
    }  
    
    public List<LancamentoConciliado> findByData(Date data) throws Exception {
        init();
        return lancamentoConciliadoDAO.findByData(data);
    } 

    public LancamentoConciliado findLancamentoByLote(Integer numeroLote) throws Exception {
        init();
        return lancamentoConciliadoDAO.findLancamentoByLote(numeroLote);
    }
    
    public LancamentoConciliado find(Integer idLancamentoConciliado) throws Exception {
        init();
        return lancamentoConciliadoDAO.find(idLancamentoConciliado);
    } 
    
    public LancamentoConciliado findLastByConta(Integer idConta) throws Exception {
        init();
        Lancamento lancamento = lancamentoService.findLastByLancamentoConciliado(idConta);
        if(lancamento == null) return null;
        return lancamentoConciliadoDAO.findByLote(lancamento.getNumeroLoteConciliado());
    } 
    
    public LancamentoConciliado save(LancamentoConciliado lancamentoConciliado) throws Exception {
        init();
        return lancamentoConciliadoDAO.save(lancamentoConciliado);
    } 
    
    public LancamentoConciliado update(LancamentoConciliado lancamentoConciliado) throws Exception {
        init();
        return lancamentoConciliadoDAO.update(lancamentoConciliado);
    } 
    
    public LancamentoConciliado delete(Integer idLancamentoConciliado) throws Exception {
        init();
        return lancamentoConciliadoDAO.remove(idLancamentoConciliado);
    }   
    
    public void deleteByLancamento(Integer idLancamento) throws Exception {
        init();
        lancamentoConciliadoDAO.removeByLancamento(idLancamento);
    }   
    
    public LancamentoConciliado createLancamento(LancamentoConciliado lancamentoConciliado) throws Exception {
        init();
        Lancamento lancamento = null;
        if(lancamentoConciliado.getValor() != 0) {
            lancamento = lancamentoService.save(lancamentoConciliado.getLancamento());
        }
        lancamentoConciliado.setLancamento(lancamento);
        lancamentoConciliado.setNumeroLote(getLastLote());
        lancamentoConciliado = save(lancamentoConciliado);
        lancamentoService.updateNumeroLoteConciliado(lancamentoConciliado.getDataLancamento(), lancamentoConciliado.getNumeroLote());
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
