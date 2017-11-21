package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BadGuys extends JPanel implements Painter{
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> xPos = new ArrayList<Integer>();
	private ArrayList<Integer> yPos = new ArrayList<Integer>();
	private ArrayList<Integer> xMov = new ArrayList<Integer>();
	private ArrayList<Integer> yMov = new ArrayList<Integer>();
	private int width;
	private int height;
	private int steps;
	private int stepLimit;
	private Constants cons;

	public BadGuys(Constants c) {
		cons = c;

		steps = 0;
		stepLimit = 20;
		
		InitAllGuys();
	}
	
	public void InitAllGuys() {
		width = cons.width;
		height = cons.height;

		xPos.clear();
		yPos.clear();
		xMov.clear();
		yMov.clear();
		
		for (int k=0; k<cons.NumberOfBadGuys; k++) {
			InitGuy();
		}
	}
	
	public void InitGuy() {
		int x = 0;
		int y = 0;
		boolean dupe;
		
		dupe = true;
		while (dupe) {
			x = (int)(Math.random()*width);
			y = (int)(Math.random()*height);
			dupe = FindMatch(x,y);
		}
		
		xPos.add(x);
		yPos.add(y);
	
		int[] pos = GetDirection(2,2,xPos.get(0),yPos.get(0));
		xMov.add(pos[0]);
		yMov.add(pos[1]);
	}
	
	private int[] GetDirection(int oldX,int oldY,int posX,int posY) {
		int xMov;
		int yMov;
		boolean useX;
		
		xMov = 0;
		yMov = 0;
		
		if (Math.abs(oldX) == 2) {
			useX = false;
			if (Math.random() < 0.5) useX = true;
		} else {
			useX = true;
			if (Math.abs(oldX) == 1) useX = false;
		}
		
		if (useX) {			//Math.random() < 0.5) { 	//X
			if (Math.random() < 0.5) { xMov = -1; }
			else { xMov = 1;}
		} else {					//Y
			if (Math.random() < 0.5) { yMov = -1; }
			else { yMov = 1; }
		}
		
		int pos[] = new int[]{xMov,yMov};
		return pos;
	}
	
	private void HitTheWall(int k) {
		int movX = xMov.get(k);
		int movY = yMov.get(k);
		int posX = xPos.get(k);
		int posY = yPos.get(k);
		
		if (Math.abs(movX) == 1) {
			int newPos = posX + movX;
			if (newPos < 0 || newPos >= width) {
				xMov.set(k, 0);
				yMov.set(k, NewYDirection(k));
			}
		} else if (Math.abs(movY) == 1) {
			int newPos = posY + movY;
			if (newPos < 0 || newPos >= height) {
				xMov.set(k, NewXDirection(k));
				yMov.set(k, 0);
			}
		}
	}
	
	private boolean FindMatch(int x,int y) {
		for (int k=0; k<xPos.size(); k++) {
			if (x == xPos.get(k) && y == yPos.get(k)) return true;
		}
		return false;
	}
	
	public void Move() {
		for (int k=0; k<xPos.size(); k++) {
			if (steps == stepLimit) {
				int[] pos = GetDirection(xMov.get(k),yMov.get(k),xPos.get(k),yPos.get(k));
				xMov.set(k, pos[0]);
				yMov.set(k, pos[1]);
			}
			
			HitTheWall(k);
			xPos.set(k, xPos.get(k) + xMov.get(k));
			yPos.set(k, yPos.get(k) + yMov.get(k));	
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		int x;
		int y;
		
		Graphics2D g2d = (Graphics2D)g;
		
		if (cons.shape == 0) {
			for (int k=0; k<xPos.size(); k++) {
				x = cons.box*xPos.get(k);
				y = cons.box*yPos.get(k);
				DrawBox(x,y,g2d);
				//g2d.setColor(Color.yellow);
				//g2d.fillRect(x,y,cons.box, cons.box);
			}
		} else if (cons.shape == 1) {
			for (int k=0; k<xPos.size(); k++) {
				x = cons.box*xPos.get(k);
				y = cons.box*yPos.get(k);
				DrawCircle(x,y,g2d);
			}
		}
		
		if (steps == stepLimit) {
			steps = 0;
		} else {
			steps = steps + 1;
		}
	}
	
	private void DrawBox(int x,int y,Graphics2D g2d) {
		g2d.setColor(Color.yellow);
		g2d.fillRect(x,y, cons.box, cons.box);
		g2d.setColor(Color.black);
		g2d.drawRect(x,y, cons.box-1, cons.box-1);
	}
	
	private void DrawCircle(int x,int y,Graphics2D g2d) {
		g2d.setColor(Color.yellow);
		g2d.fill(new Ellipse2D.Double(x, y,cons.box,cons.box));
		g2d.setColor(Color.black);
		g2d.draw(new Ellipse2D.Double(x, y,cons.box-1,cons.box-1));
	}
	
	private int  NewYDirection(int k) {
		if (yPos.get(k) > height/2) return -1;
		return 1;
	}
	private int  NewXDirection(int k) {
		if (xPos.get(k) > width/2) return -1;
		return 1;
	}
	
	public boolean CheckIfHit(int x,int y,boolean theEnd) {
		for (int k=0; k<xPos.size(); k++) {
			if (x == xPos.get(k) && y == yPos.get(k)) {
				RemoveGuy(k);
				if (!theEnd) InitGuy();
				return true;
			}
		}
		return false;
	}
	
	private void RemoveGuy(int k) {
		xPos.remove(k);
		yPos.remove(k);
		xMov.remove(k);
		yMov.remove(k);
	}
}
