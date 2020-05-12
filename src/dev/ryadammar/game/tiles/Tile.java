package dev.ryadammar.game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

	// Used to store all instances of tiles
	public static Tile[] tiles = new Tile[256];
	
	// Construction of all tiles with unique ID's
	public static Tile defaultTile = new DefaultTile(0);
	public static Tile dirtTile = new DirtTile(1);
	public static Tile grassTile = new GrassTile(2);
	public static Tile rockTile = new RockTile(3);
	
	// Tile class
	public static final int DEFAULT_TILEWIDTH = 64, DEFAULT_TILEHEIGHT = 64;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, DEFAULT_TILEWIDTH, DEFAULT_TILEHEIGHT, null);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public int getId() {
		return id;
	}
	
}
