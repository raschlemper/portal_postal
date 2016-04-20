package com.portalpostal.validation;

import com.portalpostal.model.LancamentoParcelado;

public class LancamentoParceladoValidation extends Validation<LancamentoParcelado>{

    @Override
    public boolean validar(LancamentoParcelado lancamentoParcelado) {
        if(!validarConta(lancamentoParcelado)) return false;   
        if(!validarPlanoConta(lancamentoParcelado)) return false; 
        if(!validarTipo(lancamentoParcelado)) return false;  
        if(!validarDocumento(lancamentoParcelado)) return false;   
        if(!validarFormaPagamento(lancamentoParcelado)) return false;   
        if(!validarFrequencia(lancamentoParcelado)) return false;   
        if(!validarDataEmissao(lancamentoParcelado)) return false;  
        if(!validarValorTotal(lancamentoParcelado)) return false;   
        if(!validarHistorico(lancamentoParcelado)) return false;   
        return true;
    }    

    public boolean validarConta(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getConta())) return true; 
        setMsg("Preencha a conta do lançamento parcelado!");
        return false;        
    }    

    public boolean validarPlanoConta(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getPlanoConta())) return true; 
        setMsg("Preencha o plano de contas do lançamento parcelado!");
        return false;        
    }    

    public boolean validarTipo(LancamentoParcelado lancamentoParcelado) {        
        if(campoNotNull(lancamentoParcelado.getTipo())) return true; 
        setMsg("Preencha o tipo do lançamento parcelado!");  
        return false;        
    }  

    public boolean validarDocumento(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getDocumento())) return true; 
        setMsg("Preencha o documento do lançamento parcelado!");
        return false;        
    }    

    public boolean validarFormaPagamento(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getFormaPagamento())) return true; 
        setMsg("Preencha a forma de pagamento do lançamento parcelado!");
        return false;        
    }        

    public boolean validarFrequencia(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getFrequencia())) return true; 
        setMsg("Preencha a frequência do lançamento parcelado!");
        return false;        
    }
    
    public boolean validarDataEmissao(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getDataEmissao())) return true; 
        setMsg("Preencha a data de emissão do lançamento parcelado!");
        return false;        
    }

    public boolean validarValorTotal(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getValorTotal())) return true; 
        setMsg("Preencha o valor do lançamento parcelado!");
        return false;        
    } 

    public boolean validarHistorico(LancamentoParcelado lancamentoParcelado) {          
        if(campoNotNull(lancamentoParcelado.getHistorico())) return true; 
        setMsg("Preencha o histórico do lançamento parcelado!");
        return false;        
    }
    
}
