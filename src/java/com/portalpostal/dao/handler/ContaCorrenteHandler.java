package com.portalpostal.dao.handler;

import com.portalpostal.model.ContaCorrente;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaCorrenteHandler implements ResultSetHandler<ContaCorrente> {
    
    private final BancoHandler bancoHandler;
    private String table = "contaCorrente";
    
    public ContaCorrenteHandler() {
        bancoHandler = new BancoHandler();
    }
    
    public ContaCorrenteHandler(String table) {
        bancoHandler = new BancoHandler();
        this.table = table;      
    }

    public ContaCorrente handle(ResultSet result) throws SQLException {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setIdContaCorrente(result.getInt(table + ".idContaCorrente"));
        contaCorrente.setBanco(bancoHandler.handle(result));
        contaCorrente.setAgencia(result.getInt(table + ".agencia"));
        contaCorrente.setContaCorrente(result.getInt(table + ".contaCorrente"));
        contaCorrente.setCarteira(result.getInt(table + ".carteira"));
        return contaCorrente;
    }
    
}
