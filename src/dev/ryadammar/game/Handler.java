package dev.ryadammar.game;

import dev.ryadammar.game.entities.creatures.Player;
import dev.ryadammar.game.gfx.GameCamera;
import dev.ryadammar.game.input.KeyManager;
import dev.ryadammar.game.worlds.World;

public class Handler {

	private Game game;
	private World world;
	private Player player;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public int getWidth() {
		return game.getWidth();
	}

	public int getHeight() {
		return game.getHeight();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
}
