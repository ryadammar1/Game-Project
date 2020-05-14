package dev.ryadammar.game.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.gfx.ImageCropper;

public class Scene {

	private BufferedImage texture;
	private BufferedImage alpha;
	private SceneCollision collision;
	private Handler handler;
	private int xScrollingDiv, yScrollingDiv;
	
	public Scene(Handler handler, BufferedImage texture, BufferedImage alpha, int xScrollingDiv, int yScrollingDiv, boolean isSolid) {
		
		this.texture = texture;
		if (isSolid)
			this.collision = new SceneCollision(alpha);
		else
			this.collision = null;
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
		
		/*
		 * if(isSolid) { for(Point point : colisionBox.getOutline()) {
		 * g.setColor(Color.MAGENTA); g.drawOval( (int)(point.x -
		 * handler.getGameCamera().getxOffset()/xScrollingDiv), (int)(point.y -
		 * handler.getGameCamera().getyOffset()/yScrollingDiv), 1, 1); } }
		 */
	}

	public SceneCollision getCollision() {
		return collision;
	}
	
}
