package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoCombustivelVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoCombustivelVeiculoSerializer.class)
public enum TipoCombustivelVeiculo {    
    
    GASOLINA("gasolina", "Gasolina"),
    ETANOL_ALCOOL("etanol", "Etanol/Álcool"),
    DIESEL("diesel", "Diesel"),
    GAS("gas", "Gás Natural Veicular"),
    FLEX("flex", "Gasolina/Álcool");
    
    private final String codigo;
    private final String descricao;
    
    TipoCombustivelVeiculo(String codigo, String descricao) {
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
