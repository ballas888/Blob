package objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import types.View;

public class Block {
	private BufferedImage frontImg;
	private BufferedImage leftImg;
	private BufferedImage topImg;
	private BufferedImage rightImg;
	
	private ArrayList<Face> faces = new ArrayList<Face>();
	
	private int blockX = 0;
	private int BlockY = 0;
	
	public void setImg(BufferedImage img, View view) {
		if(view == View.FRONT){
			this.frontImg = img;
		}else if(view == View.LEFT){
			this.leftImg = img;
		}else if(view == View.TOP){
			this.topImg = img;
		}else if(view == View.RIGHT){
			this.rightImg = img;
		}
	}
	
	public BufferedImage getImg(View view) {
		if(view == View.FRONT){
			return this.frontImg;
		}else if(view == View.LEFT){
			return this.leftImg;
		}else if(view == View.TOP){
			return this.topImg;
		}else if(view == View.RIGHT){
			return this.rightImg;
		}else{
			return this.frontImg;
		}
	}

	public ArrayList<Face> getFaces() {
		return faces;
	}

	public void setFaces(ArrayList<Face> faces) {
		this.faces = faces;
	}

	public int getBlockX() {
		return blockX;
	}

	public void setBlockX(int blockX) {
		this.blockX = blockX;
	}

	public int getBlockY() {
		return BlockY;
	}

	public void setBlockY(int blockY) {
		BlockY = blockY;
	}
	
}
