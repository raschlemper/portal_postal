package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSituacaoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSituacaoSerializer.class)
public enum TipoSituacao {    
    
    ATIVO("ativo", "Ativo"),
    CANCELADO("cancelado", "Cancelado"),
    ENCERRADO("encerrado", "Encerrado");
    
    private final String codigo;
    private final String descricao;
    
    TipoSituacao(String codigo, String descricao) {
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
