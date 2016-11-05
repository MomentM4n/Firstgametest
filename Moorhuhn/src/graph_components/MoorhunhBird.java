package graph_components;

import java.awt.Graphics;
import java.util.Random;

public class MoorhunhBird {

	private int posX;
	private int posY;
	private MoorhunhBirdSheet birdSheet;
	
	private int rowInSheetID = 1;
	private int columnInSheetID = 0;
	private int movingSpeed = 3;
	
	//da se ne prebacuju slike previse brzo
	private int frameInterval = 2;
	private int frameCountdown = 0;
	
	public MoorhunhBird(MoorhunhBirdSheet sheet, int posX, int posY){
		birdSheet = sheet;
		this.posX = posX;
		this.posY = posY;
		Random r = new Random();
		movingSpeed = r.nextInt(3)+2;
		rowInSheetID = r.nextInt(3);
	}
	
	//poziva se prilikom kretanja , da bi se slicice u istoj koloni menjale
	public void update(){
		
		frameCountdown--;
		if(frameCountdown < 0){
			//pomera slicicu na sledecu u istoj koloni i moduje da ne predje preko 
			columnInSheetID = (columnInSheetID + 1) % birdSheet.getColumnCount();
			frameCountdown = frameInterval;
		}
	}
	
	public void draw(Graphics g){
	
		birdSheet.drawSheet(g, posX, posY, columnInSheetID, rowInSheetID);
	}
	
	public void move(int movX, int movY){
		posX += movX;
		posY += movY;
	}
	
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
	
	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	public int getPositionX() { return posX; }
	public int getPositionY() { return posY; }
	
}
