package com.portalpostal.validation;

import com.portalpostal.model.Veiculo;

public class VeiculoValidation extends Validation<Veiculo> {

    @Override
    public boolean validar(Veiculo veiculo) {               
        if(!validarTipo(veiculo)) return false;       
        if(!validarMarca(veiculo)) return false;       
        if(!validarModelo(veiculo)) return false;       
        if(!validarVersao(veiculo)) return false;  
        if(!validarPlaca(veiculo)) return false;   
        if(!validarAnoModelo(veiculo)) return false;   
        if(!validarRenavam(veiculo)) return false;   
        if(!validarQuilometragem(veiculo)) return false;   
        if(!validarCombustivel(veiculo)) return false;  
        if(!validarStatus(veiculo)) return false;  
        if(!validarSituacao(veiculo)) return false;  
        if(!validarDataCadastro(veiculo)) return false;  
        return true;        
    }     

    public boolean validarTipo(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getTipo())) return true; 
        setMsg("Preencha o tipo do veículo!");
        return false;        
    }     

    public boolean validarMarca(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getIdMarca()) || campoNotNull(veiculo.getMarca())) return true; 
        setMsg("Preencha a marca do veículo!");
        return false;        
    }   

    public boolean validarModelo(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getIdModelo()) || campoNotNull(veiculo.getModelo())) return true; 
        setMsg("Preencha o modelo do veículo!");
        return false;        
    }   

    public boolean validarVersao(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getIdVersao()) || campoNotNull(veiculo.getVersao())) return true; 
        setMsg("Preencha a versão do veículo!");
        return false;        
    } 

    public boolean validarPlaca(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getPlaca())) return true; 
        setMsg("Preencha a placa do veículo!");
        return false;        
    }        

    public boolean validarAnoModelo(Veiculo veiculo) {  
        Integer anoPosterior = getAnoCorrente() + 1;
        setMsg("Preencha o ano do modelo do veículo com valores entre 1970 e " + anoPosterior + "!");        
        return campoBetween(veiculo.getAnoModelo(), 1970, anoPosterior);
    }         

    public boolean validarRenavam(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getRenavam())) return true; 
        setMsg("Preencha o renavam do veículo!");  
        return false;        
    }         

    public boolean validarQuilometragem(Veiculo veiculo) {        
        if(campoNotNull(veiculo.getQuilometragem())) return true; 
        setMsg("Preencha a quilometragem do veículo!");  
        return false;        
    }          

    public boolean validarCombustivel(Veiculo veiculo) {        
        if(campoNotNull(veiculo.getCombustivel())) return true; 
        setMsg("Preencha o tipo de combustível do veículo!");  
        return false;        
    }        

    public boolean validarStatus(Veiculo veiculo) {        
        if(campoNotNull(veiculo.getStatus())) return true; 
        setMsg("Preencha o status do veículo!");  
        return false;        
    }     

    public boolean validarSituacao(Veiculo veiculo) {        
        if(campoNotNull(veiculo.getSituacao())) return true; 
        setMsg("Preencha a situação do veículo!");  
        return false;        
    }    

    public boolean validarDataCadastro(Veiculo veiculo) {        
        if(campoNotNull(veiculo.getDataCadastro())) return true; 
        setMsg("Preencha a data de cadastro do veículo!");  
        return false;        
    } 
    
}
