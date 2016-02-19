package Veiculo.Validacao;

import Veiculo.Entidade.VeiculoMulta;
import Veiculo.Entidade.VeiculoSeguro;

/**
 *
 * @author rafael
 */
public class VeiculoSeguroValidacao extends Validacao<VeiculoSeguro> {

    public boolean validar(VeiculoSeguro veiculo) {          
        if(!validarNumeroSeguro(veiculo)) return false;   
        if(!validarValorFranquia(veiculo)) return false;   
        if(!validarAssegurado(veiculo)) return false;  
        return true;        
    }          

    public boolean validarNumeroSeguro(VeiculoSeguro veiculo) {        
        if(campoNotNull(veiculo.getNumeroSeguro())) return true; 
        setMsg("Preencha o número do seguro!");  
        return false;        
    }   

    public boolean validarValorFranquia(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getValorFranquia())) return true; 
        setMsg("Preencha o valor da franquia!");
        return false;        
    }         

    public boolean validarAssegurado(VeiculoSeguro veiculo) {          
        if(campoNotNull(veiculo.getAssegurado())) return true; 
        setMsg("Preencha o assegurado!");   
        return false;        
    }    
    
}