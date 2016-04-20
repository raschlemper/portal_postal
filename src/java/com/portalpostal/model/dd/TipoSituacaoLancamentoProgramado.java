package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSituacaoLancamentoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSituacaoLancamentoSerializer.class)
public enum TipoSituacaoLancamentoProgramado {    
    
    ATIVO("ativo", "Ativo"),
    CANCELADO("cancelado", "Cancelado"),
    ENCERRADO("encerrado", "Encerrado");
    
    private final String codigo;
    private final String descricao;
    
    TipoSituacaoLancamentoProgramado(String codigo, String descricao) {
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
