package dev.ryadammar.game.gfx;

import java.awt.image.BufferedImage;

public class ImageCropper {
	
	private static BufferedImage loadedImage;
	
	public static BufferedImage crop(BufferedImage image, int x, int y, int width, int height) {
		try {
		ImageCropper.loadedImage = image.getSubimage(x, y, width, height);
		return loadedImage;
		} catch(Exception e) {
			e.printStackTrace();
			return loadedImage;
		}
	}
	
}
