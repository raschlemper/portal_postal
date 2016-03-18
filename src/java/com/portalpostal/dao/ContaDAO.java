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
                   + "LEFT OUTER JOIN conta_cartao_credito ON(conta.idConta = conta_cartao_credito.idConta), "
                   + "tipo_conta, banco "
                   + "WHERE conta.idTipoConta = tipo_conta.idTipoConta AND conta.idBanco = banco.idBanco"
                   + "ORDER BY conta.idConta";        
        return findAll(sql, null, contaHandler);
    }

    public Conta find(Integer idConta) throws Exception {
        String sql = "SELECT * FROM conta "
                   + "LEFT OUTER JOIN conta_cartao_credito ON(conta.idConta = conta_cartao_credito.idConta), "
                   + "tipo_conta, banco "
                   + "WHERE conta.idTipoConta = tipo_conta.idTipoConta AND conta.idBanco = banco.idBanco "
                   + "AND conta.idConta = :idConta";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);
        return (Conta) find(sql, params, contaHandler);
    }

    public Conta save(Conta conta) throws Exception {  
        String sql = "INSERT INTO conta (idTipo, idBanco, agencia, contaCorrente, carteira, valorLimiteCredito, dataVencimentoCredito,"
                   + "status, dataAbertura, valorSaldoAbertura) "
                   + "VALUES(:tipo, :idBanco, :agencia, :contaCorrente, :carteira, :valorLimiteCredito, :dataVencimentoCredito, "
                   + ":status, :dataAbertura, :valorSaldoAbertura)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", conta.getTipo().getIdTipoConta());
        params.put("idBanco", conta.getBanco().getIdBanco());
        params.put("agencia", conta.getAgencia());      
        params.put("contaCorrente", conta.getContaCorrente());
        params.put("carteira", conta.getCarteira());
        params.put("valorLimiteCredito", conta.getValorLimiteCredito());
        params.put("dataVencimentoCredito", conta.getDataVencimentoCredito());
        params.put("status", conta.getStatus());
        params.put("dataAbertura", conta.getDataAbertura());
        params.put("valorSaldoAbertura", conta.getValorSaldoAbertura());
        Integer idConta = save(sql, params, contaHandler);
        return find(idConta);
    }

    public Conta update(Conta conta) throws Exception {
        String sql = "UPDATE conta "
                   + "SET tipo = :tipo, idBanco = :idBanco, agencia = :agencia, contaCorrente = :contaCorrente, carteira = :carteira, "
                   + "valorLimiteCredito = :valorLimiteCredito, dataVencimentoCredito = :dataVencimentoCredito, status = :status, "
                   + "dataAbertura = :dataAbertura, valorSaldoAbertura = :valorSaldoAbertura "
                   + "WHERE idConta = :idConta ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", conta.getIdConta());
        params.put("tipo", conta.getTipo().getIdTipoConta());
        params.put("idBanco", conta.getBanco().getIdBanco());
        params.put("agencia", conta.getAgencia());      
        params.put("contaCorrente", conta.getContaCorrente());
        params.put("carteira", conta.getCarteira());
        params.put("valorLimiteCredito", conta.getValorLimiteCredito());
        params.put("dataVencimentoCredito", conta.getDataVencimentoCredito());
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

    public Conta findByContaCorrente(Integer idBanco, Integer agencia, Integer contaCorrente) throws Exception {     
        String sql = "SELECT * FROM conta "
                   + "WHERE numero = :idBanco AND agencia = :agencia AND contaCorrente = :contaCorrente";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idBanco", idBanco);
        params.put("agencia", agencia);
        params.put("contaCorrente", contaCorrente);
        return (Conta) find(sql, params, contaHandler);
    }
    
}
