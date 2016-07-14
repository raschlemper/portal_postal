package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoFavorecidoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoFavorecidoSerializer.class)
public enum TipoFavorecido {    
    
    COLABORADOR("colaborador", "Colabordor"),
    FORNECEDOR("fornecedor", "Fornecedor"),
    CLIENTE("cliente", "Cliente");
    
    private final String codigo;
    private final String descricao;
    
    TipoFavorecido(String codigo, String descricao) {
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
