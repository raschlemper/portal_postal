package com.portalpostal.dao.handler;

import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.ContaCorrente;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CartaoCreditoHandler implements ResultSetHandler<CartaoCredito> {    
    
    private final ContaCorrenteHandler contaCorrenteHandler;
    private String table = "cartao_credito";
    
    public CartaoCreditoHandler() {
        contaCorrenteHandler = new ContaCorrenteHandler();
    }
        
    public CartaoCreditoHandler(String table) {
        contaCorrenteHandler = new ContaCorrenteHandler();
        this.table = table;      
    }

    public CartaoCredito handle(ResultSet result) throws SQLException {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setIdCartaoCredito(result.getInt(table  + ".idCartaoCredito"));
        cartaoCredito.setNome(result.getString(table + ".nome"));
        cartaoCredito.setBandeira(result.getString(table + ".bandeira"));
        cartaoCredito.setDiaFechamento(result.getInt(table  + ".diaFechamento"));
        cartaoCredito.setDiaVencimento(result.getInt(table  + ".diaVencimento"));
        cartaoCredito.setValorLimiteCredito(result.getDouble(table  + ".valorLimiteCredito"));
        cartaoCredito.setContaCorrente(getContaCorrente(result));
        return cartaoCredito;
    }
    
    private ContaCorrente getContaCorrente(ResultSet result) throws SQLException {
        Integer idContaCorrente = (Integer) result.getObject("conta_corrente.idContaCorrente");        
        if(idContaCorrente != null) { return contaCorrenteHandler.handle(result); };    
        return null;
    }
    
}
