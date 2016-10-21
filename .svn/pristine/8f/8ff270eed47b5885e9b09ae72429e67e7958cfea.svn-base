package com.portalpostal.validation;

import com.portalpostal.model.VeiculoSinistro;

public class VeiculoSinistroValidation extends Validation<VeiculoSinistro> {

    @Override
    public boolean validar(VeiculoSinistro veiculo) {               
        if(!validarVeiculo(veiculo)) return false;           
        if(!validarNumeroSinistro(veiculo)) return false;   
        if(!validarData(veiculo)) return false;   
        return true;        
    }         

    public boolean validarVeiculo(VeiculoSinistro veiculo) {          
        if(campoNotNull(veiculo.getVeiculo())) return true; 
        setMsg("Preencha o veículo do sinistro!");
        return false;        
    }           

    public boolean validarTipo(VeiculoSinistro veiculo) {          
        if(campoNotNull(veiculo.getTipo())) return true; 
        setMsg("Preencha o tipo de sinistro!");
        return false;        
    }   

    public boolean validarNumeroSinistro(VeiculoSinistro veiculo) {        
        if(campoNotNull(veiculo.getBoletimOcorrencia())) return true; 
        setMsg("Preencha o número do sinistro BO!");  
        return false;        
    }       

    public boolean validarData(VeiculoSinistro veiculo) {          
        if(campoNotNull(veiculo.getData())) return true; 
        setMsg("Preencha a data do sinistro!");   
        return false;        
    }             

    public boolean validarResponsavel(VeiculoSinistro veiculo) {          
        if(campoNotNull(veiculo.getResponsavel())) return true; 
        setMsg("Preencha o responsável pelo sinistro!");
        return false;        
    }       
    
}
