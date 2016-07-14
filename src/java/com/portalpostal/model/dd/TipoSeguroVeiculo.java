package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSeguroVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSeguroVeiculoSerializer.class)
public enum TipoSeguroVeiculo {    
    
    PARCIAL("parcial", "Parcial"),
    INTEGRAL("integral", "Integral");
    
    private final String codigo;
    private final String descricao;
    
    TipoSeguroVeiculo(String codigo, String descricao) {
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
