package com.portalpostal.dao.handler;

import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.ContaCorrente;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CartaoCreditoHandler extends GenericHandler implements ResultSetHandler<CartaoCredito> {    
        
    public CartaoCreditoHandler() {
        super("cartao_credito");
    }
        
    public CartaoCreditoHandler(String table) {
        super(table);
    }

    public CartaoCredito handle(ResultSet result) throws SQLException {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setIdCartaoCredito(getInt(result, "idCartaoCredito"));
        cartaoCredito.setNome(getString(result, "nome"));
        cartaoCredito.setBandeira(getString(result, "bandeira"));
        cartaoCredito.setDiaFechamento(getInt(result, "diaFechamento"));
        cartaoCredito.setDiaVencimento(getInt(result, "diaVencimento"));
        cartaoCredito.setValorLimiteCredito(getDouble(result, "valorLimiteCredito"));
        cartaoCredito.setContaCorrente(getContaCorrente(result));
        return cartaoCredito;
    }
    
    private ContaCorrente getContaCorrente(ResultSet result) throws SQLException {
        if(!existColumn(result, "conta_corrente.idContaCorrente")) return null;  
        return new ContaCorrenteHandler().handle(result);
    }
    
}
