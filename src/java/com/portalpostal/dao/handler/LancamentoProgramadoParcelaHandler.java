package com.portalpostal.dao.handler;

import com.portalpostal.model.CentroCusto;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoProgramadoParcela;
import com.portalpostal.model.PlanoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoProgramadoParcelaHandler extends GenericHandler implements ResultSetHandler<LancamentoProgramadoParcela> {
    
    public LancamentoProgramadoParcelaHandler() {
        super("lancamento_programado_parcela");
    }
    
    public LancamentoProgramadoParcelaHandler(String table) {
        super(table);
    }

    public LancamentoProgramadoParcela handle(ResultSet result) throws SQLException {
        LancamentoProgramadoParcela lancamentoProgramadoParcela = new LancamentoProgramadoParcela();
        lancamentoProgramadoParcela.setIdLancamentoProgramadoParcela(getInt(result, "idLancamentoProgramadoParcela"));
        lancamentoProgramadoParcela.setPlanoConta(getPlanoConta(result));
        lancamentoProgramadoParcela.setCentroCusto(getCentroCusto(result));
        lancamentoProgramadoParcela.setLancamentoProgramado(getLancamentoProgramado(result));
        lancamentoProgramadoParcela.setLancamento(getLancamento(result));
        lancamentoProgramadoParcela.setNumero(getInt(result, "numero"));
        lancamentoProgramadoParcela.setDataVencimento(getDate(result, "dataVencimento"));
        lancamentoProgramadoParcela.setValor(getDouble(result, "valor"));
        return lancamentoProgramadoParcela;
    }
    
    private PlanoConta getPlanoConta(ResultSet result) throws SQLException {
        if(!existColumn(result, "plano_conta.idPlanoConta")) return null;
        return new PlanoContaHandler().handle(result); 
    }
    
    private CentroCusto getCentroCusto(ResultSet result) throws SQLException {
        if(!existColumn(result, "centro_custo.idCentroCusto")) return null;
        return new CentroCustoHandler().handle(result); 
    }
        
    private LancamentoProgramado getLancamentoProgramado(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento_programado.idLancamentoProgramado")) return null;
        if(!existFKValue(result, "lancamento_programado.idLancamentoProgramado")) return null;
        LancamentoProgramado lancamentoProgramado = new LancamentoProgramado();
        lancamentoProgramado.setIdLancamentoProgramado(getInt(result, "idLancamentoProgramadoParcela", "lancamento_programado"));
        return lancamentoProgramado; 
    }
        
    private Lancamento getLancamento(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento.idLancamento")) return null;
        if(!existFKValue(result, "lancamento.idLancamento")) return null;
        Lancamento lancamento = new Lancamento();
        lancamento.setIdLancamento(getInt(result, "idLancamento", "lancamento"));
        return new LancamentoHandler().handle(result); 
    }
    
}
