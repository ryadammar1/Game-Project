package dev.ryadammar.game.tiles;

import java.awt.image.BufferedImage;

import dev.ryadammar.game.gfx.Assets;

public class RockTile extends Tile {

	public RockTile(int id) {
		super(Assets.a_tiles.get(id), id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
	
}
