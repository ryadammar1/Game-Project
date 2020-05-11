package dev.ryadammar.game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.ryadammar.game.gfx.Assets;

public class DefaultTile extends Tile {

	public DefaultTile(int id) {
		super(Assets.defaultTile, id);
	}
	
	@Override
	public void render(Graphics g, int x, int y) {
	}

}
