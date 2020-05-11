package dev.ryadammar.game.gfx;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.Entity;
import dev.ryadammar.game.tiles.Tile;

public class GameCamera {

	private Handler handler;
	private float xOffset, yOffset;

	public void move(float xAmt, float yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
		checkBounds();
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBounds();
	}
	
	public GameCamera(Handler handler, float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.handler = handler;
	}
	
	public void checkBounds() {
		if(xOffset < 0)
			xOffset = 0;
		else if(xOffset > handler.getWorld().getWidth() * Tile.DEFAULT_TILEWIDTH - handler.getWidth())
			xOffset = handler.getWorld().getWidth() * Tile.DEFAULT_TILEWIDTH - handler.getWidth();
		if(yOffset < 0)
			yOffset = 0;
		else if(yOffset > handler.getWorld().getHeight() * Tile.DEFAULT_TILEHEIGHT - handler.getHeight())
			yOffset = handler.getWorld().getHeight() * Tile.DEFAULT_TILEHEIGHT - handler.getHeight();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
	
}
