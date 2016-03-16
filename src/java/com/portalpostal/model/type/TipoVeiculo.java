package com.portalpostal.model.type;

import com.portalpostal.model.serializer.TipoVeiculoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoVeiculoSerializer.class)
public enum TipoVeiculo {
    
    MOTO("motos", "Moto"),
    CARRO("carros", "Carro"),
    CAMINHAO("caminhoes", "Caminhão");
    
    private final String codigo;
    private final String descricao;
    
    TipoVeiculo(String codigo, String descricao) {
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
