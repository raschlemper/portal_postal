package com.portalpostal.model;

import com.portalpostal.model.serializer.TipoStatusSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoStatusSerializer.class)
public enum TipoResponsavel {    
    
    MOTORISTA("motorista", "Motorista"),
    TERCEIROS("terceiros", "Terceiros");
    
    private final String codigo;
    private final String descricao;
    
    TipoResponsavel(String codigo, String descricao) {
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
