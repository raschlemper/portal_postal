package com.portalpostal.service;

import com.portalpostal.dao.ContaDAO;
import com.portalpostal.model.Conta;
import java.util.List;

public class ContaService {
    
    private final ContaDAO contaDAO;

    public ContaService(String nomeBD) {
        contaDAO = new ContaDAO(nomeBD);
    }
    
    public List<Conta> findAll() throws Exception {
        return contaDAO.findAll();
    }  
    
    public Conta find(Integer idConta) throws Exception {
        return contaDAO.find(idConta);
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
