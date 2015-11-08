package Veiculo.builder;

import Veiculo.Entidade.VeiculoMulta;
import Veiculo.Entidade.VeiculoMultaDTO;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class VeiculoMultaBuilder extends Builder<VeiculoMulta, VeiculoMultaDTO>{

    public VeiculoMulta toEntidade(HttpServletRequest request) {
        try {
            VeiculoMulta veiculo = new VeiculoMulta();
            veiculo.setId(getIntegerParameter(request.getParameter("idVeiculoMulta")));
            veiculo.setVeiculo(getVeiculo(request));
            veiculo.setNumeroMulta(getNumericParameter(request.getParameter("numeroMulta")));      
            veiculo.setData(getDataParameter(request.getParameter("data")));      
            veiculo.setValor(getDoubleParameter(request.getParameter("valor")));
            veiculo.setLocal(request.getParameter("local"));
            veiculo.setDescricao(request.getParameter("descricao"));
            return veiculo;     
        } catch (Exception ex) {
            Logger.getLogger(VeiculoMultaBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }

    public VeiculoMulta toEntidade(ResultSet result) {
        try {
            return new VeiculoMulta(
                result.getInt("veiculo_multa.idVeiculoMulta"),
                getVeiculo(result),
                result.getInt("veiculo_multa.numeroMulta"),  
                result.getDate("veiculo_multa.data"),
                result.getDouble("veiculo_multa.valor"),
                result.getString("veiculo_multa.local"),
                result.getString("veiculo_multa.descricao")
            );
        } catch (Exception ex) {
            Logger.getLogger(VeiculoMultaBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public VeiculoMultaDTO toDTO(VeiculoMulta veiculo) {
        try {
            return new VeiculoMultaDTO(
                veiculo.getId(),
                veiculo.getVeiculo().getId(),
                veiculo.getVeiculo().getPlaca(),
                veiculo.getNumeroMulta(),
                getDataDTO(veiculo.getData()),
                veiculo.getValor(),
                veiculo.getLocal(),
                veiculo.getDescricao()
            );      
        } catch (Exception ex) {
            Logger.getLogger(VeiculoMultaBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    
}
