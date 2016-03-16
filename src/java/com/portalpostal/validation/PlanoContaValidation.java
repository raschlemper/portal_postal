package com.portalpostal.validation;

import com.portalpostal.model.PlanoConta;

public class PlanoContaValidation extends Validation<PlanoConta>{

    @Override
    public boolean validar(PlanoConta planoConta) {
        return true;
    }   

    public boolean validarNome(PlanoConta planoConta) {          
        if(campoNotNull(planoConta.getNome())) return true; 
        setMsg("Preencha o nome do plano de conta!");
        return false;        
    }  
    
}
