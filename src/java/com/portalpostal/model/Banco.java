package com.portalpostal.model;

import java.util.List;

public class Banco {
    
    private Integer idBanco;
    private String nome;
    private Integer numero;
    private String website;
    private List<ContaCorrente> contaCorrentes;

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<ContaCorrente> getContaCorrentes() {
        return contaCorrentes;
    }

    public void setContaCorrentes(List<ContaCorrente> contaCorrentes) {
        this.contaCorrentes = contaCorrentes;
    }
    
}
