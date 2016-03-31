package com.portalpostal.dao.handler;

import com.portalpostal.model.Lancamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoHandler extends GenericHandler implements ResultSetHandler<Lancamento> {
    
    private final ContaHandler contaHandler;
    private final PlanoContaHandler planoContaHandler;
    
    public LancamentoHandler() {
        super("lancamento");
        contaHandler = new ContaHandler();
        planoContaHandler = new PlanoContaHandler();
    }
    
    public LancamentoHandler(String table) {
        super(table);
        contaHandler = new ContaHandler();
        planoContaHandler = new PlanoContaHandler();
        if(table != null) { this.table = table; }
    }

    public Lancamento handle(ResultSet result) throws SQLException {
        Lancamento lancamento = new Lancamento();
        lancamento.setIdLancamento(result.getInt(table + ".idLancamento"));
        lancamento.setConta(contaHandler.handle(result));
        lancamento.setPlanoConta(planoContaHandler.handle(result));
        lancamento.setData(result.getDate(table + ".data"));
        lancamento.setValor(result.getDouble(table + ".valor"));
        lancamento.setHistorico(result.getString(table + ".historico"));
        return lancamento;
    }
    
}
