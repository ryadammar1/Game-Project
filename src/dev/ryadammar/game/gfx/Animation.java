package dev.ryadammar.game.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	private int f_speed, index;
	private ArrayList<BufferedImage> frames;
	private long lastTime, timer;
	private boolean loopable;

	public Animation(int f_speed, ArrayList<BufferedImage> frames, boolean loopable) {
		this.f_speed = f_speed;
		this.frames = frames;
		this.loopable = loopable;
		index = 0;
		lastTime = System.currentTimeMillis();
	}

	public void tick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (timer > f_speed) {
			index++;
			timer = 0;
		}

		if (index > frames.size() - 1) {
			if (!loopable)
				index--;
			else
				index = 0;
		}
	}

	public BufferedImage getCurrentFrame() {
		return frames.get(index);
	}

	public void reset() {
		index = 0;
	}
}
