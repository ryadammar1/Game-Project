package dev.ryadammar.game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ryadammar.game.Handler;

public abstract class Entity {

	public final static int HITBOX_SUBDIVISON_X = 1;
	public final static int HITBOX_SUBDIVISON_Y = 1;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	
	protected Rectangle hitbox;
	protected Rectangle[][] subhitboxes;
	protected int hitbox_subdiv_x;
	protected int hitbox_subdiv_y;

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
		this.hitbox_subdiv_x = HITBOX_SUBDIVISON_X;
		this.hitbox_subdiv_y = HITBOX_SUBDIVISON_Y;
		this.hitbox_x = 0;
		this.hitbox_y = 0;
		this.hitbox_height = height;
		this.hitbox_width = width;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void gravity();
	
	protected void generateHitbox() {
		subhitboxes = new Rectangle[hitbox_subdiv_x][hitbox_subdiv_y];
		for(int y = 0; y < hitbox_subdiv_y; y++ ) {
			for(int x = 0; x < hitbox_subdiv_x; x++ ) {	
				subhitboxes[x][y] = new Rectangle
						(hitbox_x + hitbox_width*y/hitbox_subdiv_y , hitbox_y + hitbox_height*x/hitbox_subdiv_x, 
								hitbox_width/hitbox_subdiv_y, hitbox_height/hitbox_subdiv_x);
			}
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
