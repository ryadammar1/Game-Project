package dev.ryadammar.game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.HashSet;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.Settings;
import dev.ryadammar.game.entities.Entity;
import dev.ryadammar.game.entities.EntityHitbox;
import dev.ryadammar.game.gfx.Animation;
import dev.ryadammar.game.utils.Utils;
import dev.ryadammar.game.scenes.Area;
import dev.ryadammar.game.scenes.DamageArea;

public abstract class Creature extends Entity {

	public static final int DEFAULT_HEALTH = 10;
	public static final int DEFAULT_ATTACK_DAMAGE = 2;
	public static final float DEFAULT_GROUND_SPEED = 2.0f;
	public static final float DEFAULT_SPRINTING_MULTIPLIER = 1.5f;
	public static final float DEFAULT_AIR_DECELERATION = 0.01f;
	public static final int DEFAULT_MAX_JUMP_NUM = 1;
	public static final float DEFAULT_GROUND_ACCELERATION = 10.0f;
	public static final float DEFAULT_AIR_ACCELERATION = 2.0f;
	public static final float DEFAULT_JUMP_SPEED = 3.0f;
	public static final int DEFAULT_WIDTH = 64, DEFAULT_HEIGHT = 64;
	public static final int DEFAULT_ATTACK_RANGE = 64;

	protected int health;

	// Movement

	protected enum State {
		idle, walk, sprint, fall
	}

	protected State state;

	protected enum Direction {
		right(1), left(-1);

		private int value;

		Direction(int value) {
			this.value = value;
		}
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

	// Attack

	protected int range;
	protected int attackDamage;

	protected Line2D sight;

	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		health = DEFAULT_HEALTH;

		state = State.idle;
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

		attackDamage = DEFAULT_ATTACK_DAMAGE;
		range = DEFAULT_ATTACK_RANGE;

		sight = new Line2D.Float();
	}

	@Override
	public void tick() {
		updateHitbox();
		state();
		direction();
		gravity();
		damage();
		updateSight();
		updateAnimation();
	}

	// Gfx

	@Override
	public void render(Graphics g) {
		g.drawImage(getAnimation().getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

		if (Settings.drawColisions) {
			g.setColor(Color.MAGENTA);
			for (Rectangle subhitboxes[] : subhitboxes) {
				for (Rectangle subhitbox : subhitboxes) {
					g.drawRect((int) (subhitbox.x - handler.getGameCamera().getxOffset()),
							(int) (subhitbox.y - handler.getGameCamera().getyOffset()), subhitbox.width,
							subhitbox.height);
				}
			}
		}

		g.setFont(Utils.consoleFont);
		g.setColor(Color.WHITE);
		g.drawString("Health: " + Integer.toString(health), (int) (hitbox.x - 16), (int) (hitbox.y - 16));
		g.drawLine((int) sight.getX1(), (int) sight.getY1(), (int) sight.getX2(), (int) sight.getY2());
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
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
			Vx = movement * direction * Math.min(Math.abs(groundSpeed), Math.abs(Vox + direction * ga * spf));
			x += Vx;
			Vox = Vx;
		} else {
			if (movement == 0) {
				Vx = Vox + aa * spf;
				x += Vx;
			} else {
				Vx = direction
						* Math.min(Math.abs(direction * groundSpeed * aa + Vox), Math.abs(Vox + direction * aa * spf));
				if (Math.signum(Vx) == Math.signum(Vox))
					x += Vx;
				else
					Vx *= d;

				Vox = Vx;
			}
		}
	}

	public void state() {
		if (Math.abs(Vy) > 0)
			state = State.fall;
		else if (Math.abs(Vx) < 0.5)
			state = State.idle;
		else if (Math.abs(Vx) < groundSpeed * sprintMult)
			state = State.walk;
		else if (Math.abs(Vx) >= groundSpeed * sprintMult)
			state = State.sprint;
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

	public void damage() {
		if (Vy >= 5 && collisionDown())
			health -= (int) (Vy - 5);
		for (Area area : handler.getWorld().getAreas()) {
			if (area instanceof DamageArea)
				if (area.getBox().intersects(hitbox))
					health = 0;
		}
	}

	public void giveDamage(int damage) {
		this.health -= damage;
	}

	public void attack() {
		for (Creature creature : handler.getWorld().getCreatures()) {
			if (creature == this)
				;
			else if (Math.abs(hitbox.x - (creature.hitbox.x)) <= creature.hitbox.width / 2
					&& Math.abs(hitbox.y - creature.hitbox.y) <= range)
				creature.giveDamage(5);
			else if (Math.abs(hitbox.x - (creature.hitbox.x)) <= range
					&& Math.abs(hitbox.y - creature.hitbox.y) <= range)
				if (creature.hitbox.x >= hitbox.x && direction == Direction.right)
					creature.giveDamage(attackDamage);
				else if (creature.hitbox.x <= hitbox.x && direction == Direction.left)
					creature.giveDamage(attackDamage);
		}
	}

	public Creature findNearestCreature() {
		Creature nearestCreature = this;
		long distance = -1;
		for (Creature creature : handler.getWorld().getCreatures()) {
			if (creature == this)
				;
			else if (Math.pow((creature.x - x), 2) + Math.pow((creature.y - y), 2) < distance || distance == -1) {
				distance = (int) (Math.pow((creature.x - x), 2) + Math.pow((creature.y - y), 2));
				nearestCreature = creature;
			}
		}
		return nearestCreature;
	}

	// Collision

	public boolean collisionUp() {

		boolean collides = false;
		HashSet<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size() - 1)
				.getCollision().getOutline();

		for (int i = 0; i < hitbox_subdiv_y; i++) {
			Rectangle subhitbox = subhitboxes[0][i];

			if (sceneCollision
					.contains(new Point((int) (subhitbox.x + subhitbox.width), (int) ((Vy + subhitbox.y + 1))))
					|| sceneCollision.contains(new Point((int) (subhitbox.x), (int) ((Vy + subhitbox.y + 1))))) {
				collides = true;
				break;
			}
		}

		return collides;
	}

	public boolean collisionDown() {
		boolean collides = false;
		HashSet<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size() - 1)
				.getCollision().getOutline();

		for (int i = 0; i < hitbox_subdiv_y; i++) {
			Rectangle subhitbox = subhitboxes[hitbox_subdiv_x - 1][i];

			if (sceneCollision.contains(
					new Point((int) (subhitbox.x + subhitbox.width), (int) ((Vy + subhitbox.y + subhitbox.height + 1))))
					|| sceneCollision.contains(
							new Point((int) (subhitbox.x), (int) ((Vy + subhitbox.y + subhitbox.height + 1))))) {
				collides = true;
				break;
			}
		}

		return collides;
	}

	public boolean collisionRight() {
		boolean collides = false;
		HashSet<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size() - 1)
				.getCollision().getOutline();

		for (int i = 0; i < hitbox_subdiv_x; i++) {
			Rectangle subhitbox = subhitboxes[i][hitbox_subdiv_y - 1];

			if (sceneCollision.contains(
					new Point((int) (subhitbox.x + Vx + subhitbox.width), (int) ((subhitbox.y + subhitbox.height))))
					|| sceneCollision
							.contains(new Point((int) (subhitbox.x + Vx + subhitbox.width), (int) ((subhitbox.y))))) {
				collides = true;
				break;
			}
		}

		return collides;
	}

	public boolean collisionLeft() {
		boolean collides = false;
		HashSet<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size() - 1)
				.getCollision().getOutline();

		for (int i = 0; i < hitbox_subdiv_x; i++) {
			Rectangle subhitbox = subhitboxes[i][0];

			if (sceneCollision.contains(new Point((int) (subhitbox.x + Vx), (int) ((subhitbox.y + subhitbox.height))))
					|| sceneCollision.contains(new Point((int) (subhitbox.x + Vx), (int) ((subhitbox.y))))) {
				collides = true;
				break;
			}
		}

		return collides;
	}

	public boolean collision(int x, int y) {
		boolean collides = false;
		HashSet<Point> sceneCollision = handler.getWorld().getScenes().get(handler.getWorld().getScenes().size() - 1)
				.getCollision().getOutline();

		Rectangle subhitbox = subhitboxes[x][y];
		if (sceneCollision.contains(
				new Point((int) (subhitbox.x + Vx + subhitbox.width), (int) ((subhitbox.y + subhitbox.height))))
				|| sceneCollision
						.contains(new Point((int) (subhitbox.x + Vx + subhitbox.width), (int) ((subhitbox.y))))) {
			collides = true;
		}

		return collides;
	}

	public void updateHitbox() {
		hitbox.x = (int) (x + hitbox.xOffset - handler.getGameCamera().getxOffset());
		hitbox.y = (int) (y + hitbox.yOffset - handler.getGameCamera().getyOffset());

		for (EntityHitbox[] subhitboxes : subhitboxes) {
			for (EntityHitbox subhitbox : subhitboxes) {
				subhitbox.x = (int) (x + subhitbox.xOffset);
				subhitbox.y = (int) (y + subhitbox.yOffset);
			}
		}
	}

	public void updateSight() {
		sight.setLine(
				subhitboxes[0][(int) hitbox_subdiv_y / 2].x + subhitboxes[(int) hitbox_subdiv_x / 2][0].width / 2
						- handler.getGameCamera().getxOffset(),
				subhitboxes[0][0].y + subhitboxes[0][0].height / 2 - handler.getGameCamera().getyOffset(),
				findNearestCreature().subhitboxes[0][(int) findNearestCreature().hitbox_subdiv_y / 2].x
						+ findNearestCreature().subhitboxes[(int) findNearestCreature().hitbox_subdiv_x / 2][0].width
								/ 2
						- handler.getGameCamera().getxOffset(),
				findNearestCreature().subhitboxes[0][0].y + findNearestCreature().subhitboxes[0][0].height / 2
						- handler.getGameCamera().getyOffset());
	}

	public void updateAnimation() {
		anim_idle_r.tick();
		anim_idle_l.tick();
		anim_walk_r.tick();
		anim_walk_l.tick();
		anim_sprint_r.tick();
		anim_sprint_l.tick();
		anim_jump_r.tick();
		anim_jump_l.tick();
	}

	public Animation getAnimation() {

		if (state != State.fall) {
			anim_jump_r.reset();
			anim_jump_l.reset();
		}
		if (state == State.walk && direction == Direction.right)
			return anim_walk_r;
		else if (state == State.walk && direction == Direction.left)
			return anim_walk_l;
		else if (state == State.idle && direction == Direction.right)
			return anim_idle_r;
		else if (state == State.idle && direction == Direction.left)
			return anim_idle_l;
		else if (state == State.fall && direction == Direction.right)
			return anim_jump_r;
		else if (state == State.fall && direction == Direction.left)
			return anim_jump_l;
		else if (state == State.sprint && direction == Direction.right)
			return anim_sprint_r;
		else if (state == State.sprint && direction == Direction.left)
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
