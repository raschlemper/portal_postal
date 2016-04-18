package com.portalpostal.validation;

import com.portalpostal.model.LancamentoProgramado;

public class LancamentoProgramadoValidation extends Validation<LancamentoProgramado>{

    @Override
    public boolean validar(LancamentoProgramado lancamentoProgramado) {
        if(!validarConta(lancamentoProgramado)) return false;   
        if(!validarPlanoConta(lancamentoProgramado)) return false; 
        if(!validarTipo(lancamentoProgramado)) return false;  
        if(!validarFrequencia(lancamentoProgramado)) return false;   
        if(!validarData(lancamentoProgramado)) return false;   
        if(!validarValor(lancamentoProgramado)) return false;   
        if(!validarSituacao(lancamentoProgramado)) return false;  
        if(!validarHistorico(lancamentoProgramado)) return false;   
        return true;
    }    

    public boolean validarConta(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getConta())) return true; 
        setMsg("Preencha a conta do lançamento programado!");
        return false;        
    }    

    public boolean validarPlanoConta(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getPlanoConta())) return true; 
        setMsg("Preencha o plano de contas do lançamento programado!");
        return false;        
    }    

    public boolean validarTipo(LancamentoProgramado lancamentoProgramado) {        
        if(campoNotNull(lancamentoProgramado.getTipo())) return true; 
        setMsg("Preencha o tipo do lançamento programado!");  
        return false;        
    }      

    public boolean validarFrequencia(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getFrequencia())) return true; 
        setMsg("Preencha a frequência do lançamento programado!");
        return false;        
    }
    
    public boolean validarData(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getData())) return true; 
        setMsg("Preencha a data do lançamento programado!");
        return false;        
    }

    public boolean validarValor(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getValor())) return true; 
        setMsg("Preencha o valor do lançamento programado!");
        return false;        
    } 

    public boolean validarSituacao(LancamentoProgramado lancamentoProgramado) {        
        if(campoNotNull(lancamentoProgramado.getSituacao())) return true; 
        setMsg("Preencha a situação do lançamento programado!");  
        return false;        
    }   

    public boolean validarHistorico(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getHistorico())) return true; 
        setMsg("Preencha o histórico do lançamento programado!");
        return false;        
    }
    
}
