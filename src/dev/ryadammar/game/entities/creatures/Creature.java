package dev.ryadammar.game.entities.creatures;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.Settings;
import dev.ryadammar.game.entities.Entity;
import dev.ryadammar.game.tiles.Tile;

public abstract class Creature extends Entity {
	
	public static final int DEFAULT_HEALTH = 10;
	public static final float DEFAULT_MAX_GROUND_SPEED = 2.0f;
	public static final float DEFAULT_SPRINTING_MULTIPLIER = 1.5f;
	public static final float DEFAULT_AIR_DECELERATION = 0.01f;	
	public static final int DEFAULT_NUM_JUMP = 1;
	public static final float DEFAULT_GROUND_ACCELERATION = 10.0f;
	public static final float DEFAULT_AIR_ACCELERATION = 2.0f;
	public static final float DEFAULT_JUMP_SPEED = 3.0f;
	public static final int DEFAULT_WIDTH = 64, DEFAULT_HEIGHT = 64;
	public static final boolean DEFAULT_DRAW_SPEED = false;
	
	protected int health;
	
	// Movement
	protected float Voy;
	protected float Vy;
	protected float Vox;
	protected float Vx;
	protected float g;
	protected float spf;
	protected float jumpSpeed;
	protected float maxGroundSpeed;
	protected float sprintingMultiplier;
	protected float ga;
	protected float aa;
	protected float d;

	protected int numJump;
	protected int jumpCounter;

	protected boolean drawSpeed;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		health = DEFAULT_HEALTH;
		
		//Movement
		jumpSpeed = DEFAULT_JUMP_SPEED;
		maxGroundSpeed = DEFAULT_MAX_GROUND_SPEED;
		sprintingMultiplier = DEFAULT_SPRINTING_MULTIPLIER;
		ga = (float) (DEFAULT_GROUND_ACCELERATION 
				* handler.getWorld().getScale() / (handler.getGame().getFps()^2));
		aa = (float) (DEFAULT_AIR_ACCELERATION 
				* handler.getWorld().getScale() / (handler.getGame().getFps()^2));
		d = DEFAULT_AIR_DECELERATION;
		numJump = DEFAULT_NUM_JUMP;
		
		this.g = handler.getWorld().getG();
		this.Voy = 0;
		this.Vy = 0;
		this.Vox = 0;
		this.Vx = 0;
		this.spf = handler.getGame().getSpf();
		
		drawSpeed = DEFAULT_DRAW_SPEED;

	}
	
	public void jump() {
		
		if(collisionDown()) {
			Voy = -jumpSpeed;
			y += Voy;
			jumpCounter = numJump;
		}
		else if(jumpCounter > 1) {
			Voy = -jumpSpeed;
			y += Voy;
			jumpCounter--;
		}
	}
	
	public void move(float movement, int direction) {
		
		if((Vox > 0 || direction == 1) && collisionRight()) {
			Vox = 0;
			return;
		}
		
		if((Vox < 0 || direction == -1) && collisionLeft()) {
			Vox = 0;
			return;
		}
		
		if(collisionDown()) {
			if(Vy >= 7)
				System.out.println("ouch");
			Vx = movement*direction*Math.min(Math.abs(maxGroundSpeed),Math.abs(Vox + direction*ga*spf));		
			x += Vx;
			Vox = Vx;
		}
		else {
			if(movement == 0) {
				Vx = Vox + aa*spf;
				x += Vx;
			}
			else {
			Vx = direction*Math.min(Math.abs(direction*maxGroundSpeed*aa+Vox),Math.abs(Vox + direction*aa*spf));
			if(Math.signum(Vx)==Math.signum(Vox) || Settings.bhopEnable)
				x += Vx;
			else 
				Vx *= d;
			
			Vox = Vx;
			}
		}
	}
	
	public void gravity() {
		
		if(collisionUp()) {
			Voy = 0;
			Vy = Voy;
		}
		if(!collisionDown()) {
			Vy = (Voy + g*spf);
			y += Vy;
			Voy = Vy;
		}
		else {
			Voy = 0;
			Vy = 0;
		}
	}
	
	public boolean collisionUp() {
		int ty = (int) ((y + Vy + bounds.y)/Tile.DEFAULT_TILEHEIGHT);
		return handler.getWorld().getTile((int) (x + bounds.x) / Tile.DEFAULT_TILEWIDTH, ty).isSolid()
			|| handler.getWorld().getTile((int) (x + bounds.x + bounds.width) / Tile.DEFAULT_TILEWIDTH, ty).isSolid();
	}
	
	public boolean collisionDown() {
		int ty = (int) ((y + Vy + bounds.y + bounds.height)/Tile.DEFAULT_TILEHEIGHT);
		return handler.getWorld().getTile((int) (x + bounds.x) / Tile.DEFAULT_TILEWIDTH, ty).isSolid()
			|| handler.getWorld().getTile((int) (x + bounds.x + bounds.width) / Tile.DEFAULT_TILEWIDTH, ty).isSolid();
	}
	
	public boolean collisionRight() {
		int tx = (int) ((x + Vx + bounds.x + bounds.width)/Tile.DEFAULT_TILEWIDTH);
		return handler.getWorld().getTile(tx, (int) (y + bounds.y + 6) / Tile.DEFAULT_TILEHEIGHT).isSolid()
			|| handler.getWorld().getTile(tx, (int) (y + bounds.y + bounds.height - 6) / Tile.DEFAULT_TILEHEIGHT).isSolid();
	}
	
	public boolean collisionLeft() {
		int tx = (int) ((x + Vx + bounds.x)/Tile.DEFAULT_TILEWIDTH);
		return handler.getWorld().getTile(tx, (int) (y + bounds.y + 6) / Tile.DEFAULT_TILEHEIGHT).isSolid()
			|| handler.getWorld().getTile(tx, (int) (y + bounds.y + bounds.height - 6) / Tile.DEFAULT_TILEHEIGHT).isSolid();
	}
	
	//GETTERS & SETTERS

	public int getHealth() {
		return health;
	}

	public float getMaxGroundSpeed() {
		return maxGroundSpeed;
	}

	public void setMaxGroundSpeed(float maxGroundSpeed) {
		this.maxGroundSpeed = maxGroundSpeed;
	}

	public float getSprintingMultiplier() {
		return sprintingMultiplier;
	}

	public void setSprintingMultiplier(float sprintingMultiplier) {
		this.sprintingMultiplier = sprintingMultiplier;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public float getGa() {
		return ga;
	}

	public void setGa(float ga) {
		this.ga = ga;
	}

	public float getAa() {
		return aa;
	}

	public void setAa(float aa) {
		this.aa = aa;
	}

	public float getD() {
		return d;
	}

	public void setD(float d) {
		this.d = d;
	}

	public int getNumAvailableJump() {
		return numJump;
	}

	public void setNumAvailableJump(int numAvailableJump) {
		this.numJump = numAvailableJump;
	}

	public float getVy() {
		return Vy;
	}

	public float getVx() {
		return Vx;
	}
	
	public float getVoy() {
		return Voy;
	}

	public float getVox() {
		return Vox;
	}
	
	public void setVoy(float Voy) {
		this.Voy = Voy;
	}
	
	public void setVox(float Vox) {
		this.Vox = Vox;
	}
	
	public void setVy(float Vy) {
		this.Vy = Vy;
	}
	
	public void setVx(float Vx) {
		this.Vx = Vx;
	}
	
}
