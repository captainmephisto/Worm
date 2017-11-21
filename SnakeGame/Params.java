package SnakeGame;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Params {
	public static int sizeOpt = 0;			// (sizeOpt + 1)*4 = box size for things
	public static int NumberOfBadGuys = 4;
	public static int delayTime = 100;
	public static int shape = 0;
	public static int bottom = 50;
	public static int timeLimit = 20;
	public static boolean useLimit = false;
	public static int SizeForWin = 100;
	
	public static int gameWidth;
	public static int gameHeight;
	static {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gameWidth = (int)screenSize.getWidth();
		gameHeight =  (int)(screenSize.getHeight() - bottom);
	}	
	
	public static int box;				 	
	public static int width;
	public static int height;
	public static int fac;	 
	static {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		fac = (int)(Math.log(screenSize.getWidth()/96)/Math.log(2));
		fac=(int)Math.pow(2, fac-1);
		box = fac*(3 + sizeOpt);		//(sizeOpt + 3)*4;
		width = gameWidth/box;
		height = gameHeight/box;
	}
	
	public void ResetValues() {
		timeLimit = 20;
		useLimit = false;
		shape = 0;
		SizeForWin = 100;
		NumberOfBadGuys = 4;
		delayTime = 100;
		SetBoxSize(0);
	}
	
	public void SetBoxSize(int opt) {
		sizeOpt = opt;
		box = fac*(3 + sizeOpt);		//(sizeOpt + 3)*4;
		width = gameWidth/box;
		height = gameHeight/box;
	}
}
