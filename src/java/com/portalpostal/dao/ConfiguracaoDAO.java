package com.portalpostal.dao;

import com.portalpostal.dao.handler.ConfiguracaoFinanceiroHandler;
import com.portalpostal.model.Banco;
import com.portalpostal.model.ConfiguracaoFinanceiro;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracaoDAO extends GenericDAO { 
    
    private final ConfiguracaoFinanceiroHandler configuracaoFinanceiroHandler;

    public ConfiguracaoDAO(String nameDB) { 
        super(nameDB, ConfiguracaoDAO.class);
        configuracaoFinanceiroHandler = new ConfiguracaoFinanceiroHandler();
    } 

    public ConfiguracaoFinanceiro findFinanceiro() throws Exception {
        String sql = "SELECT * FROM configuracao_financeiro";
        return (ConfiguracaoFinanceiro) find(sql, null, configuracaoFinanceiroHandler);
    }

    public ConfiguracaoFinanceiro saveFinanceiro(ConfiguracaoFinanceiro configuracaoFinanceiro) throws Exception {
        String sql = "UPDATE configuracao_financeiro "
                   + "SET favorecido = :favorecido, historico = :historico, planoConta = :planoConta, centroCusto = :centroCusto";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("favorecido", configuracaoFinanceiro.getFavorecido());
        params.put("historico", configuracaoFinanceiro.getHistorico());
        params.put("planoConta", configuracaoFinanceiro.getPlanoConta());
        params.put("centroCusto", configuracaoFinanceiro.getCentroCusto());
        update(sql, params, configuracaoFinanceiroHandler);
        return configuracaoFinanceiro;  
    }
    
}
