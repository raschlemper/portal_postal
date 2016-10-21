package com.portalpostal.validation;

import com.portalpostal.model.LancamentoProgramadoTransferencia;

public class LancamentoProgramadoTransferenciaValidation extends Validation<LancamentoProgramadoTransferencia>{

    @Override
    public boolean validar(LancamentoProgramadoTransferencia lancamento) {
        if(!validarLancamentoOrigem(lancamento)) return false;   
        if(!validarLancamentoDestino(lancamento)) return false; 
        if(!validarDocumento(lancamento)) return false;   
        if(!validarFormaPagamento(lancamento)) return false;   
        if(!validarFrequencia(lancamento)) return false;      
        if(!validarDataCompetencia(lancamento)) return false;   
        if(!validarDataEmissao(lancamento)) return false;   
        if(!validarDataLancamento(lancamento)) return false;   
        if(!validarValor(lancamento)) return false;   
        if(!validarHistorico(lancamento)) return false;   
        return true;
    }    

    public boolean validarLancamentoOrigem(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getLancamentoProgramadoOrigem())) return true; 
        setMsg("Preencha a conta de origem do lançamento programado!");
        return false;        
    }    

    public boolean validarLancamentoDestino(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getLancamentoProgramadoDestino())) return true; 
        setMsg("Preencha a conta de destino do lançamento programado!");
        return false;        
    }       

    public boolean validarDocumento(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDocumento())) return true; 
        setMsg("Preencha o documento do lançamento programado!");
        return false;        
    }    

    public boolean validarFormaPagamento(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getFormaPagamento())) return true; 
        setMsg("Preencha a forma de pagamento do lançamento programado!");
        return false;        
    }   

    public boolean validarFrequencia(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getFrequencia())) return true; 
        setMsg("Preencha a frequência do lançamento programado!");
        return false;        
    } 

    public boolean validarDataCompetencia(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataCompetencia())) return true; 
        setMsg("Preencha a data de competência do lançamento!");
        return false;        
    }    

    public boolean validarDataEmissao(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataEmissao())) return true; 
        setMsg("Preencha a data de emissão do lançamento!");
        return false;        
    }

    public boolean validarDataLancamento(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getDataVencimento())) return true; 
        setMsg("Preencha a data do vencimento!");
        return false;        
    }

    public boolean validarValor(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getValor())) return true; 
        setMsg("Preencha o valor do lançamento programado!");
        return false;        
    }    

    public boolean validarHistorico(LancamentoProgramadoTransferencia lancamento) {          
        if(campoNotNull(lancamento.getHistorico())) return true; 
        setMsg("Preencha o histórico do lançamento programado!");
        return false;        
    }
        
}
