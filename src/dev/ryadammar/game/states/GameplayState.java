package dev.ryadammar.game.states;

import java.awt.Graphics;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.creatures.Player;
import dev.ryadammar.game.worlds.World;

public class GameplayState extends State {

	private Player player;
	private World world;

	public GameplayState(Handler handler) {
		super(handler);
		world = new World(handler, "res/worlds/world1.lvl");
		handler.setWorld(world);
		player = new Player(handler, world.getSpawnX(), world.getSpawnY());
		handler.setPlayer(player);
	}

	@Override
	public void tick() {
		world.tick();
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		player.render(g);
	}

	public Player getPlayer() {
		return player;
	}

}
