package com.portalpostal.service;

import com.portalpostal.dao.CartaoCreditoDAO;
import com.portalpostal.dao.ContaDAO;
import com.portalpostal.model.CartaoCredito;
import java.util.List;

public class CartaoCreditoService {
    
    private final CartaoCreditoDAO cartaoCreditoDAO;
    private final ContaDAO contaDAO;

    public CartaoCreditoService(String nomeBD) {
        cartaoCreditoDAO = new CartaoCreditoDAO(nomeBD);
        contaDAO = new ContaDAO(nomeBD);
    }
    
    public List<CartaoCredito> findAll() throws Exception {
        return cartaoCreditoDAO.findAll();
    }  
    
    public CartaoCredito find(Integer idCartaoCredito) throws Exception {
        return cartaoCreditoDAO.find(idCartaoCredito);
    } 
    
    public CartaoCredito findConta(Integer idCartaoCredito) throws Exception {
        CartaoCredito cartaoCredito = find(idCartaoCredito);
        cartaoCredito.setContas(contaDAO.findByContaCorrente(idCartaoCredito));
        return cartaoCredito;
    } 
    
    public CartaoCredito save(CartaoCredito cartaoCredito) throws Exception {
        return cartaoCreditoDAO.save(cartaoCredito);
    } 
    
    public CartaoCredito update(CartaoCredito cartaoCredito) throws Exception {
        return cartaoCreditoDAO.update(cartaoCredito);
    } 
    
    public CartaoCredito delete(Integer idCartaoCredito) throws Exception {
        return cartaoCreditoDAO.remove(idCartaoCredito);
    }   
    
}
