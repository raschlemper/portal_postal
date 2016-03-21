package com.portalpostal.dao;

import com.portalpostal.dao.handler.ContaHandler;
import com.portalpostal.model.Conta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContaDAO extends GenericDAO { 
    
    private ContaHandler contaHandler;

    public ContaDAO(String nameDB) { 
        super(nameDB, ContaDAO.class);
        contaHandler = new ContaHandler();
    } 

    public List<Conta> findAll() throws Exception {
        String sql = "SELECT * FROM conta "
                   + "LEFT OUTER JOIN conta_corrente ON(conta.idContaCorrente = conta_corrente.idContaCorrente) "
                   + "ORDER BY conta.idConta";        
        return findAll(sql, null, contaHandler);
    }

    public Conta find(Integer idConta) throws Exception {
        String sql = "SELECT * FROM conta "
                   + "LEFT OUTER JOIN conta_corrente ON(conta.idContaCorrente = conta_corrente.idContaCorrente) "
                   + "WHERE conta.idConta = :idConta";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);
        return (Conta) find(sql, params, contaHandler);
    }

    public Conta save(Conta conta) throws Exception {  
        String sql = "INSERT INTO conta (idContaCorrente, status, dataAbertura, valorSaldoAbertura) "
                   + "VALUES(:idContaCorrente, :status, :dataAbertura, :valorSaldoAbertura)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contaCorrente", conta.getContaCorrente().getIdContaCorrente());
        params.put("status", conta.getStatus());
        params.put("dataAbertura", conta.getDataAbertura());
        params.put("valorSaldoAbertura", conta.getValorSaldoAbertura());
        Integer idConta = save(sql, params, contaHandler);
        return find(idConta);
    }

    public Conta update(Conta conta) throws Exception {
        String sql = "UPDATE conta "
                   + "SET idContaCorrente = :idContaCorrente, status = :status, dataAbertura = :dataAbertura, "
                   + "valorSaldoAbertura = :valorSaldoAbertura "
                   + "WHERE idConta = :idConta ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", conta.getIdConta());
        params.put("contaCorrente", conta.getContaCorrente().getIdContaCorrente());
        params.put("status", conta.getStatus());
        params.put("dataAbertura", conta.getDataAbertura());
        params.put("valorSaldoAbertura", conta.getValorSaldoAbertura());
        update(sql, params, contaHandler);
        return conta;  
    }

    public Conta remove(Integer idConta) throws Exception { 
        String sql = "DELETE FROM conta WHERE idConta = :idConta ";
        Conta conta = find(idConta);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idConta", idConta);
        remove(sql, params, contaHandler);
        return conta;
    }
    
}
