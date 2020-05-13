package dev.ryadammar.game;

import java.awt.event.KeyEvent;

import dev.ryadammar.game.entities.creatures.Creature;
import dev.ryadammar.game.entities.creatures.Player;

public class Controller {

	private Creature creature;
	private Handler handler;
	private boolean hasJumped;

	public Controller(Creature creature, Handler handler) {
		this.creature = creature;
		this.handler = handler;
	}

	public void getInputHold() {
		
		// Camera
		if (handler.getKeyManager().DOWN)
			handler.getGameCamera().setyPeek(150);
		else if (handler.getKeyManager().UP)
			handler.getGameCamera().setyPeek(-150);
		else
			handler.getGameCamera().setyPeek(0);

		if (handler.getKeyManager().RIGHT)
			handler.getGameCamera().setxPeek(150);
		else if (handler.getKeyManager().LEFT)
			handler.getGameCamera().setxPeek(-150);
		else
			handler.getGameCamera().setxPeek(0);

		// Movement
		if (handler.getKeyManager().A && handler.getKeyManager().SHIFT)
			creature.move(creature.getSprintingMultiplier(), -1);
		else if (handler.getKeyManager().A)
			creature.move(1.0f, -1);
		else if (handler.getKeyManager().D && handler.getKeyManager().SHIFT)
			creature.move(creature.getSprintingMultiplier(), 1);
		else if (handler.getKeyManager().D)
			creature.move(1.0f, 1);
		else
			creature.move(0, 0);
	}

	public void getInputPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_C) {
			creature.setVx(Math.signum(creature.getVox()) * 15.0f);
			creature.setVox(Math.signum(creature.getVox()) * 15.0f);
			creature.setVoy(Math.signum(-15.0f));
		}

		// Settings
		if (e.getKeyCode() == KeyEvent.VK_W)
			creature.jump();
		if (e.getKeyCode() == KeyEvent.VK_F1)
			Settings.toggleConsole();
		if (e.getKeyCode() == KeyEvent.VK_F2)
			Settings.toggleColisions();
	}

}
