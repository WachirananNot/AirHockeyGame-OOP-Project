package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;


import javax.imageio.ImageIO;

public class MainMenu extends MouseAdapter{

	public boolean active;
	// button play
	private Rectangle playBtn;
	protected String playTxt = "Play";
	private boolean pHighlight = false;

	private Rectangle quitBtn;
	protected String quitTxt = "Quit";
	private boolean qHighlight = false;
	private Font font;
	protected String location = "images\\testmenu.jpg";

	public MainMenu(Game game) {
		active = true;
		game.start();
		
		int w,h,x,y;
		w = 300;
		h = 150;
		
		y = Game.HEIGHT/2 - h/ 2;
		x = Game.WIDTH/4 - w/2;
		playBtn = new Rectangle(x,y,w,h);
		
		x = Game.WIDTH* 3/4 - w/2;
		quitBtn = new Rectangle(x,y,w,h);
		
		font = new Font("Cooper Black",Font.PLAIN,50);
		
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		try {
			BufferedImage image = ImageIO.read(new File(location));
			
			g.drawImage(image, 0, 0, null);
		}catch(Exception e) {};
		g.setFont(font);
		
		g.setColor(Color.black);
		if(pHighlight) {
			g.setColor(Color.white);
		}
		g2d.fill(playBtn);
		
		g.setColor(Color.black);
		if(qHighlight) {
			g.setColor(Color.white);
		}
		g2d.fill(quitBtn);
		
		g.setColor(Color.white);
		g2d.draw(playBtn);
		g2d.draw(quitBtn);
		
		int strWidth;
		int strHeight;
		
		strWidth = g.getFontMetrics(font).stringWidth(playTxt);
		strHeight = g.getFontMetrics(font).getHeight();
		
		g.setColor(Color.green);
		g.drawString(playTxt, (int)(playBtn.getX() + playBtn.getWidth()/2 - strWidth /2), 
				(int)(playBtn.getY() + playBtn.getHeight()/2 + strHeight /4));
		
		strWidth = g.getFontMetrics(font).stringWidth(playTxt);
		strHeight = g.getFontMetrics(font).getHeight();
		
		g.setColor(Color.red);
		g.drawString(quitTxt, (int)(quitBtn.getX() + quitBtn.getWidth()/2 - strWidth /2), 
				(int)(quitBtn.getY() + quitBtn.getHeight()/2 + strHeight /4));
			
	}
	
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		
		if(playBtn.contains(p)) {
			active = false;
		}else if(quitBtn.contains(p)) {
			System.exit(0);
		}
	}
	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();
		
		pHighlight = playBtn.contains(p);
		qHighlight = quitBtn.contains(p);
	}
}	
