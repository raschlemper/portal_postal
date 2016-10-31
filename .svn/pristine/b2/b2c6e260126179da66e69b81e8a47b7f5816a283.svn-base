package com.portalpostal.service;

import com.portalpostal.dao.ConfiguracaoDAO;
import com.portalpostal.model.ConfiguracaoFinanceiro;

public class ConfiguracaoService {
    
    private final String nomeBD;   
    
    private ConfiguracaoDAO configuracaoDAO;    

    public ConfiguracaoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        configuracaoDAO = new ConfiguracaoDAO(nomeBD);
    }
    
    public ConfiguracaoFinanceiro findFinanceiro() throws Exception {
        init();
        return configuracaoDAO.findFinanceiro();
    } 
    
    public ConfiguracaoFinanceiro saveFinanceiro(ConfiguracaoFinanceiro configuracaoFinanceiro) throws Exception {
        init();
        return configuracaoDAO.saveFinanceiro(configuracaoFinanceiro);
    } 
    
}
