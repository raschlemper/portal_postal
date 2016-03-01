package com.portalpostal.model;

import com.portalpostal.model.serializer.TipoStatusSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoStatusSerializer.class)
public enum TipoStatus {    
    
    NOVO("novo", "Novo"),
    SEMINOVO("seminovo", "Seminovo"),
    USADO("usado", "Usado");
    
    private final String codigo;
    private final String descricao;
    
    TipoStatus(String codigo, String descricao) {
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
