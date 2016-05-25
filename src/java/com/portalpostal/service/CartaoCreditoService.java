package com.portalpostal.service;

import com.portalpostal.dao.CartaoCreditoDAO;
import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.Conta;
import java.util.List;

public class CartaoCreditoService {
    
    private final CartaoCreditoDAO cartaoCreditoDAO;
    private final ContaService contaService;

    public CartaoCreditoService(String nomeBD) {
        cartaoCreditoDAO = new CartaoCreditoDAO(nomeBD);
        contaService = new ContaService(nomeBD);
    }
    
    public List<CartaoCredito> findAll() throws Exception {
        return cartaoCreditoDAO.findAll();
    }  
    
    public CartaoCredito find(Integer idCartaoCredito) throws Exception {
        return cartaoCreditoDAO.find(idCartaoCredito);
    } 

    public List<CartaoCredito> findByContaCorrente(Integer idContaCorrente) throws Exception {
        return cartaoCreditoDAO.findByContaCorrente(idContaCorrente);
    }
    
    public CartaoCredito findConta(Integer idCartaoCredito) throws Exception {
        CartaoCredito cartaoCredito = find(idCartaoCredito);
        cartaoCredito.setContas(contaService.findByContaCorrente(idCartaoCredito));
        return cartaoCredito;
    } 
    
    public CartaoCredito save(CartaoCredito cartaoCredito) throws Exception {
        return cartaoCreditoDAO.save(cartaoCredito);
    } 
    
    public CartaoCredito update(CartaoCredito cartaoCredito) throws Exception {
        return cartaoCreditoDAO.update(cartaoCredito);
    } 
    
    public CartaoCredito delete(Integer idCartaoCredito) throws Exception {
        if(!podeExcluir(idCartaoCredito)) throw new Exception("Este cartão de crédito não pode ser excluído!"); 
        return cartaoCreditoDAO.remove(idCartaoCredito);
    }     
    
    public boolean podeExcluir(Integer idCartaoCredito) throws Exception {
        List<Conta> contas = contaService.findByCartaoCredito(idCartaoCredito);
        if(!contas.isEmpty()) return false;
        return true;                
    } 
    
}
