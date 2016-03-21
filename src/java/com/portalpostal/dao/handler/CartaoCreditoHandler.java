package com.portalpostal.dao.handler;

import com.portalpostal.model.CartaoCredito;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CartaoCreditoHandler implements ResultSetHandler<CartaoCredito> {
    
    private final ContaCorrenteHandler contaCorrenteHandler;
    
    private String table = "cartao_credito";
    
    public CartaoCreditoHandler() {
        contaCorrenteHandler = new ContaCorrenteHandler("conta");
    }
        
    public CartaoCreditoHandler(String table) {
        contaCorrenteHandler = new ContaCorrenteHandler("conta");
        this.table = table;      
    }

    public CartaoCredito handle(ResultSet result) throws SQLException {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setIdCartaoCredito(result.getInt(table  + ".idCartaoCredito"));
        cartaoCredito.setContaCorrente(contaCorrenteHandler.handle(result));
        cartaoCredito.setBandeira(result.getString(table + ".bandeira"));
        cartaoCredito.setDiaFechamento(result.getInt(table  + ".diaFechamento"));
        cartaoCredito.setDiaVencimento(result.getInt(table  + ".diaVencimento"));
        cartaoCredito.setValorLimiteCredito(result.getDouble(table  + ".valorLimiteCredito"));
        return cartaoCredito;
    }
    
}
