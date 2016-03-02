package com.portalpostal.model;

public class VeiculoSeguro {
    
    private Integer idVeiculoSeguro;
    private Veiculo veiculo;
    private Integer numeroSeguro;
    private String assegurado;
    private Double valorFranquia;
    private TipoSeguro indenizacao;
    
    public VeiculoSeguro() {}

    public Integer getIdVeiculoSeguro() {
        return idVeiculoSeguro;
    }

    public void setIdVeiculoSeguro(Integer idVeiculoSeguro) {
        this.idVeiculoSeguro = idVeiculoSeguro;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getNumeroSeguro() {
        return numeroSeguro;
    }

    public void setNumeroSeguro(Integer numeroSeguro) {
        this.numeroSeguro = numeroSeguro;
    }

    public String getAssegurado() {
        return assegurado;
    }

    public void setAssegurado(String assegurado) {
        this.assegurado = assegurado;
    }

    public Double getValorFranquia() {
        return valorFranquia;
    }

    public void setValorFranquia(Double valorFranquia) {
        this.valorFranquia = valorFranquia;
    }

    public TipoSeguro getIndenizacao() {
        return indenizacao;
    }

    public void setIndenizacao(TipoSeguro indenizacao) {
        this.indenizacao = indenizacao;
    }
    
}
