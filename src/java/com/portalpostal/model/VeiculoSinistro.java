package com.portalpostal.model;

import com.portalpostal.model.dd.TipoSinistroVeiculo;
import com.portalpostal.model.dd.TipoResponsavelVeiculo;
import java.util.Date;

public class VeiculoSinistro {
    
    private Integer idVeiculoSinistro;
    private Veiculo veiculo;
    private TipoSinistroVeiculo tipo;
    private Integer boletimOcorrencia;
    private Date data;
    private TipoResponsavelVeiculo responsavel;
    private String local;
    private String descricao;
    
    public VeiculoSinistro() {}

    public Integer getIdVeiculoSinistro() {
        return idVeiculoSinistro;
    }

    public void setIdVeiculoSinistro(Integer idVeiculoSinistro) {
        this.idVeiculoSinistro = idVeiculoSinistro;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public TipoSinistroVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoSinistroVeiculo tipo) {
        this.tipo = tipo;
    }

    public Integer getBoletimOcorrencia() {
        return boletimOcorrencia;
    }

    public void setBoletimOcorrencia(Integer boletimOcorrencia) {
        this.boletimOcorrencia = boletimOcorrencia;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public TipoResponsavelVeiculo getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(TipoResponsavelVeiculo responsavel) {
        this.responsavel = responsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
     
}
