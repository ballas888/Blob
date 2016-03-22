package load;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import objects.Face;
import types.BlockType;
import types.View;

public class Faces {
	
	private Map<BlockType, ArrayList<Face>> map;
	
	public Faces(){
		Load load = new Load();
		map = load.loadFaces();	
	}
	
	
	public ArrayList<Face> getFaces(BlockType blk){
		for(BlockType t: map.keySet()){
			if(blk == t){
				return map.get(t);
			}
		}
		return null;
	}
}
