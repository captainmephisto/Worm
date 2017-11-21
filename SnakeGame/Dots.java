package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class Dots extends JPanel implements Painter {
	private static final long serialVersionUID = 1L;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private Constants cons;
	
	public Dots(Constants c) {
		cons = c;
		width = cons.width;
		height = cons.height;
		
		SetDotPosition();
	}
	
	public void SetDotPosition() {
		xPos = (int)(Math.random()*width);
		yPos = (int)(Math.random()*height);
	}
	
	public boolean CheckIfHit(int x,int y) {
		if (x == xPos && y == yPos) return true;
		return false;
	}
	
	@Override
	public void paintComponent(Graphics g){
		int x;
		int y;

		Graphics2D g2d = (Graphics2D)g;
		x = cons.box*xPos;
		y = cons.box*yPos;
		
		if (cons.shape == 0) DrawBox(x,y,g2d); 
		else if (cons.shape == 1) DrawCircle(x,y,g2d);
		
		//g2d.setColor(Color.red);
		//g2d.fillRect(x,y, cons.box,cons.box);
	}
	
	private void DrawBox(int x,int y,Graphics2D g2d) {
		g2d.setColor(Color.red);
		g2d.fillRect(x,y, cons.box, cons.box);
		g2d.setColor(Color.black);
		g2d.drawRect(x,y, cons.box-1, cons.box-1);
	}
	
	private void DrawCircle(int x,int y,Graphics2D g2d) {
		g2d.setColor(Color.red);
		g2d.fill(new Ellipse2D.Double(x, y,cons.box,cons.box));
		g2d.setColor(Color.black);
		g2d.draw(new Ellipse2D.Double(x, y,cons.box-1,cons.box-1));
	}
}
