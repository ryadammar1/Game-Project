package dev.ryadammar.game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ryadammar.game.PlayerController;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.Settings;
import dev.ryadammar.game.entities.creatures.Creature.Direction;
import dev.ryadammar.game.gfx.Animation;
import dev.ryadammar.game.gfx.Assets;
import dev.ryadammar.game.utils.Utils;

public class Enemy extends Creature {

	/**
	 * Enemy constructor 
	 * The hitbox properties and enemy width and height (in the
	 * super constructor call) must be hard coded to fit the attributes of the
	 * enemy sprite.
	 * 
	 * @params handler, position x, position y
	 * @author Ryad Ammar
	 */

	public Enemy(Handler handler, float x, float y) {
		super(handler, x, y, 144, 150);

		// Collision
		hitbox_subdiv_x = 5;
		hitbox_subdiv_y = 3;
		
		hitbox.x = 50;
		hitbox.y = 38;
		hitbox.height = 93;
		hitbox.width = 40;
		
		generateHitbox();

		// Properties
		maxJumpsNum = 2;

		// Animation
		anim_walk_r = new Animation(100, Assets.a_enemy_walk_r, true);
		anim_walk_l = new Animation(100, Assets.a_enemy_walk_l, true);
		anim_sprint_r = new Animation(100, Assets.a_enemy_sprint_r, true);
		anim_sprint_l = new Animation(100, Assets.a_enemy_sprint_l, true);
		anim_idle_r = new Animation(200, Assets.a_enemy_idle_r, true);
		anim_idle_l = new Animation(200, Assets.a_enemy_idle_l, true);
		anim_jump_r = new Animation(200, Assets.a_enemy_jump_r, false);
		anim_jump_l = new Animation(200, Assets.a_enemy_jump_l, false);
		
		//Attack
		range = 70;
		attackDamage = 2;
	}

}
