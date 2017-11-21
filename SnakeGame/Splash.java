package SnakeGame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Splash extends JPanel {		//implements ActionListener {
	private Image splashBack;
	private Image title;
	private int width;
	private int height;
	private Timer timer;
	private float alpha = 1f;//0f;
	private long startTime = -1;
	public static final long RUNNING_TIME = 2000;
	private ImagePanel imagePanel;
	private JFrame mainFrm;
	private CountDownLatch latch1;
	private CountDownLatch latch2;
	private boolean first;
	
	public Splash(JFrame frm,CountDownLatch lat1,CountDownLatch lat2) {
		mainFrm = frm;
		latch1 = lat1;
		latch2 = lat2;
		first = true;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		
		splashBack = GetImage("splash.png");
		title = GetImage("title3.png");		
	
		System.out.println(splashBack.getWidth(null)+" "+splashBack.getHeight(null));
		JFrame frame = new JFrame();
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setSize(width,height);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0f,0f,0f,0.0f));
        frame.setBackground(new Color(0f,0f,0f,0.0f));
        
        imagePanel = new ImagePanel();
        imagePanel.repaint();
        frame.getContentPane().add(imagePanel);
        frame.setVisible(true);
        frame.repaint();
        
        final Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (first) {
            		first = false;
            		latch2.countDown();
            	}
            	
                if (startTime < 0) {
                    startTime = System.currentTimeMillis();
                } else {
                    long time = System.currentTimeMillis();
                    long duration = time - startTime;
                    if (duration >= RUNNING_TIME) {
                        startTime = -1;
                        ((Timer) e.getSource()).stop();
                        alpha = 0f;
                    } else {
                        alpha = 1f - ((float) duration / (float) RUNNING_TIME);
                    }
                    
                    imagePanel.repaint();
                    if (alpha <= 0) {
                    	mainFrm.setVisible(true);
                    	frame.dispose();
                    	latch1.countDown();
                    }
                }
            }
        });
        
        timer.setInitialDelay(1000);
        timer.start(); 
	}
	
	private Image GetImage(String name) {
		URL url  = this.getClass().getResource("/resources/"+name);
		ImageIcon icon = new ImageIcon(url);
		return icon.getImage();
	}
	
    public class ImagePanel extends JPanel {
        public ImagePanel() {
         	this.setBounds(0, 0,width,height);
            setOpaque(false);
            setLayout(null);
            setVisible(true);
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
    	    super.paintComponent(g);
    		int x;
    		int y;

    	    Graphics2D g2d = (Graphics2D)g;
    	    g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
    	    g2d.drawImage(splashBack, 0, 0, getWidth(), getHeight(),null);
    	    
    	    x = (getWidth() - title.getWidth(null))/2;
    	    y = (getHeight() - title.getHeight(null))/2;
    	    g2d.drawImage(title, x, y, null);
    	}
    }
 }
