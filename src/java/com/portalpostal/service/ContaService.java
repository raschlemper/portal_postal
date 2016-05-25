package com.portalpostal.service;

import com.portalpostal.dao.ContaDAO;
import com.portalpostal.model.Conta;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import java.util.List;

public class ContaService {
    
    private final ContaDAO contaDAO;
    private final LancamentoService lancamentoService; 
    private final LancamentoProgramadoService lancamentoProgramadoService; 

    public ContaService(String nomeBD) {
        contaDAO = new ContaDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<Conta> findAll() throws Exception {
        return contaDAO.findAll();
    }  
    
    public Conta find(Integer idConta) throws Exception {
        return contaDAO.find(idConta);
    } 

    public List<Conta> findByContaCorrente(Integer idContaCorrente) throws Exception {
        return contaDAO.findByContaCorrente(idContaCorrente);
    }

    public List<Conta> findByCartaoCredito(Integer idCartaoCredito) throws Exception {        
        return contaDAO.findByCartaoCredito(idCartaoCredito);
    }

    public List<Conta> findByCarteiraCobranca(Integer idCarteiraCobranca) throws Exception {
        return contaDAO.findByCarteiraCobranca(idCarteiraCobranca);
    }
    
    public List<Conta> findSaldo() throws Exception {
        return contaDAO.findSaldo();
    }  
    
    public Conta findLancamento(Integer idConta) throws Exception {
        Conta conta = find(idConta);
        conta.setLancamentos(lancamentoService.findByConta(idConta));
        return conta;
    } 
    
    public Conta findLancamentoProgramado(Integer idConta) throws Exception {
        Conta conta = find(idConta);
        conta.setLancamentosProgramados(lancamentoProgramadoService.findByConta(idConta));
        return conta;
    } 
    
    public Conta save(Conta conta) throws Exception {
        return contaDAO.save(conta);
    } 
    
    public Conta update(Conta conta) throws Exception {
        return contaDAO.update(conta);
    } 
    
    public Conta delete(Integer idConta) throws Exception {
        if(!podeExcluir(idConta)) throw new Exception("Esta conta não pode ser excluída!"); 
        return contaDAO.remove(idConta);
    }     
    
    public boolean podeExcluir(Integer idConta) throws Exception {
        List<Lancamento> lancamentos = lancamentoService.findByConta(idConta);
        if(!lancamentos.isEmpty()) return false;
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoService.findByConta(idConta);
        if(!lancamentoProgramados.isEmpty()) return false;
        return true;                
    }  
    
}
