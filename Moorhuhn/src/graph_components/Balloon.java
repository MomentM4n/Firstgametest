package graph_components;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import graph_components.Feathers.Feather;
import rafgfxlib.Util;

public class Balloon {

	private BufferedImage balloonImage;
	private int speed;
	public float posX;
	public float posY;
	public float dX;
	public float dY;

	public Balloon() {
		Random r = new Random();
		balloonImage = Util.loadImage("pictures/balloon.png");
		dX = 0;
		dY = 5;
		posY = 500;
		posX = 500;
				//r.nextInt(700)+100;
	}

	public void render(Graphics2D g, int sw, int sh) {
		
		
		AffineTransform transform = new AffineTransform();

		transform.setToIdentity();
		transform.translate(posX, posY);
		//transform.translate(-16.0, -16.0);

		g.drawImage(balloonImage, 500, 500, null);

	}
	
	public void restart() {
		Random r = new Random();
		balloonImage = Util.loadImage("pictures/balloon.png");
		dX = 0;
		dY = 5;
		posY = 700;
		posX = r.nextInt(700)+100;
	}

	public void update() {

		//posX += dX;
		//posY -= dY;
		dX *= 0.99f;
		dY *= 0.99f;
		dY += 0.1f;
	}

}
