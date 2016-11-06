package graph_components;

import java.awt.image.BufferedImage;

import rafgfxlib.Util;

public class Bullets {

	private int currentNumberOfBullets;
	private int maxNumberOfBullets;
	private BufferedImage bulletImg;

	public Bullets(int max) {
		this.maxNumberOfBullets = max;
		currentNumberOfBullets = max;
		bulletImg = Util.loadImage("pictures/bullet.png");
	}

	public int getCurrentNumberOfBullets() {
		return currentNumberOfBullets;
	}

	public void setCurrentNumberOfBullets(int currentNumberOfBullets) {
		if (currentNumberOfBullets <= 0) {
			this.currentNumberOfBullets = 0;
		} else {
			this.currentNumberOfBullets = currentNumberOfBullets;
		}
	}

	public int getMaxNumberOfBullets() {
		return maxNumberOfBullets;
	}

	public void setMaxNumberOfBullets(int maxNumberOfBullets) {
		this.maxNumberOfBullets = maxNumberOfBullets;
	}

	public BufferedImage getBulletImg() {
		return bulletImg;
	}

	public void setBulletImg(BufferedImage bulletImg) {
		this.bulletImg = bulletImg;
	}

}
