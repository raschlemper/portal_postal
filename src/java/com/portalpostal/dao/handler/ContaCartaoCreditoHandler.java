package com.portalpostal.dao.handler;

import com.portalpostal.model.CartaoCredito;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaCartaoCreditoHandler implements ResultSetHandler<CartaoCredito> {
    
    private final ContaHandler contaHandler;
    private final ContaHandler contaPagamentoHandler;
    
    private String table = "conta_cartao_credito";
    
    public ContaCartaoCreditoHandler() {
        contaHandler = new ContaHandler("conta");
        contaPagamentoHandler = new ContaHandler("contaPagamento");
    }
        
    public ContaCartaoCreditoHandler(String table) {
        contaHandler = new ContaHandler("conta");
        contaPagamentoHandler = new ContaHandler("contaPagamento");
        this.table = table;      
    }

    public CartaoCredito handle(ResultSet result) throws SQLException {
        CartaoCredito contaCartaoCredito = new CartaoCredito();
        contaCartaoCredito.setIdContaCartaoCredito(result.getInt(table  + ".idContaCartaoCredito"));
        contaCartaoCredito.setConta(contaHandler.handle(result));
        contaCartaoCredito.setDiaFechamento(result.getInt(table  + ".diaFechamento"));
        contaCartaoCredito.setDiaVencimento(result.getInt(table  + ".diaVencimento"));
        contaCartaoCredito.setContaPagamento(contaPagamentoHandler.handle(result));
        return contaCartaoCredito;
    }
    
}
