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
	public static ArrayList<BufferedImage> a_tiles;

	public static void init() {

		// Creating new sprite sheet
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
		SpriteSheet s_tiles = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"), 32, 32, 8, 8);

		a_player_walk_r = s_player_walk_r.extractSprites();
		a_player_walk_l = s_player_walk_l.extractSprites();
		a_player_sprint_r = s_player_sprint_r.extractSprites();
		a_player_sprint_l = s_player_sprint_l.extractSprites();
		a_player_jump_r = s_player_jump_r.extractSprites();
		a_player_jump_l = s_player_jump_l.extractSprites();
		a_player_idle_r = s_player_idle_r.extractSprites();
		a_player_idle_l = s_player_idle_l.extractSprites();
		a_tiles = s_tiles.extractSprites();
	}

}
