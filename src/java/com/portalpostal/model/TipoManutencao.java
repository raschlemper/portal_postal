package com.portalpostal.model;

import com.portalpostal.model.serializer.TipoManutencaoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoManutencaoSerializer.class)
public enum TipoManutencao {
    
    PROGRAMADA("programada", "Programada"),
    ROTINA("rotina", "Rotina"),
    TROCA_OLEO("trocaoleo", "Troca de Óleo");
    
    private final String codigo;
    private final String descricao;
    
    TipoManutencao(String codigo, String descricao) {
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
