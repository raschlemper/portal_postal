package com.portalpostal.dao;

import com.portalpostal.dao.handler.EnderecoHandler;
import com.portalpostal.model.Endereco;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnderecoDAO extends GenericDAO { 
    
    private final EnderecoHandler enderecoHandler;

    public EnderecoDAO(String nameDB) { 
        super(nameDB, EnderecoDAO.class);
        enderecoHandler = new EnderecoHandler();
    } 

    public List<Endereco> findAll() throws Exception {
        String sql = "SELECT * FROM endereco ORDER BY idEndereco";        
        return findAll(sql, null, enderecoHandler);
    }

    public Endereco find(Integer idEndereco) throws Exception {
        String sql = "SELECT * FROM endereco WHERE idEndereco = :idEndereco";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idEndereco", idEndereco);
        return (Endereco) find(sql, params, enderecoHandler);
    }

    public List<Endereco> findByColaborador(Integer idColaborador) throws Exception {
        String sql = "SELECT endereco.* FROM endereco, colaborador_endereco "
                   + "WHERE endereco.idEndereco = colaborador_endereco.idEndereco "
                   + "AND colaborador_endereco.idColaborador = :idColaborador";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", idColaborador);
        return findAll(sql, params, enderecoHandler);
    }

    public List<Endereco> findByFornecedor(Integer idFornecedor) throws Exception {
        String sql = "SELECT endereco.* FROM endereco, fornecedor_endereco "
                   + "WHERE endereco.idEndereco = fornecedor_endereco.idEndereco "
                   + "AND fornecedor_endereco.idFornecedor = :idFornecedor";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFornecedor", idFornecedor);
        return findAll(sql, params, enderecoHandler);
    }

    public Endereco findVinculoColaborador(Integer idEndereco) throws Exception {
        String sql = "SELECT endereco.* FROM endereco, colaborador_endereco "
                   + "WHERE endereco.idEndereco = colaborador_endereco.idEndereco "
                   + "AND endereco.idEndereco = :idEndereco";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idEndereco", idEndereco);
        return (Endereco) find(sql, params, enderecoHandler);
    }

    public Endereco findVinculoFornecedor(Integer idEndereco) throws Exception {
        String sql = "SELECT endereco.* FROM endereco, fornecedor_endereco "
                   + "WHERE endereco.idEndereco = fornecedor_endereco.idEndereco "
                   + "AND endereco.idEndereco = :idEndereco";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idEndereco", idEndereco);
        return (Endereco) find(sql, params, enderecoHandler);
    }

    public Endereco save(Endereco endereco) throws Exception {  
        String sql = "INSERT INTO endereco (logradouro, cep, complemento, numero, bairro, cidade, estado) "
                   + "VALUES(:logradouro, :cep, :complemento, :numero, :bairro, :cidade, :estado)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("logradouro", endereco.getLogradouro());
        params.put("cep", endereco.getCep());
        params.put("complemento", endereco.getComplemento());      
        params.put("numero", endereco.getNumero());      
        params.put("bairro", endereco.getBairro());      
        params.put("cidade", endereco.getCidade());      
        params.put("estado", endereco.getEstado());    
        Integer idEndereco = save(sql, params, enderecoHandler);
        return find(idEndereco);
    }

    public Endereco update(Endereco endereco) throws Exception {
        String sql = "UPDATE endereco "
                   + "SET logradouro = :logradouro, cep = :cep, complemento = :complemento, numero = :numero, bairro = :bairro, "
                   + "cidade = :cidade, estado = :estado "
                   + "WHERE idEndereco = :idEndereco ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idEndereco", endereco.getIdEndereco());
        params.put("logradouro", endereco.getLogradouro());
        params.put("cep", endereco.getCep());
        params.put("complemento", endereco.getComplemento());      
        params.put("numero", endereco.getNumero());      
        params.put("bairro", endereco.getBairro());      
        params.put("cidade", endereco.getCidade());      
        params.put("estado", endereco.getEstado());    
        update(sql, params, enderecoHandler);
        return endereco;  
    }

    public Endereco remove(Integer idEndereco) throws Exception { 
        String sql = "DELETE FROM endereco WHERE idEndereco = :idEndereco ";
        Endereco endereco = find(idEndereco);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idEndereco", idEndereco);
        remove(sql, params, enderecoHandler);
        return endereco;
    }

    public Integer saveColaborador(Integer idColaborador, Integer idEndereco) throws Exception {  
        String sql = "INSERT INTO colaborador_endereco (idColaborador, idEndereco) "
                   + "VALUES(:idColaborador, :idEndereco)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", idColaborador);
        params.put("idEndereco", idEndereco);
        return save(sql, params, enderecoHandler);
    }

    public Integer saveFornecedor(Integer idFornecedor, Integer idEndereco) throws Exception {  
        String sql = "INSERT INTO fornecedor_endereco (idFornecedor, idEndereco) "
                   + "VALUES(:idFornecedor, :idEndereco)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFornecedor", idFornecedor);
        params.put("idEndereco", idEndereco);
        return save(sql, params, enderecoHandler);
    }

    public void removeColaborador(Integer idFornecedor, Integer idEndereco) throws Exception { 
        String sql = "DELETE FROM colaborador_endereco WHERE idEndereco = :idEndereco AND idFornecedor = :idFornecedor ";
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idEndereco", idEndereco);    
        params.put("idFornecedor", idFornecedor);
        remove(sql, params, enderecoHandler);
    }

    public void removeFornecedor(Integer idFornecedor, Integer idEndereco) throws Exception { 
        String sql = "DELETE FROM fornecedor_endereco WHERE idEndereco = :idEndereco AND idFornecedor = :idFornecedor ";
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idEndereco", idEndereco);    
        params.put("idFornecedor", idFornecedor);
        remove(sql, params, enderecoHandler);
    }
    
}
