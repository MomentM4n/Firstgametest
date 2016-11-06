package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class Start extends GameFrame {
	
	private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private BufferedImage background;

	public Start() {
		super("Moorhuhn RAF Rework",900, 600);
		//super("RAF Game",800, 600);
		setHighQuality(true);
		
		//initComponents();
		
		//Stavljamo da kursor misa bude unvisible
		background = Util.loadImage("pictures/start.jpg");
		initGameWindow();
		this.getWindow().setLocationRelativeTo(null);
		startThread();
		
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
		// TODO Auto-generated method stub
		g.drawImage(background, 0, 0, getWidth(), getHeight(),
			       0, 0, background.getWidth(), background.getHeight(),
			       this);
		g.drawRect (25, 215, 280, 60); 
		g.drawRect (85, 285, 150, 60);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		//System.out.println(getMouseX() + ":" + getMouseY());
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub
		if(button == GFMouseButton.Left) {
			if(getMouseX() > 25 && getMouseX() < 305 && getMouseY() > 215 && getMouseY() < 275) {
				System.out.println("new game");
				AppFrame app = new AppFrame();
				app.initGameWindow();
				getWindow().setVisible(false);	
			}
			if(getMouseX() > 85 && getMouseX() < 235 && getMouseY() > 285 && getMouseY() < 345) {
				System.out.println("quite game");
				getWindow().dispose();
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
		
	}

	@Override
	public void handleKeyUp(int keyCode) {
		// TODO Auto-generated method stub
		
	}
	
}

