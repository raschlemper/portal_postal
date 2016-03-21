package com.portalpostal.service;

import com.portalpostal.dao.CartaoCreditoDAO;
import com.portalpostal.model.CartaoCredito;
import java.util.List;

public class CartaoCreditoService {
    
    private final CartaoCreditoDAO cartaoCreditoDAO;

    public CartaoCreditoService(String nomeBD) {
        cartaoCreditoDAO = new CartaoCreditoDAO(nomeBD);
    }
    
    public List<CartaoCredito> findAll() throws Exception {
        return cartaoCreditoDAO.findAll();
    }  
    
    public CartaoCredito find(Integer idCartaoCredito) throws Exception {
        return cartaoCreditoDAO.find(idCartaoCredito);
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
