package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Background extends JPanel implements Painter{
	private static final long serialVersionUID = 1L;
	private static Image img1;
	private static Image img2;
	private static Image img3;
	private static Image img4;
	private int width;
	private int height;
	private int labX;
	private int labY;
	private int numberOf;
	private int dx;
	private int dy;
	private int numX;
	private int numY;
	private Font font;
	private Constants cons;
	private Timer timer;
	private long currentTick;
	private long startTime;
	private long elapsedMS;
	private boolean outOfTime;
	private long elapsed;
	private long countdown;
	private long startCount;
	private GradientPaint blueShades;
	
	public Background() {
	}
	
	public Background(Constants c) {
		cons = c;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();

		blueShades = new GradientPaint(0,0, new Color(0,0,128), cons.gameWidth,30,
				Color.blue);
		
		font = new Font("TimesRoman", Font.BOLD, 30);
		labX = 50;
		labY = height - cons.bottom/2;
		
		this.setSize(width, height);
		height = height - cons.bottom;
		this.setLocation(0, 0);
		
		img1 = GetImage("texture1.png");
		img2 = GetImage("texture2.png");
		img3 = GetImage("texture3.png");
		img4 = GetImage("texture4.png");
		
		dx = img1.getWidth(null);
		dy = img1.getHeight(null);
		numX = width/dx + 1;
		numY = height/dy;
		
		timer = new Timer(500,new TimerTick());
		timer.setRepeats(true);
		currentTick = -1;
		
		if(cons.useLimit) countdown = cons.timeLimit;
	} 
	
	private Image GetImage(String name) {
		URL url  = this.getClass().getResource("/resources/"+name);
		ImageIcon icon = new ImageIcon(url);
		return icon.getImage();
	}
	
	@Override
	public void paintComponent(Graphics g){   //paintComponent
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		
		for (int k=0; k<=numY; k++) {
			for (int m=0; m<=numX; m=m+2) {
				int x0 = m*dx;
				int y0 = k*dy;
				
				if (k % 2 == 0) {
					g2d.drawImage(img1,x0,y0,null);
					x0 = x0 + dx;
					g2d.drawImage(img2,x0,y0,null);
				} else {
					g2d.drawImage(img3,x0,y0,null);
					x0 = x0 + dx;
					g2d.drawImage(img4,x0,y0,null);
				}
			}
		}
		
		g2d.setPaint(blueShades);
		
		//g2d.setColor(Color.magenta);
		g2d.fillRect(0,height,width,cons.bottom);
		g2d.setPaint(Color.black);
		g2d.setColor(Color.black);
		g2d.drawRect(0,height,width,cons.bottom);
		
		g2d.setFont(font);
		FontRenderContext frc = g2d.getFontRenderContext();
	    int h0 = (int) font.getStringBounds("X", frc).getHeight();
		g2d.setColor(Color.white);
		g2d.drawString(Integer.toString(numberOf),labX,labY + h0/4);
		
		if (currentTick >= 0) {
			g2d.drawString(Long.toString(currentTick) + " s",cons.gameWidth-150,labY + h0/4);
		}
		
		if (cons.useLimit) DrawTimerSquares(g2d);
	}
	
	public void SetNumberOf(int numb) {
		numberOf = numb;
	}
	
	public void StartTimer() {
		startTime = System.currentTimeMillis();
		currentTick = 0;
		timer.start();
	}
	
	public void ResetTimer() {
		timer.stop();
	}	
	
	class TimerTick implements ActionListener {
		  public void actionPerformed(ActionEvent e) {
			  elapsed = System.currentTimeMillis() - startTime;
			  elapsedMS = elapsed;
			  elapsed = elapsed/1000;
			  
			  if (elapsed > currentTick) currentTick = elapsed;
			  if (cons.useLimit) UpdateCountdown();
		  }
	}
	
	public float[] GetTimes(int numberOf) {
		float total = (float)elapsedMS/1000;
		float average = total/numberOf;
		return (new float[]{total,average});
	}
	
	private void DrawTimerSquares(Graphics2D g2d) {
		int nx = 23;
		int wide = cons.timeLimit*(nx + 2);
		int x = (cons.gameWidth - wide)/2;
		int y = cons.gameHeight + cons.bottom - nx - 10;
		
		for (int k=0; k<cons.timeLimit; k++) {
			if (k < countdown) { g2d.setColor(Color.green); }
			else { g2d.setColor(Color.red); }
			
			g2d.fillRect(x,y,nx,nx);
			g2d.setColor(Color.black);
			g2d.drawRect(x,y, nx,nx);	
			x = x + nx + 2;
		}
	}
	
	public void StartCountdown(boolean newGame) {
		outOfTime = false;
		if (newGame) elapsed = 0;
		startCount = elapsed;
		countdown = cons.timeLimit;
	}
	
	public void UpdateCountdown() {
		if (!outOfTime) {
			countdown = cons.timeLimit - elapsed + startCount;
			outOfTime =  false;
			if (countdown == 0) {
				outOfTime = true; 
			// {
			}
		//		countdown = cons.timeLimit;
		//		startCount = elapsed;
		//	}
		}
	}
	
	public boolean OutOfTime() {
		//if (countdown <=0) return true;
		return outOfTime;		//false;
	}
}
