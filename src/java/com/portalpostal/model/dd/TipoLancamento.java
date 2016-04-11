package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoLancamentoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoLancamentoSerializer.class)
public enum TipoLancamento {    
    
    RECEITA("receita", "Receita"),
    DESPESA("despesa", "Despesa");
    
    private final String codigo;
    private final String descricao;
    
    TipoLancamento(String codigo, String descricao) {
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
