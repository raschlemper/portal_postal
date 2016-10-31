package com.portalpostal.validation;

import com.portalpostal.model.VeiculoManutencao;

public class VeiculoManutencaoValidation extends Validation<VeiculoManutencao> {

    public boolean validar(VeiculoManutencao veiculo) {        
        if(!validarVeiculo(veiculo)) return false;        
        if(!validarTipo(veiculo)) return false;                 
        return true;        
    }   

    public boolean validarVeiculo(VeiculoManutencao veiculo) {          
        if(campoNotNull(veiculo.getVeiculo())) return true; 
        setMsg("Preencha o veículo da manutenção!");
        return false;        
    }    

    public boolean validarTipo(VeiculoManutencao veiculo) {          
        if(campoNotNull(veiculo.getTipo())) return true; 
        setMsg("Preencha o tipo de manutenção!");
        return false;        
    }      
    
}
