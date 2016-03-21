package com.portalpostal.validation;

import com.portalpostal.model.Lancamento;

public class LancamentoValidation extends Validation<Lancamento>{

    @Override
    public boolean validar(Lancamento lancamento) {
        if(!validarConta(lancamento)) return false;   
        if(!validarPlanoConta(lancamento)) return false;   
        if(!validarTipo(lancamento)) return false;   
        if(!validarData(lancamento)) return false;   
        if(!validarValor(lancamento)) return false;   
        if(!validarHistorico(lancamento)) return false;   
        return true;
    }    

    public boolean validarConta(Lancamento lancamento) {          
        if(campoNotNull(lancamento.getConta())) return true; 
        setMsg("Preencha a conta do lançamento!");
        return false;        
    }    

    public boolean validarPlanoConta(Lancamento lancamento) {          
        if(campoNotNull(lancamento.getPlanoConta())) return true; 
        setMsg("Preencha o plano de contas do lançamento!");
        return false;        
    }     

    public boolean validarTipo(Lancamento lancamento) {          
        if(campoNotNull(lancamento.getTipo())) return true; 
        setMsg("Preencha o tipo do lançamento!");
        return false;        
    } 

    public boolean validarData(Lancamento lancamento) {          
        if(campoNotNull(lancamento.getData())) return true; 
        setMsg("Preencha a data do lançamento!");
        return false;        
    }

    public boolean validarValor(Lancamento lancamento) {          
        if(campoNotNull(lancamento.getValor())) return true; 
        setMsg("Preencha o valor do lançamento!");
        return false;        
    }

    public boolean validarHistorico(Lancamento lancamento) {          
        if(campoNotNull(lancamento.getHistorico())) return true; 
        setMsg("Preencha o histórico do lançamento!");
        return false;        
    }
    
}
