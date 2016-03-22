package objects;

import java.awt.Point;
import java.util.ArrayList;

import types.View;

public class Face {
	private View faceView;	
	private ArrayList<Integer> x = new ArrayList<Integer>();
	private ArrayList<Integer> y = new ArrayList<Integer>();
	
	public Face(View view){
		this.faceView = view;
	}
	
	public void addPoint(Point p){
		this.x.add(p.x);
		this.y.add(p.y);
	}
	
	public View getFaceView() {
		return faceView;
	}

	public void setFaceView(View faceView) {
		this.faceView = faceView;
	}

	public ArrayList<Integer> getXArray() {
		return x;
	}

	public void setXArray(ArrayList<Integer> x) {
		this.x = x;
	}

	public ArrayList<Integer> getYArray() {
		return y;
	}

	public void setYArray(ArrayList<Integer> y) {
		this.y = y;
	}
	
}
