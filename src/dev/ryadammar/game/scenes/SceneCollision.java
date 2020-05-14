package dev.ryadammar.game.scenes;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

public class SceneCollision {

	private HashSet<Point> outline;

	public SceneCollision(BufferedImage alpha) {
		
		
		outline = GenerateOutline(alpha);
	}

	private HashSet<Point> GenerateOutline(BufferedImage alpha) {
		
		HashSet<Point> tempOutline = new HashSet<Point>();
		
		for( int i = 0; i < alpha.getWidth(); i++ )
		    for( int j = 0; j < alpha.getHeight(); j++ ) {
		    	if(alpha.getRGB(i, j) < -1)
		    		tempOutline.add(new Point(i,j));
		    }
		
		return tempOutline;
	}
	
	public HashSet<Point> getOutline() {
		return this.outline;
	}

}
