package com.portalpostal.model;

import com.portalpostal.model.serializer.TipoContaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoContaSerializer.class)
public enum TipoConta {    
    
    DINHEIRO("dinheiro", "Dinheiro"),
    CONTA_CORRENTE("contacorrente", "Conta Corrente"),
    CARTAO_CREDITO("cartacredito", "Cartão Crédito"),
    POUPANCA("poupanca","Poupança");
    
    private final String codigo;
    private final String descricao;
    
    TipoConta(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }    
}
