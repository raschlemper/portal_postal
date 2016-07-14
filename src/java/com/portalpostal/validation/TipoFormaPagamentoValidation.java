package com.portalpostal.validation;

import com.portalpostal.model.TipoFormaPagamento;

public class TipoFormaPagamentoValidation extends Validation<TipoFormaPagamento>{

    @Override
    public boolean validar(TipoFormaPagamento tipoFormaPagamento) {
        if(!validarDescricao(tipoFormaPagamento)) return false;   
        return true;
    }    

    public boolean validarDescricao(TipoFormaPagamento tipoFormaPagamento) {          
        if(campoNotNull(tipoFormaPagamento.getDescricao())) return true; 
        setMsg("Preencha a descrição da forma de pagamento!");
        return false;        
    }  
    
}
