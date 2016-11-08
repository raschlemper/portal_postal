package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoPeriodoLancamentoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoPeriodoLancamentoSerializer.class)
public enum TipoPeriodoLancamento {    
    
    VENCIDO("vencido", "Vencido"),
    MES_ATUAL("mesatual", "Mês Vigente"),
    SETE_DIAS("setedias", "Últimos 7 Dias"),
    QUINZE_DIAS("quinzedias", "Últimos 15 Dias"),
    TRINTA_DIAS("trintadias", "Últimos 30 Dias"),
    SESSENTA_DIAS("sessentadias", "Últimos 60 Dias"),
    NOVENTA_DIAS("noventadias", "Últimos 90 Dias");
    
    private final String codigo;
    private final String descricao;
    
    TipoPeriodoLancamento(String codigo, String descricao) {
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
