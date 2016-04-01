package com.portalpostal.dao.handler;

import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.Conta;
import com.portalpostal.model.ContaCorrente;
import com.portalpostal.model.dd.TipoConta;
import com.portalpostal.model.dd.TipoStatusConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ContaHandler extends GenericHandler implements ResultSetHandler<Conta> {
        
    public ContaHandler() {
        super("conta");
    }
    
    public ContaHandler(String table) {
        super(table);
    }

    public Conta handle(ResultSet result) throws SQLException {
        Conta conta = new Conta();
        conta.setIdConta(getInt(result, "idConta"));
        conta.setNome(getString(result, "nome"));        
        conta.setTipo(TipoConta.values()[getInt(result, "tipo")]);              
        conta.setStatus(TipoStatusConta.values()[getInt(result, "status")]);
        conta.setDataAbertura(getDate(result, "dataAbertura"));
        conta.setValorSaldoAbertura(getDouble(result, "valorSaldoAbertura"));
        conta.setContaCorrente(getContaCorrente(result));
        conta.setCartaoCredito(getCartaoCredito(result));
        return conta;
    }
    
    private ContaCorrente getContaCorrente(ResultSet result) throws SQLException {
        if(!existColumn(result, "conta_corrente.idContaCorrente")) return null;
        return new ContaCorrenteHandler().handle(result); 
    }
    
    private CartaoCredito getCartaoCredito(ResultSet result) throws SQLException {
        if(!existColumn(result, "cartao_credito.idCartaoCredito")) return null;  
        return new CartaoCreditoHandler().handle(result);
    }
    
}
