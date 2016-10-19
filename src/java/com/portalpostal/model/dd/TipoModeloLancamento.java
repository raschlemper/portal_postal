package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoModeloLancamentoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoModeloLancamentoSerializer.class)
public enum TipoModeloLancamento {    
    
    NORMAL("normal", "Normal"),
    TRANSFERENCIA("transferencia", "Transferência"),
    PROGRAMADO("programado", "Programado"),
    TRANSFERENCIA_PROGRAMADO("transferenciaprogramado", "Transferência Programada"),
    PARCELADO("parcelado", "Parcelado"),
    CONCILIADO("reconciliado", "Reconciliado"),
    AUTOMATICO("automatico", "Automático");
    
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
