package com.portalpostal.validation;

import com.portalpostal.model.VeiculoSeguro;

public class VeiculoSeguroValidation extends Validation<VeiculoSeguro> {

    @Override
    public boolean validar(VeiculoSeguro veiculo) {             
        if(!validarVeiculo(veiculo)) return false;          
        if(!validarNumeroApolice(veiculo)) return false;   
        if(!validarCorretora(veiculo)) return false;    
        if(!validarAssegurado(veiculo)) return false;   
        if(!validarValorFranquia(veiculo)) return false;  
        if(!validarIndenizacao(veiculo)) return false;  
        if(!validarDataInicioVigencia(veiculo)) return false; 
        if(!validarDataFimVigencia(veiculo)) return false; 
        return true;        
    }        

    public boolean validarVeiculo(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getVeiculo())) return true; 
        setMsg("Preencha o veículo do seguro!");
        return false;        
    }      

    public boolean validarNumeroApolice(VeiculoSeguro veiculo) {        
        if(campoNotNull(veiculo.getNumeroApolice())) return true; 
        setMsg("Preencha o número da apólice do seguro!");  
        return false;        
    }       

    public boolean validarAssegurado(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getAssegurado())) return true; 
        setMsg("Preencha o assegurado!");   
        return false;        
    }          

    public boolean validarCorretora(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getCorretora())) return true; 
        setMsg("Preencha a corretora!");   
        return false;        
    }    

    public boolean validarValorFranquia(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getValorFranquia())) return true; 
        setMsg("Preencha o valor da franquia!");
        return false;        
    }      

    public boolean validarIndenizacao(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getValorFranquia())) return true; 
        setMsg("Preencha o tipo de indenização!");
        return false;        
    }            

    public boolean validarDataInicioVigencia(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getDataInicioVigencia())) return true; 
        setMsg("Preencha a data inicio vigência!");   
        return false;        
    }             

    public boolean validarDataFimVigencia(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getDataFimVigencia())) return true; 
        setMsg("Preencha a data fim vigência!");   
        return false;        
    }  
    
}
