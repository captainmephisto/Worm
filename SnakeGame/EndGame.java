package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndGame extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	int winWidth;
	int winHeight;
	boolean retFlag;
	JFrame frame;
	
	public EndGame(JFrame parent,String msg0,String msg1,String msg2) {
		frame = parent;
		winWidth = 425;
		winHeight = 150;
		
		Dimension parentSize = parent.getSize();
	    setLayout(null);
	    setResizable( false ); 
	    setBounds(0,0,winWidth,winHeight);
		setLocation((parentSize.width - winWidth)/2,(parentSize.height-winHeight)/2);
		setModal(true);
		setUndecorated( true );
		
		JPanel back = new JPanel();
		back.setLayout(null);
		back.setBounds(0, 0, winWidth, winHeight);
		back.setBackground(Color.white);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(null);
		buttonPane.setBounds(2,2, winWidth-4, winHeight-4);
		buttonPane.setBackground(Color.red);
		
		JLayeredPane layer = new JLayeredPane();
		layer.setBounds(0,0, winWidth, winHeight);
		layer.add(buttonPane, -1);
		layer.add(back,-1);
		
		JLabel[] label = new JLabel[3];
		Font font = new Font("TimesRoman", Font.BOLD, 28);
		label[0] = new JLabel();
		label[0].setText(msg0);
		label[0].setFont(font);
		label[0].setHorizontalAlignment(SwingConstants.CENTER);
		
		label[1] = new JLabel();
		label[1].setText(msg1);
		label[1].setFont(font);
		label[1].setHorizontalAlignment(SwingConstants.CENTER);
		
		label[2] = new JLabel();
		label[2].setText(msg2);
		label[2].setFont(font);
		label[2].setHorizontalAlignment(SwingConstants.CENTER);
	
		int x = 0;
		int y = 20;
		
		for (int k=0; k<3; k++) {
			label[k].setBounds(x,y, winWidth, 25);
			label[k].setForeground(Color.white);
			buttonPane.add(label[k], null);
			y = y + 25;
		}
		
		//buttonPane.add(label, null);
		
		int width = 80;
		int height = 25;
		int spaceX = (winWidth - 2*width)/3;
		x = spaceX;
		y = winHeight - height - 10;
		
		JButton buttonReplay = new JButton("Replay"); 
		buttonReplay.setActionCommand("Replay");
		buttonReplay.setBounds(x, y, width, height);
		buttonReplay.addActionListener(this);
		
		x = x + width + spaceX;
		JButton buttonQuit = new JButton("Quit"); 
		buttonQuit.setActionCommand("Quit");
		buttonQuit.setBounds(x, y, width, height);
		buttonQuit.addActionListener(this);
		
	    buttonPane.add(buttonReplay,null); 
	    buttonPane.add(buttonQuit,null); 
	    
	    getContentPane().add(layer,null);
//	    getContentPane().add(back,null);
//	    getContentPane().add(buttonPane,null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		retFlag = false;
		
		switch (e.getActionCommand()) {
	 	case "Replay" :
	 		retFlag = true;
		}
		setVisible(false); 
		dispose();
 	}
	
	public boolean showDialog() {
	    setVisible(true);
	    return retFlag;
	}

}
