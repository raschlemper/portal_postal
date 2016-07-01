package com.portalpostal.validation;

import com.portalpostal.model.Colaborador;

public class ColaboradorValidation extends Validation<Colaborador>{

    @Override
    public boolean validar(Colaborador colaborador) {
        if(!validarNome(colaborador)) return false;   
        if(!validarStatus(colaborador)) return false;   
        return true;
    }    

    public boolean validarNome(Colaborador colaborador) {          
        if(campoNotNull(colaborador.getNome())) return true; 
        setMsg("Preencha o nome do colaborador!");
        return false;        
    }    

    public boolean validarStatus(Colaborador colaborador) {          
        if(campoNotNull(colaborador.getStatus())) return true; 
        setMsg("Preencha o status do colaborador!");
        return false;        
    }    
    
}
