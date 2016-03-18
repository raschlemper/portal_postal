package com.portalpostal.dao.handler;

import com.portalpostal.model.Lancamento;
import com.portalpostal.model.dd.TipoPlanoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoHandler implements ResultSetHandler<Lancamento> {
    
    private final ContaHandler contaHandler;
    private final PlanoContaHandler planoContaHandler;
    private String table = "lancamento";
    
    public LancamentoHandler() {
        contaHandler = new ContaHandler();
        planoContaHandler = new PlanoContaHandler();
    }
    
    public LancamentoHandler(String table) {
        contaHandler = new ContaHandler();
        planoContaHandler = new PlanoContaHandler();
        if(table != null) { this.table = table; }
    }

    public Lancamento handle(ResultSet result) throws SQLException {
        Lancamento lancamento = new Lancamento();
        lancamento.setIdLancamento(result.getInt(table + ".idLancamento"));
        lancamento.setConta(contaHandler.handle(result));
        lancamento.setPlanoConta(planoContaHandler.handle(result));
        lancamento.setTipo(TipoPlanoConta.values()[result.getInt(table  + ".tipo")]);
        lancamento.setData(result.getDate(table + ".data"));
        lancamento.setValor(result.getDouble(table + ".valor"));
        lancamento.setHistorico(result.getString(table + ".historico"));
        return lancamento;
    }
    
}
