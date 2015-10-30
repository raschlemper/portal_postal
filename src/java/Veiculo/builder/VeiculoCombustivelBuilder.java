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
            veiculo.setQuantidade(getIntegerParameter(request.getParameter("quantidade")));        
            veiculo.setValorUnitario(getDoubleParameter(request.getParameter("valorUnitario")));        
            veiculo.setData(getDataParameter(request.getParameter("data")));       
            veiculo.setMedia(getIntegerParameter(request.getParameter("media")));       
            veiculo.setValorTotal(getDoubleParameter(request.getParameter("valorTotal")));
            veiculo.setQuilometragemInicial(getIntegerParameter(request.getParameter("quilometragemInicial")));
            veiculo.setQuilometragemFinal(getIntegerParameter(request.getParameter("quilometragemFinal")));
            veiculo.setQuilometragemPercorrida(getIntegerParameter(request.getParameter("quilometragemPercorrida")));
            return veiculo;     
        } catch (Exception ex) {
            Logger.getLogger(VeiculoCombustivelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }

    public VeiculoCombustivel toEntidade(ResultSet result) {
        try {
            return new VeiculoCombustivel(
                result.getInt("veiculo_manutencao.idVeiculoCombustivel"),
                getVeiculo(result),
                result.getString("veiculo_manutencao.tipo"),  
                result.getInt("veiculo_manutencao.quantidade"),
                result.getDouble("veiculo_manutencao.valorUnitario"),
                result.getDate("veiculo_manutencao.data"),
                result.getInt("veiculo_manutencao.media"),
                result.getDouble("veiculo_manutencao.valorTotal"),
                result.getInt("veiculo_manutencao.quilometragemInicial"),
                result.getInt("veiculo_manutencao.quilometragemFinal"),
                result.getInt("veiculo_manutencao.quilometragemPercorrida")  
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
                veiculo.getMedia(),
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
