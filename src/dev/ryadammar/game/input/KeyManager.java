package dev.ryadammar.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.ryadammar.game.Handler;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	private Handler handler;
	public boolean SPACE, W, C, A, D, SHIFT, DOWN, UP, RIGHT, LEFT;

	public KeyManager(Handler handler) {
		this.handler = handler;
		keys = new boolean[256];
	}

	public void tick() {
		W = keys[KeyEvent.VK_W];
		C = keys[KeyEvent.VK_C];
		A = keys[KeyEvent.VK_A];
		D = keys[KeyEvent.VK_D];
		DOWN = keys[KeyEvent.VK_DOWN];
		UP = keys[KeyEvent.VK_UP];
		RIGHT = keys[KeyEvent.VK_RIGHT];
		LEFT = keys[KeyEvent.VK_LEFT];
		SHIFT = keys[KeyEvent.VK_SHIFT];
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			handler.getGame().getGameplayState().getController().getInputPressed(e);
			keys[e.getKeyCode()] = true;
		} catch (Exception e_input) {
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
