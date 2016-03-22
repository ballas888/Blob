package load;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import types.BlockType;
import types.View;


public class Images {
	private BufferedImage DEFAULT_FRONT;	
	private BufferedImage DEFAULT_LEFT;
	private BufferedImage DEFAULT_TOP;
	private BufferedImage DEFAULT_RIGHT;
	
	private BufferedImage DIRT_FRONT;
	private BufferedImage DIRT_LEFT;
	private BufferedImage DIRT_TOP;
	private BufferedImage DIRT_RIGHT;
	
	private BufferedImage FLOOR_FRONT;
	private BufferedImage FLOOR_LEFT;
	private BufferedImage FLOOR_TOP;
	private BufferedImage FLOOR_RIGHT;
	
	private BufferedImage GRASS_FRONT;
	private BufferedImage GRASS_LEFT;
	private BufferedImage GRASS_TOP;
	private BufferedImage GRASS_RIGHT;
	
	private BufferedImage STONE_FRONT;
	private BufferedImage STONE_LEFT;
	private BufferedImage STONE_TOP;
	private BufferedImage STONE_RIGHT;
	
	public Images(){
		createDice();
	}
	
	private void createDice(){
		Load load = new Load();
		DEFAULT_FRONT = load.loadImg(BlockType.DEFAULT,View.FRONT);
		DEFAULT_LEFT = load.loadImg(BlockType.DEFAULT,View.LEFT);
		DEFAULT_TOP = load.loadImg(BlockType.DEFAULT,View.TOP);
		DEFAULT_RIGHT = load.loadImg(BlockType.DEFAULT,View.RIGHT);
		
//		DIRT_FRONT = load.loadImg(BlockType.DIRT,View.FRONT);
//		DIRT_LEFT = load.loadImg(BlockType.DIRT,View.LEFT);
//		DIRT_TOP = load.loadImg(BlockType.DIRT,View.TOP);
//		DIRT_RIGHT = load.loadImg(BlockType.DIRT,View.RIGHT);
//		
		FLOOR_FRONT = load.loadImg(BlockType.FLOOR,View.FRONT);
//		FLOOR_LEFT  = load.loadImg(BlockType.FLOOR,View.LEFT);
//		FLOOR_TOP = load.loadImg(BlockType.FLOOR,View.TOP);
//		FLOOR_RIGHT = load.loadImg(BlockType.FLOOR,View.RIGHT);
//		
//		GRASS_FRONT = load.loadImg(BlockType.GRASS,View.FRONT);
//		GRASS_LEFT  = load.loadImg(BlockType.GRASS,View.LEFT);
//		GRASS_TOP = load.loadImg(BlockType.GRASS,View.TOP);
//		GRASS_RIGHT = load.loadImg(BlockType.GRASS,View.RIGHT);
//		
//		STONE_FRONT = load.loadImg(BlockType.STONE,View.FRONT);
//		STONE_LEFT  = load.loadImg(BlockType.STONE,View.LEFT);
//		STONE_TOP = load.loadImg(BlockType.STONE,View.TOP);
//		STONE_RIGHT = load.loadImg(BlockType.STONE,View.RIGHT);		
	}
	
	public BufferedImage getImage(BlockType blk, View view){
		Class<? extends Images> c = this.getClass();
		try {
			Field f = c.getDeclaredField(blk+"_"+view);
			return (BufferedImage) f.get(this);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}	
		return DEFAULT_FRONT;		
	}
}
