package SnakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Message extends JPanel implements Painter {
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	int wide;
	int tall;
	Font font;
	String msgString;
	boolean showMessage;
	
	public Message(Constants cons) {
		wide = 500;
		tall = 100;
		x = (cons.gameWidth - wide)/2;
		y = (cons.gameHeight - tall)/2;
		font = new Font("TimesRoman", Font.BOLD, 48);
		showMessage = false;
		msgString = "";
	}
	
	@Override
	public void paintComponent(Graphics g){
		if (showMessage) {
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setColor(Color.red);
			g2d.fillRect(x,y,wide,tall);
			g2d.setColor(Color.black);
			g2d.drawRect(x,y,wide,tall);
			
			g2d.setFont(font);
			FontMetrics fm = g2d.getFontMetrics();
			
			java.awt.geom.Rectangle2D rect = fm.getStringBounds(msgString, g);
			int h0 = (int)(rect.getHeight()); 
			int w0  = (int)(rect.getWidth());
			h0 = (tall - h0)/2 + fm.getAscent();
		    
		    int labX = x + (wide - w0)/2;
		    int labY = y + h0;

			g2d.setColor(Color.white);
			g2d.drawString(msgString,labX,labY);
		}
	}
	
	public void popMessage(String msg) {
		showMessage = true;
		msgString = msg;
	}
	
}
