package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoStatusContaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoStatusContaSerializer.class)
public enum TipoStatusConta {    
    
    ABERTO("aberto", "Aberto"),
    ENCERRADO("encerrado", "Encerrado");
    
    private final String codigo;
    private final String descricao;
    
    TipoStatusConta(String codigo, String descricao) {
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
