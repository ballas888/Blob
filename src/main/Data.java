package main;

import java.awt.Point;

import objects.Tile;
import types.View;

public class Data {
	private Tile[][] tiles;
	private View view;
	private int blockW;
	private int blockH;	
	
	private Point mousePos = new Point(0,0);
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public int getBlockW() {
		return blockW;
	}

	public void setBlockW(int blockW) {
		this.blockW = blockW;
	}

	public int getBlockH() {
		return blockH;
	}

	public void setBlockH(int blockH) {
		this.blockH = blockH;
	}

	public Point getMousePos() {
		return mousePos;
	}

	public void setMousePos(Point mousePos) {
		this.mousePos = mousePos;
	}
}
