package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoSexoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoSexoSerializer.class)
public enum TipoSexo {    
    
    MASCULINO("masculino", "Masculino"),
    FEMININO("feminino", "Feminino");
    
    private final String codigo;
    private final String descricao;
    
    TipoSexo(String codigo, String descricao) {
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
