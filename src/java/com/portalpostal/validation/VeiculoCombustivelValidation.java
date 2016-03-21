package com.portalpostal.validation;

import com.portalpostal.model.VeiculoCombustivel;

public class VeiculoCombustivelValidation extends Validation<VeiculoCombustivel> {

    @Override
    public boolean validar(VeiculoCombustivel veiculo) {          
        if(!validarVeiculo(veiculo)) return false;            
        if(!validarTipo(veiculo)) return false;            
        if(!validarQuantidade(veiculo)) return false;   
        if(!validarValorTotal(veiculo)) return false;   
        if(!validarValorUnitario(veiculo)) return false;   
        if(!validarData(veiculo)) return false;   
        if(!validarQuilometragem(veiculo)) return false;  
        return true;        
    }   

    public boolean validarVeiculo(VeiculoCombustivel veiculo) {          
        if(campoNotNull(veiculo.getVeiculo())) return true; 
        setMsg("Preencha o veículo do abastecidos!");
        return false;        
    }     

    public boolean validarTipo(VeiculoCombustivel veiculo) {          
        if(campoNotNull(veiculo.getTipo())) return true; 
        setMsg("Preencha o tipo de combustível!");
        return false;        
    }   

    public boolean validarQuantidade(VeiculoCombustivel veiculo) {          
        if(campoNotNull(veiculo.getQuantidade())) return true; 
        setMsg("Preencha a quantidade de litros abastecidos!");
        return false;        
    }     

    public boolean validarValorTotal(VeiculoCombustivel veiculo) {          
        if(campoNotNull(veiculo.getValorTotal())) return true; 
        setMsg("Preencha o valor total do abastecimento!");   
        return false;        
    }      

    public boolean validarValorUnitario(VeiculoCombustivel veiculo) {          
        if(campoNotNull(veiculo.getValorUnitario())) return true; 
        setMsg("Preencha o valor unitário do abastecimento!");   
        return false;        
    }        

    public boolean validarData(VeiculoCombustivel veiculo) {          
        if(campoNotNull(veiculo.getData())) return true; 
        setMsg("Preencha a data de abastecimento!");   
        return false;        
    }           

    public boolean validarQuilometragem(VeiculoCombustivel veiculo) {        
        if(campoNotNull(veiculo.getQuilometragem())) return true; 
        setMsg("Preencha a quilometragem anterior do veículo!");  
        return false;        
    } 
    
}
