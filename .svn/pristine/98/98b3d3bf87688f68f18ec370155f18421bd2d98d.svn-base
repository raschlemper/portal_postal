package com.portalpostal.dao.handler;

import com.portalpostal.model.Endereco;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class EnderecoHandler extends GenericHandler implements ResultSetHandler<Endereco> {
        
    public EnderecoHandler() {
        super("endereco");
    }
    
    public EnderecoHandler(String table) {
        super(table);
    }

    public Endereco handle(ResultSet result) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(getInt(result, "idEndereco"));
        endereco.setLogradouro(getString(result, "logradouro"));
        endereco.setCep(getString(result, "cep"));
        endereco.setComplemento(getString(result, "complemento"));    
        endereco.setNumero(getInt(result, "numero"));   
        endereco.setBairro(getString(result, "bairro"));  
        endereco.setCidade(getString(result, "cidade"));     
        endereco.setEstado(getString(result, "estado"));   
        return endereco;
    }
    
}
