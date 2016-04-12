package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoSaldo;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.model.dd.TipoLancamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoSaldoHandler extends GenericHandler implements ResultSetHandler<LancamentoSaldo> {
    
    public LancamentoSaldoHandler() {
        super(null);
    }
    
    public LancamentoSaldoHandler(String table) {
        super(table);
    }

    public LancamentoSaldo handle(ResultSet result) throws SQLException {
        LancamentoSaldo lancamento = new LancamentoSaldo();
        lancamento.setConta(getInt(result, "idConta"));
        lancamento.setPlanoConta(getInt(result, "idPlanoConta"));
        lancamento.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamento.setData(getDate(result, "data"));
        lancamento.setValor(getDouble(result, "valor"));
        return lancamento;
    }
    
}
