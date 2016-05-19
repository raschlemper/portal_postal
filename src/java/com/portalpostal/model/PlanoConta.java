package com.portalpostal.model;

import com.portalpostal.model.dd.TipoPlanoConta;
import java.util.List;
import java.util.Map;

public class PlanoConta {
    
    private Integer idPlanoConta;
    private TipoPlanoConta tipo;
    private Integer codigo;
    private String nome;
    private String descricao;
    private Integer nivel;
    private Map<Integer,Integer> estrutura;
    private PlanoConta grupo;
    private List<PlanoConta> contas;
    private List<Lancamento> lancamentos;
    private List<LancamentoProgramado> lancamentosProgramados;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Map<Integer, Integer> getEstrutura() {
        return estrutura;
    }

    public void setEstrutura(Map<Integer, Integer> estrutura) {
        this.estrutura = estrutura;
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

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public List<LancamentoProgramado> getLancamentosProgramados() {
        return lancamentosProgramados;
    }

    public void setLancamentosProgramados(List<LancamentoProgramado> lancamentosProgramados) {
        this.lancamentosProgramados = lancamentosProgramados;
    }
    
}
