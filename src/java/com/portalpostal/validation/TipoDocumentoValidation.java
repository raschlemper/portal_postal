package com.portalpostal.validation;

import com.portalpostal.model.TipoDocumento;

public class TipoDocumentoValidation extends Validation<TipoDocumento>{

    @Override
    public boolean validar(TipoDocumento tipoDocumento) {
        if(!validarDescricao(tipoDocumento)) return false;   
        return true;
    }    

    public boolean validarDescricao(TipoDocumento tipoDocumento) {          
        if(campoNotNull(tipoDocumento.getDescricao())) return true; 
        setMsg("Preencha a descrição do documento!");
        return false;        
    }  
    
}
