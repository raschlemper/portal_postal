package com.portalpostal.validation;

import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoTransferencia;
import com.portalpostal.model.LancamentoTransferenciaProgramado;

public class LancamentoTransferenciaProgramadoValidation extends Validation<LancamentoTransferenciaProgramado>{

    @Override
    public boolean validar(LancamentoTransferenciaProgramado lancamento) {
        if(!validarLancamentoOrigem(lancamento)) return false;   
        if(!validarLancamentoDestino(lancamento)) return false; 
        if(!validarDocumento(lancamento)) return false;   
        if(!validarFormaPagamento(lancamento)) return false;   
        if(!validarFrequencia(lancamento)) return false;    
        if(!validarDataEmissao(lancamento)) return false;   
        if(!validarValor(lancamento)) return false;   
        if(!validarHistorico(lancamento)) return false;   
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

    public boolean validarDocumento(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getDocumento())) return true; 
        setMsg("Preencha o documento do lançamento programado!");
        return false;        
    }    

    public boolean validarFormaPagamento(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getFormaPagamento())) return true; 
        setMsg("Preencha a forma de pagamento do lançamento programado!");
        return false;        
    }   

    public boolean validarFrequencia(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getFrequencia())) return true; 
        setMsg("Preencha a frequência do lançamento programado!");
        return false;        
    }

    public boolean validarDataEmissao(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getDataEmissao())) return true; 
        setMsg("Preencha a data de pagamento do lançamento programado!");
        return false;        
    }

    public boolean validarValor(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getValor())) return true; 
        setMsg("Preencha o valor do lançamento programado!");
        return false;        
    }    

    public boolean validarHistorico(LancamentoTransferenciaProgramado lancamento) {          
        if(campoNotNull(lancamento.getHistorico())) return true; 
        setMsg("Preencha o histórico do lançamento programado!");
        return false;        
    }
        
}
