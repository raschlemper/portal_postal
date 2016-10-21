package com.portalpostal.service;

import com.portalpostal.dao.ContaDAO;
import com.portalpostal.model.Conta;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import java.util.Date;
import java.util.List;

public class ContaService {
    
    private final String nomeBD;
    
    private ContaDAO contaDAO;
    private LancamentoService lancamentoService; 
    private LancamentoProgramadoService lancamentoProgramadoService; 

    public ContaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        contaDAO = new ContaDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<Conta> findAll() throws Exception {
        init();
        return contaDAO.findAll();
    }  
    
    public Conta find(Integer idConta) throws Exception {
        init();
        return contaDAO.find(idConta);
    } 

    public List<Conta> findByContaCorrente(Integer idContaCorrente) throws Exception {
        init();
        return contaDAO.findByContaCorrente(idContaCorrente);
    }

    public List<Conta> findByCartaoCredito(Integer idCartaoCredito) throws Exception {  
        init();      
        return contaDAO.findByCartaoCredito(idCartaoCredito);
    }

    public List<Conta> findByCarteiraCobranca(Integer idCarteiraCobranca) throws Exception {
        init();
        return contaDAO.findByCarteiraCobranca(idCarteiraCobranca);
    }
    
    public List<Conta> findSaldo() throws Exception {
        init();
        return contaDAO.findSaldo();
    }  
    
    public Conta findSaldoLancamento(Integer idConta, Date data) throws Exception {
        init();
        return contaDAO.findSaldoLancamento(idConta, data);
    }  
    
    public Conta findLancamento(Integer idConta, Date dataInicio, Date dataFim) throws Exception {
        init();
        Conta conta = find(idConta);
        conta.setLancamentos(lancamentoService.findByConta(idConta, dataInicio, dataFim));
        return conta;
    } 
    
    public Conta findLancamentoProgramado(Integer idConta, Date dataInicio, Date dataFim) throws Exception {
        init();
        Conta conta = find(idConta);
        conta.setLancamentosProgramados(lancamentoProgramadoService.findByConta(idConta, dataInicio, dataFim));
        return conta;
    } 
    
    public Conta save(Conta conta) throws Exception {
        init();
        return contaDAO.save(conta);
    } 
    
    public Conta update(Conta conta) throws Exception {
        init();
        return contaDAO.update(conta);
    } 
    
    public Conta delete(Integer idConta) throws Exception {
        init();
        if(!podeExcluir(idConta)) throw new Exception("Esta conta não pode ser excluída!"); 
        return contaDAO.remove(idConta);
    }     
    
    private boolean podeExcluir(Integer idConta) throws Exception {
        List<Lancamento> lancamentos = lancamentoService.findByConta(idConta, null, null);
        if(!lancamentos.isEmpty()) return false;
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoService.findByConta(idConta, null, null);
        if(!lancamentoProgramados.isEmpty()) return false;
        return true;                
    }  
    
}
