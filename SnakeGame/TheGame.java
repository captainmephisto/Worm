package SnakeGame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TheGame extends JPanel {
	private static final long serialVersionUID = 1L;
	private TheSnake theSnake;
	private Dots theDot;
	private BadGuys badGuys;
	private ArrayList<Painter> layerPainters = new ArrayList<Painter>();
	private Constants cons;
	private Background back;
	private Explosions boom;
	private int numberOfHits;
	private Timer timer;
	private int counter;

	public TheGame(JFrame frame,Constants c) {
		cons = c;
		
		back = new Background(cons);
		layerPainters.add(back);
		frame.getContentPane().add(back, null);
		
		timer = new Timer(20,new TimerEx());
		timer.setRepeats(true);
		
		KeyThread kThread = new KeyThread();
		kThread.start();
	}	
	
	public void RunGame(JFrame frame) {	
		badGuys = new BadGuys(cons);
		theDot = new Dots(cons);
		theSnake = new TheSnake(theDot,badGuys,back,cons);
		boom = new Explosions();
		
		layerPainters.add(badGuys);
		layerPainters.add(theSnake);
		layerPainters.add(theDot);
		layerPainters.add(boom);

		frame.getContentPane().add(badGuys, null);
		frame.getContentPane().add(theSnake, null);
		frame.getContentPane().add(theDot, null);
		frame.getContentPane().add(boom, null);
		
		boolean loopFlag = true;
		
		while (loopFlag) {
			if (cons.useLimit) back.StartCountdown(true);
		numberOfHits = 0;
		boolean hit = false;
		boolean retValue = false;
		int count = 0;
		int del = 0;
		back.StartTimer();
		
		while (true) {
			this.requestFocus();
			theSnake.Move(count,del);
        	repaint();
			
			if (theSnake.WonGame()) {
				retValue = PopEndMessage(frame,"You Won The Game!");
    			break;			}
			
			if (count > 0) count = count - 1;
			if (del > 0) del = del - 1;
         	
        	hit = theSnake.HitTheDot();
        	if (hit) {
        		numberOfHits = numberOfHits + 1;
        		count = count + 5;
        		theDot.SetDotPosition();
        		back.StartCountdown(false);
        	} else if (cons.useLimit && back.OutOfTime()) {
     			retValue = PopEndMessage(frame,"Ran Out of Time");
    			break;
        	}
        	
        	hit = theSnake.HitBadGuy(theSnake.GetSize());
        	if (hit) {
        		DoExplosion();
         		if (theSnake.GetSize() > 1) {
        			del = del + 5;
        		} else {
        			retValue = PopEndMessage(frame,"Game Over");
        			break;
        		}
        	}
        	
			badGuys.Move();        	
			repaint();
        	
			if (!hit) {
				hit = theSnake.HitBadGuy(theSnake.GetSize());
	        	if (hit) {
	        		DoExplosion();
	         		if (theSnake.GetSize() > 1) {
	        			del = del + 5;
	        		} else {
	        			retValue = PopEndMessage(frame,"Game Over");
	        			break;
	        		}
	        	}
			}
        	
        	try {
        		Thread.sleep(cons.delayTime);
        	}catch(InterruptedException e){
//        		System.out.println("got interrupted!");
        	}
        }
		
		boom.Reset();
		
		if (retValue) { 
			cons.ResetValues();
			Options options = new Options(frame,cons);
			options.setVisible(true);
			theSnake.InitSnake();
			badGuys.InitAllGuys();
		} else {
			loopFlag = false;
		}
		repaint();
		}
	}
	
	private void DoExplosion() {
		int pos[] = theSnake.ThePosition();
		
		int x = cons.box*pos[0] + cons.box/2;
		int y = cons.box*pos[1] + cons.box/2;
		
		boom.Explode(x, y);
		ExplodeSound();
		
		counter = 0;
		timer.start();
	}
	
	private class EndGraphics extends Thread {
		 public void run() {
			 repaint();
		 }
	}
	
	private boolean PopEndMessage(JFrame frame,String out0) {
		String out2 ;
		
		Thread endGraphics = new EndGraphics();
		endGraphics.start();
		
		back.ResetTimer();
		
		float[] numbs = back.GetTimes(numberOfHits);
		String out1 = "Total Game Time: " + Integer.toString((int)numbs[0]) + " s";
		
		if (numberOfHits == cons.SizeForWin) {
			out2 = String.format("%.3f", numbs[1]);
			out2 = "Average Hit Time: " + out2 + " s";
		} else if (numberOfHits == 0) {
			out2 = "No Hits Were Made";
		} else {
			out2 = String.format("%.3f", numbs[1]);
			out2 = "Hits/Average Time " + numberOfHits + " / " + out2 + " s";
		}
		
		EndGame endGame = new EndGame(frame,out0,out1,out2);
		boolean retValue = endGame.showDialog();
		return retValue;
	}
	
	public void paintComponent( Graphics g ){
		super.paintComponent(g);
	    for(Painter painter : layerPainters){
	        painter.paintComponent(g);
	    }
	}
	
	public class KeyThread extends Thread { 
	    public void run(){
	    	KeyListen myListener = new KeyListen();
	    	requestFocusInWindow();
	    	addKeyListener(myListener);	       
	    }
	  }
	
	 private class KeyListen implements KeyListener{
         public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.VK_W) theSnake.ChangeDirection(0);
            if (keyCode == KeyEvent.VK_A) theSnake.ChangeDirection(1);
            if (keyCode == KeyEvent.VK_S) theSnake.ChangeDirection(2);
            if (keyCode == KeyEvent.VK_D) theSnake.ChangeDirection(3);
         }

        @Override
        public void keyReleased(KeyEvent event) {
        }

		@Override
		public void keyTyped(KeyEvent e) {
		}
	 }
	 
	 private class TimerEx implements ActionListener {
		  public void actionPerformed(ActionEvent e) {
			  repaint();
			  counter = counter + 1;
			  if (counter == 19) timer.stop();
		  }
	 }
	 
	 private void ExplodeSound() {
		String path;
		AudioInputStream ais;

		path = "explosion3.wav";
		URL url  = this.getClass().getResource("/resources/"+path);
		try {
			ais = AudioSystem.getAudioInputStream(url);
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);		
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	 }
}
