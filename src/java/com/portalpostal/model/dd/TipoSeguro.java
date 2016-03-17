package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSeguroSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSeguroSerializer.class)
public enum TipoSeguro {    
    
    PARCIAL("parcial", "Parcial"),
    INTEGRAL("integral", "Integral");
    
    private final String codigo;
    private final String descricao;
    
    TipoSeguro(String codigo, String descricao) {
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
