package com.portalpostal.validation;

import com.portalpostal.model.VeiculoManutencao;

public class VeiculoManutencaoValidation extends Validation<VeiculoManutencao> {

    public boolean validar(VeiculoManutencao veiculo) {          
        if(!validarQuilometragem(veiculo)) return false;   
        if(!validarValor(veiculo)) return false;   
        return true;        
    }          

    public boolean validarQuilometragem(VeiculoManutencao veiculo) {        
        if(campoNotNull(veiculo.getQuilometragem())) return true; 
        setMsg("Preencha a quilometragem do veículo!");  
        return false;        
    }   

    public boolean validarValor(VeiculoManutencao veiculo) {          
        if(campoNotNull(veiculo.getValor())) return true; 
        setMsg("Preencha o valor da manutenção!");
        return false;        
    }     
    
}
