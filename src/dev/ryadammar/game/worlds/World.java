package dev.ryadammar.game.worlds;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.gfx.ImageLoader;
import dev.ryadammar.game.scenes.Area;
import dev.ryadammar.game.scenes.DamageArea;
import dev.ryadammar.game.scenes.Scene;
import dev.ryadammar.game.tiles.Tile;
import dev.ryadammar.game.utils.Utils;

public class World {

	private Handler handler;
	private int width, height; // Unit: tiles
	private int spawnX, spawnY; // Unit: tiles
	private int[][] tiles; // Contains the id of a tile at coordinates x, y

	private int bg_num;
	private ArrayList<Scene> scenes;
	private ArrayList<Area> areas;

	private int scale;
	private float g; // Gravity acceleration in pixels per spf^2

	public World(Handler handler, String path) {
		scenes = new ArrayList<Scene>();
		areas = new ArrayList<Area>();
		this.handler = handler;
		loadWorld(path);

		g = (float) (9.8 * scale / (handler.getGame().getFps() ^ 2));
	}

	public void tick() {
		for (int i = 0; i < bg_num; i++) {
			scenes.get(i).tick();
		}
		for (int i = 0; i < areas.size(); i++) {
			areas.get(i).tick();
		}
	}

	public void render(Graphics g) {

		for (int i = 0; i < bg_num; i++) {
			scenes.get(i).render(g);
		}
		for (int i = 0; i < areas.size(); i++) {
			areas.get(i).render(g);
		}
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		scale = Utils.parseInt(tokens[0]);
		width = Utils.parseInt(tokens[1]);
		height = Utils.parseInt(tokens[2]);
		spawnX = Utils.parseInt(tokens[3]);
		spawnY = Utils.parseInt(tokens[4]);
		bg_num = Utils.parseInt(tokens[5]);

		for (int i = 0; i < bg_num; i++) {
			try {
				scenes.add(new Scene(handler, ImageLoader.loadImage("/worlds/"+tokens[6 + 5 * i]+".png"), 
						ImageLoader.loadImage("/worlds/"+tokens[7 + 5 * i]+".png"), Utils.parseInt(tokens[8 + 5 * i]), 
						Utils.parseInt(tokens[9 + 5 * i]), Utils.parseBool(tokens [10 + 5 * i])));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		areas.add(new DamageArea(handler, 100, 100, 50, 50));
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

	public ArrayList<Scene> getScenes() {
		return scenes;
	}

}
