package graph_components;

import java.awt.Graphics;

import rafgfxlib.GameFrame;

public class ScoreNumbers {

	private int posX;
	private int posY;
	private MoorhunhBirdSheet birdSheet;
	
	private int rowInSheetID = 1;
	private int columnInSheetID = 0;
	private int movingSpeed = 2;
	
	private int frameInterval = 4;
	private int frameCountdown = 0;
	
	public ScoreNumbers(MoorhunhBirdSheet sheet, int posX, int posY){
		
		birdSheet = sheet;
		this.posX = posX;
		this.posY = posY;
		movingSpeed = 5;
		
	}
	
	public void move(int movX, int movY){
		posX += movX;
		posY += movY;
	}
	
	public void draw(Graphics g){
		
		birdSheet.drawSheet(g, posX, posY, columnInSheetID, rowInSheetID);
	}
	
    public void update(){
		
		frameCountdown--;
		if(frameCountdown < 0){
			//pomera slicicu na sledecu u istoj koloni i moduje da ne predje preko 
			columnInSheetID = (columnInSheetID + 1) % birdSheet.getColumnCount();
			frameCountdown = frameInterval;
		}
	}
    
    public void update(GameFrame frame){
    	
    	
    	
    }
    
    public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
    
    public MoorhunhBirdSheet getSheet(){return birdSheet;  }
	public void setBirdSheet(MoorhunhBirdSheet newSheet){birdSheet = newSheet; }	
	public int getRowInSheetID() { return rowInSheetID; }
	public int getColumnInSheetID() { return columnInSheetID; }
	public int getMovingSpeed(){return movingSpeed; }
	
	public void setMovingSpeed(int movS){movingSpeed = movS;  }
	public void setRowInSheetID(int rowID){
		if(rowID >= 0 && rowID < birdSheet.getRowCount())
			rowInSheetID = rowID;
	}
	
	public void setColumnInSheetID(int columnID){
		if(columnID >= 0 && columnID < birdSheet.getColumnCount())
			columnInSheetID = columnID;
	}
	
	public int getAnimationInterval() { return frameInterval; }
	public void setAnimationInterval(int i){
		if(i >= 0)
			frameInterval = i;
	}
	public int getPositionX() { return posX; }
	public int getPositionY() { return posY; }
}
