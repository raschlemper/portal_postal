package Veiculo.Validacao;

import Veiculo.Entidade.Veiculo;

/**
 *
 * @author rafael
 */
public class VeiculoValidacao extends Validacao<Veiculo> {

    public boolean validar(Veiculo veiculo) {          
        if(!validarPlaca(veiculo)) return false;   
        if(!validarAnoFabricacao(veiculo)) return false;   
        if(!validarAnoModelo(veiculo)) return false;   
        if(!validarRenavam(veiculo)) return false;   
        if(!validarQuilometragem(veiculo)) return false;   
        return true;        
    }   

    public boolean validarPlaca(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getPlaca())) return true; 
        setMsg("Preencha a placa do veículo!");
        return false;        
    }     

    public boolean validarAnoFabricacao(Veiculo veiculo) {  
        Integer anoPosterior = getAnoCorrente() + 1;
        setMsg("Preencha o ano de fabricação do veículo com valores entre 1970 e " + anoPosterior + "!");        
        return campoBetween(veiculo.getAnoFabricacao(), 1970, anoPosterior);
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
    
}
