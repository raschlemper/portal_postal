package Veiculo.builder;

import Veiculo.Entidade.VeiculoSinistro;
import Veiculo.Entidade.VeiculoSinistroDTO;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class VeiculoSinistroBuilder extends Builder<VeiculoSinistro, VeiculoSinistroDTO>{

    public VeiculoSinistro toEntidade(HttpServletRequest request) {
        try {
            VeiculoSinistro veiculo = new VeiculoSinistro();
            veiculo.setId(getIntegerParameter(request.getParameter("idVeiculoSinistro")));
            veiculo.setVeiculo(getVeiculo(request));
            veiculo.setTipo(request.getParameter("tipo"));      
            veiculo.setBoletimOcorrencia(getIntegerParameter(request.getParameter("boletimOcorrencia")));      
            veiculo.setData(getDataParameter(request.getParameter("data")));      
            veiculo.setLocal(request.getParameter("local"));
            veiculo.setResponsavel(request.getParameter("responsavel"));
            veiculo.setDescricao(request.getParameter("descricao"));
            return veiculo;     
        } catch (Exception ex) {
            Logger.getLogger(VeiculoSinistroBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }

    public VeiculoSinistro toEntidade(ResultSet result) {
        try {
            return new VeiculoSinistro(
                result.getInt("veiculo_manutencao.idVeiculoSinistro"),
                getVeiculo(result),
                result.getString("veiculo_manutencao.tipo"),  
                result.getInt("veiculo_manutencao.boletimOcorrencia"),  
                result.getDate("veiculo_manutencao.data"),
                result.getString("veiculo_manutencao.local"),
                result.getString("veiculo_manutencao.responsavel"),
                result.getString("veiculo_manutencao.descricao")
            );
        } catch (Exception ex) {
            Logger.getLogger(VeiculoSinistroBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public VeiculoSinistroDTO toDTO(VeiculoSinistro veiculo) {
        try {
            return new VeiculoSinistroDTO(
                veiculo.getId(),
                veiculo.getVeiculo().getId(),
                veiculo.getVeiculo().getPlaca(),
                veiculo.getTipo(),
                veiculo.getBoletimOcorrencia(),
                veiculo.getData(),
                veiculo.getLocal(),
                veiculo.getResponsavel(),
                veiculo.getDescricao()
            );      
        } catch (Exception ex) {
            Logger.getLogger(VeiculoSinistroBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    
}
