package com.portalpostal.model;

import com.portalpostal.model.dd.TipoFile;
import com.portalpostal.model.serializer.JsonInputStreamSerializer;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.ws.rs.core.StreamingOutput;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class LancamentoAnexo {
    
    private Integer idLancamentoAnexo;
    private Lancamento lancamento;
    private TipoFile tipo;
    private String nome;
    @JsonSerialize(using=JsonInputStreamSerializer.class)
    private InputStream anexo;
    private StreamingOutput file;
    private String usuario;

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

    public TipoFile getTipo() {
        return tipo;
    }

    public void setTipo(TipoFile tipo) {
        this.tipo = tipo;
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

    public StreamingOutput getFile() {
        return file;
    }

    public void setFile(StreamingOutput file) {
        this.file = file;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
