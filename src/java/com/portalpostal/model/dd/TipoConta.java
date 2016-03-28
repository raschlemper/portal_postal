package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoContaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoContaSerializer.class)
public enum TipoConta {    
    
    DINHEIRO("dinheiro", "Dinheiro"),
    CONTA_CORRENTE("contacorrente", "Conta Corrente"),
    POUPANCA("poupanca", "Poupança"),
    COBRANCA("cobranca", "Cobrança"),
    CARTAO_CREDITO("cartaocredito", "Cartão Crédito");
    
    private final String codigo;
    private final String descricao;
    
    TipoConta(String codigo, String descricao) {
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
