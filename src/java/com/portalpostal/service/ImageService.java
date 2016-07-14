package com.portalpostal.service;

import com.sun.jersey.multipart.FormDataBodyPart;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ImageService {      
    
    public InputStream trataInputStream(String type, InputStream fileInputString, int targetWidth, int targetHeight) {
        if(type.equalsIgnoreCase("JPEG")) { return resizeImage(fileInputString, targetWidth, targetHeight); }
        if(type.equalsIgnoreCase("PNG")) { return resizeImage(fileInputString, targetWidth, targetHeight); }
        if(type.equalsIgnoreCase("JPG")) { return resizeImage(fileInputString, targetWidth, targetHeight); }
        return fileInputString;
    }
    
    public InputStream resizeImage(InputStream inputStream, int targetWidth, int targetHeight) {  
        try {  
            BufferedImage srcImage = ImageIO.read(inputStream);  
            double determineImageScale = determineImageScale(srcImage.getWidth(), srcImage.getHeight(), targetWidth, targetHeight);  
            BufferedImage dstImage = scaleImage(srcImage, determineImageScale);  
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(dstImage, "jpg", outputStream); 
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
    } 
    
    private BufferedImage scaleImage(BufferedImage sourceImage, double scaledWidth) {  
        Image scaledImage = sourceImage.getScaledInstance((int) 
                (sourceImage.getWidth() * scaledWidth), (int) (sourceImage.getHeight() * scaledWidth), Image.SCALE_SMOOTH);  
        BufferedImage bufferedImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);  
        Graphics g = bufferedImage.createGraphics();  
        g.drawImage(scaledImage, 0, 0, null);  
        g.dispose();  
        return bufferedImage;  
    }  
    
    private double determineImageScale(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {  
        double scalex = (double) targetWidth / sourceWidth;  
        double scaley = (double) targetHeight / sourceHeight;  
        return Math.min(scalex, scaley);  
    } 
    
}
