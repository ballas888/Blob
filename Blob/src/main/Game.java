package main;

import input.Key;
import input.Mouse;

import javax.swing.JFrame;

import render.Screen;
import types.View;
import load.Load;

public class Game implements Runnable {
	private int screenWidth = 800;
	private int screenHeight = 400;
	private String title = "IsoGame";

	private Thread thread;
	private boolean running = true;

	private JFrame mainFrame;

	private Load load = new Load();

	private Key keyboard = new Key();

	private Screen screen;
	
	private Mouse mouse= new Mouse();

	private static Data data;

	public Game() {
		mainFrame = new JFrame();
		mainFrame.setTitle(title);

		data = new Data();
		data = new Data();
		data.setView(View.FRONT);
		data.setTiles(load.loadMap("big", data));
		System.out.println(data.getBlockW() + " : " + data.getBlockH());

		screen = new Screen(screenWidth, screenHeight);
		screen.addKeyListener(keyboard);
		screen.addMouseMotionListener(mouse);
		screen.setFocusable(true);

		mainFrame.add(screen);
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// Gets the time when the program is at this line.
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();

		//
		final double ns = 1000000000.0 / 60.0;

		// Used as the difference of the two times.
		double delta = 0;

		// Obvious is obvious.
		int frames = 0;

		int updates = 0;

		while (running) {
			long now = System.nanoTime();

			// This gets the difference of the time and then divide by ns. when
			// this equal 1 then it calls update
			// This is also used to make sure that it catches up if something
			// slowed down. say then render slowed down something
			// and delta = 2 then this loop will call update twice to make it
			// catch up.
			delta += (now - lastTime) / ns;

			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				mainFrame.setTitle(title + ": " + frames + " fps, " + updates
						+ " Ups");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void update(){
		keyboard.update();
		if(keyboard.rotLeft){
			rotate(true);
		}else if(keyboard.rotRight){
			rotate(false);
		}
		data.setMousePos(mouse.retrievePos());
	}
	
	private void rotate(Boolean isLeft){
		if(isLeft){
			keyboard.rotLeft = false;
			if(data.getView() == View.FRONT){
				data.setView(View.LEFT);
			}else if(data.getView() == View.LEFT){
				data.setView(View.TOP);
			}else if(data.getView() == View.TOP){
				data.setView(View.RIGHT);
			}else if(data.getView() == View.RIGHT){
				data.setView(View.FRONT);
			}
		}else{
			keyboard.rotRight = false;
			if(data.getView() == View.FRONT){
				data.setView(View.RIGHT);
			}else if(data.getView() == View.RIGHT){
				data.setView(View.TOP);
			}else if(data.getView() == View.TOP){
				data.setView(View.LEFT);
			}else if(data.getView() == View.LEFT){
				data.setView(View.FRONT);
			}
		}
	}
	
	public void render(){
		screen.renderAll(data);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

}
