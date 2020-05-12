package dev.ryadammar.game.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {

	private int s_width, s_height, s_row, s_col;
	private BufferedImage sheet;

	public SpriteSheet(BufferedImage sheet, int width, int height, int numColumns, int numRows) {
		this.sheet = sheet;
		this.s_height = height;
		this.s_width = width;
		this.s_row = numRows;
		this.s_col = numColumns;
	}

	public BufferedImage crop(int x, int y, int width, int height) {
		return ImageCropper.crop(sheet, x, y, width, height);
	}

	public int getS_width() {
		return s_width;
	}

	public int getS_height() {
		return s_height;
	}

	public ArrayList<BufferedImage> extractSprites() {
		ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();

		int counter = 0;
		for (int y = 0; y < s_row; y++) {
			for (int x = 0; x < s_col; x++) {
				temp.add(ImageCropper.crop(sheet, x * s_width, y * s_height, s_width, s_height));
				counter++;
			}
		}
		return temp;
	}

}
