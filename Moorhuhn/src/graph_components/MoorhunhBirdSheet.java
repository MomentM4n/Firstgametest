package graph_components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import rafgfxlib.Util;

public class MoorhunhBirdSheet {

	private BufferedImage sheet = null;
	private int frameW, frameH;
	private int sheetW, sheetH;
	private int offsetX = 0, offsetY = 0;
	
	public MoorhunhBirdSheet(){
		
	}
	
	public MoorhunhBirdSheet(String imgName, int rows, int colums){
		
		sheet = Util.loadImage(imgName);
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
	
	public void setOffsetX(int x) { offsetX = x; }
	public void setOffsetY(int y) { offsetY = y; }
	public int getOffsetX() { return offsetX; }
	public int getOffsetY() { return offsetY; }
}

	

