package com.portalpostal.model.serializer;

import com.portalpostal.model.dd.TipoSituacaoLancamentoProgramado;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class TipoSituacaoLancamentoProgramadoSerializer extends JsonSerializer<TipoSituacaoLancamentoProgramado> {

    @Override
    public void serialize(TipoSituacaoLancamentoProgramado value, JsonGenerator jg, SerializerProvider sp) 
            throws IOException, JsonProcessingException {        
        jg.writeStartObject();
        jg.writeFieldName("id");
        jg.writeNumber(value.ordinal());
        jg.writeFieldName("codigo");
        jg.writeString(value.getCodigo());
        jg.writeFieldName("descricao");
        jg.writeString(value.getDescricao());
        jg.writeEndObject();
    }
    
}
