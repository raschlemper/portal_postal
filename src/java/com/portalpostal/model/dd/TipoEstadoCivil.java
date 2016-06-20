package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoEstadoCivilSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoEstadoCivilSerializer.class)
public enum TipoEstadoCivil {    
    
    ATIVO("solteiro", "Solteiro"),
    ENCERRADO("casado", "Casado"),
    SEPARADO("separado", "Separado"),
    DIVORCIADO("divorciado", "Divorciado"),
    VIUVO("viuvo", "Viúvo");
    
    private final String codigo;
    private final String descricao;
    
    TipoEstadoCivil(String codigo, String descricao) {
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
