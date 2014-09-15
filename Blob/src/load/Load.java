package load;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;








import main.Data;
import objects.Block;
import objects.Face;
import objects.Tile;
import types.BlockType;
import types.View;

public class Load {	
	
	public Tile[][] loadMap(String name, Data data){	
		System.out.println("Loading...");
		Images images = new Images();
		Faces face = new Faces();
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream in = classLoader.getResourceAsStream("assets/"+name+".txt");
				
		if( in == null){
			System.out.println("Failed to load Map");
			System.exit(0);
		}		
		//return loadMapBuffered(in, data);
		return loadMap(in, data, images, face);
	}
	
	private Tile[][] loadMap(InputStream in, Data data, Images images, Faces faces){
		
		int count = 0;
		//long start = System.nanoTime();
		Scanner scan = new Scanner(in);
		
		String[] info = scan.nextLine().split(",");
		int BlockW = Integer.parseInt(info[0]);
		int BlockH = Integer.parseInt(info[1]);
		
		data.setBlockW(BlockW);
		data.setBlockH(BlockH);
		
		String[] info1 = scan.nextLine().split(",");
		int width = Integer.parseInt(info1[0]);
		int height = Integer.parseInt(info1[1]);
		
		Tile[][] tiles = new Tile[height][width];
		
		int tileCount = 0;
		while(scan.hasNext()){
			String[] d = scan.nextLine().split(",");
			for(int i = 0; i < d.length;i++){
				tiles[tileCount][i] = createTile(i,tileCount,d[i], data, images, faces);
			}
			tileCount++;
			System.out.println(count++);
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		scan.close();
		System.out.println("Done Loading: "+count);
		//long time = System.nanoTime() - start;			
		//System.out.println("Time taken: "+time);
		return tiles;
	}
			
	public BufferedImage loadImg(String name){
		BufferedImage img = null;
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream in = classLoader.getResourceAsStream("assets/"+name+".png");
		if(in == null){
			System.out.println("Image not found: "+name);
			System.exit(0);
		}else{
			try {
				img = ImageIO.read(in);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return img;
	}
	
	public BufferedImage loadImg(BlockType blk, View view){
		return loadImg(blk.toString()+"/"+view.toString());
	}
	
	private Tile createTile(int x, int y, String dt, Data data, Images images, Faces faces){
		int d = Integer.parseInt(dt);
		Tile tile = new Tile();
		Block block = new Block();
		tile.setCoords(new Point(x, y));
		
		if(d == 1){		
			block.setImg(images.getImage(BlockType.FLOOR, View.FRONT),View.FRONT);
			block.setImg(images.getImage(BlockType.FLOOR, View.FRONT),View.LEFT);
			block.setImg(images.getImage(BlockType.FLOOR, View.FRONT),View.RIGHT);
			block.setImg(images.getImage(BlockType.FLOOR, View.FRONT),View.TOP);
			block.setType(BlockType.FLOOR);
		}else if(d == 2){
			block.setImg(images.getImage(BlockType.DEFAULT, View.FRONT),View.FRONT);
			block.setImg(images.getImage(BlockType.DEFAULT, View.LEFT),View.LEFT);
			block.setImg(images.getImage(BlockType.DEFAULT, View.RIGHT),View.RIGHT);
			block.setImg(images.getImage(BlockType.DEFAULT, View.TOP),View.TOP);
			block.setType(BlockType.DEFAULT);
			ArrayList<Face> f = faces.getFaces(BlockType.DEFAULT);
			if(f == null){
				System.out.println("Faces returned null. Failed to load block");
				System.exit(0);
			}
			block.setFaces(f);
		}else{
			tile.setEmpty(true);
		}	
		tile.setBlock(block);
		return tile;
	}
	
	public Map<BlockType, ArrayList<Face>> loadFaces(){
		Map<BlockType, ArrayList<Face>> map = new HashMap<BlockType, ArrayList<Face>>();
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream in = classLoader.getResourceAsStream("assets/Faces.txt");				
		if( in == null){
			System.out.println("Failed to load Faces");
			System.exit(0);
		}
		
		Scanner scan = new Scanner(in);
		
		BlockType type = BlockType.DEFAULT;
		ArrayList<Face> faces = new ArrayList<Face>();
		while(scan.hasNext()){
			String line = scan.nextLine();
			if(line.contains("DEFAULT")){
				faces = new ArrayList<Face>();
				type = BlockType.DEFAULT;
			}else if(line.contains("FRONT")){
				String[] coords = scan.nextLine().split(" ");
				Face face = new Face(View.FRONT);
				for(int i = 0; i < coords.length;i++){
					String[] point = coords[i].split(",");
					face.addPoint(new Point(Integer.parseInt(point[0]),Integer.parseInt(point[1])));
				}
				faces.add(face);
			}else if(line.contains("LEFT")){
				String[] coords = scan.nextLine().split(" ");
				Face face = new Face(View.LEFT);
				for(int i = 0; i < coords.length;i++){
					String[] point = coords[i].split(",");
					face.addPoint(new Point(Integer.parseInt(point[0]),Integer.parseInt(point[1])));
				}
				faces.add(face);
			}else if(line.contains("TOP")){
				String[] coords = scan.nextLine().split(" ");
				Face face = new Face(View.TOP);
				for(int i = 0; i < coords.length;i++){
					String[] point = coords[i].split(",");
					face.addPoint(new Point(Integer.parseInt(point[0]),Integer.parseInt(point[1])));
				}
				faces.add(face);
			}else if(line.contains("RIGHT")){
				String[] coords = scan.nextLine().split(" ");
				Face face = new Face(View.RIGHT);
				for(int i = 0; i < coords.length;i++){
					String[] point = coords[i].split(",");
					face.addPoint(new Point(Integer.parseInt(point[0]),Integer.parseInt(point[1])));
				}
				faces.add(face);
			}else if(line.contains(">")){
				map.put(type, faces);
			}else{
				System.out.println("Error reading faces. Wrong format.");
				System.exit(0);
			}
			
			
		}
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan.close();
		
		return map;
	}
	
	public static void main(String[] args){
//		Load load = new Load();
//		Map<BlockType, ArrayList<Face>> map =load.loadFaces();
//		for(BlockType t: map.keySet()){
//			System.out.printf("%s\n",t.toString());
//			ArrayList<Face> face = map.get(t);
//			for(int i = 0; i < face.size();i++){
//				System.out.printf("	View: %s\n		", face.get(i).getFaceView().toString());
//				for(int j = 0; j < face.get(i).getPoints().size();j++){
//					System.out.printf("%d,%d ", face.get(i).getPoints().get(j).x,face.get(i).getPoints().get(j).y);
//				}
//				System.out.println();
//			}
//			System.out.println();
//		}
	}
}
