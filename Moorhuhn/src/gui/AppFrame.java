package gui;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import graph_components.MoorhunhBird;
import graph_components.MoorhunhBirdSheet;
import graph_components.Sniper;
import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class AppFrame extends GameFrame {

	private ArrayList<MoorhunhBird> birds;
	private MoorhunhBirdSheet birdSheet;
	
	private BufferedImage backgroundImage ;
	private BufferedImage sniperImage;
	
	private Sniper sniper ;
	
	public AppFrame() {
		
		super("RAF Game", 880, 680);
		setHighQuality(true);
		initComponents();
		
		//Stavljamo da kursor misa bude unvisible
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
			    cursorImg, new Point(0, 0), "blank cursor");
		this.setCursor(c);
		initBird();
		
		startThread();
	}

	public void initComponents(){
		
		sniper = new Sniper();
		backgroundImage = Util.loadImage("pictures/unnamed.png");
		sniperImage = Util.loadImage("pictures/sniper.png");
		
	}
	
	public void initBird(){
		
		birdSheet = new MoorhunhBirdSheet("pictures/birdLeftFinal.png", 4, 4);
		birdSheet.setOffsets(32, 64);
		
		//initBirds
		birds = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			MoorhunhBird bird = new MoorhunhBird(birdSheet, r.nextInt(2000), r.nextInt(700));
			bird.setAnimationInterval(r.nextInt(3)+2);
			birds.add(bird);
		}
		
		
		
		
	}
	
	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		
		g.drawImage(backgroundImage,0,0,this);
		
		//Iscrtavanje ptica
		for (MoorhunhBird moorhunhBird : birds) {
			moorhunhBird.draw(g);
		}
		
		//Iscrtavanje Snipera na ekranu i pamcenje kordinata u modelu
		sniper.setX(getMouseX());
		sniper.setY(getMouseY());
		sniper.setWidth(sniperImage.getWidth());
		sniper.setHeight(sniperImage.getHeight());
		g.drawImage(sniperImage, getMouseX()- 20, getMouseY() - 20, this);
		
		
		
	

	}

	@Override
	public void update() {
		
		Random r = new Random();
		for (MoorhunhBird bird : birds) {
			bird.move(-(bird.getMovingSpeed()), 0);
			bird.update();
		}
		

	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseMove(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyDown(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleKeyUp(int keyCode) {
		// TODO Auto-generated method stub

	}

}
