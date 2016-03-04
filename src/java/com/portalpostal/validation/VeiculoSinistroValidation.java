package com.portalpostal.validation;

import com.portalpostal.model.VeiculoSinistro;

public class VeiculoSinistroValidation extends Validation<VeiculoSinistro> {

    @Override
    public boolean validar(VeiculoSinistro veiculo) {          
        if(!validarNumeroSinistro(veiculo)) return false;   
        if(!validarData(veiculo)) return false;   
        return true;        
    }          

    public boolean validarNumeroSinistro(VeiculoSinistro veiculo) {        
        if(campoNotNull(veiculo.getBoletimOcorrencia())) return true; 
        setMsg("Preencha o número do sinistro!");  
        return false;        
    }       

    public boolean validarData(VeiculoSinistro veiculo) {          
        if(campoNotNull(veiculo.getData())) return true; 
        setMsg("Preencha a data do sinistro!");   
        return false;        
    }   
    
}
