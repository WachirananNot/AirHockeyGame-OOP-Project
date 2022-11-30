package com.main;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1052;
	public static final int HEIGHT = 597;

	public boolean running = false;
	private Thread gameThread;

	private Ball ball;
	private Paddle paddle1;
	private Paddle paddle2;
	public MainMenu menu;
	private String location;

	public Game() {
		location = "";
		location = "images\\Field";
		String n = String.valueOf((int)(Math.random()*(5-1))+1);
		n += ".jpg";
		location += n;
		canvasSetup();

		new Window("Air Hockey", this);
		
		initialize();
		
		this.addKeyListener(new KeyInput(paddle1, paddle2));
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.setFocusable(true);
		

	}

	private void initialize() {
		// ball
		ball = new Ball();
		// paddles
		paddle1 = new Paddle(Color.green, true);
		paddle2 = new Paddle(Color.red, false);

		menu = new MainMenu(this);
	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void run() {
		this.requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			if (running)
				draw();
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop();
	}

	private void draw() {
		BufferStrategy buffer = this.getBufferStrategy();

		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();
		

		if (menu.active){
			menu.draw(g);
		} else {
			drawBackground(g);
			ball.draw(g);

			paddle1.draw(g);
			paddle2.draw(g);

			g.dispose();
			
		}buffer.show();
	}

	private void drawBackground(Graphics g) {
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
		g2d.setStroke(dashed);
		try {
			BufferedImage image = ImageIO.read(new File(location));
			
			g.drawImage(image, 0, 0, null);
		}catch(Exception e) {};
		//g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
	}

	private void update() {
		BufferStrategy buffer = this.getBufferStrategy();
		Graphics g = buffer.getDrawGraphics();
		if (!menu.active) {
			// ball
			ball.update(paddle1, paddle2,g,menu);
			// update paddles
			paddle1.update(ball);
			paddle2.update(ball);
		}

	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running = true;
	}

	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Game();
		try {
			File sound = new File("sounds//bgm.wav");
			AudioInputStream af = AudioSystem.getAudioInputStream(sound);
			Clip clip = AudioSystem.getClip();
			clip.open(af);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
		}
		;

	}

	public static int sign(double d) {
		if (d <= 0)
			return -1;
		return 1;
	}

	public static int ensureRange(int val, int min, int max) {
		// TODO Auto-generated method stub
		return Math.min(Math.max(val, min), max);
	}
}