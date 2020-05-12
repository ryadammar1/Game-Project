package dev.ryadammar.game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ryadammar.game.gfx.Assets;

public class Object {

	public static Object[] objects = new Object[256];
	
	public static Object plateform_m1 = new Object(0, 64, 32, Assets.a_tiles.get(5), true);
	
	// Object class
	
	protected final BufferedImage texture;
	protected final int o_width;
	protected final int o_height;
	protected final int id;
	protected final boolean isSolid;
	
	protected Rectangle bounds;
	
	public Object(int id, int width, int height, BufferedImage texture, boolean isSolid) {
		this.id = id;
		this.o_width = width;
		this.o_height = height;
		this.texture = texture;
		this.isSolid = isSolid;
		
		this.bounds.x = 0;
		this.bounds.y = 0;
		this.bounds.width = o_width;
		this.bounds.height = o_height;
		
		objects[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, o_width, o_height, null);
	}
}
