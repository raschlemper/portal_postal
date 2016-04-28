package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.dao.LancamentoConciliadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoConciliado;
import java.util.Date;
import java.util.List;

public class LancamentoConciliadoService {
    
    private final LancamentoDAO lancamentoDAO;
    private final LancamentoConciliadoDAO lancamentoConciliadoDAO;

    public LancamentoConciliadoService(String nomeBD) {
        lancamentoDAO = new LancamentoDAO(nomeBD);
        lancamentoConciliadoDAO = new LancamentoConciliadoDAO(nomeBD);
    }
    
    public List<LancamentoConciliado> findAll() throws Exception {
        return lancamentoConciliadoDAO.findAll();
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
    
    public LancamentoConciliado createLancamento(LancamentoConciliado lancamentoConciliado, Date dataInicio, Date dataFim) throws Exception {
        List<Lancamento> lancamentos = lancamentoDAO.findByDataLancamento(dataInicio, dataFim);
        for (Lancamento lancamento : lancamentos) {
            lancamento.setLancamentoConciliado(lancamentoConciliado);
            lancamentoDAO.update(lancamento);
        }
        lancamentoConciliado = save(lancamentoConciliado);
        return lancamentoConciliado;
    } 
    
    private Double calculateValorLancamento(Lancamento lancamento) {
        return (lancamento.getValor() + lancamento.getValorJuros() + lancamento.getValorMulta()) - lancamento.getValorDesconto();
    } 
}
