package com.portalpostal.dao;

import com.portalpostal.dao.handler.TipoContaHandler;
import com.portalpostal.model.TipoConta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoContaDAO extends GenericDAO { 
    
    private TipoContaHandler tipoContaHandler;

    public TipoContaDAO(String nameDB) { 
        super(nameDB, TipoContaDAO.class);
        tipoContaHandler = new TipoContaHandler();
    } 

    public List<TipoConta> findAll() throws Exception {
        String sql = "SELECT * FROM tipo_conta ORDER BY tipo_conta.idTipoConta";        
        return findAll(sql, null, tipoContaHandler);
    }

    public TipoConta find(Integer idTipoConta) throws Exception {
        String sql = "SELECT * FROM tipo_conta WHERE tipo_conta.idTipoConta = :idTipoConta";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoConta", idTipoConta);
        return (TipoConta) find(sql, params, tipoContaHandler);
    }

    public TipoConta save(TipoConta tipoConta) throws Exception {  
        String sql = "INSERT INTO tipo_conta (categoria, descricao) "
                   + "VALUES(:categoria, :descricao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoria", tipoConta.getCategoria().ordinal());
        params.put("descricao", tipoConta.getDescricao());  
        Integer idTipoConta = save(sql, params, tipoContaHandler);
        return find(idTipoConta);
    }

    public TipoConta update(TipoConta tipoConta) throws Exception {
        String sql = "UPDATE tipo_conta "
                   + "SET categoria = :categoria, descricao = :descricao "
                   + "WHERE idTipoConta = :idTipoConta ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoConta", tipoConta.getIdTipoConta());
        params.put("categoria", tipoConta.getCategoria().ordinal());
        params.put("descricao", tipoConta.getDescricao());  
        update(sql, params, tipoContaHandler);
        return tipoConta;  
    }

    public TipoConta remove(Integer idTipoConta) throws Exception { 
        String sql = "DELETE FROM tipo_conta WHERE idTipoConta = :idTipoConta ";
        TipoConta tipoConta = find(idTipoConta);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idTipoConta", idTipoConta);
        remove(sql, params, tipoContaHandler);
        return tipoConta;
    }
    
}
