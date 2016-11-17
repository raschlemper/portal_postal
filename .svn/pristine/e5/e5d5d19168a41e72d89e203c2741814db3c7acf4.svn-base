package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoLimiteLancamentoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoLimiteLancamentoSerializer.class)
public enum TipoLimiteLancamento {    
    
    LIMITE_25("25", "25"),
    LIMITE_50("50", "50"),
    LIMITE_75("75", "75"),
    LIMITE_100("100", "100");
    
    private final String codigo;
    private final String descricao;
    
    TipoLimiteLancamento(String codigo, String descricao) {
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
