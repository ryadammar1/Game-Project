package dev.ryadammar.game.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.gfx.ImageCropper;

public class Scene {

	private BufferedImage texture;
	private BufferedImage alpha;
	private ColisionBox colisionBox;
	private Handler handler;
	private int xScrollingDiv, yScrollingDiv;
	private boolean isSolid;
	
	public Scene(Handler handler, BufferedImage texture, BufferedImage alpha, int xScrollingDiv, int yScrollingDiv, boolean isSolid) {
		
		this.texture = texture;
		if (isSolid)
			this.colisionBox = new ColisionBox(alpha);
		else
			this.colisionBox = null;
		this.xScrollingDiv = xScrollingDiv;
		this.yScrollingDiv = yScrollingDiv;
		this.handler = handler;
		this.isSolid = isSolid;
		
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

	public ColisionBox getColisionBox() {
		return colisionBox;
	}
	
}
