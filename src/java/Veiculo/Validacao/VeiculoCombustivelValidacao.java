package Veiculo.Validacao;

import Veiculo.Entidade.VeiculoCombustivel;

/**
 *
 * @author rafael
 */
public class VeiculoCombustivelValidacao extends Validacao<VeiculoCombustivel> {

    public boolean validar(VeiculoCombustivel veiculo) {          
        if(!validarQuantidade(veiculo)) return false;   
        if(!validarValorTotal(veiculo)) return false;   
        if(!validarValorUnitario(veiculo)) return false;   
        if(!validarData(veiculo)) return false;   
        if(!validarQuilometragemInicial(veiculo)) return false;   
        if(!validarQuilometragemFinal(veiculo)) return false;   
        return true;        
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

    public boolean validarQuilometragemInicial(VeiculoCombustivel veiculo) {        
        if(campoNotNull(veiculo.getQuilometragemFinal())) return true; 
        setMsg("Preencha a quilometragem inicial do veículo!");  
        return false;        
    }     

    public boolean validarQuilometragemFinal(VeiculoCombustivel veiculo) {        
        if(!campoNotNull(veiculo.getQuilometragemFinal())) { 
            setMsg("Preencha a quilometragem final do veículo!"); 
            return false;
        }
        if(campoMoreEqualThen(veiculo.getQuilometragemFinal(), veiculo.getQuilometragemInicial())) {
            setMsg("A quilometragem não pode ser inferior ou igual a última quilometragem inserida " +
                "para este veículo (" + toNumberFormat(veiculo.getQuilometragemInicial()) + ")!"); 
            return false;            
        }
        return true;        
    }  
    
}
