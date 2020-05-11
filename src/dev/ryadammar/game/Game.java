package dev.ryadammar.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;

import dev.ryadammar.game.display.Display;
import dev.ryadammar.game.gfx.Assets;
import dev.ryadammar.game.gfx.GameCamera;
import dev.ryadammar.game.input.KeyManager;
import dev.ryadammar.game.states.GameplayState;
import dev.ryadammar.game.states.MenuState;
import dev.ryadammar.game.states.SettingsState;
import dev.ryadammar.game.states.State;
import dev.ryadammar.game.states.StateMachine;
import dev.ryadammar.game.utils.Utils;

public class Game implements Runnable {

	private static final int DEFAULT_FPS = 128;
	private static final boolean DEFAULT_BHOP = false;
	private static final boolean DEFAULT_DRAW_CONSOLE = false;
	private static final boolean DEFAULT_DRAW_COLISIONS = false;
	
	private Display display;
	private int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	//Inputs
	private KeyManager keyManager;
	
	//GFx
	private BufferStrategy bs;
	private Graphics g;
	private ImageObserver observer = null;
	
	//States
	private State gameplayState;
	private State menuState;
	private State settingsState;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;
	
	//Settings
	private int fps;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;

		handler = new Handler(this);
		keyManager = new KeyManager(handler);
		
		fps = DEFAULT_FPS;
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		Assets.init();
		
		gameCamera = new GameCamera(handler, 0, 0);
		
		gameplayState = new GameplayState(handler);
		menuState = new MenuState(handler);
		settingsState = new SettingsState(handler);
		StateMachine.setState(gameplayState);
	}
	
	private void tick() {
		keyManager.tick();
		
		if(StateMachine.getState() != null)
			StateMachine.getState().tick();
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		if(StateMachine.getState() != null)
			StateMachine.getState().render(g);
		
		if(Settings.drawConsole) {
			g.setFont(Utils.consoleFont);
			g.setColor(Color.WHITE);
			double speed = (double)(Math.sqrt(Math.pow(gameplayState.getPlayer().getVy(), 2) + Math.pow(gameplayState.getPlayer().getVx(), 2)));
			g.drawString("Speed: "+ String.format("%.2f", speed) +" m/s", 5, 15);
			g.drawString("Draw colisions: "+Settings.drawColisions, 5, 30);
		}
		
		bs.show();
		g.dispose();
	}
	
	public void run() {
		
		init();
		
		double spf = 1000000000 / fps;
		double delta = 0;
		long presentTime;
		long timer = 0;
		int ticks = 0;
		double elapsedTime;
		long previousTime = System.nanoTime();
		
		while(running) {
			presentTime = System.nanoTime();
			elapsedTime = presentTime - previousTime;
			delta += elapsedTime;
			timer += elapsedTime;
			previousTime = presentTime;
			
			if(delta >= spf) {
			tick();
			render();
			ticks++;
			delta = 0;
			}
			
			if(timer >= 1000000000) {
				//System.out.println("FPS: "+ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	public State getGameplayState() {
		return gameplayState;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}
	
	public float getSpf() {
		return 1.0f/fps;
	}
}
