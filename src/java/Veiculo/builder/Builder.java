package Veiculo.builder;

import Veiculo.Entidade.Veiculo;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public abstract class Builder<E,T> {    
    
    protected Veiculo getVeiculo(HttpServletRequest request) {
        Veiculo veiculo = new Veiculo(
            getIntegerParameter(request.getParameter("veiculo"))
        );
        return veiculo;
    }
          
    protected String getJsonParameter(String parameter, String campo) {
        if(parameter == null) return null;
        return new JSONObject(parameter).getString(campo);
    }
          
    protected Integer getIntegerParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return Integer.parseInt(parameter);
    }
          
    protected Integer getNumericParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return getIntegerParameter(parameter.replace(".", "")); 
    }    
          
    protected Double getDoubleParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return Double.parseDouble(parameter.replaceAll("\\.","").replace(",","."));
    }
          
    protected Date getDataParameter(String parameter) throws Exception {
        if(parameter == null || parameter.equals("")) return null;
        return Util.FormatarData.getDateFromString(parameter, "dd/MM/yyyy");
    }
    
    protected String getDataDTO(Date data) throws Exception {
        if(data == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
        
    public abstract E toEntidade(HttpServletRequest request); 
    public abstract E toEntidade(ResultSet result); 
    public abstract T toDTO(E object); 
}
