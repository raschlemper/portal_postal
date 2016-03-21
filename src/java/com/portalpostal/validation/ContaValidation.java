package com.portalpostal.validation;

import com.portalpostal.model.Conta;

public class ContaValidation extends Validation<Conta>{

    @Override
    public boolean validar(Conta conta) {
        if(!validarStatus(conta)) return false;   
        if(!validarDataAbertura(conta)) return false;   
        return true;
    }    

    public boolean validarStatus(Conta conta) {          
        if(campoNotNull(conta.getStatus())) return true; 
        setMsg("Preencha o status da conta!");
        return false;        
    }    

    public boolean validarDataAbertura(Conta conta) {          
        if(campoNotNull(conta.getDataAbertura())) return true; 
        setMsg("Preencha a data de abertura do conta!");
        return false;        
    }   
    
}
