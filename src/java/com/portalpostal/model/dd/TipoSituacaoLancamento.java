package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSituacaoLancamentoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSituacaoLancamentoSerializer.class)
public enum TipoSituacaoLancamento {    
    
    NORMAL("normal", "Normal"),
    NAO_COMPENSADO("naocompensado", "Não Compensado"),
    COMPENSADO("compensado", "Compensado");
    
    private final String codigo;
    private final String descricao;
    
    TipoSituacaoLancamento(String codigo, String descricao) {
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
