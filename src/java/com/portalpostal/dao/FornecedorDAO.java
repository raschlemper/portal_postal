package com.portalpostal.dao;

import com.portalpostal.dao.handler.FornecedorHandler;
import com.portalpostal.model.Fornecedor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FornecedorDAO extends GenericDAO { 
    
    private final FornecedorHandler fornecedorHandler;

    public FornecedorDAO(String nameDB) { 
        super(nameDB, FornecedorDAO.class);
        fornecedorHandler = new FornecedorHandler();
    } 

    public List<Fornecedor> findAll() throws Exception {
        String sql = "SELECT * FROM fornecedor ORDER BY fornecedor.idFornecedor";        
        return findAll(sql, null, fornecedorHandler);
    }

    public Fornecedor find(Integer idFornecedor) throws Exception {
        String sql = "SELECT * FROM fornecedor WHERE fornecedor.idFornecedor = :idFornecedor";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFornecedor", idFornecedor);
        return (Fornecedor) find(sql, params, fornecedorHandler);
    }

    public Fornecedor findByCpf(String cpf) throws Exception {
        String sql = "SELECT * FROM fornecedor WHERE fornecedor.cpf = :cpf";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cpf", cpf);
        return (Fornecedor) find(sql, params, fornecedorHandler);
    }

    public Fornecedor findByCnpj(String cnpj) throws Exception {
        String sql = "SELECT * FROM fornecedor WHERE fornecedor.cnpj = :cnpj";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cnpj", cnpj);
        return (Fornecedor) find(sql, params, fornecedorHandler);
    }

    public Fornecedor save(Fornecedor fornecedor) throws Exception {  
        String sql = "INSERT INTO fornecedor (nomeFantasia, razaoSocial, status, tipoPessoa, cpf, rg, cnpj, sexo, "
                   + "dataNascimento, inscricaoEstadual, dataFundacao, capitalSocial, idTipoCategoria, telefone, "
                   + "celular, email, observacao) "
                   + "VALUES(:nomeFantasia, :razaoSocial, :status, :tipoPessoa, :cpf, :rg, :cnpj, :sexo, :dataNascimento, "
                   + ":inscricaoEstadual, :dataFundacao, :capitalSocial, :idTipoCategoria, :telefone, :celular, :email, "
                   + ":observacao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nomeFantasia", fornecedor.getNomeFantasia());
        params.put("razaoSocial", fornecedor.getRazaoSocial());
        params.put("status", (fornecedor.getStatus() == null ? null : fornecedor.getStatus().ordinal()));
        params.put("tipoPessoa", (fornecedor.getTipoPessoa() == null ? null : fornecedor.getTipoPessoa().ordinal()));
        params.put("cpf", fornecedor.getCpf());      
        params.put("rg", fornecedor.getRg());       
        params.put("cnpj", fornecedor.getCnpj());       
        params.put("sexo", (fornecedor.getSexo() == null ? null : fornecedor.getSexo().ordinal()));      
        params.put("dataNascimento", fornecedor.getDataNascimento());        
        params.put("inscricaoEstadual", fornecedor.getInscricaoEstadual());          
        params.put("dataFundacao", fornecedor.getDataFundacao());          
        params.put("capitalSocial", fornecedor.getCapitalSocial());          
        params.put("idTipoCategoria", (fornecedor.getCategoria() == null ? null : fornecedor.getCategoria().getIdTipoCategoria()));
        params.put("telefone", fornecedor.getTelefone());        
        params.put("celular", fornecedor.getCelular());          
        params.put("email", fornecedor.getEmail());              
        params.put("observacao", fornecedor.getObservacao()); 
        Integer idFornecedor = save(sql, params, fornecedorHandler);
        return find(idFornecedor);
    }

    public Fornecedor update(Fornecedor fornecedor) throws Exception {
        String sql = "UPDATE fornecedor "
                   + "SET nomeFantasia = :nomeFantasia, razaoSocial = :razaoSocial, status = :status, tipoPessoa = :tipoPessoa, "
                   + "cpf = :cpf, rg = :rg, cnpj = :cnpj, sexo = :sexo, dataNascimento = :dataNascimento, "
                   + "inscricaoEstadual = :inscricaoEstadual, dataFundacao = :dataFundacao, capitalSocial = :capitalSocial, "
                   + "idTipoCategoria = :idTipoCategoria, telefone = :telefone, celular = :celular, email = :email, "
                   + "observacao = :observacao "
                   + "WHERE idFornecedor = :idFornecedor ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFornecedor", fornecedor.getIdFornecedor());
        params.put("nomeFantasia", fornecedor.getNomeFantasia());
        params.put("razaoSocial", fornecedor.getRazaoSocial());
        params.put("status", (fornecedor.getStatus() == null ? null : fornecedor.getStatus().ordinal()));
        params.put("tipoPessoa", (fornecedor.getTipoPessoa() == null ? null : fornecedor.getTipoPessoa().ordinal()));
        params.put("cpf", fornecedor.getCpf());      
        params.put("rg", fornecedor.getRg());       
        params.put("cnpj", fornecedor.getCnpj());       
        params.put("sexo", (fornecedor.getSexo() == null ? null : fornecedor.getSexo().ordinal()));      
        params.put("dataNascimento", fornecedor.getDataNascimento());        
        params.put("inscricaoEstadual", fornecedor.getInscricaoEstadual());          
        params.put("dataFundacao", fornecedor.getDataFundacao());          
        params.put("capitalSocial", fornecedor.getCapitalSocial());          
        params.put("idTipoCategoria", (fornecedor.getCategoria() == null ? null : fornecedor.getCategoria().getIdTipoCategoria()));
        params.put("telefone", fornecedor.getTelefone());        
        params.put("celular", fornecedor.getCelular());          
        params.put("email", fornecedor.getEmail());              
        params.put("observacao", fornecedor.getObservacao());    
        update(sql, params, fornecedorHandler);
        return fornecedor;  
    }

    public Fornecedor remove(Integer idFornecedor) throws Exception { 
        String sql = "DELETE FROM fornecedor WHERE idFornecedor = :idFornecedor ";
        Fornecedor fornecedor = find(idFornecedor);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idFornecedor", idFornecedor);
        remove(sql, params, fornecedorHandler);
        return fornecedor;
    }
    
}
