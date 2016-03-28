package com.portalpostal.service;

import com.portalpostal.dao.CartaoCreditoDAO;
import com.portalpostal.dao.ContaCorrenteDAO;
import com.portalpostal.model.ContaCorrente;
import java.util.List;

public class ContaCorrenteService {
    
    private final ContaCorrenteDAO contaCorrenteDAO;    
    private final CartaoCreditoDAO cartaoCreditoDAO;

    public ContaCorrenteService(String nomeBD) {
        contaCorrenteDAO = new ContaCorrenteDAO(nomeBD);
        cartaoCreditoDAO = new CartaoCreditoDAO(nomeBD);
    }
    
    public List<ContaCorrente> findAll() throws Exception {
        return contaCorrenteDAO.findAll();
    }  
    
    public ContaCorrente find(Integer idContaCorrente) throws Exception {
        return contaCorrenteDAO.find(idContaCorrente);
    } 
    
    public ContaCorrente save(ContaCorrente contaCorrente) throws Exception {
        validation(contaCorrente);
        return contaCorrenteDAO.save(contaCorrente);
    } 
    
    public ContaCorrente update(ContaCorrente contaCorrente) throws Exception {
        validation(contaCorrente);
        return contaCorrenteDAO.update(contaCorrente);
    } 
    
    public ContaCorrente delete(Integer idContaCorrente) throws Exception {
        return contaCorrenteDAO.remove(idContaCorrente);
    }   
    
    public ContaCorrente findByContaCorrente(Integer idBanco, Integer agencia, Integer contaCorrente) throws Exception {
        return contaCorrenteDAO.findByContaCorrente(idBanco, agencia, contaCorrente);
    }    
    
    public ContaCorrente findByCarteira(Integer idBanco, Integer agencia, Integer contaCorrente, Integer carteira) throws Exception {
        return contaCorrenteDAO.findByCarteira(idBanco, agencia, contaCorrente, carteira);
    }     
    
    private void validation(ContaCorrente contaCorrente) throws Exception {  
        if(existeContaCorrente(contaCorrente)) {
            throw new Exception("Esta Conta Corrente já foi cadastrada!");
        } 
        if(existeCarteira(contaCorrente)) {
            throw new Exception("Esta Carteira já foi cadastrada!");
        }
    }  
    
    private boolean existeContaCorrente(ContaCorrente contaCorrente) throws Exception {
        ContaCorrente conta = contaCorrenteDAO.findByContaCorrente(contaCorrente.getBanco().getIdBanco(),
                contaCorrente.getAgencia(), contaCorrente.getContaCorrente());
        if(conta == null) return false;
        if(conta.getBanco().getIdBanco().equals(contaCorrente.getBanco().getIdBanco()) &&
           conta.getAgencia().equals(contaCorrente.getAgencia()) && 
           conta.getContaCorrente().equals(contaCorrente.getContaCorrente())) return false;
        return true;
    }
    
    private boolean existeCarteira(ContaCorrente contaCorrente) throws Exception {
        ContaCorrente conta = contaCorrenteDAO.findByCarteira(contaCorrente.getBanco().getIdBanco(),
                contaCorrente.getAgencia(), contaCorrente.getContaCorrente(), contaCorrente.getCarteira());
        if(conta == null) return false;
        if(conta.getBanco().getIdBanco().equals(contaCorrente.getBanco().getIdBanco()) &&
           conta.getAgencia().equals(contaCorrente.getAgencia()) && 
           conta.getContaCorrente().equals(contaCorrente.getContaCorrente()) && 
           conta.getCarteira().equals(contaCorrente.getCarteira())) return false;
        return true;
    }
    
}
