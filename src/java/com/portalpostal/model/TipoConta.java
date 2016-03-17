package com.portalpostal.model;

import com.portalpostal.model.dd.TipoContaCategoria;

public class TipoConta {
    
    private Integer idTipoConta;
    private TipoContaCategoria categoria;
    private String descricao;

    public Integer getIdTipoConta() {
        return idTipoConta;
    }

    public void setIdTipoConta(Integer idTipoConta) {
        this.idTipoConta = idTipoConta;
    }

    public TipoContaCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoContaCategoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
