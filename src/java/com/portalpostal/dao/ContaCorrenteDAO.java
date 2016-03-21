package com.portalpostal.dao;

import com.portalpostal.dao.handler.ContaCorrenteHandler;
import com.portalpostal.model.ContaCorrente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContaCorrenteDAO extends GenericDAO { 
    
    private ContaCorrenteHandler contaCorrenteHandler;

    public ContaCorrenteDAO(String nameDB) { 
        super(nameDB, ContaCorrenteDAO.class);
        contaCorrenteHandler = new ContaCorrenteHandler();
    } 

    public List<ContaCorrente> findAll() throws Exception {
        String sql = "SELECT * FROM conta_corrente, banco "
                   + "WHERE conta_corrente.idBanco = banco.idBanco "
                   + "ORDER BY conta_corrente.idContaCorrente";        
        return findAll(sql, null, contaCorrenteHandler);
    }

    public ContaCorrente find(Integer idContaCorrente) throws Exception {
        String sql = "SELECT * FROM conta_corrente, banco "
                   + "WHERE conta_corrente.idBanco = banco.idBanco "
                   + "AND conta_corrente.idContaCorrente = :idContaCorrente";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", idContaCorrente);
        return (ContaCorrente) find(sql, params, contaCorrenteHandler);
    }

    public ContaCorrente save(ContaCorrente contaCorrente) throws Exception {  
        String sql = "INSERT INTO conta_corrente (idBanco, agencia, contaCorrente, carteira) "
                   + "VALUES(:idBanco, :agencia, :contaCorrente, :carteira)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idBanco", contaCorrente.getBanco().getIdBanco());
        params.put("agencia", contaCorrente.getAgencia());
        params.put("contaCorrente", contaCorrente.getContaCorrente());
        params.put("carteira", contaCorrente.getCarteira());      
        Integer idContaCorrente = save(sql, params, contaCorrenteHandler);
        return find(idContaCorrente);
    }

    public ContaCorrente update(ContaCorrente contaCorrente) throws Exception {
        String sql = "UPDATE conta_corrente "
                   + "SET idBanco = :idBanco, agencia = :agencia, contaCorrente = :contaCorrente, carteira = :carteira "
                   + "WHERE idContaCorrente = :idContaCorrente ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", contaCorrente.getIdContaCorrente());
        params.put("idBanco", contaCorrente.getBanco().getIdBanco());
        params.put("agencia", contaCorrente.getAgencia());
        params.put("contaCorrente", contaCorrente.getContaCorrente());
        params.put("carteira", contaCorrente.getCarteira());      
        update(sql, params, contaCorrenteHandler);
        return contaCorrente;  
    }

    public ContaCorrente remove(Integer idContaCorrente) throws Exception { 
        String sql = "DELETE FROM conta_corrente WHERE idContaCorrente = :idContaCorrente ";
        ContaCorrente contaCorrente = find(idContaCorrente);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idContaCorrente", idContaCorrente);
        remove(sql, params, contaCorrenteHandler);
        return contaCorrente;
    }

    public ContaCorrente findByContaCorrente(Integer idBanco, Integer agencia, Integer contaCorrente) throws Exception {     
        String sql = "SELECT * FROM conta_corrente "
                   + "WHERE idBanco = :idBanco AND agencia = :agencia AND contaCorrente = :contaCorrente ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idBanco", idBanco);
        params.put("idBanco", agencia);
        params.put("idBanco", contaCorrente);
        return (ContaCorrente) find(sql, params, contaCorrenteHandler);
    }
    
}
