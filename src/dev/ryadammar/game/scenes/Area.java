package dev.ryadammar.game.scenes;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ryadammar.game.Handler;

public abstract class Area {

	protected Rectangle box;
	protected Handler handler;
	
	public Area(Handler handler, int x, int y, int width, int height) {
		this.box = new Rectangle();
		this.box.x = x;
		this.box.y = y;
		this.box.width = width;
		this.box.height = height;
		this.handler = handler;
	}

	public abstract void tick();

	public abstract void render(Graphics g);
	
	public abstract void trigger();

	public Rectangle getBox() {
		return box;
	}

	public void setBox(Rectangle box) {
		this.box = box;
	}
	
}
