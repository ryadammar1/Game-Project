package dev.ryadammar.game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ryadammar.game.Controller;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.Settings;
import dev.ryadammar.game.entities.creatures.Creature.Direction;
import dev.ryadammar.game.gfx.Animation;
import dev.ryadammar.game.gfx.Assets;
import dev.ryadammar.game.utils.Utils;

public class Player extends Creature {

	private Controller controller;

	/**
	 * Player constructor 
	 * The hitbox properties and player width and height (in the
	 * super constructor call) must be hard coded to fit the attributes of the
	 * player sprite.
	 * 
	 * @params handler, position x, position y
	 * @author Ryad Ammar
	 */

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, 144, 150);

		controller = new Controller(this, handler);

		// Collision
		hitbox_subdiv_x = 5;
		hitbox_subdiv_y = 3;
		
		hitbox_x = 50;
		hitbox_y = 38;
		hitbox_height = 93;
		hitbox_width = 40;
		
		generateHitbox();

		// Properties
		maxJumpsNum = 2;

		// Animation
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

		state();
		direction();
		gravity();
		damage();

		anim_idle_r.tick();
		anim_idle_l.tick();
		anim_walk_r.tick();
		anim_walk_l.tick();
		anim_sprint_r.tick();
		anim_sprint_l.tick();
		anim_jump_r.tick();
		anim_jump_l.tick();
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
