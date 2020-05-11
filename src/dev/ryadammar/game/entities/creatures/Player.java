package dev.ryadammar.game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;

import dev.ryadammar.game.Controller;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.Settings;
import dev.ryadammar.game.gfx.Assets;
import dev.ryadammar.game.utils.Utils;

public class Player extends Creature{
	
	Controller controller;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);

		controller = new Controller(this, handler);
		
		// Collision
		bounds.x = 16;
		bounds.y = 0;
		bounds.height = 64;
		bounds.width = 32;
		
		// Properties
		numJump = 2;
		
		drawSpeed = true;
		drawColisions = true;
		
	}

	@Override
	public void tick() {
		controller.getInputHold();
		gravity();
		handler.getGameCamera().centerOnEntity(this);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.player, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		if(Settings.drawColisions) {
		g.setColor(Color.MAGENTA);
		g.drawRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), 
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
