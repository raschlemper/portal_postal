/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author SCC4
 */
public class ResizeImage {
    
   /* public static InputStream resizeImage(InputStream p_image, int p_width, int p_height) throws Exception {
 
        BufferedImage img = ImageIO.read(p_image);
        int w = img.getWidth();
        int h = img.getHeight();
               
        int thumbWidth = p_width;
        int thumbHeight = p_height;        
 
        // Make sure the aspect ratio is maintained, so the image is not skewed
        double thumbRatio = (double)thumbWidth / (double)thumbHeight;
        double imageRatio = (double)w / (double)h;
        if (thumbRatio < imageRatio) {
          thumbHeight = (int)(thumbWidth / imageRatio);
        } else {
          thumbWidth = (int)(thumbHeight * imageRatio);
        }
        
        BufferedImage dimg = new BufferedImage(thumbWidth, thumbHeight, img.getType());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        ImageIO.write(dimg, "jpg", bos);  
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());          
 
        return bis;        
    }*/
    
    public static InputStream resizeImage(InputStream p_image, int p_width, int p_height) throws Exception{
        BufferedImage originalImage = ImageIO.read(p_image);
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        
        // Make sure the aspect ratio is maintained, so the image is not skewed
        double thumbRatio = (double)p_width / (double)p_height;
        double imageRatio = (double)w / (double)h;
        if (thumbRatio < imageRatio) {
          p_height = (int)(p_width / imageRatio);
        } else {
          p_width = (int)(p_height * imageRatio);
        }
        
	BufferedImage resizedImage = new BufferedImage(p_width, p_height, BufferedImage.TYPE_INT_RGB);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, p_width, p_height, null);
	g.dispose();
	g.setComposite(AlphaComposite.Src);
 
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        ImageIO.write(resizedImage, "jpg", bos);  
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());   
 
	return bis;
    }

}
