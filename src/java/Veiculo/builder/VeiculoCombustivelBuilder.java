package Veiculo.builder;

import Veiculo.Entidade.VeiculoCombustivel;
import Veiculo.Entidade.VeiculoCombustivelDTO;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class VeiculoCombustivelBuilder extends Builder<VeiculoCombustivel, VeiculoCombustivelDTO>{

    public VeiculoCombustivel toEntidade(HttpServletRequest request) {
        try {
            VeiculoCombustivel veiculo = new VeiculoCombustivel();
            veiculo.setId(getIntegerParameter(request.getParameter("idVeiculoCombustivel")));
            veiculo.setVeiculo(getVeiculo(request));
            veiculo.setTipo(request.getParameter("tipo"));
            veiculo.setQuantidade(getNumericParameter(request.getParameter("quantidade")));        
            veiculo.setData(getDataParameter(request.getParameter("data")));       
            veiculo.setValorTotal(getDoubleParameter(request.getParameter("valorTotal")));
            veiculo.setQuilometragemInicial(getNumericParameter(request.getParameter("quilometragemInicial")));
            veiculo.setQuilometragemFinal(getNumericParameter(request.getParameter("quilometragemFinal")));
            veiculo.setQuilometragemPercorrida(getNumericParameter(request.getParameter("quilometragemPercorrida")));
            veiculo.setValorUnitario(getDoubleParameter(request.getParameter("valorUnitario")));  
            return veiculo;     
        } catch (Exception ex) {
            Logger.getLogger(VeiculoCombustivelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }

    public VeiculoCombustivel toEntidade(ResultSet result) {
        try {
            return new VeiculoCombustivel(
                result.getInt("veiculo_combustivel.idVeiculoCombustivel"),
                getVeiculo(result),
                result.getString("veiculo_combustivel.tipo"),  
                result.getInt("veiculo_combustivel.quantidade"),
                result.getDouble("veiculo_combustivel.valorUnitario"),
                result.getDate("veiculo_combustivel.data"),
                result.getDouble("veiculo_combustivel.valorTotal"),
                result.getInt("veiculo_combustivel.quilometragemInicial"),
                result.getInt("veiculo_combustivel.quilometragemFinal"),
                result.getInt("veiculo_combustivel.quilometragemPercorrida")  
            );
        } catch (Exception ex) {
            Logger.getLogger(VeiculoCombustivelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public VeiculoCombustivelDTO toDTO(VeiculoCombustivel veiculo) {
        try {
            return new VeiculoCombustivelDTO(
                veiculo.getId(),
                veiculo.getVeiculo().getId(),
                veiculo.getVeiculo().getPlaca(),
                veiculo.getTipo(),
                veiculo.getQuantidade(),
                veiculo.getValorUnitario(),
                getDataDTO(veiculo.getData()),
                veiculo.getValorTotal(),
                veiculo.getQuilometragemInicial(),
                veiculo.getQuilometragemFinal(),
                veiculo.getQuilometragemPercorrida()
            );      
        } catch (Exception ex) {
            Logger.getLogger(VeiculoCombustivelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    
}
