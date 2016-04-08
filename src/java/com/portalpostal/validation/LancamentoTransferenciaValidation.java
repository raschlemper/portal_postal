package com.portalpostal.validation;

import com.portalpostal.model.LancamentoTransferencia;

public class LancamentoTransferenciaValidation extends Validation<LancamentoTransferencia>{

    @Override
    public boolean validar(LancamentoTransferencia lancamento) {
        if(!validarLancamentoOrigem(lancamento)) return false;   
        if(!validarLancamentoDestino(lancamento)) return false; 
        return true;
    }    

    public boolean validarLancamentoOrigem(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getLancamentoOrigem())) return true; 
        setMsg("Preencha a conta de origem do lançamento!");
        return false;        
    }    

    public boolean validarLancamentoDestino(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getLancamentoDestino())) return true; 
        setMsg("Preencha a conta de destino do lançamento!");
        return false;        
    } 
    
}
