package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoFrequenciaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoFrequenciaSerializer.class)
public enum TipoFrequencia {    
    
    UNICO("unico", "Único"),
    DIARIO("diario", "Diário"),
    SEMANAL("semanal", "Semanal"),
    QUINZENAL("quinzenal", "Quinzenal"),
    MENSAL("mensal", "Mensal"),
    BIMESTRAL("bimestral", "Bimestral"),
    TRIMESTRAL("trimestral", "Trimestral"),
    QUADRIMESTRAL("quadrimestral", "Quadrimestral"),
    SEMESTRAL("semestral", "Semestral"),
    ANUAL("anual", "Anual");
    
    private final String codigo;
    private final String descricao;
    
    TipoFrequencia(String codigo, String descricao) {
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

