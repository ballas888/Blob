package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class Key implements KeyListener{
	//A array to keep track of the keys that were pressed.
		private boolean[] keys = new boolean[256];
		public boolean up, down, left, right, rotLeft, rotRight;
		
		public void update(){
			//if vk_w or vk_up is pressed up equals true. 
			up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
			down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
			left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
			right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];		
		}

		//when a key is pressed its keycode number is used to set that number in the array to true.
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			//Just in case a key is pressed with a code that is more than 120 cant make the array go out of bounds
			if(keyCode < 256){ 
			keys[keyCode] = true;
			}
		}

		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if(keyCode < 256){
				keys[keyCode] = false;
			}
			if(keyCode == KeyEvent.VK_Q){
				rotLeft = true;
			}else if(keyCode == KeyEvent.VK_E){
				rotRight = true;
			}
		}
	
		public void keyTyped(KeyEvent arg0) {}
}
