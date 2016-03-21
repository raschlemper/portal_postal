package com.portalpostal.validation;

import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.ContaCorrente;

public class ContaCorrenteValidation extends Validation<ContaCorrente>{

    @Override
    public boolean validar(ContaCorrente contaCorrente) {
        if(!validarBanco(contaCorrente)) return false;   
        if(!validarAgencia(contaCorrente)) return false;   
        if(!validarContaCorrente(contaCorrente)) return false;   
        return true;
    }

    public boolean validarBanco(ContaCorrente contaCorrente) {          
        if(campoNotNull(contaCorrente.getBanco())) return true; 
        setMsg("Preencha o número do banco da conta corrente!");
        return false;        
    }  

    public boolean validarAgencia(ContaCorrente contaCorrente) {          
        if(campoNotNull(contaCorrente.getAgencia())) return true; 
        setMsg("Preencha o número do agência da conta corrente!");
        return false;        
    }   

    public boolean validarContaCorrente(ContaCorrente contaCorrente) {          
        if(campoNotNull(contaCorrente.getContaCorrente())) return true; 
        setMsg("Preencha o número da conta corrente!");
        return false;        
    }  
    
}
