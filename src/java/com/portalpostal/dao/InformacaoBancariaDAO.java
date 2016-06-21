package com.portalpostal.dao;

import com.portalpostal.dao.handler.InformacaoBancariaHandler;
import com.portalpostal.model.InformacaoBancaria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformacaoBancariaDAO extends GenericDAO { 
    
    private final InformacaoBancariaHandler informacaoBancariaHandler;

    public InformacaoBancariaDAO(String nameDB) { 
        super(nameDB, InformacaoBancariaDAO.class);
        informacaoBancariaHandler = new InformacaoBancariaHandler();
    } 

    public List<InformacaoBancaria> findAll() throws Exception {
        String sql = "SELECT * FROM informacao_bancaria, colaborador, banco "
                   + "WHERE informacao_bancaria.idColaborador = colaborador.idColaborador "
                   + "AND informacao_bancaria.idBanco = banco.idBanco "
                   + "ORDER BY idInformacaoBancaria";        
        return findAll(sql, null, informacaoBancariaHandler);
    }

    public InformacaoBancaria find(Integer idInformacaoBancaria) throws Exception {
        String sql = "SELECT * FROM informacao_bancaria, colaborador, banco "
                   + "WHERE informacao_bancaria.idColaborador = colaborador.idColaborador "
                   + "AND informacao_bancaria.idBanco = banco.idBanco "
                   + "AND informacao_bancaria.idInformacaoBancaria = :idInformacaoBancaria";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idInformacaoBancaria", idInformacaoBancaria);
        return (InformacaoBancaria) find(sql, params, informacaoBancariaHandler);
    }

    public InformacaoBancaria save(InformacaoBancaria informacaoBancaria) throws Exception {  
        String sql = "INSERT INTO informacao_bancaria (idColaborador, idBanco, tipoConta, agencia, agencia_dv, contaCorrente, contaCorrente_dv) "
                   + "VALUES(:idColaborador, :idBanco, :tipoConta, :agencia, :agencia_dv, :contaCorrente, :contaCorrente_dv)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", informacaoBancaria.getColaborador().getIdColaborador());
        params.put("idBanco", informacaoBancaria.getBanco().getIdBanco());
        params.put("tipoConta", informacaoBancaria.getTipoConta().ordinal());      
        params.put("agencia", informacaoBancaria.getAgencia());      
        params.put("agencia_dv", informacaoBancaria.getAgenciaDv());      
        params.put("contaCorrente", informacaoBancaria.getContaCorrente());      
        params.put("contaCorrente_dv", informacaoBancaria.getContaCorrenteDv());  
        Integer idInformacaoBancaria = save(sql, params, informacaoBancariaHandler);
        return find(idInformacaoBancaria);
    }

    public InformacaoBancaria update(InformacaoBancaria informacaoBancaria) throws Exception {
        String sql = "UPDATE informacao_bancaria "
                   + "SET idColaborador = :idColaborador, idBanco = :idBanco, tipoConta = :tipoConta, agencia = :agencia, agencia_dv = :agencia_dv, "
                   + "contaCorrente = :contaCorrente, contaCorrente_dv = :contaCorrente_dv "
                   + "WHERE idInformacaoBancaria = :idInformacaoBancaria ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idInformacaoBancaria", informacaoBancaria.getIdInformacaoBancaria());
        params.put("idColaborador", informacaoBancaria.getColaborador().getIdColaborador());
        params.put("idBanco", informacaoBancaria.getBanco().getIdBanco());
        params.put("tipoConta", informacaoBancaria.getTipoConta().ordinal());      
        params.put("agencia", informacaoBancaria.getAgencia());      
        params.put("agencia_dv", informacaoBancaria.getAgenciaDv());      
        params.put("contaCorrente", informacaoBancaria.getContaCorrente());      
        params.put("contaCorrente_dv", informacaoBancaria.getContaCorrenteDv());   
        update(sql, params, informacaoBancariaHandler);
        return informacaoBancaria;  
    }

    public InformacaoBancaria remove(Integer idInformacaoBancaria) throws Exception { 
        String sql = "DELETE FROM informacao_bancaria WHERE idInformacaoBancaria = :idInformacaoBancaria ";
        InformacaoBancaria informacaoBancaria = find(idInformacaoBancaria);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idInformacaoBancaria", idInformacaoBancaria);
        remove(sql, params, informacaoBancariaHandler);
        return informacaoBancaria;
    }
    
}
