package dev.ryadammar.game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.Settings;
import dev.ryadammar.game.entities.Entity;
import dev.ryadammar.game.gfx.Animation;
import dev.ryadammar.game.tiles.Tile;
import dev.ryadammar.game.utils.Utils;

public abstract class Creature extends Entity {

	public static final int DEFAULT_HEALTH = 10;
	public static final float DEFAULT_GROUND_SPEED = 2.0f;
	public static final float DEFAULT_SPRINTING_MULTIPLIER = 1.5f;
	public static final float DEFAULT_AIR_DECELERATION = 0.01f;
	public static final int DEFAULT_MAX_JUMP_NUM = 1;
	public static final float DEFAULT_GROUND_ACCELERATION = 10.0f;
	public static final float DEFAULT_AIR_ACCELERATION = 2.0f;
	public static final float DEFAULT_JUMP_SPEED = 3.0f;
	public static final int DEFAULT_WIDTH = 64, DEFAULT_HEIGHT = 64;
	public static final boolean DEFAULT_DRAW_SPEED = false;

	protected int health;

	// Movement

	protected enum Action {
		idle, walk, sprint, fall
	}

	protected Action action;

	protected enum Direction {
		right, left
	}

	protected Direction direction;

	protected float Voy;
	protected float Vy;
	protected float Vox;
	protected float Vx;
	protected float g;
	protected float spf;
	protected float ga;
	protected float aa;
	protected float d;

	protected float jumpSpeed;
	protected float groundSpeed;
	protected float sprintMult;

	protected int maxJumpsNum;

	// Animation

	protected Animation anim_walk_r;
	protected Animation anim_walk_l;
	protected Animation anim_sprint_r;
	protected Animation anim_sprint_l;
	protected Animation anim_idle_l;
	protected Animation anim_idle_r;
	protected Animation anim_jump_l;
	protected Animation anim_jump_r;

	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		health = DEFAULT_HEALTH;

		action = Action.idle;
		direction = Direction.right;

		jumpSpeed = DEFAULT_JUMP_SPEED;
		groundSpeed = DEFAULT_GROUND_SPEED;
		sprintMult = DEFAULT_SPRINTING_MULTIPLIER;
		ga = (float) (DEFAULT_GROUND_ACCELERATION * handler.getWorld().getScale() / (handler.getGame().getFps() ^ 2));
		aa = (float) (DEFAULT_AIR_ACCELERATION * handler.getWorld().getScale() / (handler.getGame().getFps() ^ 2));
		d = DEFAULT_AIR_DECELERATION;
		maxJumpsNum = DEFAULT_MAX_JUMP_NUM;

		g = handler.getWorld().getG();
		Voy = 0;
		Vy = 0;
		Vox = 0;
		Vx = 0;
		spf = handler.getGame().getSpf();
	}

	// Movement

	protected int jumpCounter;

	public void jump() {

		if (collisionDown()) {
			Voy = -jumpSpeed;
			y += Voy;
			jumpCounter = maxJumpsNum;
		} else if (jumpCounter > 1) {
			Voy = -jumpSpeed;
			y += Voy;
			jumpCounter--;
		}
	}

	public void move(float movement, int direction) {
			
		if ((Vox > 0 || direction == 1) && collisionRight()) {
			Vox *= -0.5;
			return;
		}

		if ((Vox < 0 || direction == -1) && collisionLeft()) {
			Vox *= -0.5;
			return;
		}

		if (collisionDown()) {
			if (Vy >= 5)
				health -= (int) (Vy - 5);
			Vx = movement * direction * Math.min(Math.abs(groundSpeed), Math.abs(Vox + direction * ga * spf));
			x += Vx;
			Vox = Vx;
		} else {
			if (movement == 0) {
				Vx = Vox + aa * spf;
				x += Vx;
			} else {
				Vx = direction * Math.min(Math.abs(direction * groundSpeed * aa + Vox),
						Math.abs(Vox + direction * aa * spf));
				if (Math.signum(Vx) == Math.signum(Vox) || Settings.bhopEnable)
					x += Vx;
				else
					Vx *= d;

				Vox = Vx;
			}
		}
	}

	public void action() {
		if (Math.abs(Vy) > 0)
			action = Action.fall;
		else if (Math.abs(Vx) < 0.5)
			action = Action.idle;
		else if (Math.abs(Vx) < groundSpeed * sprintMult)
			action = Action.walk;
		else if (Math.abs(Vx) >= groundSpeed * sprintMult)
			action = Action.sprint;
	}

	public void direction() {
		if (Vx > 0.3)
			direction = Direction.right;
		if (Vx < -0.3)
			direction = Direction.left;
	}

	public void gravity() {

		if (collisionUp()) {
			Voy = 0;
			Vy = Voy;
		}
		if (!collisionDown()) {
			Vy = (Voy + g * spf);
			y += Vy;
			Voy = Vy;
		} else {
			Voy = 0;
			Vy = 0;
		}
	}

	// Collision
	
	public boolean collisionUp() {
		
		boolean collides = false;
		ArrayList<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size()-1).getCollision().getOutline();
		
		for(Rectangle subhitbox : subhitboxes) {
		if  (sceneCollision.contains
			(new Point((int) (x + subhitbox.x + subhitbox.width),(int) ((y + Vy + subhitbox.y + 1))))
			|| sceneCollision.contains
			(new Point((int) (x + subhitbox.x),(int) ((y + Vy + subhitbox.y + 1))))) {
			collides = true;
			break;
			}
		}
		
		return collides || collisionUpTile();
	}

	public boolean collisionDown() {
		boolean collides = false;
		ArrayList<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size()-1).getCollision().getOutline();
		
		for(Rectangle subhitbox : subhitboxes) {
		if  (sceneCollision.contains
			(new Point((int) (x + subhitbox.x + subhitbox.width),(int) ((y + Vy + subhitbox.y + subhitbox.height + 1))))
			|| sceneCollision.contains
			(new Point((int) (x + subhitbox.x),(int) ((y + Vy + subhitbox.y + subhitbox.height + 1))))) {
			collides = true;
			break;
			}
		}
		
		return collides || collisionDownTile();
	}

	public boolean collisionRight() {
		boolean collides = false;
		ArrayList<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size()-1).getCollision().getOutline();
		
		for(Rectangle subhitbox : subhitboxes) {
		if  (sceneCollision.contains
			(new Point((int) (x + subhitbox.x + Vx + subhitbox.width),(int) ((y + subhitbox.y + subhitbox.height))))
			|| sceneCollision.contains
			(new Point((int) (x + subhitbox.x + Vx + subhitbox.width),(int) ((y + subhitbox.y))))) {
			collides = true;
			break;
			}
		}
		
		return collides || collisionRightTile();
	}

	public boolean collisionLeft() {
		boolean collides = false;
		ArrayList<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size()-1).getCollision().getOutline();
		
		for(Rectangle subhitbox : subhitboxes) {
		if  (sceneCollision.contains
			(new Point((int) (x + subhitbox.x + Vx),(int) ((y + subhitbox.y + subhitbox.height))))
			|| sceneCollision.contains
			(new Point((int) (x + subhitbox.x + Vx),(int) ((y + subhitbox.y))))) {
			collides = true;
			break;
			}
		}
		
		return collides || collisionLeftTile();
	}

	public boolean collisionUpTile() {
		
		for(Rectangle subhitbox : subhitboxes) {
			int ty = (int) ((y + Vy + subhitbox.y) / Tile.DEFAULT_TILEHEIGHT);
			if (handler.getWorld().getTile((int) (x + subhitbox.x) / Tile.DEFAULT_TILEWIDTH, ty).isSolid() || 
					handler.getWorld().getTile((int) (x + subhitbox.x + subhitbox.width) / Tile.DEFAULT_TILEWIDTH, ty).isSolid())
				return true;
		}
		return false;
	}

	public boolean collisionDownTile() {
		
		for(Rectangle subhitbox : subhitboxes) {
			int ty = (int) ((y + Vy + subhitbox.y + subhitbox.height) / Tile.DEFAULT_TILEHEIGHT);
			if (handler.getWorld().getTile((int) (x + subhitbox.x) / Tile.DEFAULT_TILEWIDTH, ty).isSolid() || 
					handler.getWorld().getTile((int) (x + subhitbox.x + subhitbox.width) / Tile.DEFAULT_TILEWIDTH, ty).isSolid())
				return true;
		}
		return false;
	}

	public boolean collisionRightTile() {
		
		for(Rectangle subhitbox : subhitboxes) {
			int tx = (int) ((x + Vx + subhitbox.x + subhitbox.width) / Tile.DEFAULT_TILEWIDTH);
			if (handler.getWorld().getTile(tx, (int) (y + subhitbox.y + 1) / Tile.DEFAULT_TILEWIDTH).isSolid() || 
					handler.getWorld().getTile(tx,(int) (y + subhitbox.y + subhitbox.height - 1) / Tile.DEFAULT_TILEWIDTH).isSolid())
				return true;
		}
		return false;
	}

	public boolean collisionLeftTile() {
		for(Rectangle subhitbox : subhitboxes) {
			int tx = (int) ((x + Vx + subhitbox.x) / Tile.DEFAULT_TILEWIDTH);
			if (handler.getWorld().getTile(tx, (int) (y + subhitbox.y + 1) / Tile.DEFAULT_TILEWIDTH).isSolid() || 
					handler.getWorld().getTile(tx,(int) (y + subhitbox.y + subhitbox.height - 1) / Tile.DEFAULT_TILEWIDTH).isSolid())
				return true;
		}
		return false;
	}

	// Gfx

	@Override
	public void render(Graphics g) {
		g.drawImage(getAnimation().getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

		if (Settings.drawColisions) {
			g.setColor(Color.MAGENTA);
			for(Rectangle subhitbox : subhitboxes) {
				g.drawRect((int) (x + subhitbox.x - handler.getGameCamera().getxOffset()),
						(int) (y + subhitbox.y - handler.getGameCamera().getyOffset()), subhitbox.width, subhitbox.height);
			}
		}

		g.setFont(Utils.consoleFont);
		g.setColor(Color.WHITE);
		g.drawString("Health: " + Integer.toString(health),
				(int) (x + hitbox_x - handler.getGameCamera().getxOffset() - 16),
				(int) (y + hitbox_y - handler.getGameCamera().getyOffset() - 16));
	}

	public Animation getAnimation() {

		if (action != Action.fall) {
			anim_jump_r.reset();
			anim_jump_l.reset();
		}
		if (action == Action.walk && direction == Direction.right)
			return anim_walk_r;
		else if (action == Action.walk && direction == Direction.left)
			return anim_walk_l;
		else if (action == Action.idle && direction == Direction.right)
			return anim_idle_r;
		else if (action == Action.idle && direction == Direction.left)
			return anim_idle_l;
		else if (action == Action.fall && direction == Direction.right)
			return anim_jump_r;
		else if (action == Action.fall && direction == Direction.left)
			return anim_jump_l;
		else if (action == Action.sprint && direction == Direction.right)
			return anim_sprint_r;
		else if (action == Action.sprint && direction == Direction.left)
			return anim_sprint_l;
		else
			return anim_idle_r;
	}

	// GETTERS & SETTERS

	public int getHealth() {
		return health;
	}

	public float getMaxGroundSpeed() {
		return groundSpeed;
	}

	public void setMaxGroundSpeed(float maxGroundSpeed) {
		this.groundSpeed = maxGroundSpeed;
	}

	public float getSprintingMultiplier() {
		return sprintMult;
	}

	public void setSprintingMultiplier(float sprintingMultiplier) {
		this.sprintMult = sprintingMultiplier;
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
		return maxJumpsNum;
	}

	public void setNumAvailableJump(int numAvailableJump) {
		this.maxJumpsNum = numAvailableJump;
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
