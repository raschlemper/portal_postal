package com.portalpostal.validation;

import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;

public class LancamentoProgramadoValidation extends Validation<LancamentoProgramado>{

    @Override
    public boolean validar(LancamentoProgramado lancamentoProgramado) {
        if(!validarConta(lancamentoProgramado)) return false;  
        if(!validarTipo(lancamentoProgramado)) return false;  
        if(!validarDocumento(lancamentoProgramado)) return false;   
        if(!validarFormaPagamento(lancamentoProgramado)) return false;   
        if(!validarFrequencia(lancamentoProgramado)) return false;   
        if(!validarDataCompetencia(lancamentoProgramado)) return false;   
        if(!validarDataEmissao(lancamentoProgramado)) return false;    
        if(!validarDataVencimento(lancamentoProgramado)) return false;  
        if(!validarValor(lancamentoProgramado)) return false;   
        if(!validarSituacao(lancamentoProgramado)) return false;
        if(!validarModelo(lancamentoProgramado)) return false;  
        //if(!validarHistorico(lancamentoProgramado)) return false;   
        return true;
    }    

    public boolean validarConta(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getConta())) return true; 
        setMsg("Preencha a conta do lançamento programado!");
        return false;        
    }     

    public boolean validarTipo(LancamentoProgramado lancamentoProgramado) {        
        if(campoNotNull(lancamentoProgramado.getTipo())) return true; 
        setMsg("Preencha o tipo do lançamento programado!");  
        return false;        
    }       

    public boolean validarDocumento(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getDocumento())) return true; 
        setMsg("Preencha o documento do lançamento programado!");
        return false;        
    }    

    public boolean validarFormaPagamento(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getFormaPagamento())) return true; 
        setMsg("Preencha a forma de pagamento do lançamento programado!");
        return false;        
    }   

    public boolean validarFrequencia(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getFrequencia())) return true; 
        setMsg("Preencha a frequência do lançamento programado!");
        return false;        
    }   

    public boolean validarDataCompetencia(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getDataCompetencia())) return true; 
        setMsg("Preencha a data de competência do lançamento programado!");
        return false;        
    }    

    
    public boolean validarDataEmissao(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getDataEmissao())) return true; 
        setMsg("Preencha a data de emissão do lançamento programado!");
        return false;        
    }
    
    public boolean validarDataVencimento(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getDataVencimento())) return true; 
        setMsg("Preencha a data de vencimento do lançamento programado!");
        return false;        
    }

    public boolean validarValor(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getValor())) return true; 
        setMsg("Preencha o valor do lançamento programado!");
        return false;        
    } 

    public boolean validarSituacao(LancamentoProgramado lancamentoProgramado) {        
        if(campoNotNull(lancamentoProgramado.getSituacao())) return true; 
        setMsg("Preencha a situação do lançamento programado!");  
        return false;        
    }      

    public boolean validarModelo(LancamentoProgramado lancamentoProgramado) {        
        if(campoNotNull(lancamentoProgramado.getModelo())) return true; 
        setMsg("Preencha o modelo do lançamento programado!");  
        return false;        
    }  

    public boolean validarHistorico(LancamentoProgramado lancamentoProgramado) {          
        if(campoNotNull(lancamentoProgramado.getHistorico())) return true; 
        setMsg("Preencha o histórico do lançamento programado!");
        return false;        
    }
    
}
