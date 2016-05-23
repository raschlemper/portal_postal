package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.Saldo;
import java.util.Date;
import java.util.List;

public class LancamentoService {
    
    private final LancamentoDAO lancamentoDAO;
    private final LancamentoProgramadoDAO lancamentoProgramadoDAO;

    public LancamentoService(String nomeBD) {
        lancamentoDAO = new LancamentoDAO(nomeBD);
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
    }
    
    public List<Lancamento> findAll() throws Exception {
        return lancamentoDAO.findAll();
    }  
    
    public Lancamento find(Integer idLancamento) throws Exception {
        return lancamentoDAO.find(idLancamento);
    }  
    
    public List<Lancamento> findByConta(Integer idConta) throws Exception {
        return lancamentoDAO.findByConta(idConta);
    } 
    
    public List<Lancamento> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        return lancamentoDAO.findByLancamentoProgramado(idLancamentoProgramado);
    } 
    
    public List<Lancamento> findSaldo(Date dataInicio, Date dataFim) throws Exception {
        return lancamentoDAO.findSaldo(dataInicio, dataFim);
    } 
    
    public List<Saldo> findSaldoPlanoConta(Date dataInicio, Date dataFim) throws Exception {
        return lancamentoDAO.findSaldoPlanoConta(dataInicio, dataFim);
    }  
    
    public List<Saldo> findSaldoPlanoContaCompetencia(Date dataInicio, Date dataFim) throws Exception {
        return lancamentoDAO.findSaldoPlanoContaCompetencia(dataInicio, dataFim);
    } 
    
    public List<Saldo> findSaldoTipo(Date dataInicio, Date dataFim) throws Exception {
        return lancamentoDAO.findSaldoTipo(dataInicio, dataFim);
    } 
    
    public List<Integer> findYearFromLancamento() throws Exception {
        return lancamentoDAO.findYearFromLancamento();
    } 
    
    public Lancamento findLastByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        return lancamentoDAO.findLastByLancamentoProgramado(idLancamentoProgramado);
    }
    
    public Lancamento findByNumeroParcela(Integer idLancamentoProgramado, Integer numeroParcela) throws Exception {
        return lancamentoDAO.findByNumeroParcela(idLancamentoProgramado, numeroParcela);
    } 
    
    public Lancamento save(Lancamento lancamento) throws Exception {
        return lancamentoDAO.save(lancamento);
    } 
    
    public Lancamento update(Lancamento lancamento) throws Exception {
        return lancamentoDAO.update(lancamento);
    } 
    
    public Lancamento delete(Integer idLancamento) throws Exception {
        Lancamento lancamento = lancamentoDAO.remove(idLancamento);
        LancamentoProgramado lancamentoProgamado = lancamento.getLancamentoProgramado();
        if(lancamentoProgamado != null) {
            Integer numeroParcela = lancamento.getNumeroParcela() - 1;
            lancamentoProgamado.setNumeroParcela(numeroParcela);
            lancamentoProgramadoDAO.updateNumeroParcela(lancamentoProgamado);            
        }
        return lancamento;
    } 
    
}
