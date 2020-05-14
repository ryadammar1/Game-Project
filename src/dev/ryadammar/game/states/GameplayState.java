package dev.ryadammar.game.states;

import java.awt.Graphics;

import dev.ryadammar.game.PlayerController;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.creatures.Player;
import dev.ryadammar.game.worlds.World;

public class GameplayState extends State {

	private Player player, player2;
	private World world;
	private PlayerController controller;

	public GameplayState(Handler handler) {
		super(handler);
		world = new World(handler, "res/worlds/world1.lvl");
		handler.setWorld(world);
		player = new Player(handler, world.getSpawnX(), world.getSpawnY());
		handler.setPlayer(player);
		player2 = new Player(handler, world.getSpawnX() + 40, world.getSpawnY());
		handler.setPlayer(player2);
		controller = new PlayerController(player, handler);

		handler.getWorld().getCreatures().add(player2);
	}

	@Override
	public void tick() {
		world.tick();
		player.tick();
		player2.tick();
		controller.tick();
		handler.getGameCamera().centerOnEntity(player);
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		player.render(g);
		player2.render(g);
	}

	public Player getPlayer() {
		return player;
	}
	
	public PlayerController getController() {
		return controller;
	}

}
