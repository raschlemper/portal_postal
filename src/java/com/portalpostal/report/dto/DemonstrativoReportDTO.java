package com.portalpostal.report.dto;

import java.util.Date;

public class DemonstrativoReportDTO {
    
    private Date dataInicio;
    private Date dataFim;
    private String descricao; 
    private Date periodo;
    private Boolean grupo;
    private Double saldo;
    
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public Boolean getGrupo() {
        return grupo;
    }

    public void setGrupo(Boolean grupo) {
        this.grupo = grupo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    } 
    
}
