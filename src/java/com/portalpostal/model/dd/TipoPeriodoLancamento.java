package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoPeriodoLancamentoSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoPeriodoLancamentoSerializer.class)
public enum TipoPeriodoLancamento {    
    
    VENCIDO("vencido", "Vencido"),
    MES_ATUAL("mesatual", "Mês Vigente"),
    ULTIMO_SETE_DIAS("ultimosetedias", "Últimos 7 Dias"),
    ULTIMO_QUINZE_DIAS("ultimoquinzedias", "Últimos 15 Dias"),
    ULTIMO_TRINTA_DIAS("ultimotrintadias", "Últimos 30 Dias"),
    ULTIMO_SESSENTA_DIAS("ultimosessentadias", "Últimos 60 Dias"),
    ULTIMO_NOVENTA_DIAS("ultimonoventadias", "Últimos 90 Dias"),
    DIA_ATUAL("diaatual", "Hoje"),
    ULTIMO_DIA_UTIL("ultimodiautil", "Último Dia Útil"),
    PROXIMO_DIA_UTIL("proximodiautil", "Próximo Dia Útil"),
    PROXIMO_SETE_DIAS("proximosetedias", "Próximos 7 Dias"),
    PROXIMO_QUINZE_DIAS("proximoquinzedias", "Próximos 15 Útil"),
    PROXIMO_TRINTA_DIAS("proximotrintadias", "Próximos 30 Útil"),
    PROXIMO_SESSENTA_DIAS("proximosessentadias", "Próximos 60 Útil"),
    PROXIMO_NOVENTA_DIAS("proximonoventadias", "Próximos 90 Útil");
    
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
