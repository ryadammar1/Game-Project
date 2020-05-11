package dev.ryadammar.game.worlds;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.gfx.Assets;
import dev.ryadammar.game.gfx.Background;
import dev.ryadammar.game.gfx.ImageCropper;
import dev.ryadammar.game.tiles.Tile;
import dev.ryadammar.game.utils.Utils;

public class World {

	private Handler handler;
	private int width, height; // Unit: tiles
	private int spawnX, spawnY; // Unit: tiles
	private int [][] tiles; // Contains the id of a tile at coordinates x, y

	private int bg_num;
	private ArrayList<Background> backgrounds;
	
	private int scale;
	private float g; // Gravity acceleration in pixels per spf^2
	
	public World(Handler handler, String path) {
		backgrounds = new ArrayList<Background>();
		this.handler = handler;
		loadWorld(path);
		
		g = (float) (9.8 * scale / (handler.getGame().getFps()^2));
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {

		for(int i = 0; i < bg_num; i++) {
			backgrounds.get(i).render(g);
		}
		
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.DEFAULT_TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.DEFAULT_TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.DEFAULT_TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.DEFAULT_TILEHEIGHT + 1);
		
		for(int y = yStart; y < yEnd; y++) {
			for(int x = xStart; x < xEnd; x++) {
				getTile(x,y).render
				(g, (int) (x * Tile.DEFAULT_TILEWIDTH - handler.getGameCamera().getxOffset()),(int) (y * Tile.DEFAULT_TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.defaultTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.defaultTile;
		return t;
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String [] tokens = file.split("\\s+");
		scale = Utils.parseInt(tokens[0]);
		width = Utils.parseInt(tokens[1]);
		height = Utils.parseInt(tokens[2]);
		spawnX = Utils.parseInt(tokens[3]);
		spawnY = Utils.parseInt(tokens[4]);
		bg_num = Utils.parseInt(tokens[5]);
		
		for(int i = 0; i < bg_num; i++) {
			try {
				backgrounds.add(new Background(handler, Assets.getAssetByString(tokens[6+3*i]), Utils.parseInt(tokens[7+3*i]), Utils.parseInt(tokens[8+3*i])));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		tiles = new int[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int positionInLoop = (x + y * width) + 5 + 3*bg_num; // Converts the 2D array into a 1D array, 
														  // and adds 16 to skip the for first parameters 
														  // of the level
				tiles[x][y] = Utils.parseInt(tokens[positionInLoop]); 
			}
		}
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public int[][] getTiles() {
		return tiles;
	}

	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	
}
