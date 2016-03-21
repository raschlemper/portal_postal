package com.portalpostal.validation;

import com.portalpostal.model.LancamentoTransferencia;

public class LancamentoTransferenciaValidation extends Validation<LancamentoTransferencia>{

    @Override
    public boolean validar(LancamentoTransferencia lancamento) {
        if(!validarContaOrigem(lancamento)) return false;   
        if(!validarContaDestino(lancamento)) return false; 
        if(!validarData(lancamento)) return false;   
        if(!validarValor(lancamento)) return false;   
        if(!validarHistorico(lancamento)) return false;   
        return true;
    }    

    public boolean validarContaOrigem(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getContaOrigem())) return true; 
        setMsg("Preencha a conta de origem do lançamento!");
        return false;        
    }    

    public boolean validarContaDestino(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getContaDestino())) return true; 
        setMsg("Preencha a conta de destino do lançamento!");
        return false;        
    } 

    public boolean validarData(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getData())) return true; 
        setMsg("Preencha a data do lançamento!");
        return false;        
    }

    public boolean validarValor(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getValor())) return true; 
        setMsg("Preencha o valor do lançamento!");
        return false;        
    }

    public boolean validarHistorico(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getHistorico())) return true; 
        setMsg("Preencha o histórico do lançamento!");
        return false;        
    }
    
}
