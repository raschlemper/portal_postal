package com.portalpostal.model;

import com.portalpostal.model.dd.TipoLimiteLancamento;
import com.portalpostal.model.dd.TipoPeriodoLancamento;

public class ConfiguracaoFinanceiro {
    
    private Integer idConfiguracaoFinanceiro;
    private Boolean favorecido;
    private Boolean historico;
    private Boolean planoConta;
    private Boolean centroCusto; 
    private TipoPeriodoLancamento periodoLancamento; 
    private TipoPeriodoLancamento periodoLancamentoProgramado; 
    private TipoLimiteLancamento limiteLancamento; 

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

    public TipoPeriodoLancamento getPeriodoLancamento() {
        return periodoLancamento;
    }

    public void setPeriodoLancamento(TipoPeriodoLancamento periodoLancamento) {
        this.periodoLancamento = periodoLancamento;
    }

    public TipoPeriodoLancamento getPeriodoLancamentoProgramado() {
        return periodoLancamentoProgramado;
    }

    public void setPeriodoLancamentoProgramado(TipoPeriodoLancamento periodoLancamentoProgramado) {
        this.periodoLancamentoProgramado = periodoLancamentoProgramado;
    }

    public TipoLimiteLancamento getLimiteLancamento() {
        return limiteLancamento;
    }

    public void setLimiteLancamento(TipoLimiteLancamento limiteLancamento) {
        this.limiteLancamento = limiteLancamento;
    }
        
}
