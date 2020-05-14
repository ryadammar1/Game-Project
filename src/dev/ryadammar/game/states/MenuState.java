package dev.ryadammar.game.states;

import java.awt.Graphics;

import dev.ryadammar.game.PlayerController;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.creatures.Player;

public class MenuState extends State{

	public MenuState(Handler handler) {
		super(handler);
	}
	
	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerController getController() {
		return null;
	}

}
