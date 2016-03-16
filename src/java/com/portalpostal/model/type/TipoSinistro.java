package com.portalpostal.model.type;

import com.portalpostal.model.serializer.TipoSinistroSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSinistroSerializer.class)
public enum TipoSinistro {    
    
    COLISAO("colisao", "Colisão"),
    ROUBO("roubo", "Roubo"),
    FURTO("furto", "Furto"),
    INCENDIO("incendio", "Incêndio"),
    ENCHENTE_ALAGAMENTO("enchentealagamento", "Enchente/Algamento");
    
    private final String codigo;
    private final String descricao;
    
    TipoSinistro(String codigo, String descricao) {
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
