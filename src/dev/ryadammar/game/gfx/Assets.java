package dev.ryadammar.game.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	// Assets
	public static BufferedImage world1_bg, world2_bg, player, dirt, grass, stone, tree, plateform_m1, defaultTile;
	
	public static void init() {
	// Creating new sprite sheet	
		SpriteSheet sheet = new 
				SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
	
		player = sheet.crop(width * 4, 0, width, height);
				
		dirt = sheet.crop(width, 0, width, height);
		grass = sheet.crop(width * 2, 0, width, height);
		stone = sheet.crop(width * 3, 0, width, height);
		tree = sheet.crop(0, 0, width, height);
		
	// Background
		world1_bg = ImageLoader.loadImage("/worlds/world1_bg.png");
		world2_bg = ImageLoader.loadImage("/worlds/world2_bg.png");
	}
	
	public static BufferedImage getAssetByString(String string) throws Exception {
		switch(string) {
		case "world1_bg" :
			return world1_bg;
		case "world2_bg" :
			return world2_bg;
		default:
			throw new Exception("World not found");
		}
	}
	
}
