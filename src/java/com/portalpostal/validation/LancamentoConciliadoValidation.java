package com.portalpostal.validation;

import com.portalpostal.model.LancamentoConciliado;

public class LancamentoConciliadoValidation extends Validation<LancamentoConciliado>{

    @Override
    public boolean validar(LancamentoConciliado lancamentoConciliado) {
        if(!validarConta(lancamentoConciliado)) return false;        
        if(!validarLancamento(lancamentoConciliado)) return false; 
        if(!validarTipo(lancamentoConciliado)) return false;  
        if(!validarDataCompetencia(lancamentoConciliado)) return false;   
        if(!validarDataEmissao(lancamentoConciliado)) return false;  
        if(!validarDataLancamento(lancamentoConciliado)) return false;   
        if(!validarValor(lancamentoConciliado)) return false;    
        //if(!validarHistorico(lancamentoConciliado)) return false;  
        return true;
    }    

    public boolean validarConta(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getConta())) return true; 
        setMsg("Preencha a conta da conciliação!");
        return false;        
    }   

    public boolean validarLancamento(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getLancamento())) return true; 
        setMsg("Preencha o lançamento da conciliação!");
        return false;        
    }    

    public boolean validarTipo(LancamentoConciliado lancamentoConciliado) {        
        if(campoNotNull(lancamentoConciliado.getTipo())) return true; 
        setMsg("Preencha o tipo da conciliação!");  
        return false;        
    }     

    public boolean validarDataCompetencia(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getDataCompetencia())) return true; 
        setMsg("Preencha a data de competência da conciliação!");
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

    public boolean validarHistorico(LancamentoConciliado lancamentoConciliado) {          
        if(campoNotNull(lancamentoConciliado.getHistorico())) return true; 
        setMsg("Preencha o histórico da conciliação!");
        return false;        
    }
    
}
