package com.portalpostal.validation;

import com.portalpostal.model.Fornecedor;

public class FornecedorValidation extends Validation<Fornecedor>{

    @Override
    public boolean validar(Fornecedor fornecedor) {
        if(!validarNome(fornecedor)) return false;   
        if(!validarStatus(fornecedor)) return false;   
        return true;
    }    

    public boolean validarNome(Fornecedor fornecedor) {          
        if(campoNotNull(fornecedor.getNomeFantasia())) return true; 
        setMsg("Preencha o nome do fornecedor!");
        return false;        
    }    

    public boolean validarStatus(Fornecedor fornecedor) {          
        if(campoNotNull(fornecedor.getStatus())) return true; 
        setMsg("Preencha o status do fornecedor!");
        return false;        
    }    
    
}
