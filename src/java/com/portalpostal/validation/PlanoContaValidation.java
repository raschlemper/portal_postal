package com.portalpostal.validation;

import com.portalpostal.model.PlanoConta;

public class PlanoContaValidation extends Validation<PlanoConta>{

    @Override
    public boolean validar(PlanoConta planoConta) {
        validarTipo(planoConta);
        validarCodigo(planoConta);
        validarNome(planoConta);
        validarGrupo(planoConta);
        return true;
    }     

    public boolean validarTipo(PlanoConta planoConta) {          
        if(campoNotNull(planoConta.getTipo())) return true; 
        setMsg("Preencha o tipo do plano de conta!");
        return false;        
    }    

    public boolean validarCodigo(PlanoConta planoConta) {          
        if(campoNotNull(planoConta.getCodigo())) return true; 
        setMsg("Preencha o código do plano de conta!");
        return false;        
    }  

    public boolean validarNome(PlanoConta planoConta) {          
        if(campoNotNull(planoConta.getNome())) return true; 
        setMsg("Preencha o nome do plano de conta!");
        return false;        
    }  

    public boolean validarGrupo(PlanoConta planoConta) {          
        if(campoNotNull(planoConta.getGrupo())) return true; 
        setMsg("Preencha o grupo do plano de conta!");
        return false;        
    }  
    
}
