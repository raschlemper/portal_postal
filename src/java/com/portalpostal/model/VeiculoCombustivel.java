package com.portalpostal.model;

import com.portalpostal.model.dd.TipoCombustivelVeiculo;
import com.portalpostal.model.serializer.JsonDateSerializer;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class VeiculoCombustivel {
    
    private Integer idVeiculoCombustivel;
    private Veiculo veiculo;
    private TipoCombustivelVeiculo tipo;
    private Integer quantidade;
    private Double valorUnitario;
    private Date data; 
    private Double valorTotal;
    private Integer quilometragem;
    
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

    public TipoCombustivelVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoCombustivelVeiculo tipo) {
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

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }
    
}
