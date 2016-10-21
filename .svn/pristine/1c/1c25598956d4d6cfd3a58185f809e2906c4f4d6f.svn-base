package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoStatusFornecedorSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoStatusFornecedorSerializer.class)
public enum TipoStatusFornecedor {    
    
    ATIVO("ativo", "Ativo"),
    CANCELADO("cancelado", "Cancelado");
    
    private final String codigo;
    private final String descricao;
    
    TipoStatusFornecedor(String codigo, String descricao) {
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
