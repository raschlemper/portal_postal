package com.portalpostal.model;

import com.portalpostal.model.dd.TipoManutencaoVeiculo;
import com.portalpostal.model.serializer.JsonDateSerializer;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class VeiculoManutencao {
    
    private Integer idVeiculoManutencao;
    private Veiculo veiculo;
    private TipoManutencaoVeiculo tipo;
    private Integer quilometragem;
    private Double valor;
    private Date dataManutencao;
    private Date dataAgendamento;
    private String descricao;
    
    public VeiculoManutencao() {}

    public Integer getIdVeiculoManutencao() {
        return idVeiculoManutencao;
    }

    public void setIdVeiculoManutencao(Integer idVeiculoManutencao) {
        this.idVeiculoManutencao = idVeiculoManutencao;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public TipoManutencaoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoManutencaoVeiculo tipo) {
        this.tipo = tipo;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(Date dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
