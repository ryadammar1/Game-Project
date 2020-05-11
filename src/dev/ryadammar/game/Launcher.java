package dev.ryadammar.game;

public class Launcher {

	static Game game;
	
	public static void main(String[] args) {
		game = new Game("title", 640, 480);
		game.start();
	}
	
	public static Game getGame() {
		return game;
	}

}
