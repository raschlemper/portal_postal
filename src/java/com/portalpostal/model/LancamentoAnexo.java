package com.portalpostal.model;

import com.portalpostal.model.serializer.JsonInputStreamSerializer;
import java.io.InputStream;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class LancamentoAnexo {
    
    private Integer idLancamentoAnexo;
    private Lancamento lancamento;
    private String nome;
    @JsonSerialize(using=JsonInputStreamSerializer.class)
    private InputStream anexo;

    public Integer getIdLancamentoAnexo() {
        return idLancamentoAnexo;
    }

    public void setIdLancamentoAnexo(Integer idLancamentoAnexo) {
        this.idLancamentoAnexo = idLancamentoAnexo;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InputStream getAnexo() {
        return anexo;
    }

    public void setAnexo(InputStream anexo) {
        this.anexo = anexo;
    } 
    
}
