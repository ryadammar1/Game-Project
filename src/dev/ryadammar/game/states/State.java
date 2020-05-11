package dev.ryadammar.game.states;

import java.awt.Graphics;

import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.creatures.Player;

public abstract class State {
	
	protected Handler handler;
	
	public State(Handler handler){
		this.handler = handler;
	}

	public abstract Player getPlayer();
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
