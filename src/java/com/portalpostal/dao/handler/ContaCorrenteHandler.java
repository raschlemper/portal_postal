package com.portalpostal.dao.handler;

import com.portalpostal.model.ContaCorrente;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaCorrenteHandler implements ResultSetHandler<ContaCorrente> {
    
    private final BancoHandler bancoHandler;
    private String table = "conta_corrente";
    
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
        contaCorrente.setNome(result.getString(table + ".nome"));
        contaCorrente.setBanco(bancoHandler.handle(result));
        contaCorrente.setAgencia(result.getInt(table + ".agencia"));
        contaCorrente.setAgenciaDv(result.getInt(table + ".agencia_dv"));
        contaCorrente.setContaCorrente(result.getInt(table + ".contaCorrente"));
        contaCorrente.setContaCorrenteDv(result.getInt(table + ".contaCorrente_dv"));
        contaCorrente.setPoupanca(result.getBoolean(table + ".poupanca"));
        return contaCorrente;
    }
    
}
