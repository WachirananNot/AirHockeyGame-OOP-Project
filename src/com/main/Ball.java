package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Ball {
	public static final int SIZE = 16;

	private int x, y;
	private int xVel, yVel; // value either 1 or -1
	private float speed = 5;
	


	public Ball() {
		reset();
	}

	private void reset() {
		// Position
		speed = 5;
		x = Game.WIDTH / 2 - SIZE / 2;
		y = Game.HEIGHT / 2 - SIZE / 2;

		xVel = Game.sign(Math.random() * 2.0 - 1);
		yVel = Game.sign(Math.random() * 2.0 - 1);

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void changeYDir() {
		yVel *= -1;
	}

	public void changeXDir() {
		xVel *= -1;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, SIZE, SIZE);

	}

	public void update(Paddle p1, Paddle p2,Graphics g,MainMenu menu) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(new Font("Yahoo",Font.PLAIN,50));
		// update movement
		x += xVel * speed;
		y += yVel * speed;

		// collisions
		if (y + SIZE >= Game.HEIGHT || y <= 0) {
			try {
				File sound = new File("sounds//boop.wav");
				AudioInputStream af = AudioSystem.getAudioInputStream(sound);
				Clip clip = AudioSystem.getClip();
				clip.open(af);
				clip.start();
			} catch (Exception e) {
			}
			;
			speed += 0.5;
			changeYDir();
		}
		// with wall
		if (x + SIZE >= Game.WIDTH) {
			p1.addPoint();
			try {
				File sound = new File("sounds//point.wav");
				AudioInputStream af = AudioSystem.getAudioInputStream(sound);
				Clip clip = AudioSystem.getClip();
				clip.open(af);
				clip.start();
			} catch (Exception e) {
			}
			;
			if (p1.getPoint() == 10) {
				menu.playTxt = "Retry";
				menu.quitTxt = " Quit";
				p1.setPoint(0);
				p2.setPoint(0);
				menu.location = "images\\p1win.jpeg";
				menu.active = true;
				try {
					File sound = new File("sounds//victory.wav");
					AudioInputStream af = AudioSystem.getAudioInputStream(sound);
					Clip clip = AudioSystem.getClip();
					clip.open(af);
					clip.start();
				} catch (Exception e) {
				}
				;
				reset();
			} else {
				reset();
			}

		}
		if (x <= 0) {
			p2.addPoint();
			try {
				File sound = new File("sounds//point.wav");
				AudioInputStream af = AudioSystem.getAudioInputStream(sound);
				Clip clip = AudioSystem.getClip();
				clip.open(af);
				clip.start();
			} catch (Exception e) {
			}
			;
			if (p2.getPoint() == 10) {
				menu.playTxt = "Retry";
				menu.quitTxt = " Quit";
				p1.setPoint(0);
				p2.setPoint(0);
				menu.location = "images\\p2win.jpeg";
				menu.active = true;
				try {
					File sound = new File("sounds//victory.wav");
					AudioInputStream af = AudioSystem.getAudioInputStream(sound);
					Clip clip = AudioSystem.getClip();
					clip.open(af);
					clip.start();
				} catch (Exception e) {
				}
				;
				reset();
			} else {
				reset();
			}
		}

	}
}
