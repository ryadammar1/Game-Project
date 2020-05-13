package dev.ryadammar.game;

public class Settings {
	private static final boolean DEFAULT_DRAW_CONSOLE = false;
	private static final boolean DEFAULT_DRAW_COLISIONS = false;
	private static final boolean DEFAULT_DRAW_FPS = false;
	
	public static boolean drawConsole = DEFAULT_DRAW_CONSOLE;
	public static boolean drawColisions = DEFAULT_DRAW_COLISIONS;
	public static boolean drawFPS = DEFAULT_DRAW_FPS;
	
	public static void toggleConsole() {
		Settings.drawConsole = !Settings.drawConsole;
	}
	
	public static void toggleColisions() {
		Settings.drawColisions = !Settings.drawColisions;
	}
	
	public static void toggleFPS() {
		Settings.drawFPS = !Settings.drawFPS;
	}
}
