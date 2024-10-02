package Core.Sikuli;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.SikuliException;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;


public class Sikuli {	
	 
      public void ClickImg(String NameImg, int Time) throws FindFailed {
    	  System.out.println("Realizando un click a la imagen "+NameImg);
    	  Screen s = new Screen();
    	  ImagePath.add("imgs");          
    	  try{
	    	  s.wait(NameImg,Time);
	          s.click();
    	  }
          catch(FindFailed e){
        	  System.out.println(" ...Click fallido");
              e.printStackTrace();
              throw e;
          } 
    	  System.out.println(" ...Click realizado con éxito");
      }
      
      //Funcion para hacer clic 
      public void ClickDosImg(String NameImg,String NameImg2, int Time) throws Exception {
    	  System.out.println("Realizando un click a la imagen "+NameImg+" o "+NameImg2);
    	  boolean Existe=false;
    	  
    	  Existe = FindImgWait(NameImg, 1);
    	  for(int i=0;i<=Time;i=i+2)
    	  {
    		  if(Existe)
        	  {
        		  ClickImg(NameImg, 1);
        		  return;
        	  }
        		  
        	  Existe = FindImgWait(NameImg2, 1);
        	  if(Existe)
        	  {
        		  ClickImg(NameImg2, 1);
        		  return;
        	  }  
    	  }
      }
      
      
      public void findTextInDocument(String text)  throws FindFailed {
    	  System.out.println("Realizando una busqueda a la imagen "+text);
    	  Screen s = new Screen();
    	  ImagePath.add("imgs");
    	  s.existsT(text, 2000);
		  s.click();
      }
      
      public int[] GetCordinates(String NameImg, int Time) {
    	  Screen s = new Screen();
    	  ImagePath.add("imgs");          
    	  try{
	    	  int x = s.wait(NameImg,Time).getX();
	    	  int y = s.wait(NameImg,Time).getY();
	    	  int w = s.wait(NameImg,Time).getW();
	    	  int h = s.wait(NameImg,Time).getH();	    	  
	    	  
    		  int[] Cordenadas = {x,y,w,h};
	          return Cordenadas;
    	  }
          catch(FindFailed e){
              e.printStackTrace();
              return null;
          } 
      }
      
      public void InputText(String Text) {
    	  Screen s = new Screen();
    	  s.type(Text);
      }
      
      public String GetTextRigth(String NameImg, int Time) throws UnsupportedFlavorException, IOException
      {
    	int []Arr=GetCordinates(NameImg,Time);
		int x= Arr[0];
		int y= Arr[1];
		int w= Arr[2];
		int h= Arr[3];
		int xRigth = x+w+10;
		int yRigth = y+h-5;
			
    	return GetText(NameImg, Time, xRigth, yRigth);
    	  
      }
	  
	  public String ClickTextRigth(String NameImg, int Time) throws UnsupportedFlavorException, IOException
      {
    	int []Arr=GetCordinates(NameImg,Time);
		int x= Arr[0];
		int y= Arr[1];
		int w= Arr[2];
		int h= Arr[3];
		int xRigth = x+w+10;
		int yRigth = y+h-5;
			
    	return ClickText(NameImg, Time, xRigth, yRigth);
    	 
      }
      
      public String GetTextDown(String NameImg, int Time) throws UnsupportedFlavorException, IOException
      {
    		int []Arr=GetCordinates(NameImg,Time);
    		int x= Arr[0];
    		int y= Arr[1];
    		int w= Arr[2];
    		int h= Arr[3];
    		int xDown = x+w+10;
    		int yDown = y+h+10;
    	  return GetText(NameImg, Time, xDown, yDown);
    	  
      }
      
      public String GetText(String NameImg, int Time, int x, int y ) throws UnsupportedFlavorException, IOException  {
      	Screen s = new Screen();
  		try
  		{	
  			Location loc = new Location(x,y);			  
  			s.rightClick(loc);
  			s.wait("copiar");
  			s.click();		
  				
  	        try
  	        {
  	        	Thread.sleep(1000);
  	        	String data = (String) Toolkit.getDefaultToolkit()
  	 	               .getSystemClipboard().getData(DataFlavor.stringFlavor); 
  	        	Thread.sleep(1000);
  	        	return data;
  	 		
  	        }
  	        catch(InterruptedException e)
  	        {}
  		 }
  		  catch(FindFailed e){
  		      e.printStackTrace();
  		  }
  			  return "";
    	  
        }
      
      public String GetTextCelda(String NameImg, int Time, int x, int y ) throws UnsupportedFlavorException, IOException  {
        	Screen s = new Screen();
    		try
    		{	
    			Location loc = new Location(x,y);			  
    			s.rightClick(loc);
    			s.wait("PaginaPrincipalInteraccion\\copiarCelda.png");
    			s.click();		
    				
    	        try
    	        {
    	        	Thread.sleep(1000);
    	        	String data = (String) Toolkit.getDefaultToolkit()
    	 	               .getSystemClipboard().getData(DataFlavor.stringFlavor); 
    	        	Thread.sleep(1000);
    	        	return data;
    	 		
    	        }
    	        catch(InterruptedException e)
    	        {}
    		 }
    		  catch(FindFailed e){
    		      e.printStackTrace();
    		  }
    			  return "";
      	  
          }
      
      public void FindImg(String NameImg, int Time) throws FindFailed   {
    	  System.out.println("Realizando una busqueda a la imagen "+NameImg);
    	  Screen s = new Screen();
    	  ImagePath.add("imgs");          
    	  try{
	    	  s.wait(NameImg,Time);	    	  
    	  }
          catch(FindFailed e){
        	  System.out.println(" ...Busqueda fallida");
              e.printStackTrace();
              throw e;
          } 
    	  System.out.print("...Busqueda exitosa "+NameImg);
      }
      
      public boolean FindImgWait(String NameImg, int Time) {
    	  System.out.println("Realizando una busqueda a la imagen "+NameImg);
    	  Screen s = new Screen();
    	  ImagePath.add("imgs");          
    	  try{
	    	  s.wait(NameImg,Time);
	    	  System.out.println(" ...Imagen encontrada");
	    	  return true;
    	  }
          catch(FindFailed e){
        	  System.out.println(" ...No se encuentra la Imagen");
              return false;
          }
      }
      
      public void EjemploOcr()
      {
    	            
           File imageFile = new File("imgs\\\\Ciclos.png");
           Tesseract instance = new Tesseract();

           //In case you don't have your own tessdata, let it also be extracted for you
           File tessDataFolder = LoadLibs.extractTessResources("tessdata");

           //Set the tessdata path
           instance.setDatapath(tessDataFolder.getAbsolutePath());

           try {
               String result = instance.doOCR(imageFile);
               System.out.println(result);
           } catch (TesseractException e) {
               System.err.println(e.getMessage());
           }
    	  
      }
      public String clickCordinates(String nameImg, int time, int with, int heigth) throws UnsupportedFlavorException, IOException {
    	  
  	  	int []Arr=GetCordinates(nameImg,time);
			int x= Arr[0];
			int y= Arr[1];
			int w= Arr[2];
			int h= Arr[3];  			
			int xRigth = x+w+with;
			int yRigth = y+h+heigth;
			
			return ClickText(nameImg, time, xRigth, yRigth);
    }
      
    public String ClickText(String NameImg, int Time, int x, int y ) throws UnsupportedFlavorException, IOException  {
	  	Screen s = new Screen();
		try
		{	
			Location loc = new Location(x,y);			  
			s.click(loc);	
				
		 }
		  catch(FindFailed e){
		      e.printStackTrace();
		  }
			  return "";
		  
    }
  
      public String GetTextRandom(String NameImg, int Time, int datW, int datH) throws UnsupportedFlavorException, IOException
      {
    	int []Arr=GetCordinates(NameImg,Time);
		int x= Arr[0];
		int y= Arr[1];
		int w= Arr[2];
		int h= Arr[3];
		int xRigth = x+w+datW;
		int yRigth = y+h+datH;
			
    	return GetText(NameImg, Time, xRigth, yRigth);
    	  
      }
      
      public String GetTextRandomCelda(String NameImg, int Time, int datW, int datH) throws UnsupportedFlavorException, IOException
      {
    	int []Arr=GetCordinates(NameImg,Time);
		int x= Arr[0];
		int y= Arr[1];
		int w= Arr[2];
		int h= Arr[3];
		int xRigth = x+w+datW;
		int yRigth = y+h+datH;
			
    	return GetTextCelda(NameImg, Time, xRigth, yRigth);
    	  
      }
      
      public String goToCoordinates(String NameImg, int time) throws UnsupportedFlavorException, IOException {
    	  
    	  Screen s = new Screen();    	  
    	  
    	  int []Arr=GetCordinates(NameImg,time);
  		  int x= Arr[0];
  		  int y= Arr[1];
  		  int w= Arr[2];
  		  int h= Arr[3];
  		  
  		  int xRigth = x+w;
  		  int yRigth = y+h;
  		  
  		  return goToImg(NameImg, time, xRigth, yRigth);
    	  
      }
      
      public String goToImg(String NameImg, int Time, int x, int y ) throws UnsupportedFlavorException, IOException  {
  	  	Screen s = new Screen();
  		try
  		{	
  			Location loc = new Location(x,y);			  
  			s.mouseMove(NameImg);	
  				
  		 }
  		  catch(FindFailed e){
  		      e.printStackTrace();
  		  }
  			  return "";
  		  
      }

       public void ClickTextDown(String NameImg, int Time) throws UnsupportedFlavorException, IOException
      {
    		int []Arr=GetCordinates(NameImg,Time);
    		int x= Arr[0];
    		int y= Arr[1];
    		int w= Arr[2];
    		int h= Arr[3];
    		int xDown = x+w+10;
    		int yDown = y+h+10;
          	Screen s = new Screen();
            System.out.println("Realizando una busqueda a la imagen "+NameImg);        	
      		try
      		{	
      			Location loc = new Location(xDown,yDown);			  
      			s.click(loc);
      			System.out.println(" ...Imagen encontrada");
      		}
      		 catch(FindFailed e){
           	  System.out.println(" ...No se encuentra la Imagen");
             }
    	  
      }
       
      public void ClickDown(String NameImg, int Time) throws UnsupportedFlavorException, IOException
      {
    		int []Arr=GetCordinates(NameImg,Time);
    		int x= Arr[0];
    		int y= Arr[1];
    		int w= Arr[2];
    		int h= Arr[3];
    		int xDown = x+w-10;
    		int yDown = y+h+10;
          	Screen s = new Screen();
            System.out.println("Realizando una busqueda a la imagen "+NameImg);        	
      		try
      		{	
      			Location loc = new Location(xDown,yDown);			  
      			s.click(loc);
      			System.out.println(" ...Imagen encontrada");
      		}
      		 catch(FindFailed e){
           	  System.out.println(" ...No se encuentra la Imagen");
             }
    	  
      }
       
      public Iterator<Match> FindAllImgs(String NameImg)
      {
    	  System.out.println("Realizando una busqueda a la imagen "+NameImg);
    	  Screen s = new Screen();
    	  ImagePath.add("imgs");          
    	  try{
    		  Iterator<Match> it = s.findAll(NameImg);
    		  return it;
    	  }
          catch(FindFailed e){
        	  System.out.println(" ...No se encuentra la Imagen");
              return null;
          }
      }
      
      public void ClickLocation( int x, int y ) throws FindFailed
      {
        	Screen s = new Screen();
    		Location loc = new Location(x,y);			  
    		s.click(loc);
      }
      
      public void DobleClickImg(String NameImg, int Time) throws FindFailed {
    	  System.out.println("Realizando un click a la imagen "+NameImg);
    	  Screen s = new Screen();
    	  ImagePath.add("imgs");          
    	  try{
	    	  s.wait(NameImg,Time);
	          s.doubleClick();
    	  }
          catch(FindFailed e){
        	  System.out.println(" ...Doble Click fallido");
              e.printStackTrace();
              throw e;
          } 
    	  System.out.println(" ...Doble Click realizado con éxito");
      }
}
