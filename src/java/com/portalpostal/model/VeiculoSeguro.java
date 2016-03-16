package com.portalpostal.model;

import com.portalpostal.model.type.TipoSeguro;
import com.portalpostal.model.serializer.JsonDateSerializer;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class VeiculoSeguro {
    
    private Integer idVeiculoSeguro;
    private Veiculo veiculo;
    private Integer numeroApolice;
    private String corretora;
    private String assegurado;
    private Double valorFranquia;
    private TipoSeguro indenizacao;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataInicioVigencia;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataFimVigencia;
    
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

    public Integer getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(Integer numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getCorretora() {
        return corretora;
    }

    public void setCorretora(String corretora) {
        this.corretora = corretora;
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

    public Date getDataInicioVigencia() {
        return dataInicioVigencia;
    }

    public void setDataInicioVigencia(Date dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    public Date getDataFimVigencia() {
        return dataFimVigencia;
    }

    public void setDataFimVigencia(Date dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
    }

    
}
