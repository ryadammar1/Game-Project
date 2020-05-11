package dev.ryadammar.game;

public class Settings {
	
	private static final boolean DEFAULT_BHOP = true;
	private static final boolean DEFAULT_DRAW_CONSOLE = false;
	private static final boolean DEFAULT_DRAW_COLISIONS = false;

	public static boolean bhopEnable = DEFAULT_BHOP;
	public static boolean drawConsole = DEFAULT_DRAW_CONSOLE;
	public static boolean drawColisions = DEFAULT_DRAW_COLISIONS;
	
	public static void toggleConsole() {
		Settings.drawConsole = !Settings.drawConsole;
	}
	
	public static void toggleColisions() {
		Settings.drawColisions = !Settings.drawColisions;
	}
}
