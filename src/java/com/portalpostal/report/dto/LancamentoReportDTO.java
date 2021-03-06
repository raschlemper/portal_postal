package com.portalpostal.report.dto;

import java.util.Date;

public class LancamentoReportDTO {
    
    private Integer tipo;
    private Integer modelo;
    private Boolean compensado;
    private Boolean reconciliado;
    private Date data;
    private String conta;
    private String numero;
    private String favorecido;
    private String planoConta;
    private String historico;
    private Double deposito;
    private Double pagamento;
    private Double saldo;

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Boolean getCompensado() {
        return compensado;
    }

    public void setCompensado(Boolean compensado) {
        this.compensado = compensado;
    }

    public Boolean getReconciliado() {
        return reconciliado;
    }

    public void setReconciliado(Boolean reconciliado) {
        this.reconciliado = reconciliado;
    }    

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFavorecido() {
        return favorecido;
    }

    public void setFavorecido(String favorecido) {
        this.favorecido = favorecido;
    }

    public String getPlanoConta() {
        return planoConta;
    }

    public void setPlanoConta(String planoConta) {
        this.planoConta = planoConta;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Double getDeposito() {
        return deposito;
    }

    public void setDeposito(Double deposito) {
        this.deposito = deposito;
    }

    public Double getPagamento() {
        return pagamento;
    }

    public void setPagamento(Double pagamento) {
        this.pagamento = pagamento;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
}
