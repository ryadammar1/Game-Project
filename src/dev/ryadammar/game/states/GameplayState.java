package dev.ryadammar.game.states;

import java.awt.Graphics;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.creatures.Enemy;
import dev.ryadammar.game.entities.creatures.EnemyBehavior;
import dev.ryadammar.game.entities.creatures.Player;
import dev.ryadammar.game.entities.creatures.PlayerController;
import dev.ryadammar.game.worlds.World;

public class GameplayState extends State {

	private Player player;
	private World world;
	private PlayerController controller;

	public GameplayState(Handler handler) {
		super(handler);
		world = new World(handler, "res/worlds/world1.lvl");
		handler.setWorld(world);
		player = new Player(handler, world.getSpawnX(), world.getSpawnY());
		handler.setPlayer(player);
		controller = new PlayerController(player, handler);
		
		enemy = new Enemy(handler, world.getSpawnX(), world.getSpawnY());
		enemyBehavior = new EnemyBehavior(enemy, handler);
		
		handler.getWorld().addCreature(enemy, player);
	}
	
	Enemy enemy;
	EnemyBehavior enemyBehavior;

	@Override
	public void tick() {
		world.tick();
		player.tick();
		controller.tick();
		enemy.tick();
		enemyBehavior.tick();
		handler.getGameCamera().centerOnEntity(player);
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		player.render(g);
		enemy.render(g);
	}

	public Player getPlayer() {
		return player;
	}
	
	public PlayerController getController() {
		return controller;
	}

}
