package objects;

import java.awt.Point;
import java.awt.image.BufferedImage;

import types.View;

public class Tile {
	
	private boolean isEmpty = false;
	
	private Block block;
	
	private Point coords;	

	public Point getCoords() {
		return coords;
	}

	public void setCoords(Point coords) {
		this.coords = coords;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}
