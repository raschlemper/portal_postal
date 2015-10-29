package Veiculo.builder;

import Veiculo.Entidade.Veiculo;
import Veiculo.Entidade.VeiculoManutencao;
import Veiculo.Entidade.VeiculoManutencaoDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class VeiculoManutencaoBuilder extends Builder<VeiculoManutencao, VeiculoManutencaoDTO>{

    public VeiculoManutencao toEntidade(HttpServletRequest request) {
        try {
            VeiculoManutencao veiculo = new VeiculoManutencao();
            veiculo.setId(getIntegerParameter(request.getParameter("idVeiculoManutencao")));
            veiculo.setVeiculo(getVeiculo(request));
            veiculo.setTipo(request.getParameter("tipo"));
            veiculo.setQuilometragem(getNumericParameter(request.getParameter("quilometragem")));        
            veiculo.setValor(getDoubleParameter(request.getParameter("valor")));        
            veiculo.setData(getDataParameter(request.getParameter("data")));       
            veiculo.setDataAgendamento(getDataParameter(request.getParameter("dataAgendamento")));       
            veiculo.setDataEntrega(getDataParameter(request.getParameter("dataEntrega")));
            veiculo.setDescricao(request.getParameter("descricao"));
            return veiculo;     
        } catch (Exception ex) {
            Logger.getLogger(VeiculoManutencaoBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }

    public VeiculoManutencao toEntidade(ResultSet result) {
        try {
            return new VeiculoManutencao(
                result.getInt("veiculo_manutencao.idVeiculoManutencao"),
                getVeiculo(result),
                result.getString("veiculo_manutencao.tipo"),  
                result.getInt("veiculo_manutencao.quilometragem"),
                result.getDouble("veiculo_manutencao.valor"),
                result.getDate("veiculo_manutencao.data"),
                result.getDate("veiculo_manutencao.dataAgendamento"),
                result.getDate("veiculo_manutencao.dataEntrega"),
                result.getString("veiculo_manutencao.descricao")  
            );
        } catch (Exception ex) {
            Logger.getLogger(VeiculoManutencaoBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public VeiculoManutencaoDTO toDTO(VeiculoManutencao veiculo) {
        try {
            return new VeiculoManutencaoDTO(
                veiculo.getId(),
                veiculo.getVeiculo().getId(),
                veiculo.getVeiculo().getPlaca(),
                veiculo.getTipo(),
                veiculo.getQuilometragem(),
                veiculo.getValor(),
                getDataDTO(veiculo.getData()),
                getDataDTO(veiculo.getDataAgendamento()),
                getDataDTO(veiculo.getDataEntrega()),
                veiculo.getDescricao()
            );      
        } catch (Exception ex) {
            Logger.getLogger(VeiculoManutencaoBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
    
    private static Veiculo getVeiculo(ResultSet result) throws SQLException {
        return new Veiculo(
            result.getInt("veiculo.idVeiculo"),
            result.getString("veiculo.tipo"),  
            result.getString("veiculo.marca"),  
            result.getString("veiculo.modelo"),  
            result.getString("veiculo.placa"),
            result.getInt("veiculo.anoFabricacao"),
            result.getInt("veiculo.anoModelo"),
            result.getString("veiculo.chassis"),
            result.getString("veiculo.renavam"),
            result.getInt("veiculo.quilometragem"),
            result.getString("veiculo.combustivel"),
            result.getString("veiculo.status"),
            result.getString("veiculo.situacao")
        );
    }
    
}
