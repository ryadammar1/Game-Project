package dev.ryadammar.game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ryadammar.game.Handler;

public abstract class Entity {

	public static final boolean DEFAULT_DRAW_COLLISIONS = false;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean drawColisions;
	
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		
		bounds = new Rectangle(0, 0, width, height);
		drawColisions = DEFAULT_DRAW_COLLISIONS;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void gravity();
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
