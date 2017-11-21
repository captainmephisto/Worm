package SnakeGame;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants {
	public int shape;
	public int box;				// box size in pixels (see sizeOpt) 
	public int width;
	public int height;
	public int bottom;
	public int gameWidth;
	public int gameHeight;
	public int SizeForWin;
	public int NumberOfBadGuys;
	public int delayTime;
	public int sizeOpt;			// (sizeOpt + 1)*4 = box size for things
	public int timeLimit;
	public boolean useLimit;
	public int fac;
	
	public Constants() {
		bottom = 50;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gameWidth = (int)screenSize.getWidth();
		gameHeight =  (int)(screenSize.getHeight() - bottom);

		fac = (int)(Math.log(screenSize.getWidth()/96)/Math.log(2));
		fac=(int)Math.pow(2, fac-1);
		SetBoxSize(0);

		ResetValues();
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
