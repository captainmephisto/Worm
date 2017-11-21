package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Options extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private int winWidth = 400;
	private int winHeight = 400;
	private int ssize;
	private int delay;
	private int shape;
	private int bGuys;
	private int limit;
	private boolean useIt;
	private Constants cons;
	private JLabel delayLabel;
	private int defaultBads;
	private int defaultShape;
	private int defaultSize;
	private int defaultDelay;
	private boolean defaultUse;
	private boolean result;
	
	public Options(JFrame parent,Constants c) {
		super(parent, "", true);
		result = true;
		
		cons = c;
		SetDefaults();
		
		if (parent != null) {
			Dimension parentSize = parent.getSize();
		    setLayout(null);
		    setResizable( false ); 
		    setBounds(0,0,winWidth,winHeight);
			setLocation((parentSize.width-winWidth)/2,(parentSize.height-winHeight)/2);
			setModal(true);
			setUndecorated( true );
		}
			    
	    int x = 30;
		int y = 10;
		int width = 80;
		int height = 25;
		
		JPanel back = new JPanel();
		back.setLayout(null);
		back.setBounds(0, 0, winWidth, winHeight);
		back.setBackground(Color.white);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(null);
		buttonPane.setBounds(2,2, winWidth-4, winHeight-4);
		buttonPane.setBackground(Color.blue);
		
		JLayeredPane layer = new JLayeredPane();
		layer.setBounds(0,0, winWidth, winHeight);
		layer.add(buttonPane, -1);
		layer.add(back,-1);
		
		ArrayList<String> names = new ArrayList<String>();
		names.add("4");
		names.add("8");
		names.add("16");
		names.add("32");
		SetRadioGroup(x,y,names,"Number Of Yellow Zonkers",buttonPane,"Zonkers");
		
		y = y + 135;
		names.clear();
		names.add("Squares");
		names.add("Circles");
		SetRadioGroup(x,y,names,"Select Shape For The Snake",buttonPane,"Shape");
		
		GameSpeed(x,275,buttonPane);
		
		x = 250;
		y = 10;
		names.clear();
		names.add(Integer.toString(3*cons.fac));		//"12");
		names.add(Integer.toString(4*cons.fac));		//("16");
		names.add(Integer.toString(5*cons.fac));		//("20");
		names.add(Integer.toString(6*cons.fac));		//("24");
		SetRadioGroup(x,y,names,"Select Snake Size",buttonPane,"Ssize");
		
		y = y + 135;
		names.clear();
		names.add("No Time Limt");
		names.add("26 Seconds");
		names.add("10 Seconds");
		names.add(" 5 Seconds");
		SetRadioGroup(x,y,names,"Capture Time",buttonPane,"Capture");
		
		width = 80;
		height = 25;
		x = winWidth - width - 10;
		y = winHeight - height - 10;
		
		JButton buttonOK = new JButton("OK"); 
		buttonOK.setActionCommand("OK");
		buttonOK.setBounds(x, y, width, height);
		
		x = x - width - 10;
		JButton buttonClose = new JButton("Close"); 
		buttonClose.setActionCommand("Close");
		buttonClose.setBounds(x, y, width, height);
		
    buttonPane.add(buttonOK,null); 
    buttonPane.add(buttonClose,null); 
    
    buttonOK.addActionListener(this);
    buttonClose.addActionListener(this);
    
    getContentPane().add(layer,null);		//buttonPane,null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public boolean showDialog() {
	    setVisible(true);
	    return result;
	}
	
	public void SetDefaults() {
		defaultBads = cons.NumberOfBadGuys;
		defaultShape = cons.shape;
		defaultSize = cons.sizeOpt;
		defaultDelay = cons.delayTime;
		defaultUse = cons.useLimit;
		
		bGuys = cons.NumberOfBadGuys; 
		shape = cons.shape;
		ssize = cons.sizeOpt;
		delay = cons.delayTime;
		limit = cons.timeLimit;
		useIt = cons.useLimit;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "OK" :
				result = true;
		 		SaveAllValues();
		 		break;
			case "Close" :
				result = false;
			    setVisible(false); 
			    dispose(); 
			case "Ssize0" :
				 ssize = 0;
				 break;				 
			case "Ssize1" :
				 ssize = 1;
				 break;
			case "Ssize2" :
				 ssize = 2;
				 break;
			case "Ssize3" :
				 ssize = 3;
				 break;
			case "Zonkers0" :
				bGuys = 4;
				break;
			case "Zonkers1" :
				bGuys = 8;
				break;
			case "Zonkers2" :
				bGuys = 16;
				break;
			case "Zonkers3" :
				bGuys = 32;
				break;
			case "Capture0" :
				useIt = false;
				break;
			case "Capture1" :
				useIt = true;
				limit = 20;
				break;
			case "Capture2" :
				useIt = true;
				limit = 10;
				break;
			case "Capture3" :
				useIt = true;
				limit = 5;
				break;
			case "Shape0" :
				shape = 0;
				break;
			case "Shape1" :
				shape = 1;
				break;
		 }
	  }
	  
	  private void SaveAllValues() {
		  if (defaultDelay != delay) cons.delayTime = delay;
		  if (defaultSize != ssize) cons.SetBoxSize(ssize);
		  if (defaultShape != shape) cons.shape = shape;
		  if (defaultBads != bGuys) cons.NumberOfBadGuys = bGuys;
		  
		  if ((defaultUse != useIt) || useIt) {
			  cons.useLimit = useIt;
			  cons.timeLimit = limit;
		  }
		  
		  setVisible(false);
		  dispose();
	  }
	  
	  private void SetRadioGroup(int x0,int y0,ArrayList<String> list,
		  String title,JPanel pane,String action) {
		  int x;
		  int y;
		  
		  x = x0;
		  y = y0;
		  JLabel label = new JLabel(title);
		  label.setBounds(x,y, 175, 25);
		  label.setForeground(Color.white);
		  pane.add(label, null);
		
		  ButtonGroup group = new ButtonGroup();
		   
		  x = x + 30;
		  y = y + 25;
		  
		  for (int k=0; k<list.size(); k++) {
			  JRadioButton button = new JRadioButton(list.get(k));
			  String command = action + Integer.toString(k);
			  button.setActionCommand(command);
			  SetRadioButton(button,x,y,list.get(k));
			  button.addActionListener(this);
			  pane.add(button,null);
			  group.add(button);
			  if (k == 0) button.setSelected(true);
			  y = y + 25;
		  }
	  }
	  
	  private void SetRadioButton(JRadioButton radio,int x, int y,String label) {
		  radio.setBounds(x, y, 125, 25);
		  radio.setOpaque(false);
		  radio.setForeground(Color.white);
		  radio.setMnemonic(KeyEvent.VK_P);
	  }
	  
	  private void GameSpeed(int x,int y,JPanel pane) {
		  JLabel label = new JLabel("Game Loop Time Delay (Game Speed Control)");
		  label.setBounds(x,y, 300, 25);
		  label.setForeground(Color.white);
		  pane.add(label, null);
		  
		  JSlider slider = new JSlider(2,200, 100);
		  slider.setMajorTickSpacing(25);
		  slider.setBounds(x+20, y+20, 250, 50);
		  slider.setMinorTickSpacing(2);
		  slider.setForeground(Color.white);
		  slider.setOpaque(false);
		  
		  //Hashtable labels = new Hashtable();
		  Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		  JLabel label1 = new JLabel("Fast");
		  label1.setForeground(Color.white);
	      JLabel label2 = new JLabel("Normal");
	      label2.setForeground(Color.white);
	      JLabel label3 = new JLabel("Slow");
	      label3.setForeground(Color.white);
	      labels.put(  2, label1);
	      labels.put(101, label2);
	      labels.put(200, label3);
	      slider.setLabelTable(labels);
	      slider.setPaintLabels(true);
	      slider.addChangeListener(new Listener());
		  pane.add(slider);
		  
		  delayLabel = new JLabel("Delay Time in Milliseconds: 100");
		  delayLabel.setForeground(Color.white);
		  delayLabel.setBounds(60,y+60, 200,25);
		  pane.add(delayLabel, null);
	  }
	  
	  class Listener implements ChangeListener {
          public void stateChanged(ChangeEvent event) {
             JSlider source = (JSlider) event.getSource();
             delay = source.getValue();
             delayLabel.setText("Delay Time in Milliseconds: "+ delay);
          }
       }
	  
	  @Override
	  public Dimension getPreferredSize() {
		  Dimension newDim = new Dimension(winWidth,winHeight);
	      return newDim;
	  }
}
