package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoPessoaFornecedorSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoPessoaFornecedorSerializer.class)
public enum TipoPessoaFornecedor {    
    
    ATIVO("fisica", "Física"),
    CANCELADO("juridica", "Jurídica");
    
    private final String codigo;
    private final String descricao;
    
    TipoPessoaFornecedor(String codigo, String descricao) {
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
