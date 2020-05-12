package dev.ryadammar.game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ryadammar.game.Handler;

public abstract class Entity {
	
	public final static int BOUND_SUBDIVISON = 1;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	
	protected Rectangle[] subhitboxes;
	protected int bsubdiv;

	protected int hitbox_x;
	protected int hitbox_y;
	protected int hitbox_height;
	protected int hitbox_width;
	
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		this.bsubdiv = BOUND_SUBDIVISON;
		this.hitbox_x = 0;
		this.hitbox_y = 0;
		this.hitbox_height = height;
		this.hitbox_width = width;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void gravity();
	
	protected void generateHitbox() {
		subhitboxes = new Rectangle[bsubdiv];
		for(int i = 0; i < bsubdiv; i++ ) {	
			subhitboxes[i] = new Rectangle(hitbox_x, hitbox_y + hitbox_height*i/bsubdiv, hitbox_width, hitbox_height/bsubdiv);
		}
	}
	
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
