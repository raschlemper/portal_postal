package com.portalpostal.validation;

import com.portalpostal.model.LancamentoConciliado;

public class LancamentoConciliadoValidation extends Validation<LancamentoConciliado>{

    @Override
    public boolean validar(LancamentoConciliado lancamentoConciliado) {
        if(!validarConta(lancamentoConciliado)) return false;   
        if(!validarPlanoConta(lancamentoConciliado)) return false; 
        if(!validarTipo(lancamentoConciliado)) return false;  
        if(!validarCompetencia(lancamentoConciliado)) return false;   
        if(!validarDataEmissao(lancamentoConciliado)) return false;  
        if(!validarDataLancamento(lancamentoConciliado)) return false;   
        if(!validarValor(lancamentoConciliado)) return false;    
        return true;
    }    

    public boolean validarConta(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getConta())) return true; 
        setMsg("Preencha a conta da conciliação!");
        return false;        
    }    

    public boolean validarPlanoConta(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getPlanoConta())) return true; 
        setMsg("Preencha o plano de contas da conciliação!");
        return false;        
    }    

    public boolean validarTipo(LancamentoConciliado lancamentoConciliado) {        
        if(campoNotNull(lancamentoConciliado.getTipo())) return true; 
        setMsg("Preencha o tipo da conciliação!");  
        return false;        
    }     

    public boolean validarCompetencia(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getCompetencia())) return true; 
        setMsg("Preencha a competência da conciliação!");
        return false;        
    }    

    public boolean validarDataEmissao(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getDataEmissao())) return true; 
        setMsg("Preencha a data de emissão da conciliação!");
        return false;        
    }    

    public boolean validarDataLancamento(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getDataLancamento())) return true; 
        setMsg("Preencha a data de lancamento da conciliação!");
        return false;        
    }  

    public boolean validarValor(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getValor())) return true; 
        setMsg("Preencha o valor da conciliação!");
        return false;        
    }  
    
}
