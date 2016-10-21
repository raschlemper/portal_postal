package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoStatusVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoStatusVeiculoSerializer.class)
public enum TipoStatusVeiculo {    
    
    NOVO("novo", "Novo"),
    SEMINOVO("seminovo", "Seminovo"),
    USADO("usado", "Usado");
    
    private final String codigo;
    private final String descricao;
    
    TipoStatusVeiculo(String codigo, String descricao) {
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
