package com.portalpostal.model.dd;

import com.portalpostal.model.serializer.TipoPlanoContaSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = TipoPlanoContaSerializer.class)
public enum TipoFile {    
    
    TXT("txt", "Txt"),
    ZIP("zip", "Zip"),
    PDF("pdf", "Pdf"),
    DOC("doc", "Doc"),
    PPT("ppt", "Ppt"),
    XLS("xls", "Xls"),
    JPG("jpg", "Jpg"),
    GIF("gif", "Gif"),
    PNG("png", "Png"),
    HTML("html", "Html");
    
    private final String codigo;
    private final String descricao;
    
    TipoFile(String codigo, String descricao) {
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
