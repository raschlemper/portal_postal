package com.portalpostal.validation;

import com.portalpostal.model.LancamentoTransferencia;

public class LancamentoTransferenciaValidation extends Validation<LancamentoTransferencia>{

    @Override
    public boolean validar(LancamentoTransferencia lancamento) {
        if(!validarLancamentoOrigem(lancamento)) return false;   
        if(!validarLancamentoDestino(lancamento)) return false;   
        if(!validarDataEmissao(lancamento)) return false;      
        if(!validarDataCompetenciaOrigem(lancamento)) return false; 
        if(!validarDataCompetenciaDestino(lancamento)) return false; 
        if(!validarDataLancamentoOrigem(lancamento)) return false;    
        if(!validarDataLancamentoDestino(lancamento)) return false;   
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

    public boolean validarDataEmissao(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataEmissao())) return true; 
        setMsg("Preencha a data de emissão do lançamento!");
        return false;        
    }  

    public boolean validarDataCompetenciaOrigem(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataCompetenciaOrigem())) return true; 
        setMsg("Preencha a data de competência de origem do lançamento!");
        return false;        
    } 

    public boolean validarDataCompetenciaDestino(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataCompetenciaDestino())) return true; 
        setMsg("Preencha a data de competência de destino do lançamento!");
        return false;        
    } 

    public boolean validarDataLancamentoOrigem(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataLancamentoOrigem())) return true; 
        setMsg("Preencha a data de origem do lançamento!");
        return false;        
    }

    public boolean validarDataLancamentoDestino(LancamentoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataLancamentoDestino())) return true; 
        setMsg("Preencha a data de destino do lançamento!");
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
