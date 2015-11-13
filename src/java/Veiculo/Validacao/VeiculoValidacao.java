package Veiculo.Validacao;

import Veiculo.Entidade.Veiculo;

/**
 *
 * @author rafael
 */
public class VeiculoValidacao extends Validacao<Veiculo> {

    public boolean validar(Veiculo veiculo) {          
        if(!validarPlaca(veiculo)) return false;   
        return true;        
    }   

    public boolean validarPlaca(Veiculo veiculo) {          
        if(campoNotNull(veiculo.getPlaca())) return true; 
        setMsg("Preencha a placa do veículo!");
        return false;        
    }     

    public boolean validarAnoFabricacao(Veiculo veiculo) {   
        
        
        var msg = 'Preencha o ano de fabrica\u00E7\u00E3o do ve\u00EDculo com valores entre 1970 e ' + anoCorrente + '!';
        var anoCorrente = (new Date).getFullYear() + 1;
        return VeiculoValidacao.campoBetween(form.anoFabricacao.value, 1970, anoCorrente, msg);
        
        
        if(campoNotNull(veiculo.getPlaca())) return true; 
        setMsg("Preencha a placa do veículo!");
        return false;        
    }   
    
}
