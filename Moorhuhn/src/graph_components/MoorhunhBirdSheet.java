package graph_components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JOptionPane;

import rafgfxlib.Util;

public class MoorhunhBirdSheet {

	private BufferedImage sheet = null;
	private BufferedImage invertSheet = null;
	private String imagePath;
	private int frameW, frameH;
	private int sheetW, sheetH;
	private int offsetX = 0, offsetY = 0;
	private boolean invert = false;
	
	public MoorhunhBirdSheet(){
		
	}
	
	public MoorhunhBirdSheet(String imgName, int rows, int colums){
		
		sheet = Util.loadImage(imgName);
		imagePath = imgName;
		if(sheet == null){
			sheet = null;
			System.out.println("Ne ucitava sliku dobro");
			JOptionPane.showMessageDialog(null, "Error loading image");
		}
		
		sheetW = rows;
		sheetH = colums;
		frameW = sheet.getWidth()/sheetW;
		frameH = sheet.getHeight()/sheetH;
		
	}
	//COPY CONSTRUCTOR
	public MoorhunhBirdSheet(MoorhunhBirdSheet newSheet){
		this(newSheet.getImagePath(),newSheet.getColumnCount(),newSheet.getRowCount());
	}
	
	public int getColumnCount() { return sheetW; }
	public int getRowCount() { return sheetH; }
	public int getFrameWidth() { return frameW; }
	public int getFrameHeight() { return frameH; }
	
	public void drawSheet(Graphics g,int posX, int posY, int frameX, int frameY ){
		
		if(sheet == null) return;
		if(frameX < 0 || frameY < 0 || frameX >= sheetW || frameY >= sheetH) return;
		
		g.drawImage(sheet,
				posX - offsetX, posY - offsetY, 
				posX - offsetX + frameW, posY - offsetY + frameH, 
				frameX * frameW, frameY * frameH, 
				frameX * frameW + frameW, frameY * frameH + frameH, 
				null);
		
	}
	
	public void setOffsets(int x, int y){
		offsetX = x;
		offsetY = y;
	}
	
	/*public void invertSheet() {
		WritableRaster source = sheet.getRaster();
		System.out.println(source.getWidth() + ":" + source.getHeight());
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
		int rgb[] = new int[4];
		
		for(int y = 0; y < source.getHeight(); y++) {
			for(int x = 0; x < source.getWidth(); x++) {
				
				source.getPixel(x, y, rgb);
				
				// Negativ slike dobijamo radeci komplement svih komponenti,
				// odnosno, novoR = 255 - staroR, itd, gdje ce onda (0,0,0)
				// postati (255,255,255), itd, sto i ocekujemo.
				rgb[0] = 255 - rgb[0];
				rgb[1] = 255 - rgb[1];
				rgb[2] = 255 - rgb[2];
				rgb[3] = 255 - rgb[3];
				
				target.setPixel(x, y, rgb);
			}
		}
		sheet = Util.rasterToImage(target);
	}*/
	
	public void invertSheet() {
		for(int y = 0; y < sheet.getHeight(); y++) {
			for(int x = 0; x < sheet.getWidth(); x++) {
				int pixel = sheet.getRGB(x, y);
				int neg = 0xFFFFFF - pixel;
				int neg1 = pixel;
				if(! ((neg1>>24) == 0x00) ) { //detects if pixel is transparent if it is just skip it

					neg1 = (0xFFFFFF - pixel) | 0xFF000000;
				}
				sheet.setRGB(x, y, neg1);
			}
		}
		invert = true;
	}
	
	public void setOffsetX(int x) { offsetX = x; }
	public void setOffsetY(int y) { offsetY = y; }
	public int getOffsetX() { return offsetX; }
	public int getOffsetY() { return offsetY; }
	public BufferedImage getSheet() {return sheet; }
	public void setSheet(BufferedImage sheet) {this.sheet = sheet; }
	public String getImagePath() {return imagePath; }
	public boolean isInvert() {return invert; }
	
	
}

	

