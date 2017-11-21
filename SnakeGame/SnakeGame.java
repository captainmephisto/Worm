package SnakeGame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Constants cons;
	private int height;
	private int width;
	private TheGame theGame;
	
	public static void main(String[] args) {
		SnakeGame snakeGame = new SnakeGame();
		snakeGame.RunTheGame();
	}
	
	public void RunTheGame() {
		InitDimensions();
		InitFrame();
		DoSplash();
		if (DoOptions()) theGame.RunGame(frame);//snakeGame.InitGame();
		System.exit(0);
	}
	
	private void InitDimensions() {
		cons = new Constants();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
	}
	
	private void InitFrame() {
		frame = new JFrame();
		frame.setLocation(-width,-height);
		frame.setSize(width,height);
		frame.setUndecorated (true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		theGame = new TheGame(frame,cons);
		frame.add(theGame);
		frame.setVisible(true);
	}
	
	private void DoSplash() {
		CountDownLatch latch1 = new CountDownLatch(1);
		CountDownLatch latch2 = new CountDownLatch(1);
		
		Splash splash = new Splash(frame,latch1,latch2);
		try {
			latch2.await();
			frame.setLocation(0, 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			latch1.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private boolean DoOptions() {		
		int x;
		int y;
	
		Options options = new Options(frame,cons);
		return options.showDialog();		
	}
	
	private void InitGame() {
		theGame.RunGame(frame);
	}
}
