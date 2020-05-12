package dev.ryadammar.game.scenes;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ColisionBox {

	private ArrayList<Point> outline;

	public ColisionBox(BufferedImage alpha) {
		outline = GenerateOutline(alpha);
	}

	private ArrayList<Point> GenerateOutline(BufferedImage alpha) {
		
		ArrayList<Point> tempOutline = new ArrayList<Point>();
		
		for( int i = 0; i < alpha.getWidth(); i++ )
		    for( int j = 0; j < alpha.getHeight(); j++ ) {
		    	if(alpha.getRGB(i, j) < -1)
		    		tempOutline.add(new Point(i, j));
		    }
		
		return tempOutline;
	}
	
	public ArrayList<Point> getOutline() {
		return this.outline;
	}

}
