package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TheSnake extends JPanel implements Painter {
	private static final long serialVersionUID = 1L;
	private Dots theDot;
	private BadGuys badGuys;
	private Background back;
	private ArrayList<Integer> xPos = new ArrayList<Integer>();
	private ArrayList<Integer> yPos = new ArrayList<Integer>();
	private int xMov;
	private int yMov;
	private Constants cons;
	
	public TheSnake(Dots d,BadGuys bg,Background b,Constants c) {
		cons = c;

		theDot = d;
		badGuys = bg;
		back = b;
		back.SetNumberOf(1);
		InitSnake();
	}
	
	public void InitSnake() {
		xPos.clear();
		yPos.clear();
		
		xPos.add(25);
		yPos.add(12);
		xMov = 0;
		yMov = 1;
	}
	
	public void Move(int count,int del) {
		int lastX = 0;
		int lastY = 0;
		
		if (count > 0) {
			lastX = xPos.get(xPos.size()-1);
			lastY = yPos.get(xPos.size()-1);
		}
		
		for (int k=xPos.size()-1; k > 0; k=k-1) {
			xPos.set(k, xPos.get(k-1));
			yPos.set(k, yPos.get(k-1));
		}
		
		if (count > 0) {
			xPos.add(lastX);
			yPos.add(lastY);
			back.SetNumberOf(xPos.size());
		}
		
		HitTheWall();
		
		xPos.set(0, xPos.get(0) + xMov);
		yPos.set(0, yPos.get(0) + yMov);
		
		if (del > 0 && xPos.size() > 1) {
			int ndx = xPos.size() - 1;
			xPos.remove(ndx);
			yPos.remove(ndx);
			back.SetNumberOf(ndx);
		}
	}
	
	// W=0, A=1, S=2, D=3
	public void ChangeDirection(int dir) {
		if (Math.abs(xMov) == 1 && (dir == 1 || dir == 3)) return;
		if (Math.abs(yMov) == 1 && (dir == 0 || dir == 2)) return;
		xMov = 0;
		yMov = 0;
		if (dir == 0) yMov = -1;
		if (dir == 1) xMov = -1;
		if (dir == 2) yMov = 1;
		if (dir == 3) xMov = 1;
	}
	
	private void HitTheWall() {
		if (Math.abs(xMov) == 1) {
			int newPos = xPos.get(0) + xMov;
			if (newPos < 0 || newPos >= cons.width) {
				xMov = 0;
				yMov = NewYDirection();
			}
		} else if (Math.abs(yMov) == 1) {
			int newPos = yPos.get(0) + yMov;
			if (newPos < 0 || newPos >=cons. height) {
				xMov = NewXDirection();
				yMov = 0;
			}
		}
	}
	
	public boolean HitTheDot() {
		return theDot.CheckIfHit(xPos.get(0), yPos.get(0));
	}
	
	public boolean HitBadGuy(int snakeSize) {
		boolean theEnd = false;
		if (snakeSize == 1) theEnd = true;
		return badGuys.CheckIfHit(xPos.get(0), yPos.get(0),theEnd);
	}
	
	private int  NewYDirection() {
		if (yPos.get(0) > cons.height/2) return -1;
		return 1;
	}
	
	private int  NewXDirection() {
		if (xPos.get(0) > cons.width/2) return -1;
		return 1;
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
			}
		} else if (cons.shape == 1) {
			for (int k=0; k<xPos.size(); k++) {
				x = cons.box*xPos.get(k);
				y = cons.box*yPos.get(k);
				DrawCircle(x,y,g2d);
			}
		}
	}
	
	private void DrawBox(int x,int y,Graphics2D g2d) {
		g2d.setColor(Color.cyan);
		g2d.fillRect(x,y, cons.box, cons.box);
		g2d.setColor(Color.black);
		g2d.drawRect(x,y, cons.box-1, cons.box-1);
	}
	
	private void DrawCircle(int x,int y,Graphics2D g2d) {
		g2d.setColor(Color.cyan);
		g2d.fill(new Ellipse2D.Double(x, y,cons.box,cons.box));
		g2d.setColor(Color.black);
		g2d.draw(new Ellipse2D.Double(x, y,cons.box-1,cons.box-1));
	}
	
	public int GetSize() {
		return xPos.size();
	}
	
	public int[] ThePosition() {
		int[] pos = new int[]{xPos.get(0),yPos.get(0)};
		return pos;
	}
	
	
	public boolean WonGame() {
		if (xPos.size() > cons.SizeForWin) return true;
		return false;
	}
}
