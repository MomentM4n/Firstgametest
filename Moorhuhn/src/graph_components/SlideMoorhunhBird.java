package graph_components;

import java.awt.Graphics;

import rafgfxlib.GameFrame;

public interface SlideMoorhunhBird {

	public void move(int movX, int movY);
	public void draw(Graphics g,int posX, int posY);
	public void update(GameFrame frame);
}
