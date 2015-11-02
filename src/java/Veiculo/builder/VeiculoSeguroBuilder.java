package Veiculo.builder;

import Veiculo.Entidade.VeiculoSeguro;
import Veiculo.Entidade.VeiculoSeguroDTO;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class VeiculoSeguroBuilder extends Builder<VeiculoSeguro, VeiculoSeguroDTO>{

    public VeiculoSeguro toEntidade(HttpServletRequest request) {
        try {
            VeiculoSeguro veiculo = new VeiculoSeguro();
            veiculo.setId(getIntegerParameter(request.getParameter("idVeiculoSeguro")));
            veiculo.setVeiculo(getVeiculo(request));
            veiculo.setNumeroSeguro(getIntegerParameter(request.getParameter("numeroSeguro")));      
            veiculo.setAssegurado(request.getParameter("assegurado"));      
            veiculo.setValorFranquia(getDoubleParameter(request.getParameter("valorFranquia")));
            veiculo.setIndenizacao(request.getParameter("indenizacao"));
            return veiculo;     
        } catch (Exception ex) {
            Logger.getLogger(VeiculoSeguroBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }

    public VeiculoSeguro toEntidade(ResultSet result) {
        try {
            return new VeiculoSeguro(
                result.getInt("veiculo_manutencao.idVeiculoSeguro"),
                getVeiculo(result),
                result.getInt("veiculo_manutencao.numeroSeguro"),  
                result.getString("veiculo_manutencao.assegurado"),
                result.getDouble("veiculo_manutencao.valorFranquia"),
                result.getString("veiculo_manutencao.indenizacao")
            );
        } catch (Exception ex) {
            Logger.getLogger(VeiculoSeguroBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public VeiculoSeguroDTO toDTO(VeiculoSeguro veiculo) {
        try {
            return new VeiculoSeguroDTO(
                veiculo.getId(),
                veiculo.getVeiculo().getId(),
                veiculo.getVeiculo().getPlaca(),
                veiculo.getNumeroSeguro(),
                veiculo.getAssegurado(),
                veiculo.getValorFranquia(),
                veiculo.getIndenizacao()
            );      
        } catch (Exception ex) {
            Logger.getLogger(VeiculoSeguroBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    
}
