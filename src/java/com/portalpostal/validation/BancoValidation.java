package com.portalpostal.validation;

import com.portalpostal.model.Banco;

public class BancoValidation extends Validation<Banco>{

    @Override
    public boolean validar(Banco banco) {
        if(!validarNome(banco)) return false;   
        if(!validarNumero(banco)) return false;   
        if(!validarNumeroRange(banco)) return false;   
        return true;
    }    

    public boolean validarNome(Banco banco) {          
        if(campoNotNull(banco.getNome())) return true; 
        setMsg("Preencha o nome do banco!");
        return false;        
    }    

    public boolean validarNumero(Banco banco) {          
        if(campoNotNull(banco.getNumero())) return true; 
        setMsg("Preencha o número do banco!");
        return false;        
    }        

    public boolean validarNumeroRange(Banco banco) { 
        setMsg("Preencha o número do banco com valores entre 1 e 999!");        
        return campoBetween(banco.getNumero(), 1, 999);
    } 
    
}
