package com.portalpostal.model.serializer;

import com.portalpostal.model.dd.TipoStatusVeiculo;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class TipoStatusVeiculoSerializer extends JsonSerializer<TipoStatusVeiculo> {

    @Override
    public void serialize(TipoStatusVeiculo value, JsonGenerator jg, SerializerProvider sp) 
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
