package com.portalpostal.dao.handler;

import com.portalpostal.model.Banco;
import com.portalpostal.model.ContaCorrente;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaCorrenteHandler extends GenericHandler implements ResultSetHandler<ContaCorrente> {
        
    public ContaCorrenteHandler() {
        super("conta_corrente");
    }
    
    public ContaCorrenteHandler(String table) {
        super(table);
    }

    public ContaCorrente handle(ResultSet result) throws SQLException {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setIdContaCorrente(getInt(result, "idContaCorrente"));
        contaCorrente.setNome(getString(result, "nome"));
        contaCorrente.setBanco(getBanco(result));
        contaCorrente.setAgencia(getInt(result, "agencia"));
        contaCorrente.setAgenciaDv(getString(result, "agencia_dv"));
        contaCorrente.setContaCorrente(getInt(result, "contaCorrente"));
        contaCorrente.setContaCorrenteDv(getString(result, "contaCorrente_dv"));
        contaCorrente.setPoupanca(getBoolean(result, "poupanca"));
        contaCorrente.setLimite(getDouble(result, "limite"));
        return contaCorrente;
    }
    
    private Banco getBanco(ResultSet result) throws SQLException {
        if(!existColumn(result, "banco.idBanco")) return null;
        return new BancoHandler().handle(result); 
    }
    
}
