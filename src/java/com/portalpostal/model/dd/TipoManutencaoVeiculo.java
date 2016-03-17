package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoManutencaoVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoManutencaoVeiculoSerializer.class)
public enum TipoManutencaoVeiculo {
    
    PROGRAMADA("programada", "Programada"),
    ROTINA("rotina", "Rotina"),
    TROCA_OLEO("trocaoleo", "Troca de Óleo");
    
    private final String codigo;
    private final String descricao;
    
    TipoManutencaoVeiculo(String codigo, String descricao) {
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
