package com.portalpostal.model.type;

import com.portalpostal.model.serializer.TipoCombustivelSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoCombustivelSerializer.class)
public enum TipoCombustivel {    
    
    GASOLINA("gasolina", "Gasolina"),
    ETANOL_ALCOOL("etanol", "Etanol/Álcool"),
    DIESEL("diesel", "Diesel"),
    GAS("gas", "Gás Natural Veicular"),
    FLEX("flex", "Gasolina/Álcool");
    
    private final String codigo;
    private final String descricao;
    
    TipoCombustivel(String codigo, String descricao) {
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
