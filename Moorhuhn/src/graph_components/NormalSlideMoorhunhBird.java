package graph_components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class NormalSlideMoorhunhBird implements SlideMoorhunhBird {

	
	private int PosX = 0;
	private int PosY = 0;
	
	private int birdDuration = 500;
	private long startTimeBird = 0;
	
	private BufferedImage image;
	private boolean isDead = false;
	private boolean stop = false;
	private boolean goingDown = false;
	private int movingSpeed = 3;
	
	public NormalSlideMoorhunhBird(String nameOfImage, int PosX, int PosY) {
		image = Util.loadImage(nameOfImage);
		this.PosX = PosX;
		this.PosY = PosY;
		Random r = new Random();
		movingSpeed = r.nextInt(3)+5;
		
	}
	
	public void move(int movX, int movY) {
		PosX += movX;
		PosY += movY;
		
	}
	
	public void update(GameFrame frame) {

		Random r = new Random();
		// Proveravamo da li je izasla cela slika, ako jeste stopiramo kretanje
		if (this.getPosY() + this.getImage().getHeight() - 70 < frame.getY() + frame.getHeight()) {
			this.setStop(true);
			this.move(0, 6);
			startTimeBird = System.currentTimeMillis();
			birdDuration = 1000;

		}
		// noramalno kretanje ka gore
		if (!this.isStop()) {
			if (!this.isGoingDown()) {
				this.move(0, -(this.getMovingSpeed()));
			}
		}
		// stopirano kretanje
		else {
			this.move(0, 0);

		}
		// Proveravamo da li je proslo 3 sekunde, ako jeste spustamo pticu dole
		if (startTimeBird != 0 && birdDuration - (System.currentTimeMillis() - startTimeBird) <= 0) {
			this.setGoingDown(true);
			this.setStop(false);
			this.setMovingSpeed(11);

		}
		// krecemo se ka dole
		if (!this.isStop() && this.isGoingDown()) {
			this.move(0, this.getMovingSpeed());
		}

		// ako je slika spustena , postavljamo je na random poziciju
		if (this.getPosY() > frame.getY() + frame.getHeight() && this.isGoingDown()) {
			this.setGoingDown(false);
			this.setStop(false);
			startTimeBird = 0;
			this.setMovingSpeed(r.nextInt(3)+5);
			this.setPosition(frame.getX() + r.nextInt(frame.getWidth() - frame.getX()),
					(frame.getY() + frame.getHeight() + 200)
							+ r.nextInt(3000 - (frame.getY() + frame.getHeight() + 1500)));
		}

	}

	public void setPosition(int PosX, int PosY){
		this.PosX = PosX;
		this.PosY = PosY;
	}
	
	public int getPosX() {return PosX; }
	public int getPosY() {return PosY; } 
	public boolean isDead() {return isDead; }
	public void setDead(boolean isDead) {this.isDead = isDead; }
	public int getMovingSpeed() {return movingSpeed; }
	public void setMovingSpeed(int movingSpeed) {this.movingSpeed = movingSpeed; }
	

	public boolean isGoingDown() {return goingDown;}
	public void setGoingDown(boolean goingDown) {this.goingDown = goingDown;}
	public boolean isStop() {return stop; }
	public void setStop(boolean stop) {this.stop = stop; }

	public BufferedImage getImage() {return image; }

	@Override
	public void draw(Graphics g, int posX, int posY) {
		
		g.drawImage(image, posX, posY, null);
		
	}
	
}
