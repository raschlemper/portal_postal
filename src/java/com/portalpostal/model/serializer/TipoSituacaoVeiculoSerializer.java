package com.portalpostal.model.serializer;

import com.portalpostal.model.dd.TipoSituacaoVeiculo;
import com.portalpostal.model.dd.TipoStatusVeiculo;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class TipoSituacaoVeiculoSerializer extends JsonSerializer<TipoSituacaoVeiculo> {

    @Override
    public void serialize(TipoSituacaoVeiculo value, JsonGenerator jg, SerializerProvider sp) 
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
