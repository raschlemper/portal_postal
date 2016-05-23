package com.portalpostal.model;

import java.sql.Blob;

public class LancamentoAnexo {
    
    private Integer idLancamentoAnexo;
    private Lancamento lancamento;
    private String nome;
    private Blob anexo;

    public Integer getIdLancamentoAnexo() {
        return idLancamentoAnexo;
    }

    public void setIdLancamentoAnexo(Integer idLancamentoAnexo) {
        this.idLancamentoAnexo = idLancamentoAnexo;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Blob getAnexo() {
        return anexo;
    }

    public void setAnexo(Blob anexo) {
        this.anexo = anexo;
    }
    
    
}
