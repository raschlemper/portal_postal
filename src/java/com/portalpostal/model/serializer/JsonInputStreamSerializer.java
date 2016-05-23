package com.portalpostal.model.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonInputStreamSerializer extends JsonSerializer<InputStream> {
    
    @Override
    public void serialize(InputStream inputStream, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
        StringWriter writer = new StringWriter();
//        IOUtils.copy(inputStream, writer, "UTF-8");
        byte[] file = IOUtils.toByteArray(inputStream);
        gen.writeBinary(file);
//        gen.writeObject(writer.toString());
    }
    
}