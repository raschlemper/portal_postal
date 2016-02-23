package Veiculo.builder;

import Veiculo.Entidade.Veiculo;
import Veiculo.Entidade.VeiculoDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public class VeiculoBuilder extends Builder<Veiculo, VeiculoDTO>{

    public Veiculo toEntidade(HttpServletRequest request) {
        try {
            JSONObject jObj = getJsonObject(request);
            Veiculo veiculo = new Veiculo();
            if(jObj.has("id") && !jObj.isNull("id")) { veiculo.setId(jObj.getInt("id")); }
            if(jObj.has("tipo") && !jObj.isNull("tipo")) { veiculo.setTipo(jObj.getJSONObject("tipo").getString("value")); }
            if(jObj.has("marca") && !jObj.isNull("marca")) { veiculo.setMarca(jObj.getJSONObject("marca").getString("name")); }
            if(jObj.has("modelo") && !jObj.isNull("modelo")) { veiculo.setModelo(jObj.getJSONObject("modelo").getString("name")); }
            if(jObj.has("placa") && !jObj.isNull("placa")) { veiculo.setPlaca(jObj.getString("placa")); }
            if(jObj.has("anoFabricacao") && !jObj.isNull("anoFabricacao")) { veiculo.setAnoFabricacao(jObj.getInt("anoFabricacao")); }
            if(jObj.has("anoModelo") && !jObj.isNull("anoModelo")) { veiculo.setAnoModelo(jObj.getInt("anoModelo")); }
            if(jObj.has("chassis") && !jObj.isNull("chassis")) { veiculo.setChassis(jObj.getString("chassis")); }
            if(jObj.has("renavam") && !jObj.isNull("renavam")) { veiculo.setRenavam(jObj.getString("renavam")); }
            if(jObj.has("quilometragem") && !jObj.isNull("quilometragem")) { veiculo.setQuilometragem(jObj.getInt("quilometragem")); }
            if(jObj.has("combustivel") && !jObj.isNull("combustivel")) { veiculo.setCombustivel(jObj.getJSONObject("combustivel").getString("value")); }
            if(jObj.has("status") && !jObj.isNull("status")) { veiculo.setStatus(jObj.getJSONObject("status").getString("value")); }
            if(jObj.has("situacao") && !jObj.isNull("situacao")) { veiculo.setSituacao(jObj.getJSONObject("situacao").getString("value")); }
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
    
    private JSONObject getJsonObject(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return new JSONObject(sb.toString());
    }
    
}
