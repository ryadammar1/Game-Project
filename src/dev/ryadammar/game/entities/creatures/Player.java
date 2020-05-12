package dev.ryadammar.game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;

import dev.ryadammar.game.Controller;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.Settings;
import dev.ryadammar.game.gfx.Animation;
import dev.ryadammar.game.gfx.Assets;
import dev.ryadammar.game.utils.Utils;

public class Player extends Creature{
	
	private Controller controller;
	private Animation anim_walk_r;
	private Animation anim_walk_l;
	private Animation anim_sprint_r;
	private Animation anim_sprint_l;
	private Animation anim_idle_l;
	private Animation anim_idle_r;
	private Animation anim_jump_l;
	private Animation anim_jump_r;

	/**
	 * Player constructor
	 * The bounds properties and 
	 * player width and height (in the super
	 * constructor call) must be hard coded 
	 * to fit the attributes of the player
	 * sprite.
	 * @params handler, position x, position y
	 * @author Ryad Ammar
	 */
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, 144, 150);
		
		controller = new Controller(this, handler);
		
		// Collision
		bounds.x = 54;
		bounds.y = 39;
		bounds.height = 93;
		bounds.width = 35;
		
		// Properties
		numJump = 2;
		
		//Animation
		anim_walk_r = new Animation(100, Assets.a_player_walk_r, false);
		anim_walk_l = new Animation(100, Assets.a_player_walk_l, false);
		anim_sprint_r = new Animation(100, Assets.a_player_sprint_r, false);
		anim_sprint_l = new Animation(100, Assets.a_player_sprint_l, false);
		anim_idle_r = new Animation(200, Assets.a_player_idle_r, false);
		anim_idle_l = new Animation(200, Assets.a_player_idle_l, false);
		anim_jump_r = new Animation(200, Assets.a_player_jump_r, true);
		anim_jump_l = new Animation(200, Assets.a_player_jump_l, true);
	}

	@Override
	public void tick() {
		controller.getInputHold();
		handler.getGameCamera().centerOnEntity(this);
		
		action();
		direction();
		gravity();

		anim_idle_r.tick();
		anim_idle_l.tick();
		anim_walk_r.tick();
		anim_walk_l.tick();
		anim_sprint_r.tick();
		anim_sprint_l.tick();
		anim_jump_r.tick();
		anim_jump_l.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getAnimation().getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		if(Settings.drawColisions) {
		g.setColor(Color.MAGENTA);
		g.drawRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), 
				   (int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
		
		g.setFont(Utils.consoleFont);
		g.setColor(Color.WHITE);
		g.drawString("Health: "+Integer.toString(health),
				(int) (x + bounds.x - handler.getGameCamera().getxOffset() - 16),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset() - 16));
	}
	
	public Animation getAnimation() {
		
		if(action != Action.fall) {
			anim_jump_r.reset();
			anim_jump_l.reset();
		}
		if(action == Action.walk && direction == Direction.right)
			return anim_walk_r;
		else if(action == Action.walk && direction == Direction.left)
			return anim_walk_l;
		else if(action == Action.idle && direction == Direction.right)
			return anim_idle_r;
		else if(action == Action.idle && direction == Direction.left)
			return anim_idle_l;
		else if(action == Action.fall && direction == Direction.right) 
			return anim_jump_r;
		else if(action == Action.fall && direction == Direction.left)
			return anim_jump_l;
		else if(action == Action.sprint && direction == Direction.right)
			return anim_sprint_r;
		else if(action == Action.sprint && direction == Direction.left)
			return anim_sprint_l;
		else 
			return anim_idle_r;
			
	}
	
	

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
