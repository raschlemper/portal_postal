package com.portalpostal.service;

import com.portalpostal.dao.CartaoCreditoDAO;
import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.Colaborador;
import com.portalpostal.model.Conta;
import com.portalpostal.model.Endereco;
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
        List<CartaoCredito> cartaoCreditos = cartaoCreditoDAO.findAll();
        cartaoCreditos = setContas(cartaoCreditos);
        return cartaoCreditos;
    }  
    
    public CartaoCredito find(Integer idCartaoCredito) throws Exception {
        init();
        CartaoCredito cartaoCredito = cartaoCreditoDAO.find(idCartaoCredito);
        cartaoCredito = setConta(cartaoCredito);
        return cartaoCredito;
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
    
    // ***** ENDERECO ***** //
    
    private List<CartaoCredito> setContas(List<CartaoCredito> cartaoCreditos) throws Exception {
        for (CartaoCredito cartaoCredito : cartaoCreditos) {
            setConta(cartaoCredito);
        }
        return cartaoCreditos;
    }
    
    private CartaoCredito setConta(CartaoCredito cartaoCredito) throws Exception {
        cartaoCredito.setContas(getContas(cartaoCredito.getIdCartaoCredito()));
        return cartaoCredito;
    }
    
    private List<Conta> getContas(Integer idCartaoCredito) throws Exception {
       return contaService.findByCartaoCredito(idCartaoCredito);
    }
    
}
