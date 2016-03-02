package com.portalpostal.model;

import java.util.Date;

public class VeiculoMulta {
    
    private Integer idVeiculoMulta;
    private Veiculo veiculo;
    private Integer numeroMulta;
    private Date data; 
    private Double valor;
    private String local;
    private String descricao;
    
    public VeiculoMulta() {}

    public Integer getIdVeiculoMulta() {
        return idVeiculoMulta;
    }

    public void setIdVeiculoMulta(Integer idVeiculoMulta) {
        this.idVeiculoMulta = idVeiculoMulta;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getNumeroMulta() {
        return numeroMulta;
    }

    public void setNumeroMulta(Integer numeroMulta) {
        this.numeroMulta = numeroMulta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
