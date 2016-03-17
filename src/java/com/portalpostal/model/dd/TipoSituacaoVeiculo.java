package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSituacaoVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSituacaoVeiculoSerializer.class)
public enum TipoSituacaoVeiculo {    
    
    ATIVO("ativo", "Ativo"),
    INATIVO("inativo", "Inativo"),
    MANUTENCAO("manutencao", "Manutenção");
    
    private final String codigo;
    private final String descricao;
    
    TipoSituacaoVeiculo(String codigo, String descricao) {
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
