package com.portalpostal.dao;

import com.portalpostal.dao.handler.ColaboradorHandler;
import com.portalpostal.model.Colaborador;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColaboradorDAO extends GenericDAO { 
    
    private final ColaboradorHandler colaboradorHandler;

    public ColaboradorDAO(String nameDB) { 
        super(nameDB, ColaboradorDAO.class);
        colaboradorHandler = new ColaboradorHandler();
    } 

    public List<Colaborador> findAll() throws Exception {
        String sql = "SELECT * FROM colaborador "
                   + "LEFT OUTER JOIN informacao_profissional ON(colaborador.idColaborador = informacao_profissional.idColaborador) "
                   + "LEFT OUTER JOIN informacao_bancaria ON(colaborador.idColaborador = informacao_bancaria.idColaborador) "
                   + "ORDER BY colaborador.idColaborador";        
        return findAll(sql, null, colaboradorHandler);
    }

    public Colaborador find(Integer idColaborador) throws Exception {
        String sql = "SELECT * FROM colaborador "
                   + "LEFT OUTER JOIN informacao_profissional ON(colaborador.idColaborador = informacao_profissional.idColaborador) "
                   + "LEFT OUTER JOIN informacao_bancaria ON(colaborador.idColaborador = informacao_bancaria.idColaborador) "
                   + "WHERE colaborador.idColaborador = :idColaborador";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", idColaborador);
        return (Colaborador) find(sql, params, colaboradorHandler);
    }

    public Colaborador findByCpf(String cpf) throws Exception {
        String sql = "SELECT * FROM colaborador WHERE colaborador.cpf = :cpf";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cpf", cpf);
        return (Colaborador) find(sql, params, colaboradorHandler);
    }

    public Colaborador save(Colaborador colaborador) throws Exception {  
        String sql = "INSERT INTO colaborador (nome, status, cpf, rg, sexo, dataNascimento, telefone, "
                   + "celular, email, conjuge, estadoCivil, naturalidade, nacionalidade, quantidadeDependente, "
                   + "nomePai, nomeMae, observacao) "
                   + "VALUES(:nome, :status, :cpf, :rg, :sexo, :dataNascimento, :telefone, "
                   + ":celular, :email, :conjuge, :estadoCivil, :naturalidade, :nacionalidade, :quantidadeDependente, "
                   + ":nomePai, :nomeMae, :observacao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nome", colaborador.getNome());
        params.put("status", (colaborador.getStatus() == null ? null : colaborador.getStatus().ordinal()));
        params.put("cpf", colaborador.getCpf());      
        params.put("rg", colaborador.getRg());      
        params.put("sexo", (colaborador.getSexo() == null ? null : colaborador.getSexo().ordinal()));      
        params.put("dataNascimento", colaborador.getDataNascimento());  
        params.put("telefone", colaborador.getTelefone());        
        params.put("celular", colaborador.getCelular());          
        params.put("email", colaborador.getEmail());          
        params.put("conjuge", colaborador.getConjuge());          
        params.put("estadoCivil", (colaborador.getEstadoCivil() == null ? null : colaborador.getEstadoCivil().ordinal()));  
        params.put("naturalidade", colaborador.getNaturalidade());          
        params.put("nacionalidade", colaborador.getNacionalidade());          
        params.put("quantidadeDependente", colaborador.getQuantidadeDependente());          
        params.put("nomePai", colaborador.getNomePai());          
        params.put("nomeMae", colaborador.getNomeMae());           
        params.put("observacao", colaborador.getObservacao()); 
        Integer idColaborador = save(sql, params, colaboradorHandler);
        return find(idColaborador);
    }

    public Colaborador update(Colaborador colaborador) throws Exception {
        String sql = "UPDATE colaborador "
                   + "SET nome = :nome, status = :status, cpf = :cpf, rg = :rg, sexo = :sexo, dataNascimento = :dataNascimento, "
                   + "telefone = :telefone, celular = :celular, email = :email, conjuge = :conjuge, estadoCivil = :estadoCivil, "
                   + "naturalidade = :naturalidade, nacionalidade = :nacionalidade, quantidadeDependente = :quantidadeDependente, "
                   + "nomePai = :nomePai, nomeMae = :nomeMae, observacao = :observacao "
                   + "WHERE idColaborador = :idColaborador ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", colaborador.getIdColaborador());
        params.put("nome", colaborador.getNome());
        params.put("status", (colaborador.getStatus() == null ? null : colaborador.getStatus().ordinal()));
        params.put("cpf", colaborador.getCpf());      
        params.put("rg", colaborador.getRg());      
        params.put("sexo", (colaborador.getSexo() == null ? null : colaborador.getSexo().ordinal()));      
        params.put("dataNascimento", colaborador.getDataNascimento());   
        params.put("telefone", colaborador.getTelefone());         
        params.put("celular", colaborador.getCelular());          
        params.put("email", colaborador.getEmail());          
        params.put("conjuge", colaborador.getConjuge());          
        params.put("estadoCivil", (colaborador.getEstadoCivil() == null ? null : colaborador.getEstadoCivil().ordinal()));         
        params.put("naturalidade", colaborador.getNaturalidade());          
        params.put("nacionalidade", colaborador.getNacionalidade());          
        params.put("quantidadeDependente", colaborador.getQuantidadeDependente());          
        params.put("nomePai", colaborador.getNomePai());          
        params.put("nomeMae", colaborador.getNomeMae());           
        params.put("observacao", colaborador.getObservacao());   
        update(sql, params, colaboradorHandler);
        return colaborador;  
    }

    public Colaborador remove(Integer idColaborador) throws Exception { 
        String sql = "DELETE FROM colaborador WHERE idColaborador = :idColaborador ";
        Colaborador colaborador = find(idColaborador);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idColaborador", idColaborador);
        remove(sql, params, colaboradorHandler);
        return colaborador;
    }
    
}
