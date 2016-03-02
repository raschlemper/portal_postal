package com.portalpostal.validation;

import Veiculo.Validacao.*;
import com.portalpostal.model.VeiculoManutencao;

public class VeiculoManutencaoValidacao extends Validacao<VeiculoManutencao> {

    public boolean validar(VeiculoManutencao veiculo) {          
        if(!validarQuilometragem(veiculo)) return false;   
        if(!validarValor(veiculo)) return false;   
        if(!validarData(veiculo)) return false;  
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

    public boolean validarData(VeiculoManutencao veiculo) {          
        if(campoNotNull(veiculo.getDataManutencao())) return true; 
        setMsg("Preencha a data da manutenção!");   
        return false;        
    }    
    
}
