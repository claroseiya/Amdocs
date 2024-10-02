package Business;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;

public class Imagenes {
	
	public static String text;
	
	 public void ConvertirTxtPng() {
		 
		 
		 
		 

	        /*
	           Because font metrics is based on a graphics context, we need to create
	           a small, temporary image so we can ascertain the width and height
	           of the final image
	         */
	        //BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
	        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = img.createGraphics();
	        
	        Font font = new Font("Arial", Font.PLAIN, 12);
			
			Map atributos = new java.util.HashMap<>();
			//Seteo el atributo que me dice que debemos subrayar
			atributos.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			
			
			//Seteamos al textarea la fuente en negrita y subrayada
			
	        
	        
	        
	        
	        
	        FontMetrics fm = g2d.getFontMetrics();
	        int width = fm.stringWidth(text);
	        int height = fm.getHeight();
	        g2d.dispose();

	        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        g2d = img.createGraphics();
	        /*
	        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	        */
	        g2d.setFont(font.deriveFont(atributos));
	        fm = g2d.getFontMetrics();
	        //Color MiColor = new Color(24, 16, 190);	        
	        g2d.setPaint(new Color (255,255,255));
	        g2d.fillRect ( 0, 0, img.getWidth(), img.getHeight() );

	        g2d.setColor(Color.BLUE);
	        g2d.drawString(text, 0, fm.getAscent());
	        g2d.dispose();
	        try {
	            ImageIO.write(img, "png", new File("imgs/PlanSalida.png"));
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	 }
	 
	 public void Img() {
		 
		 try {
			//final BufferedImage img = ImageIO.read( new File("imgs/PlanBlanco.png"));
			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			Graphics g = img.getGraphics();
			g.setColor(Color.BLUE);
			Font font = new Font("Arial", Font.PLAIN, 11);
			
			Map atributos = new java.util.HashMap<>();
			//Seteo el atributo que me dice que debemos subrayar
			atributos.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			//Seteamos al textarea la fuente en negrita y subrayada
			g.setFont(font.deriveFont(atributos));
			
			//g.setFont(font);
			g.drawString("06 2016 PRO+ 10 / Promo Fide", 0, 12);
			
			FontMetrics fm = g.getFontMetrics();
			 int width = fm.stringWidth("06 2016 PRO+ 10 / Promo Fide");
		        int height = fm.getHeight();
		        g.dispose();

		        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		        g = img.createGraphics();
			
			
			//g.dispose();
			ImageIO.write(img, "png", new File("imgs/PlanSalida.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 
		}
		
		

}
