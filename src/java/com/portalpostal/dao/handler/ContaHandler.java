package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.dd.TipoStatusConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaHandler implements ResultSetHandler<Conta> {
    
    private final TipoContaHandler tipoContaHandler;
    private final BancoHandler bancoHandler;
    private String table = "conta";
    
    public ContaHandler() {
        tipoContaHandler = new TipoContaHandler();
        bancoHandler = new BancoHandler();
    }
    
    public ContaHandler(String table) {
        tipoContaHandler = new TipoContaHandler();
        bancoHandler = new BancoHandler();
        if(table != null) { this.table = table; }
    }

    public Conta handle(ResultSet result) throws SQLException {
        Conta conta = new Conta();
        conta.setIdConta(result.getInt(table + ".idConta"));
        conta.setTipo(tipoContaHandler.handle(result));
        conta.setBanco(bancoHandler.handle(result));
        conta.setAgencia(result.getInt(table + ".agencia"));
        conta.setContaCorrente(result.getInt(table + ".contaCorrente"));
        conta.setCarteira(result.getInt(table + ".carteira"));
        conta.setValorLimiteCredito(result.getDouble(table + ".valorLimiteCredito"));
        conta.setDataVencimentoCredito(result.getDate(table + ".dataVencimentoCredito"));
        conta.setStatus(TipoStatusConta.values()[result.getInt(table + ".status")]);
        conta.setDataAbertura(result.getDate(table + ".dataAbertura"));
        conta.setValorSaldoAbertura(result.getDouble(table + ".valorSaldoAbertura"));
        return conta;
    }
    
}
