package com.portalpostal.model;

import java.util.Date;

public class VeiculoCombustivel {
    
    private Integer idVeiculoCombustivel;
    private Veiculo veiculo;
    private TipoCombustivel tipo;
    private Integer quantidade;
    private Double valorUnitario;
    private Date data; 
    private Double valorTotal;
    private Integer quilometragemInicial;
    private Integer quilometragemFinal;
    private Integer quilometragemPercorrida;
    
    public VeiculoCombustivel() {}

    public Integer getIdVeiculoCombustivel() {
        return idVeiculoCombustivel;
    }

    public void setIdVeiculoCombustivel(Integer idVeiculoCombustivel) {
        this.idVeiculoCombustivel = idVeiculoCombustivel;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public TipoCombustivel getTipo() {
        return tipo;
    }

    public void setTipo(TipoCombustivel tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getQuilometragemInicial() {
        return quilometragemInicial;
    }

    public void setQuilometragemInicial(Integer quilometragemInicial) {
        this.quilometragemInicial = quilometragemInicial;
    }

    public Integer getQuilometragemFinal() {
        return quilometragemFinal;
    }

    public void setQuilometragemFinal(Integer quilometragemFinal) {
        this.quilometragemFinal = quilometragemFinal;
    }

    public Integer getQuilometragemPercorrida() {
        return quilometragemPercorrida;
    }

    public void setQuilometragemPercorrida(Integer quilometragemPercorrida) {
        this.quilometragemPercorrida = quilometragemPercorrida;
    }
    
}
