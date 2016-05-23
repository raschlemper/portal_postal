package com.portalpostal.validation;

import com.portalpostal.model.CentroCusto;

public class CentroCustoValidation extends Validation<CentroCusto>{

    @Override
    public boolean validar(CentroCusto centroCusto) {
        validarCodigo(centroCusto);
        validarNome(centroCusto);
        validarGrupo(centroCusto);
        return true;
    } 

    public boolean validarCodigo(CentroCusto centroCusto) {          
        if(campoNotNull(centroCusto.getCodigo())) return true; 
        setMsg("Preencha o código do centro de custo!");
        return false;        
    }  

    public boolean validarNome(CentroCusto centroCusto) {          
        if(campoNotNull(centroCusto.getNome())) return true; 
        setMsg("Preencha o nome do centro de custo!");
        return false;        
    }  

    public boolean validarGrupo(CentroCusto centroCusto) {          
        if(campoNotNull(centroCusto.getGrupo())) return true; 
        setMsg("Preencha o grupo do centro de custo!");
        return false;        
    }  
    
}
