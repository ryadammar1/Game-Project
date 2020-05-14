package dev.ryadammar.game.states;

import java.awt.Graphics;

import dev.ryadammar.game.PlayerController;
import dev.ryadammar.game.Handler;
import dev.ryadammar.game.entities.creatures.Player;

public class SettingsState extends State{

	public SettingsState(Handler handler) {
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
		return null;
	}

	@Override
	public PlayerController getController() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
