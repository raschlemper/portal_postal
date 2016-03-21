package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.dd.TipoStatusConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaHandler implements ResultSetHandler<Conta> {
    
    private final ContaCorrenteHandler contaCorrenteHandler;
    private String table = "conta";
    
    public ContaHandler() {
        contaCorrenteHandler = new ContaCorrenteHandler();
    }
    
    public ContaHandler(String table) {
        contaCorrenteHandler = new ContaCorrenteHandler();
        this.table = table;
    }

    public Conta handle(ResultSet result) throws SQLException {
        Conta conta = new Conta();
        conta.setIdConta(result.getInt(table + ".idConta"));
        conta.setContaCorrente(contaCorrenteHandler.handle(result));
        conta.setStatus(TipoStatusConta.values()[result.getInt(table + ".status")]);
        conta.setDataAbertura(result.getDate(table + ".dataAbertura"));
        conta.setValorSaldoAbertura(result.getDouble(table + ".valorSaldoAbertura"));
        return conta;
    }
    
}
