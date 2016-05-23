package com.portalpostal.validation;

import com.portalpostal.model.LancamentoTransferencia;

public class LancamentoTransferenciaValidation extends Validation<LancamentoTransferencia>{

    @Override
    public boolean validar(LancamentoTransferencia lancamento) {
        if(!validarLancamentoOrigem(lancamento)) return false;   
        if(!validarLancamentoDestino(lancamento)) return false;    
        if(!validarDataCompetencia(lancamento)) return false;   
        if(!validarDataEmissao(lancamento)) return false;   
        if(!validarDataLancamento(lancamento)) return false;   
        if(!validarValor(lancamento)) return false;   
        if(!validarHistorico(lancamento)) return false;   
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

    public boolean validarDataCompetencia(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataCompetencia())) return true; 
        setMsg("Preencha a data de competência do lançamento!");
        return false;        
    }    

    public boolean validarDataEmissao(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataEmissao())) return true; 
        setMsg("Preencha a data de emissão do lançamento!");
        return false;        
    }

    public boolean validarDataLancamento(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataLancamento())) return true; 
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
