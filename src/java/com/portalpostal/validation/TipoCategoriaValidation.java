package com.portalpostal.validation;

import com.portalpostal.model.TipoCategoria;

public class TipoCategoriaValidation extends Validation<TipoCategoria>{

    @Override
    public boolean validar(TipoCategoria tipoCategoria) {
        if(!validarDescricao(tipoCategoria)) return false;   
        return true;
    }    

    public boolean validarDescricao(TipoCategoria tipoCategoria) {          
        if(campoNotNull(tipoCategoria.getDescricao())) return true; 
        setMsg("Preencha a descrição do categoria!");
        return false;        
    }  
    
}
