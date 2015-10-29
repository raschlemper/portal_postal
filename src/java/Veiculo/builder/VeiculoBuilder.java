package Veiculo.builder;

import Veiculo.Entidade.Veiculo;
import Veiculo.Entidade.VeiculoDTO;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class VeiculoBuilder extends Builder<Veiculo, VeiculoDTO>{

    public Veiculo toEntidade(HttpServletRequest request) {
        try {
            Veiculo veiculo = new Veiculo();
            veiculo.setId(getIntegerParameter(request.getParameter("idVeiculo")));
            veiculo.setTipo(request.getParameter("tipo"));
            veiculo.setMarca(getJsonParameter(request.getParameter("marca"), "name"));
            veiculo.setModelo(getJsonParameter(request.getParameter("modelo"), "name"));
            veiculo.setPlaca(request.getParameter("placa"));
            veiculo.setAnoFabricacao(getIntegerParameter(request.getParameter("anoFabricacao")));
            veiculo.setAnoModelo(getIntegerParameter(request.getParameter("anoModelo")));
            veiculo.setChassis(request.getParameter("chassis"));
            veiculo.setRenavam(request.getParameter("renavam"));
            veiculo.setQuilometragem(getNumericParameter(request.getParameter("quilometragem")));
            veiculo.setCombustivel(request.getParameter("combustivel"));
            veiculo.setStatus(request.getParameter("status"));
            veiculo.setSituacao(request.getParameter("situacao"));
            return veiculo;     
        } catch (Exception ex) {
            Logger.getLogger(VeiculoBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }

    public Veiculo toEntidade(ResultSet result) {
        try {
            return new Veiculo(
                    result.getInt("idVeiculo"),
                    result.getString("tipo"),
                    result.getString("marca"),
                    result.getString("modelo"),
                    result.getString("placa"),
                    result.getInt("anoFabricacao"),
                    result.getInt("anoModelo"),
                    result.getString("chassis"),
                    result.getString("renavam"),
                    result.getInt("quilometragem"),
                    result.getString("combustivel"),
                    result.getString("status"),
                    result.getString("situacao")
            );
        } catch (Exception ex) {
            Logger.getLogger(VeiculoBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public VeiculoDTO toDTO(Veiculo veiculo) {
        try {
            return new VeiculoDTO(
                veiculo.getId(),
                veiculo.getTipo(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getPlaca(),
                veiculo.getAnoFabricacao(),
                veiculo.getAnoModelo(),
                veiculo.getChassis(),
                veiculo.getRenavam(),
                veiculo.getQuilometragem(),
                veiculo.getCombustivel(),
                veiculo.getStatus(),
                veiculo.getSituacao()
            );
        } catch (Exception ex) {
            Logger.getLogger(VeiculoBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
