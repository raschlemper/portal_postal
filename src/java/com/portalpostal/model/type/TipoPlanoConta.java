package com.portalpostal.model.type;

import com.portalpostal.model.serializer.TipoPlanoContaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoPlanoContaSerializer.class)
public enum TipoPlanoConta {    
    
    RECEITA("receita", "Receita"),
    DESPESA("despesa", "Despesa");
    
    private final String codigo;
    private final String descricao;
    
    TipoPlanoConta(String codigo, String descricao) {
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
