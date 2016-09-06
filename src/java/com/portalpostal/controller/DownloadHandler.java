package com.portalpostal.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import sun.misc.BASE64Decoder;

public class DownloadHandler {
    
    public static Response image(InputStream inputStream, String name) throws IOException {
        if(name.contains(".png")) {
            BufferedImage image = toImage(inputStream);
            return response(image, name);
        }
        ByteArrayOutputStream out = toByteArrayOutputStream(inputStream);
        return response(out.toByteArray(), name);
    } 
    
    public static Response pdf(InputStream inputStream, String name) throws IOException {
        ByteArrayOutputStream out = toByteArrayOutputStream(inputStream);
        return response(out.toByteArray(), name);
    } 
    
    private static Response response(Object out, String name) {
        return Response
            .ok(out)
            .header("content-disposition","attachment; filename = " + name)
            .build();        
    }
    
    private static byte[] toByte(InputStream inputStream) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(inputStream);        
    }
    
    private static BufferedImage toImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);       
    }
    
    private static ByteArrayOutputStream toByteArrayOutputStream(final InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int data = inputStream.read();
        while (data >= 0) {
          out.write((char) data);
          data = inputStream.read();
        }
        out.flush();    
        return out;
    }            
    
}
