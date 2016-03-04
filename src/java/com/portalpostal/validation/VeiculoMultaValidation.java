package com.portalpostal.validation;

import com.portalpostal.model.VeiculoMulta;

public class VeiculoMultaValidation extends Validation<VeiculoMulta> {

    @Override
    public boolean validar(VeiculoMulta veiculo) {          
        if(!validarNumeroMulta(veiculo)) return false;   
        if(!validarValor(veiculo)) return false;   
        if(!validarData(veiculo)) return false;  
        return true;        
    }          

    public boolean validarNumeroMulta(VeiculoMulta veiculo) {        
        if(campoNotNull(veiculo.getNumeroMulta())) return true; 
        setMsg("Preencha o número da multa!");  
        return false;        
    }   

    public boolean validarValor(VeiculoMulta veiculo) {          
        if(campoNotNull(veiculo.getValor())) return true; 
        setMsg("Preencha o valor da multa!");
        return false;        
    }         

    public boolean validarData(VeiculoMulta veiculo) {          
        if(campoNotNull(veiculo.getData())) return true; 
        setMsg("Preencha a data da multa!");   
        return false;        
    }    
    
}
