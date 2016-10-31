package com.portalpostal.validation;

import com.portalpostal.model.ConfiguracaoFinanceiro;

public class ConfiguracaoFinanceiroValidation extends Validation<ConfiguracaoFinanceiro>{

    @Override
    public boolean validar(ConfiguracaoFinanceiro configuracao) {
        if(!validarFavorecido(configuracao)) return false;
        return true;
    }    

    public boolean validarFavorecido(ConfiguracaoFinanceiro configuracao) {          
        if(campoNotNull(configuracao.getFavorecido())) return true; 
        setMsg("Selecione o favorecido!");
        return false;        
    }      

    public boolean validarHistorico(ConfiguracaoFinanceiro configuracao) {          
        if(campoNotNull(configuracao.getHistorico())) return true; 
        setMsg("Selecione o histórico!");
        return false;        
    }      

    public boolean validarPlanoConta(ConfiguracaoFinanceiro configuracao) {          
        if(campoNotNull(configuracao.getPlanoConta())) return true; 
        setMsg("Selecione o plano de contas!");
        return false;        
    }      

    public boolean validarCentroCusto(ConfiguracaoFinanceiro configuracao) {          
        if(campoNotNull(configuracao.getCentroCusto())) return true; 
        setMsg("Selecione o centro de custos!");
        return false;        
    }    
    
}
