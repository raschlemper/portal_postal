package com.portalpostal.validation;

import com.portalpostal.model.LancamentoTransferenciaProgramado;

public class LancamentoTransferenciaProgramadoValidation extends Validation<LancamentoTransferenciaProgramado>{

    @Override
    public boolean validar(LancamentoTransferenciaProgramado lancamento) {
        if(!validarLancamentoOrigem(lancamento)) return false;   
        if(!validarLancamentoDestino(lancamento)) return false; 
        return true;
    }    

    public boolean validarLancamentoOrigem(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getLancamentoProgramadoOrigem())) return true; 
        setMsg("Preencha a conta de origem do lançamento programado!");
        return false;        
    }    

    public boolean validarLancamentoDestino(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getLancamentoProgramadoDestino())) return true; 
        setMsg("Preencha a conta de destino do lançamento programado!");
        return false;        
    } 
    
}
