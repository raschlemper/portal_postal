package com.portalpostal.service;

import com.portalpostal.dao.CartaoCreditoDAO;
import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.Conta;
import java.util.List;

public class CartaoCreditoService {
    
    private final String nomeBD;
    
    private CartaoCreditoDAO cartaoCreditoDAO;
    private ContaService contaService;

    public CartaoCreditoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        cartaoCreditoDAO = new CartaoCreditoDAO(nomeBD);
        contaService = new ContaService(nomeBD);
    }
    
    public List<CartaoCredito> findAll() throws Exception {
        init();
        return cartaoCreditoDAO.findAll();
    }  
    
    public CartaoCredito find(Integer idCartaoCredito) throws Exception {
        init();
        return cartaoCreditoDAO.find(idCartaoCredito);
    } 

    public List<CartaoCredito> findByContaCorrente(Integer idContaCorrente) throws Exception {
        init();
        return cartaoCreditoDAO.findByContaCorrente(idContaCorrente);
    }
    
    public CartaoCredito findConta(Integer idCartaoCredito) throws Exception {
        init();
        CartaoCredito cartaoCredito = find(idCartaoCredito);
        cartaoCredito.setContas(contaService.findByContaCorrente(idCartaoCredito));
        return cartaoCredito;
    } 
    
    public CartaoCredito save(CartaoCredito cartaoCredito) throws Exception {
        init();
        return cartaoCreditoDAO.save(cartaoCredito);
    } 
    
    public CartaoCredito update(CartaoCredito cartaoCredito) throws Exception {
        init();
        return cartaoCreditoDAO.update(cartaoCredito);
    } 
    
    public CartaoCredito delete(Integer idCartaoCredito) throws Exception {
        init();
        if(!podeExcluir(idCartaoCredito)) throw new Exception("Este cartão de crédito não pode ser excluído!"); 
        return cartaoCreditoDAO.remove(idCartaoCredito);
    }     
    
    private boolean podeExcluir(Integer idCartaoCredito) throws Exception {
        List<Conta> contas = contaService.findByCartaoCredito(idCartaoCredito);
        if(!contas.isEmpty()) return false;
        return true;                
    } 
    
}
