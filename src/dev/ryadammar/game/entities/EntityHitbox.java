package dev.ryadammar.game.entities;

public class EntityHitbox extends java.awt.Rectangle{

	private static final long serialVersionUID = -5496195165445992632L;
	
	public int xOffset, yOffset;
	
	public EntityHitbox(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.xOffset = x;
		this.yOffset = y;
	}
	
	public EntityHitbox() {
		super();
		this.xOffset = x;
		this.yOffset = y;
	}

}
