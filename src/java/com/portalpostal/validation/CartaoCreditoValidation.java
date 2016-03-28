package com.portalpostal.validation;

import com.portalpostal.model.CartaoCredito;

public class CartaoCreditoValidation extends Validation<CartaoCredito>{

    @Override
    public boolean validar(CartaoCredito contaCorrente) {
        if(!validarNome(contaCorrente)) return false;   
        if(!validarBandeira(contaCorrente)) return false;   
        if(!validarDiaFechamento(contaCorrente)) return false;   
        if(!validarDiaVencimento(contaCorrente)) return false;   
        if(!validarValorLimiteCredito(contaCorrente)) return false;   
        return true;
    }

    public boolean validarNome(CartaoCredito cartaoCredito) {          
        if(campoNotNull(cartaoCredito.getNome())) return true; 
        setMsg("Preencha o nome do cartão de crédito!");
        return false;        
    }  

    public boolean validarBandeira(CartaoCredito cartaoCredito) {          
        if(campoNotNull(cartaoCredito.getBandeira())) return true; 
        setMsg("Preencha o nome da bandeira do cartão de crédito!");
        return false;        
    }  

    public boolean validarDiaFechamento(CartaoCredito cartaoCredito) {          
        if(campoNotNull(cartaoCredito.getDiaFechamento())) return true; 
        setMsg("Preencha o dia de fechamento do cartão de crédito!");
        return false;        
    }  

    public boolean validarDiaVencimento(CartaoCredito cartaoCredito) {          
        if(campoNotNull(cartaoCredito.getDiaVencimento())) return true; 
        setMsg("Preencha o dia de vencimento do cartão de crédito!");
        return false;        
    }   

    public boolean validarValorLimiteCredito(CartaoCredito cartaoCredito) {          
        if(campoNotNull(cartaoCredito.getValorLimiteCredito())) return true; 
        setMsg("Preencha o valor do limite do crédito!");
        return false;        
    }  
    
}
