package com.portalpostal.dao.handler;

import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.Conta;
import com.portalpostal.model.ContaCorrente;
import com.portalpostal.model.dd.TipoConta;
import com.portalpostal.model.dd.TipoStatusConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaHandler implements ResultSetHandler<Conta> {
    
    private final ContaCorrenteHandler contaCorrenteHandler;
    private final CartaoCreditoHandler cartaoCreditoHandler;
    private String table = "conta";
    
    public ContaHandler() {
        contaCorrenteHandler = new ContaCorrenteHandler();
        cartaoCreditoHandler = new CartaoCreditoHandler();
    }
    
    public ContaHandler(String table) {
        contaCorrenteHandler = new ContaCorrenteHandler();
        cartaoCreditoHandler = new CartaoCreditoHandler();
        this.table = table;
    }

    public Conta handle(ResultSet result) throws SQLException {
        Conta conta = new Conta();
        conta.setIdConta(result.getInt(table + ".idConta"));
        conta.setNome(result.getString(table + ".nome"));        
        conta.setTipo(TipoConta.values()[result.getInt(table  + ".tipo")]);              
        conta.setStatus(TipoStatusConta.values()[result.getInt(table + ".status")]);
        conta.setDataAbertura(result.getDate(table + ".dataAbertura"));
        conta.setValorSaldoAbertura(result.getDouble(table + ".valorSaldoAbertura"));
        conta.setContaCorrente(getContaCorrente(result));
        conta.setCartaoCredito(getCartaoCredito(result));
        return conta;
    }
    
    private ContaCorrente getContaCorrente(ResultSet result) throws SQLException {
        Integer idContaCorrente = (Integer) result.getObject("conta_corrente.idContaCorrente");        
        if(idContaCorrente != null) { return contaCorrenteHandler.handle(result); };    
        return null;
    }
    
    private CartaoCredito getCartaoCredito(ResultSet result) throws SQLException {
        Integer idCartaoCredito = (Integer) result.getObject("cartao_credito.idCartaoCredito");        
        if(idCartaoCredito != null) { return cartaoCreditoHandler.handle(result); };    
        return null;
    }
    
}
