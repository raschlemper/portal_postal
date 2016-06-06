package com.portalpostal.dao;

import com.portalpostal.dao.handler.ContaHandler;
import com.portalpostal.model.Conta;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContaDAO extends GenericDAO { 
    
    private final ContaHandler contaHandler;

    public ContaDAO(String nameDB) { 
        super(nameDB, ContaDAO.class);
        this.contaHandler = new ContaHandler();
    } 

    public List<Conta> findAll() throws Exception {
        String sql = "SELECT * FROM conta "
                   + "LEFT OUTER JOIN conta_corrente ON(conta.idContaCorrente = conta_corrente.idContaCorrente) "
                   + "LEFT OUTER JOIN banco ON(conta_corrente.idBanco = banco.idBanco) "
                   + "LEFT OUTER JOIN cartao_credito ON(conta.idCartaoCredito = cartao_credito.idCartaoCredito) "
                   + "ORDER BY conta.idConta";        
        return findAll(sql, null, contaHandler);
    }

    public Conta find(Integer idConta) throws Exception {
        String sql = "SELECT * FROM conta "
                   + "LEFT OUTER JOIN conta_corrente ON(conta.idContaCorrente = conta_corrente.idContaCorrente) "
                   + "LEFT OUTER JOIN banco ON(conta_corrente.idBanco = banco.idBanco) "
                   + "LEFT OUTER JOIN cartao_credito ON(conta.idCartaoCredito = cartao_credito.idCartaoCredito) "
                   + "WHERE conta.idConta = :idConta";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);
        return (Conta) find(sql, params, contaHandler);
    }

    public List<Conta> findByContaCorrente(Integer idContaCorrente) throws Exception {
        String sql = "SELECT * FROM conta, conta_corrente "
                   + "WHERE conta.idContaCorrente = conta_corrente.idContaCorrente "
                   + "AND conta.idContaCorrente = :idContaCorrente "
                   + "ORDER BY conta.idConta"; 
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", idContaCorrente);       
        return findAll(sql, params, contaHandler);
    }

    public List<Conta> findByCartaoCredito(Integer idCartaoCredito) throws Exception {
        String sql = "SELECT * FROM conta, cartao_credito "
                   + "WHERE conta.idCartaoCredito = cartao_credito.idCartaoCredito "
                   + "AND conta.idCartaoCredito = :idCartaoCredito";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCartaoCredito", idCartaoCredito);       
        return findAll(sql, params, contaHandler);
    }

    public List<Conta> findByCarteiraCobranca(Integer idCarteiraCobranca) throws Exception {
        String sql = "SELECT * FROM conta, conta_corrente, carteira_cobranca "
                   + "WHERE conta.idContaCorrente = conta_corrente.idContaCorrente "
                   + "AND conta_corrente.idContaCorrente = carteira_cobranca.idContaCorrente "
                   + "AND carteira_cobranca.idCarteiraCobranca = :idCarteiraCobranca "
                   + "ORDER BY conta.idConta"; 
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCarteiraCobranca", idCarteiraCobranca);       
        return findAll(sql, params, contaHandler);
    }

    public List<Conta> findSaldo() throws Exception {
        String sql = "SELECT *, (SELECT sum(if(lancamento.tipo = 0, lancamento.valor, lancamento.valor * -1)) " 
                              + "FROM lancamento WHERE conta.idConta = lancamento.idConta) as saldo "
                   + "FROM conta "
                   + "LEFT OUTER JOIN conta_corrente ON(conta.idContaCorrente = conta_corrente.idContaCorrente) "
                   + "LEFT OUTER JOIN banco ON(conta_corrente.idBanco = banco.idBanco) ";     
        return findAll(sql, null, contaHandler);
    }

    public Conta findSaldoLancamento(Integer idConta, Date data) throws Exception {
        String sql = "SELECT *, (SELECT sum(if(tipo=0, valor, valor * -1)) FROM lancamento "
                              + "WHERE DATE(dataLancamento) <= :data "
                              + "AND conta.idConta = lancamento.idConta) as saldo "
                   + "FROM conta "                 
                   + "WHERE idConta = :idConta ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);
        params.put("data", data);        
        return (Conta) find(sql, params, contaHandler);
    }

    public Conta save(Conta conta) throws Exception {  
        String sql = "INSERT INTO conta (idContaCorrente, idCartaoCredito, nome, tipo, status, dataAbertura, valorSaldoAbertura) "
                   + "VALUES(:idContaCorrente, :idCartaoCredito, :nome, :tipo, :status, :dataAbertura, :valorSaldoAbertura)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", (conta.getContaCorrente() == null ? null :conta.getContaCorrente().getIdContaCorrente()));
        params.put("idCartaoCredito", (conta.getCartaoCredito()== null ? null :conta.getCartaoCredito().getIdCartaoCredito()));
        params.put("nome", conta.getNome());
        params.put("tipo", conta.getTipo().ordinal());
        params.put("status", conta.getStatus().ordinal());
        params.put("dataAbertura", conta.getDataAbertura());
        params.put("valorSaldoAbertura", conta.getValorSaldoAbertura());
        Integer idConta = save(sql, params, contaHandler);
        return find(idConta);
    }

    public Conta update(Conta conta) throws Exception {
        String sql = "UPDATE conta "
                   + "SET idContaCorrente = :idContaCorrente, idCartaoCredito = :idCartaoCredito, nome = :nome, tipo = :tipo, "
                   + "status = :status, dataAbertura = :dataAbertura, valorSaldoAbertura = :valorSaldoAbertura "
                   + "WHERE idConta = :idConta ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", conta.getIdConta());
        params.put("idContaCorrente", (conta.getContaCorrente() == null ? null :conta.getContaCorrente().getIdContaCorrente()));
        params.put("idCartaoCredito", (conta.getCartaoCredito()== null ? null :conta.getCartaoCredito().getIdCartaoCredito()));
        params.put("nome", conta.getNome());
        params.put("tipo", conta.getTipo().ordinal());
        params.put("status", conta.getStatus().ordinal());
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
