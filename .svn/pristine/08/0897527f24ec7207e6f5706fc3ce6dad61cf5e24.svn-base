package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSinistroVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSinistroVeiculoSerializer.class)
public enum TipoSinistroVeiculo {    
    
    COLISAO("colisao", "Colisão"),
    ROUBO("roubo", "Roubo"),
    FURTO("furto", "Furto"),
    INCENDIO("incendio", "Incêndio"),
    ENCHENTE_ALAGAMENTO("enchentealagamento", "Enchente/Algamento");
    
    private final String codigo;
    private final String descricao;
    
    TipoSinistroVeiculo(String codigo, String descricao) {
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
