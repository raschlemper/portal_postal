package com.portalpostal.model;

import com.portalpostal.model.dd.TipoPlanoConta;
import java.util.List;

public class PlanoConta {
    
    private Integer idPlanoConta;
    private TipoPlanoConta tipo;
    private Integer codigo;
    private String nome;
    private PlanoConta grupo;
    private List<PlanoConta> contas;

    public Integer getIdPlanoConta() {
        return idPlanoConta;
    }

    public void setIdPlanoConta(Integer idPlanoConta) {
        this.idPlanoConta = idPlanoConta;
    }   

    public TipoPlanoConta getTipo() {
        return tipo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setTipo(TipoPlanoConta tipo) {
        this.tipo = tipo;
    }  

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PlanoConta getGrupo() {
        return grupo;
    }

    public void setGrupo(PlanoConta grupo) {
        this.grupo = grupo;
    }

    public List<PlanoConta> getContas() {
        return contas;
    }

    public void setContas(List<PlanoConta> contas) {
        this.contas = contas;
    }
    
}
