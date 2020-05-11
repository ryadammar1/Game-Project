package dev.ryadammar.game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ryadammar.game.Handler;

public class Background {
		
	private BufferedImage texture;
	private int xScrollingDiv, yScrollingDiv;
	private Handler handler;
	
	public Background(Handler handler, BufferedImage texture, int xScrollingDiv, int yScrollingDiv) {
		this.texture = texture;
		this.xScrollingDiv = xScrollingDiv;
		this.yScrollingDiv = yScrollingDiv;
		this.handler = handler;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(ImageCropper.crop(texture, 
				(int) (handler.getGameCamera().getxOffset()/xScrollingDiv), 
				(int) (handler.getGameCamera().getyOffset()/yScrollingDiv), 
				handler.getWidth(), handler.getHeight()), 0, 0, null);
	}
		
}
