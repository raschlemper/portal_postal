package Veiculo.builder;

import Veiculo.Entidade.VeiculoSeguro;
import Veiculo.EntidadDTO;
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
            veiculo.setNumeroMulta(getIntegerParameter(request.getParameter("numeroMulta")));      
            veiculo.setData(getDataParameter(request.getParameter("data")));      
            veiculo.setValor(getDoubleParameter(request.getParameter("valor")));
            veiculo.setLocal(request.getParameter("local"));
            veiculo.setDescricao(request.getParameter("descricao"));
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
                result.getInt("veiculo_manutencao.numeroMulta"),  
                result.getDate("veiculo_manutencao.data"),
                result.getDouble("veiculo_manutencao.valor"),
                result.getString("veiculo_manutencao.local"),
                result.getString("veiculo_manutencao.descricao")
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
                veiculo.getNumeroMulta(),
                veiculo.getData(),
                veiculo.getValor(),
                veiculo.getLocal(),
                veiculo.getDescricao()
            );      
        } catch (Exception ex) {
            Logger.getLogger(VeiculoSeguroBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    
}
