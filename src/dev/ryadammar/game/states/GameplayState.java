package dev.ryadammar.game.states;

import java.awt.Graphics;

import dev.ryadammar.game.PlayerController;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.creatures.Enemy;
import dev.ryadammar.game.entities.creatures.Player;
import dev.ryadammar.game.worlds.World;

public class GameplayState extends State {

	private Player player;
	private Enemy enemy1, enemy2;
	private World world;
	private PlayerController controller;

	public GameplayState(Handler handler) {
		super(handler);
		world = new World(handler, "res/worlds/world1.lvl");
		handler.setWorld(world);
		player = new Player(handler, 500, 0/* world.getSpawnX(), world.getSpawnY()*/);
		handler.setPlayer(player);
		enemy1 = new Enemy(handler, world.getSpawnX(), world.getSpawnY());
		enemy2 = new Enemy(handler, world.getSpawnX() + 600, world.getSpawnY());
		controller = new PlayerController(player, handler);

		handler.getWorld().getCreatures().add(player);
		handler.getWorld().getCreatures().add(enemy1);
		handler.getWorld().getCreatures().add(enemy2);
	}

	@Override
	public void tick() {
		world.tick();
		enemy1.tick();
		player.tick();
		enemy2.tick();
		controller.tick();
		handler.getGameCamera().centerOnEntity(player);
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		player.render(g);
		enemy1.render(g);
		enemy2.render(g);
	}

	public Player getPlayer() {
		return player;
	}
	
	public PlayerController getController() {
		return controller;
	}

}
