package dev.ryadammar.game.scenes;

import java.awt.Color;
import java.awt.Graphics;

import dev.ryadammar.game.Handler;

public class DamageArea extends Area {

	public DamageArea(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {	
		g.setColor(Color.RED);
		g.drawRect((int)(box.x - handler.getGameCamera().getxOffset()), 
				(int)(box.y - handler.getGameCamera().getyOffset()), this.box.width, this.box.height);
	}

	@Override
	public void trigger() {
		
	}

}
