package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import graph_components.MoorhunhBird;
import graph_components.MoorhunhBirdSheet;
import graph_components.Sniper;
import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class AppFrame extends GameFrame {

	private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Ovde cuvamo ptice. Pri cemu je KEY u hasmap-i ptica(MoorhunhBird), a VALUE 
	// predstavlja smer letenja ptica ("left" or "right").
	private HashMap<MoorhunhBird, String> flyingBirds;
	
	private MoorhunhBirdSheet birdSheetLeft;
	private MoorhunhBirdSheet birdSheetRight;
	
	private BufferedImage backgroundImage ;
	private BufferedImage sniperImage;
	
	private Sniper sniper ;
	
	public AppFrame() {
		
		//Postavljamo prozor aplikacije na sredinu ekrana = NEUSPESNO
		super("RAF Game",dim.width - dim.width/3, dim.height - 100);
		setHighQuality(true);
		System.out.println(this.getX());
		//centerFrame();
		System.out.println(this.getX());
		
		
		initComponents();
		
		//Stavljamo da kursor misa bude unvisible
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
			    cursorImg, new Point(0, 0), "blank cursor");
		this.setCursor(c);
		initBird();
		initGameWindow();
		this.getWindow().setLocationRelativeTo(null);
		
		startThread();
	}

	public void initComponents(){
		
		sniper = new Sniper();
		backgroundImage = Util.loadImage("pictures/background.jpg");
       // BufferedImage im = Util.loadImage("pictures/final.png");
		sniperImage = Util.loadImage("pictures/sniper.png");
		
	}
	
	private void centerFrame() {

        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;    
        this.setLocation(dx, dy);
}
	
	public void initBird(){
		
		birdSheetLeft = new MoorhunhBirdSheet("pictures/birdLeftFinal.png", 4, 4);
		birdSheetLeft.setOffsets(32, 64);
		
		birdSheetRight = new MoorhunhBirdSheet("pictures/birdRightFinal.png", 4, 4);
		birdSheetRight.setOffsets(32, 64);
		
		//initBirds
		flyingBirds = new HashMap<>();
		Random r = new Random();
		//Konstruisemo 5 ptica koje lete na levu stranu
		for (int i = 0; i < 5; i++) {
			MoorhunhBird bird = new MoorhunhBird(birdSheetLeft, r.nextInt(2000), r.nextInt(dim.height-200));
			bird.setAnimationInterval((5%bird.getMovingSpeed()) + 1);
			flyingBirds.put(bird, "left");
			
		}
		//Konstruisemo 5 ptica koje lete na desnu stranu
		for (int i = 0; i < 5; i++) {
			MoorhunhBird bird = new MoorhunhBird(birdSheetRight, r.nextInt(2000), r.nextInt(dim.height-200));
			bird.setAnimationInterval((5%bird.getMovingSpeed()) + 1);
			flyingBirds.put(bird, "right");
			
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
		for (MoorhunhBird moorhunhBird : flyingBirds.keySet()) {
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
		for (MoorhunhBird bird : flyingBirds.keySet()) {
			//Proveravamo da li ptica leti u levo, ako da, pomeramo je u levo i
			//proveravamo da li izlazi iz sa frame-a u levo, ako je izasla, vracamo je 
			//na random poziciju u desno sa random velicinom i random brzinom kretanja(brzinom pokreta krila).
			if(flyingBirds.get(bird).equals("left")){
				bird.move(-(bird.getMovingSpeed()), 0);
				if(bird.getPositionX() + bird.getSheet().getFrameWidth() < this.getX()){
					bird.setMovingSpeed(r.nextInt(3)+3);
					bird.setRowInSheetID(r.nextInt(3));
					int minW = this.getX() + this.getWidth() + bird.getSheet().getFrameWidth();
					int maxW = 2*dim.width;
					int minH = 50;
					int maxH = dim.height - 320;
					bird.setPosition(minW + r.nextInt(maxW - minW), minH + r.nextInt(maxH - minH));
					bird.setAnimationInterval((5%bird.getMovingSpeed()) + 1);
				}
			}
			//ako ptica ne lete u lefo (leti u desno) pomeramo je u desno i radimo isto
			//kao i za "levo" samo sto je pomeramo na random poziciju levo.
			else{
				bird.move(bird.getMovingSpeed(), 0);
			    if(bird.getPositionX() > this.getX() + this.getWidth()){
			    	bird.setMovingSpeed(r.nextInt(3)+3);
					bird.setRowInSheetID(r.nextInt(3));
					int minW = -2*dim.width;
					int maxW = this.getX() - bird.getSheet().getFrameWidth();
					int minH = 50;
					int maxH = dim.height - 320;
					bird.setPosition(minW + r.nextInt(maxW - minW), minH + r.nextInt(maxH - minH));
					bird.setAnimationInterval((5%bird.getMovingSpeed()) + 1);
			    }
				
			}
			
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
