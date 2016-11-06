package graph_components;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import rafgfxlib.Util;

public class Feathers {

	private int numberofFeathers = 40;
	private BufferedImage featherImage;
	
	public static class Feather {
		public float posX;
		public float posY;
		public float dX;
		public float dY;
		public int life = 0;
		public int lifeMax = 0;
		public int imageID = 0;
		public float angle = 0.0f;
		public float rot = 0.0f;
	}
	
	private Feather[] parts = new Feather[numberofFeathers];
	
	
	public Feathers() {
		
		for(int i = 0; i < numberofFeathers; ++i)
		
			parts[i] = new Feather(); 
			featherImage = Util.loadImage("pictures/final.png");
	}
	
	public void render(Graphics2D g, int sw, int sh)
	{
		AffineTransform transform = new AffineTransform();
		
		for(Feather p : parts)
		{
			if(p.life <= 0) continue;
			
			transform.setToIdentity();
			transform.translate(p.posX, p.posY);
			transform.rotate(p.angle);
			transform.translate(-16.0, -16.0);
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    (float)p.life / (float)p.lifeMax));

			g.drawImage(featherImage, transform, null);
		}
		
	}
	
	public void update(){
		
		for(Feather p : parts)
		{
			if(p.life <= 0) continue;
			
			p.life--;
			p.posX += p.dX;
			p.posY += p.dY;
			p.dX *= 0.99f;
			p.dY *= 0.99f;
			p.dY += 0.1f;
			p.angle += p.rot;
			p.rot *= 0.99f;
		}
	}
	
	public void genEx(float cX, float cY, float radius, int life, int count)
	{
		for(Feather p : parts)
		{
			if(p.life <= 0)
			{
				p.life = p.lifeMax = (int)(Math.random() * life * 0.5) + life / 2;
				p.posX = cX;
				p.posY = cY;
				double angle = Math.random() * Math.PI * 2.0;
				double speed = Math.random() * radius;
				p.dX = (float)(Math.cos(angle) * speed);
				p.dY = (float)(Math.sin(angle) * speed);
				p.angle = (float)(Math.random() * Math.PI * 2.0);
				p.rot = (float)(Math.random() - 0.5) * 0.3f;
				
				count--;
				if(count <= 0) return;
			}
		}
	}
}
