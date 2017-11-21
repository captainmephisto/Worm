package SnakeGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Explosions extends JPanel implements Painter {
	private static final long serialVersionUID = 1L;
	Image ex1;
	String path;
	String[] type = new String[]{"exp1"};
	ArrayList<Integer> offset = new ArrayList<Integer>();
	ArrayList<Image[]> picts = new ArrayList<Image[]>();
	ArrayList<Integer> xPos = new ArrayList<Integer>();
	ArrayList<Integer> yPos = new ArrayList<Integer>();
	ArrayList<Integer> kind = new ArrayList<Integer>();
	ArrayList<Integer> curIndex = new ArrayList<Integer>();
	int[] count = new int[]{19};
	
	public Explosions() {
		for (int k=0; k<type.length; k++) {
			path = "/" +  type[k] + "/";
			Image[] pict = new Image[count[k]];
			
			for (int i=0;i<count[k];i++) {
				ex1 = null;
		    	String name = path + ((Integer)i).toString() + ".gif";
		    		 
		    	URL url  = this.getClass().getResource(name);
		    	ImageIcon icon = new ImageIcon(url);
		    	pict[i] = icon.getImage();
		    	offset.add(icon.getIconWidth()/2);
		    }
			
			picts.add(pict);
		}
	}
	
	public void Explode(int x,int y) {
		xPos.add(x-offset.get(0));
		yPos.add(y-offset.get(0));
		kind.add(0);	//rand.nextInt(type.length));
		curIndex.add(0);
	}

	private Image GetNextImage(int k) {
		int ndx = kind.get(k);
		int current = curIndex.get(k);
		
		if (current < count[ndx]) {
			curIndex.set(k, current+1);
			Image[] pictures = picts.get(ndx);
			return pictures[current];
		}
		
		return null;
	}
	
	private void RemoveExplosions() {
		for (int k=xPos.size()-1; k>=0; k--) {
			if (curIndex.get(k) < 0) {
				xPos.remove(k);
				yPos.remove(k);
				kind.remove(k);
				curIndex.remove(k);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		Image pic;
		int xbomb;
		int ybomb;
		
		Graphics2D g2d = (Graphics2D)g;
		
		for (int k=0; k<xPos.size(); k++) {
			pic = GetNextImage(k);
			if (pic != null){
				xbomb = xPos.get(k);
				ybomb = yPos.get(k);
				g2d.drawImage(pic,xbomb,ybomb, null);
			} else {
				curIndex.set(k,-1);
			}
		}
		
		RemoveExplosions();
	}
	
	public void Reset() {
		xPos.clear();
		yPos.clear();
		kind.clear();
	}
}
