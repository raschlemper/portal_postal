package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoContaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoContaSerializer.class)
public enum TipoModeloLancamento {    
    
    NORMAL("normal", "Normal"),
    TRANSFERENCIA("transferencia", "Transferência"),
    PROGRAMADO("programado", "Programado"),
    TRANSFERENCIA_PROGRAMADO("transferenciaprogramado", "Transferência Programada"),
    PARCELADO("parcelado", "Parcelado");
    
    private final String codigo;
    private final String descricao;
    
    TipoModeloLancamento(String codigo, String descricao) {
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
