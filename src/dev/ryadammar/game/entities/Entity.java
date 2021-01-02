package dev.ryadammar.game.entities;

import java.awt.Graphics;

import dev.ryadammar.game.Handler;

public abstract class Entity {

	public final static int HITBOX_SUBDIVISON_X = 1;
	public final static int HITBOX_SUBDIVISON_Y = 1;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	
	protected EntityHitbox hitbox;
	protected EntityHitbox[][] subhitboxes;
	protected int hitbox_subdiv_x;
	protected int hitbox_subdiv_y;
	
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.hitbox_subdiv_x = HITBOX_SUBDIVISON_X;
		this.hitbox_subdiv_y = HITBOX_SUBDIVISON_Y;
		this.hitbox = new EntityHitbox();
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	protected abstract void gravity();
	
	protected void generateHitbox() {

		hitbox.xOffset = hitbox.x;
		hitbox.yOffset = hitbox.y;
		
		subhitboxes = new EntityHitbox[hitbox_subdiv_x][hitbox_subdiv_y];
		for(int y = 0; y < hitbox_subdiv_y; y++ ) {
			for(int x = 0; x < hitbox_subdiv_x; x++ ) {	
				subhitboxes[x][y] = new EntityHitbox
						(hitbox.x + hitbox.width*y/hitbox_subdiv_y , hitbox.y + hitbox.height*x/hitbox_subdiv_x, 
								hitbox.width/hitbox_subdiv_y, hitbox.height/hitbox_subdiv_x);
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
