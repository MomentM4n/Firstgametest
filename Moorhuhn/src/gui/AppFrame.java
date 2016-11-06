package gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import graph_components.Bullets;
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
	
	private BufferedImage backgroundImage;
	private BufferedImage sniperImage;
	
	private Sniper sniper;
	Bullets bullets;
	
	private int offX;
	private int offY;
	
	private AudioClip shootSound;
	private AudioClip emptySound;
	private AudioClip reloadSound;
	
	
	
	public AppFrame() {
		
		//Postavljamo prozor aplikacije na sredinu ekrana = NEUSPESNO
		super("RAF Game",900, 600);
		//super("RAF Game",800, 600);
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
		
		offX = 0;
		offY = 0;
		music();
	}

	public void initComponents(){
		bullets = new Bullets(8); 
		sniper = new Sniper();
		backgroundImage = Util.loadImage("pictures/background.jpg");
       // BufferedImage im = Util.loadImage("pictures/final.png");
		sniperImage = Util.loadImage("pictures/sniper.png");
		shootSound = Applet.newAudioClip(getClass().getResource("/sounds/shootsound.wav"));
		reloadSound = Applet.newAudioClip(getClass().getResource("/sounds/reloadsound.wav"));
		emptySound = Applet.newAudioClip(getClass().getResource("/sounds/emptysound.wav"));
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
		int x = (backgroundImage.getWidth()-getWidth())/2+offX;
		//Util.clamp(x, 10, getWidth());
		int y = (backgroundImage.getHeight()-getHeight())/2+offY;
		//Util.clamp(y, 10, getHeight());
		if(x < 0) {
			x = 0;
		}
		if(x > (backgroundImage.getWidth()-getWidth())){
			x = (backgroundImage.getWidth()-getWidth());
		}
		if(y < 0) {
			y = 1;
		}
		if(y > (backgroundImage.getHeight()-getHeight()-50)){
			y = (backgroundImage.getHeight()-getHeight()-50);
		}
		System.out.println(x + ":" + y);
		System.out.println(backgroundImage.getWidth());
		System.out.println(getWidth());
		System.out.println(offX);
		
		BufferedImage img = backgroundImage;
	    img = img.getSubimage(x, y, this.getWindow().getWidth(), this.getWindow().getHeight());
	    g.drawImage(img,0,0,this);
		//System.out.println(x + " " + y);
		
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
		
		for(int i = 0; i < bullets.getCurrentNumberOfBullets(); i++) {
			g.drawImage(bullets.getBulletImg(), getWidth()-50 - i*30, getHeight()-100, this);
		}
		
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
		
		int mouseX = getMouseX();
		int mouseY = getMouseY();
		
		if(mouseX >= getWidth()-100) {
			offX+=10;
		}else if(mouseX <= 100) {
			offX-=10;
		}else if(mouseY >= getHeight() - 100) {
			offY+=10;
		}else if(mouseY <= 100) {
			offY-=10;
		}
	}
	
	public void music() { 
		URL url = Util.class.getClassLoader().getResource("sounds/bgsound.wav");
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(url.toURI()));
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playSound(AudioClip clip) {
		clip.play();
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub
		if(button == GFMouseButton.Left){
			   for (MoorhunhBird bird : flyingBirds.keySet()) {
			    if(flyingBirds.get(bird).equals("left") && bird.getRowInSheetID() == 0 && sniper.contains(bird.getPositionX(),
			      bird.getPositionY(), bird.getSheet().getFrameWidth() - 90, bird.getSheet().getFrameHeight() - 85)){
			     System.out.println("Pogodili smo pticurinu levu, veliku");
			     
			    }
			    if(flyingBirds.get(bird).equals("right") && bird.getRowInSheetID() == 0 && sniper.contains(bird.getPositionX()+57,
			      bird.getPositionY(), bird.getSheet().getFrameWidth() - 90 , bird.getSheet().getFrameHeight() - 85)){
			     System.out.println("Pogodili smo pticurinu desnu, veliku");
			    }
			    if(flyingBirds.get(bird).equals("left") && bird.getRowInSheetID() == 1 && sniper.contains(bird.getPositionX()+27,
			      bird.getPositionY(), bird.getSheet().getFrameWidth() - 100 , bird.getSheet().getFrameHeight() - 85)){
			     System.out.println("Pogodili smo pticurinu desnu, srednju");
			    }
			    if(flyingBirds.get(bird).equals("right") && bird.getRowInSheetID() == 1 && sniper.contains(bird.getPositionX()+57,
			      bird.getPositionY(), bird.getSheet().getFrameWidth() - 90 , bird.getSheet().getFrameHeight() - 85)){
			     System.out.println("Pogodili smo pticurinu desnu, srednju");
			    }
			   }
			   bullets.setCurrentNumberOfBullets(bullets.getCurrentNumberOfBullets()-1);
			   if(bullets.getCurrentNumberOfBullets() == 0) {
				   playSound(emptySound);
			   }else{
				   playSound(shootSound); 
			   }
			  }
		if(button == GFMouseButton.Right) {
			if(bullets.getCurrentNumberOfBullets() == 0){
				bullets.setCurrentNumberOfBullets(bullets.getMaxNumberOfBullets());
				playSound(reloadSound);
			}
		}
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
		if(keyCode == KeyEvent.VK_SPACE) {
			if(bullets.getCurrentNumberOfBullets() == 0){
				bullets.setCurrentNumberOfBullets(bullets.getMaxNumberOfBullets());
				playSound(reloadSound);
			}
		}
	}

	@Override
	public void handleKeyUp(int keyCode) {
		// TODO Auto-generated method stub

	}

}
