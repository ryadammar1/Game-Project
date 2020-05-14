package dev.ryadammar.game.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets {

	// Assets
	public static ArrayList<BufferedImage> a_player_walk_r;
	public static ArrayList<BufferedImage> a_player_walk_l;
	public static ArrayList<BufferedImage> a_player_sprint_r;
	public static ArrayList<BufferedImage> a_player_sprint_l;
	public static ArrayList<BufferedImage> a_player_jump_r;
	public static ArrayList<BufferedImage> a_player_jump_l;
	public static ArrayList<BufferedImage> a_player_idle_r;
	public static ArrayList<BufferedImage> a_player_idle_l;
	public static ArrayList<BufferedImage> a_enemy_walk_r;
	public static ArrayList<BufferedImage> a_enemy_walk_l;
	public static ArrayList<BufferedImage> a_enemy_sprint_r;
	public static ArrayList<BufferedImage> a_enemy_sprint_l;
	public static ArrayList<BufferedImage> a_enemy_jump_r;
	public static ArrayList<BufferedImage> a_enemy_jump_l;
	public static ArrayList<BufferedImage> a_enemy_idle_r;
	public static ArrayList<BufferedImage> a_enemy_idle_l;

	public static void init() {

		// Player
		
		SpriteSheet s_player_walk_r = new SpriteSheet(ImageLoader.loadImage("/textures/player_walk_r.png"), 50, 48, 6,
				1);
		SpriteSheet s_player_walk_l = new SpriteSheet(ImageLoader.loadImage("/textures/player_walk_l.png"), 50, 48, 6,
				1);
		SpriteSheet s_player_sprint_r = new SpriteSheet(ImageLoader.loadImage("/textures/player_sprint_r.png"), 50, 48,
				6, 1);
		SpriteSheet s_player_sprint_l = new SpriteSheet(ImageLoader.loadImage("/textures/player_sprint_l.png"), 50, 48,
				6, 1);
		SpriteSheet s_player_jump_r = new SpriteSheet(ImageLoader.loadImage("/textures/player_jump_r.png"), 50, 48, 4,
				1);
		SpriteSheet s_player_jump_l = new SpriteSheet(ImageLoader.loadImage("/textures/player_jump_l.png"), 50, 48, 4,
				1);
		SpriteSheet s_player_idle_r = new SpriteSheet(ImageLoader.loadImage("/textures/player_idle_r.png"), 50, 48, 3,
				1);
		SpriteSheet s_player_idle_l = new SpriteSheet(ImageLoader.loadImage("/textures/player_idle_l.png"), 50, 48, 3,
				1);
		
		// Enemy
		
		SpriteSheet s_enemy_walk_r = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_walk_r.png"), 50, 48, 6,
				1);
		SpriteSheet s_enemy_walk_l = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_walk_l.png"), 50, 48, 6,
				1);
		SpriteSheet s_enemy_sprint_r = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_sprint_r.png"), 50, 48,
				6, 1);
		SpriteSheet s_enemy_sprint_l = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_sprint_l.png"), 50, 48,
				6, 1);
		SpriteSheet s_enemy_jump_r = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_jump_r.png"), 50, 48, 4,
				1);
		SpriteSheet s_enemy_jump_l = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_jump_l.png"), 50, 48, 4,
				1);
		SpriteSheet s_enemy_idle_r = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_idle_r.png"), 50, 48, 3,
				1);
		SpriteSheet s_enemy_idle_l = new SpriteSheet(ImageLoader.loadImage("/textures/enemy_idle_l.png"), 50, 48, 3,
				1);

		a_player_walk_r = s_player_walk_r.extractSprites();
		a_player_walk_l = s_player_walk_l.extractSprites();
		a_player_sprint_r = s_player_sprint_r.extractSprites();
		a_player_sprint_l = s_player_sprint_l.extractSprites();
		a_player_jump_r = s_player_jump_r.extractSprites();
		a_player_jump_l = s_player_jump_l.extractSprites();
		a_player_idle_r = s_player_idle_r.extractSprites();
		a_player_idle_l = s_player_idle_l.extractSprites();
		
		a_enemy_walk_r = s_enemy_walk_r.extractSprites();
		a_enemy_walk_l = s_enemy_walk_l.extractSprites();
		a_enemy_sprint_r = s_enemy_sprint_r.extractSprites();
		a_enemy_sprint_l = s_enemy_sprint_l.extractSprites();
		a_enemy_jump_r = s_enemy_jump_r.extractSprites();
		a_enemy_jump_l = s_enemy_jump_l.extractSprites();
		a_enemy_idle_r = s_enemy_idle_r.extractSprites();
		a_enemy_idle_l = s_enemy_idle_l.extractSprites();
	}

}
