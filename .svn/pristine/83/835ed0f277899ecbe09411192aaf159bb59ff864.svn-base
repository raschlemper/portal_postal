package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoResponsavelVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoResponsavelVeiculoSerializer.class)
public enum TipoResponsavelVeiculo {    
    
    MOTORISTA("motorista", "Motorista"),
    TERCEIROS("terceiros", "Terceiros");
    
    private final String codigo;
    private final String descricao;
    
    TipoResponsavelVeiculo(String codigo, String descricao) {
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
