package com.portalpostal.model;

public class ConfiguracaoFinanceiro {
    
    private Integer idConfiguracaoFinanceiro;
    private Boolean favorecido;
    private Boolean historico;
    private Boolean planoConta;
    private Boolean centroCusto; 

    public Integer getIdConfiguracaoFinanceiro() {
        return idConfiguracaoFinanceiro;
    }

    public void setIdConfiguracaoFinanceiro(Integer idConfiguracaoFinanceiro) {
        this.idConfiguracaoFinanceiro = idConfiguracaoFinanceiro;
    }

    public Boolean getFavorecido() {
        return favorecido;
    }

    public void setFavorecido(Boolean favorecido) {
        this.favorecido = favorecido;
    }

    public Boolean getHistorico() {
        return historico;
    }

    public void setHistorico(Boolean historico) {
        this.historico = historico;
    }

    public Boolean getPlanoConta() {
        return planoConta;
    }

    public void setPlanoConta(Boolean planoConta) {
        this.planoConta = planoConta;
    }

    public Boolean getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(Boolean centroCusto) {
        this.centroCusto = centroCusto;
    }
    
    
}
