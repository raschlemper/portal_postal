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
            veiculo.setNumeroMulta(getIntegerParameter(request.getParameter("numeroMulta")));      
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
                result.getInt("veiculo_manutencao.idVeiculoMulta"),
                getVeiculo(result),
                result.getInt("veiculo_manutencao.numeroMulta"),  
                result.getDate("veiculo_manutencao.data"),
                result.getDouble("veiculo_manutencao.valor"),
                result.getString("veiculo_manutencao.local"),
                result.getString("veiculo_manutencao.descricao")
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
                veiculo.getData(),
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
