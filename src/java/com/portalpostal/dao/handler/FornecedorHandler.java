package com.portalpostal.dao.handler;

import com.portalpostal.model.Fornecedor;
import com.portalpostal.model.Endereco;
import com.portalpostal.model.TipoCategoria;
import com.portalpostal.model.dd.TipoPessoa;
import com.portalpostal.model.dd.TipoSexo;
import com.portalpostal.model.dd.TipoStatusFornecedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class FornecedorHandler extends GenericHandler implements ResultSetHandler<Fornecedor> {
        
    public FornecedorHandler() {
        super("fornecedor");
    }
    
    public FornecedorHandler(String table) {
        super(table);
    }

    public Fornecedor handle(ResultSet result) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(getInt(result, "idFornecedor"));
        fornecedor.setNomeFantasia(getString(result, "nomeFantasia"));
        fornecedor.setRazaoSocial(getString(result, "razaoSocial"));
        fornecedor.setStatus(TipoStatusFornecedor.values()[getInt(result, "status")]);
        fornecedor.setTipoPessoa(TipoPessoa.values()[getInt(result, "tipoPessoa")]);
        fornecedor.setCpf(getString(result, "cpf"));
        fornecedor.setRg(getString(result, "rg"));   
        fornecedor.setCnpj(getString(result, "cnpj"));    
        fornecedor.setSexo(TipoSexo.values()[getInt(result, "sexo")]); 
        fornecedor.setDataNascimento(getDate(result, "dataNascimento")); 
        fornecedor.setInscricaoEstadual(getString(result, "inscricaoEstadual"));   
        fornecedor.setDataFundacao(getDate(result, "dataFundacao"));   
        fornecedor.setCapitalSocial(getDouble(result, "capitalSocial")); 
        fornecedor.setCategoria(getTipoCategoria(result));  
        fornecedor.setTelefone(getString(result, "telefone")); 
        fornecedor.setCelular(getString(result, "celular"));
        fornecedor.setEmail(getString(result, "email"));  
        fornecedor.setObservacao(getString(result, "observacao"));
        fornecedor.setEndereco(getEndereco(result));
        return fornecedor;
    }
    
    private TipoCategoria getTipoCategoria(ResultSet result) throws SQLException {
        if(!existColumn(result, "tipo_categoria.idTipoCategoria")) return null;
        return new TipoCategoriaHandler().handle(result); 
    }
    
    private Endereco getEndereco(ResultSet result) throws SQLException {
        if(!existColumn(result, "endereco.idEndereco")) return null;
        if(!existFKValue(result, "endereco.idEndereco")) return null;
        return new EnderecoHandler().handle(result); 
    }
    
}
