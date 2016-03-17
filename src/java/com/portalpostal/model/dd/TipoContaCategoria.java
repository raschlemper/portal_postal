package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoContaCategoriaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoContaCategoriaSerializer.class)
public enum TipoContaCategoria {    
    
    DINHEIRO("dinheiro", "Dinheiro"),
    CONTA_CORRENTE("contacorrente", "Conta Corrente"),
    CARTAO_CREDITO("cartacredito", "Cartão Crédito"),
    POUPANCA("poupanca","Poupança");
    
    private final String codigo;
    private final String descricao;
    
    TipoContaCategoria(String codigo, String descricao) {
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
