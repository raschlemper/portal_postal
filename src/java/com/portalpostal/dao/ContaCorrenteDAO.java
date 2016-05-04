package com.portalpostal.dao;

import com.portalpostal.dao.handler.ContaCorrenteHandler;
import com.portalpostal.model.ContaCorrente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContaCorrenteDAO extends GenericDAO { 
    
    private final ContaCorrenteHandler contaCorrenteHandler;

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

    public List<ContaCorrente> findByBanco(Integer idBanco) throws Exception {
        String sql = "SELECT * FROM conta_corrente, banco "
                   + "WHERE conta_corrente.idBanco = banco.idBanco "
                   + "AND conta_corrente.idBanco = :idBanco "
                   + "ORDER BY conta_corrente.idContaCorrente";  
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idBanco", idBanco);      
        return findAll(sql, params, contaCorrenteHandler);
    }

    public ContaCorrente save(ContaCorrente contaCorrente) throws Exception {  
        String sql = "INSERT INTO conta_corrente (nome, idBanco, agencia, agencia_dv, contaCorrente, contaCorrente_dv, poupanca) "
                   + "VALUES(:nome, :idBanco, :agencia, :agenciaDv, :contaCorrente, :contaCorrenteDv, :poupanca)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nome", contaCorrente.getNome());
        params.put("idBanco", contaCorrente.getBanco().getIdBanco());
        params.put("agencia", contaCorrente.getAgencia());
        params.put("agenciaDv", contaCorrente.getAgenciaDv());
        params.put("contaCorrente", contaCorrente.getContaCorrente());
        params.put("contaCorrenteDv", contaCorrente.getContaCorrenteDv());
        params.put("poupanca", contaCorrente.getPoupanca());      
        Integer idContaCorrente = save(sql, params, contaCorrenteHandler);
        return find(idContaCorrente);
    }

    public ContaCorrente update(ContaCorrente contaCorrente) throws Exception {
        String sql = "UPDATE conta_corrente "
                   + "SET nome = :nome, idBanco = :idBanco, agencia = :agencia, agencia_dv = :agenciaDv, "
                   + "contaCorrente = :contaCorrente, contaCorrente_dv = :contaCorrenteDv, poupanca = :poupanca "
                   + "WHERE idContaCorrente = :idContaCorrente ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", contaCorrente.getIdContaCorrente());
        params.put("nome", contaCorrente.getNome());
        params.put("idBanco", contaCorrente.getBanco().getIdBanco());
        params.put("agencia", contaCorrente.getAgencia());
        params.put("agenciaDv", contaCorrente.getAgenciaDv());
        params.put("contaCorrente", contaCorrente.getContaCorrente());
        params.put("contaCorrenteDv", contaCorrente.getContaCorrenteDv());
        params.put("poupanca", contaCorrente.getPoupanca());       
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

    public ContaCorrente findByContaCorrente(Integer idBanco, Integer agencia, Integer agenciaDv, 
            Integer contaCorrente, Integer contaCorrenteDv) throws Exception {     
        String sql = "SELECT * FROM conta_corrente, banco "
                   + "WHERE conta_corrente.idBanco = banco.idBanco AND banco.idBanco = :idBanco "
                   + "AND conta_corrente.agencia = :agencia AND conta_corrente.agencia_dv = :agenciaDv "
                   + "AND conta_corrente.contaCorrente = :contaCorrente AND conta_corrente.contaCorrente_dv = :contaCorrenteDv ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idBanco", idBanco);
        params.put("agencia", agencia);
        params.put("agenciaDv", agenciaDv);
        params.put("contaCorrente", contaCorrente);
        params.put("contaCorrenteDv", contaCorrenteDv);
        return (ContaCorrente) find(sql, params, contaCorrenteHandler);
    }
    
}
