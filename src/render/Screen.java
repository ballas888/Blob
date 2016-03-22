package render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.Block;
import objects.Face;
import objects.Tile;
import types.BlockType;
import types.View;
import main.Data;

public class Screen extends Canvas{
	private int width;
	private int height;
	
	public Screen(int w, int h){
		this.width = w;
		this.height = h;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);	
		setBackground(new Color(50,50,50));
	}
	
	public void renderAll(Data data){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			// This sets the buffering to do Double buffering
			createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());

		renderBlocks(g, data);
		renderBlocks2(g, data);
		//renderfaces(g, data);
		//renderShadows(g, data);
		bs.show();		
	}
	
	private void renderShadows(Graphics2D g, Data data){
		Tile[][] tiles = data.getTiles();
		Tile light = tiles[3][6];
		int w = data.getBlockW();
		int h = data.getBlockH();
		
		Point lightP = data.getMousePos();	
		
		g.setColor(new Color(50,50,50));
		for(int y = 0; y< tiles.length;y++){
			for(int x = 0; x< tiles[0].length;x++){
				Block block = tiles[y][x].getBlock();
				int ofX = block.getBlockX();
				int ofY = block.getBlockY();
				ArrayList<Face> faces = block.getFaces();
				for(Face face: faces){					
					
					
					ArrayList<Point> pnts = new ArrayList<Point>();			
					for(int i = 0; i < face.getXArray().size()-1;i++){
						int fx = face.getXArray().get(i)+ofX;
						int fy = face.getYArray().get(i)+ofY;
						int xdist = fx - lightP.x;
						int ydist = fy - lightP.y;
						
						pnts.add(new Point(fx,fy));
						pnts.add(new Point(fx+xdist, fy+ydist));
						//g.drawLine(fx, fy, fx+xdist, fy+ydist);
						int ffx = face.getXArray().get(i+1)+ofX;
						int ffy = face.getYArray().get(i+1)+ofY;
						
						int xdist2 = ffx - lightP.x;
						int ydist2 = ffy - lightP.y;
						
						pnts.add(new Point(fx+xdist, fy+ydist));
						pnts.add(new Point(ffx+xdist2,ffy+ydist2));
						//g.drawLine(fx+xdist, fy+ydist, ffx+xdist2,ffy+ydist2);
						
						pnts.add(new Point(ffx+xdist2,ffy+ydist2));
						pnts.add(new Point(ffx,ffy));
						//g.drawLine(ffx+xdist2,ffy+ydist2,ffx,ffy );
					}
					
					int[] xs = new int[pnts.size()];
					int[] ys = new int[pnts.size()];
//					
					for(int i = 0; i< xs.length;i++){
						xs[i] = pnts.get(i).x;//+ofX;
					}
					for(int i = 0; i< ys.length;i++){
						ys[i] = pnts.get(i).y;//+ofY;
					}
					g.fillPolygon(xs, ys, xs.length);
//					int fx = face.getXArray().get(1)+ofX;
//					int fy = face.getYArray().get(1)+ofY;
//					xs[0] = fx;
//					ys[0] = fy;
//					int distx = fx - lightP.x;
//					int disty = fy - lightP.y;
//					
//					xs[1] = fx + distx;
//					ys[1] = fx + disty;
//					
//					fx = face.getXArray().get(2)+ofX;
//					fy = face.getYArray().get(2)+ofY;
//					
//					distx = fx - lightP.x;
//					disty = fy - lightP.y;
//					
//					xs[2] = fx + distx;
//					ys[2] = fx + disty;
//					
//					xs[3] = fx;
//					ys[3] = fx;
					
//					g.drawPolygon(xs, ys, xs.length);
					
				}
			}
		}
	}

	private void renderfaces(Graphics2D g, Data data) {
		Tile[][] tiles = data.getTiles();
		int w = data.getBlockW();
		int h = data.getBlockH();
		
		for(int y = 0; y< tiles.length;y++){
			for(int x = 0; x< tiles[0].length;x++){
				Block block = tiles[y][x].getBlock();
				int ofX = block.getBlockX();
				int ofY = block.getBlockY();
				ArrayList<Face> faces = block.getFaces();
				for(Face face: faces){
					if(face.getFaceView() == View.FRONT){
						g.setColor(Color.green);
					}else if(face.getFaceView() == View.LEFT){
						g.setColor(Color.red);
					}else if(face.getFaceView() == View.TOP){
						g.setColor(Color.blue);
					}else if(face.getFaceView() == View.RIGHT){
						g.setColor(Color.yellow);
					}
					int[] xs = new int[face.getXArray().size()];
					int[] ys = new int[face.getYArray().size()];
					
					for(int i = 0; i< xs.length;i++){
						xs[i] = face.getXArray().get(i)+ofX;
					}
					for(int i = 0; i< ys.length;i++){
						ys[i] = face.getYArray().get(i)+ofY;
					}	
					
					g.drawPolygon(xs,ys, face.getYArray().size());
				}
			}
		}
	}

	private void renderBlocks(Graphics2D g, Data data) {
		Tile[][] tiles = data.getTiles();
		
		int w = data.getBlockW();
		int h = data.getBlockH();
		
		int numXtiles = tiles[0].length;
		int numYtiles = tiles.length;
		
		g.setColor(Color.green);
		int halfW = w/2;
		int halfH = h/2;
		
		int cX = (int) Math.round(getWidth()/2.0-w/2);
		int cY = (int) Math.round(getHeight()/2.0-(numYtiles*(h/2)));
		if(data.getView() == View.FRONT){
			for(int y = 0; y < numYtiles;y++){
				for(int x = 0; x< numXtiles;x++){	
					if(!tiles[y][x].isEmpty()){
						
						Tile tile = tiles[y][x];
						if(tile.getBlock().getType() == BlockType.FLOOR){
							BufferedImage img = tile.getBlock().getImg(data.getView());						
							int X = cX+((x*(halfW))-(y*(halfW)));					
							int Y = cY+(y*halfH)+(x*halfH)-(img.getHeight() - h);			
							//g.drawRect(X,Y, w, h);
							tile.getBlock().setBlockX(X);
							tile.getBlock().setBlockY(Y);
							g.drawImage(img,X,Y,null);
						}
						
						
					}				
				}
			}
		}else if(data.getView() == View.LEFT){
			
			for(int x = numXtiles-1; x>=0; x--){
				for(int y = 0; y < numYtiles; y++){
					if(!tiles[y][x].isEmpty()){
						Tile tile = tiles[y][x];
						BufferedImage img = tile.getBlock().getImg(data.getView());	
						int newX = Math.abs(x-(numXtiles-1));						
						int X = cX+((y*(halfW))-(newX*(halfW)));					
						int Y = cY+(newX*halfH)+(y*halfH)-(img.getHeight() - h);			
						//g.drawRect(X,Y, w, h);
						tile.getBlock().setBlockX(X);
						tile.getBlock().setBlockY(Y);
						g.drawImage(img,X,Y,null);
					}
				}
			}			
		}else if(data.getView() == View.TOP){
			
			for(int y = numYtiles-1; y>=0; y--){
				for(int x = numXtiles-1;x>=0;x--){
					if(!tiles[y][x].isEmpty()){
						Tile tile = tiles[y][x];
						BufferedImage img = tile.getBlock().getImg(data.getView());	
						int newX = Math.abs(x-(numXtiles-1));
						int newY = Math.abs(y-(numYtiles-1));
						int X = cX+((newX*(halfW))-(newY*(halfW)));					
						int Y = cY+(newY*halfH)+(newX*halfH)-(img.getHeight() - h);
						tile.getBlock().setBlockX(X);
						tile.getBlock().setBlockY(Y);
						g.drawImage(img,X,Y,null);
					}
				}
			}						
		}else if(data.getView() == View.RIGHT){
			for(int x = 0; x < numXtiles; x++){
				for(int y = numYtiles-1; y>=0; y--){
					if(!tiles[y][x].isEmpty()){
						Tile tile = tiles[y][x];
						BufferedImage img = tile.getBlock().getImg(data.getView());	
						int newY = Math.abs(y-(numYtiles-1));
						int X = cX+((newY*(halfW))-(x*(halfW)));					
						int Y = cY+(x*halfH)+(newY*halfH)-(img.getHeight() - h);
						tile.getBlock().setBlockX(X);
						tile.getBlock().setBlockY(Y);
						g.drawImage(img,X,Y,null);
					}
				}
			}						
		}
	}
	
	private void renderBlocks2(Graphics2D g, Data data) {
		Tile[][] tiles = data.getTiles();
		
		int w = data.getBlockW();
		int h = data.getBlockH();
		
		int numXtiles = tiles[0].length;
		int numYtiles = tiles.length;
		
		g.setColor(Color.green);
		int halfW = w/2;
		int halfH = h/2;
		
		int cX = (int) Math.round(getWidth()/2.0-w/2);
		int cY = (int) Math.round(getHeight()/2.0-(numYtiles*(h/2)));
		if(data.getView() == View.FRONT){
			for(int y = 0; y < numYtiles;y++){
				for(int x = 0; x< numXtiles;x++){	
					if(!tiles[y][x].isEmpty()){
						
						Tile tile = tiles[y][x];
						if(tile.getBlock().getType() != BlockType.FLOOR){
							BufferedImage img = tile.getBlock().getImg(data.getView());						
							int X = cX+((x*(halfW))-(y*(halfW)));					
							int Y = cY+(y*halfH)+(x*halfH)-(img.getHeight() - h);			
							//g.drawRect(X,Y, w, h);
							tile.getBlock().setBlockX(X);
							tile.getBlock().setBlockY(Y);
//							if(y =  ){
//								
//							}
							renderShadows(g, tile.getBlock(), data);
							g.drawImage(img,X,Y,null);
						}
						
						
					}				
				}
			}
		}else if(data.getView() == View.LEFT){
			
			for(int x = numXtiles-1; x>=0; x--){
				for(int y = 0; y < numYtiles; y++){
					if(!tiles[y][x].isEmpty()){
						Tile tile = tiles[y][x];
						BufferedImage img = tile.getBlock().getImg(data.getView());	
						int newX = Math.abs(x-(numXtiles-1));						
						int X = cX+((y*(halfW))-(newX*(halfW)));					
						int Y = cY+(newX*halfH)+(y*halfH)-(img.getHeight() - h);			
						//g.drawRect(X,Y, w, h);
						tile.getBlock().setBlockX(X);
						tile.getBlock().setBlockY(Y);
						g.drawImage(img,X,Y,null);
					}
				}
			}			
		}else if(data.getView() == View.TOP){
			
			for(int y = numYtiles-1; y>=0; y--){
				for(int x = numXtiles-1;x>=0;x--){
					if(!tiles[y][x].isEmpty()){
						Tile tile = tiles[y][x];
						BufferedImage img = tile.getBlock().getImg(data.getView());	
						int newX = Math.abs(x-(numXtiles-1));
						int newY = Math.abs(y-(numYtiles-1));
						int X = cX+((newX*(halfW))-(newY*(halfW)));					
						int Y = cY+(newY*halfH)+(newX*halfH)-(img.getHeight() - h);
						tile.getBlock().setBlockX(X);
						tile.getBlock().setBlockY(Y);
						g.drawImage(img,X,Y,null);
					}
				}
			}						
		}else if(data.getView() == View.RIGHT){
			for(int x = 0; x < numXtiles; x++){
				for(int y = numYtiles-1; y>=0; y--){
					if(!tiles[y][x].isEmpty()){
						Tile tile = tiles[y][x];
						BufferedImage img = tile.getBlock().getImg(data.getView());	
						int newY = Math.abs(y-(numYtiles-1));
						int X = cX+((newY*(halfW))-(x*(halfW)));					
						int Y = cY+(x*halfH)+(newY*halfH)-(img.getHeight() - h);
						tile.getBlock().setBlockX(X);
						tile.getBlock().setBlockY(Y);
						g.drawImage(img,X,Y,null);
					}
				}
			}						
		}
	}
	
	private void renderShadows(Graphics2D g, Block blk, Data data){
		Point lightP = data.getMousePos();	
		int ofX = blk.getBlockX();
		int ofY = blk.getBlockY();
		ArrayList<Face> faces = blk.getFaces();
		g.setColor(new Color(50,50,50));
		for(Face face: faces){					
			
			
			ArrayList<Point> pnts = new ArrayList<Point>();			
			for(int i = 0; i < face.getXArray().size()-1;i++){
				int fx = face.getXArray().get(i)+ofX;
				int fy = face.getYArray().get(i)+ofY;
				int xdist = fx - lightP.x;
				int ydist = fy - lightP.y;
				
				pnts.add(new Point(fx,fy));
				pnts.add(new Point(fx+xdist, fy+ydist));
				//g.drawLine(fx, fy, fx+xdist, fy+ydist);
				int ffx = face.getXArray().get(i+1)+ofX;
				int ffy = face.getYArray().get(i+1)+ofY;
				
				int xdist2 = ffx - lightP.x;
				int ydist2 = ffy - lightP.y;
				
				pnts.add(new Point(fx+xdist, fy+ydist));
				pnts.add(new Point(ffx+xdist2,ffy+ydist2));
				//g.drawLine(fx+xdist, fy+ydist, ffx+xdist2,ffy+ydist2);
				
				pnts.add(new Point(ffx+xdist2,ffy+ydist2));
				pnts.add(new Point(ffx,ffy));
				//g.drawLine(ffx+xdist2,ffy+ydist2,ffx,ffy );
			}
			
			int[] xs = new int[pnts.size()];
			int[] ys = new int[pnts.size()];
//			
			for(int i = 0; i< xs.length;i++){
				xs[i] = pnts.get(i).x;//+ofX;
			}
			for(int i = 0; i< ys.length;i++){
				ys[i] = pnts.get(i).y;//+ofY;
			}
			g.fillPolygon(xs, ys, xs.length);
	}
	}
}
