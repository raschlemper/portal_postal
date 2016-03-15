package com.portalpostal.dao;

import com.portalpostal.dao.handler.BancoHandler;
import com.portalpostal.model.Banco;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoDAO extends GenericDAO { 
    
    private BancoHandler bancoHandler;

    public BancoDAO(String nameDB) { 
        super(nameDB, BancoDAO.class);
        bancoHandler = new BancoHandler();
    } 

    public List<Banco> findAll() throws Exception {
        String sql = "SELECT * FROM banco ORDER BY banco.idBanco";        
        return findAll(sql, null, bancoHandler);
    }

    public Banco find(Integer idBanco) throws Exception {
        String sql = "SELECT * FROM banco WHERE banco.idBanco = :idBanco";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idBanco", idBanco);
        return (Banco) find(sql, params, bancoHandler);
    }

    public Banco save(Banco banco) throws Exception {  
        String sql = "INSERT INTO banco (nome, numero, website) "
                   + "VALUES(:nome, :numero, :website)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nome", banco.getNome());
        params.put("numero", banco.getNumero());
        params.put("website", banco.getWebsite());      
        Integer idBanco = save(sql, params, bancoHandler);
        return find(idBanco);
    }

    public Banco update(Banco banco) throws Exception {
        String sql = "UPDATE banco "
                   + "SET nome = :nome, numero = :numero, website = :website "
                   + "WHERE idBanco = :idBanco ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idBanco", banco.getIdBanco());
        params.put("nome", banco.getNome());
        params.put("numero", banco.getNumero());
        params.put("website", banco.getWebsite());      
        update(sql, params, bancoHandler);
        return banco;  
    }

    public Banco remove(Integer idBanco) throws Exception { 
        String sql = "DELETE FROM banco WHERE idBanco = :idBanco ";
        Banco banco = find(idBanco);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idBanco", idBanco);
        remove(sql, params, bancoHandler);
        return banco;
    }

    public Banco findByNumero(Integer numero) throws Exception {     
        String sql = "SELECT * FROM banco WHERE numero = :numero ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("numero", numero);
        return (Banco) find(sql, params, bancoHandler);
    }
    
}
