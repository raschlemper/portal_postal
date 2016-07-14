package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoStatusColaboradorSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoStatusColaboradorSerializer.class)
public enum TipoStatusColaborador {    
    
    ATIVO("ativo", "Ativo"),
    CANCELADO("cancelado", "Cancelado");
    
    private final String codigo;
    private final String descricao;
    
    TipoStatusColaborador(String codigo, String descricao) {
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
