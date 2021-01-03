package dev.ryadammar.game.entities.creatures;

import dev.ryadammar.game.Handler;

public class EnemyBehavior {

	private Creature creature;
	private Handler handler;

	private int direction;

	public EnemyBehavior(Creature creature, Handler handler) {
		this.creature = creature;
		this.handler = handler;
		this.direction = 1;
	}

	public void tick() {
		getState();
	}

	private void getState() {
		if (creature.isRayBool())
			pursuing();
		else
			patrolling();
	}

	private void pursuing() {
		if (Math.abs(creature.getX() - handler.getPlayer().getX()) < creature.range && !isCooldown()) {
			startCooldown();
			creature.attack();
			creature.move(0, 0);
		}
		else if (creature.getX() < handler.getPlayer().getX()-creature.range/1.5 && !creature.isCollisionRight())
			creature.move(creature.getSprintingMultiplier(), 1);
		else if (creature.getX() > handler.getPlayer().getX()+creature.range/1.5 && !creature.isCollisionLeft())
			creature.move(creature.getSprintingMultiplier(), -1);
		else creature.move(0, 0);
	}

	private void patrolling() {
		if (creature.isCollisionRight())
			direction = -1;
		if (creature.isCollisionLeft())
			direction = 1;

		creature.move(1.0f, direction);
	}

	public void goTo(int x, int y) {
		if (creature.getX() < x-1 && !creature.isCollisionRight())
			creature.move(creature.getSprintingMultiplier(), 1);
		else if (creature.getX() > x+1 && !creature.isCollisionLeft())
			creature.move(creature.getSprintingMultiplier(), -1);
		else creature.move(0, 0);;
	}
	
	// Timer for 2 second
	
	private boolean isCooldown() {
		return System.nanoTime() - startTime < 2000000000;
	}

	private long startTime = 0;
	
	private void startCooldown() {
		startTime = System.nanoTime();
	}
	
}
