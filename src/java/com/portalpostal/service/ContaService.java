package com.portalpostal.service;

import com.portalpostal.dao.ContaDAO;
import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.model.Conta;
import java.util.List;

public class ContaService {
    
    private final ContaDAO contaDAO;
    private final LancamentoDAO lancamentoDAO; 

    public ContaService(String nomeBD) {
        contaDAO = new ContaDAO(nomeBD);
        lancamentoDAO = new LancamentoDAO(nomeBD);
    }
    
    public List<Conta> findAll() throws Exception {
        return contaDAO.findAll();
    }  
    
    public Conta find(Integer idConta) throws Exception {
        return contaDAO.find(idConta);
    } 
    
    public List<Conta> findSaldo() throws Exception {
        return contaDAO.findSaldo();
    }  
    
    public Conta findLancamento(Integer idConta) throws Exception {
        Conta conta = find(idConta);
        conta.setLancamentos(lancamentoDAO.findByConta(idConta));
        return conta;
    } 
    
    public Conta save(Conta conta) throws Exception {
        return contaDAO.save(conta);
    } 
    
    public Conta update(Conta conta) throws Exception {
        return contaDAO.update(conta);
    } 
    
    public Conta delete(Integer idConta) throws Exception {
        return contaDAO.remove(idConta);
    }   
    
}
