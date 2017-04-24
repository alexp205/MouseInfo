package main;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class AngleGUI extends Frame 
		implements MouseListener, KeyListener{
	private int numCoords = 0;
	private double firstX = 0;
	private double firstY = 0;
	private double secondX = 0;
	private double secondY = 0;
	private double thirdX = 0;
	private double thirdY = 0;
	GUI guiRef;

	public AngleGUI(GUI gui) {
		guiRef = gui;
		
		addMouseListener(this);
		addKeyListener(this);
		
		setUndecorated(true);
		setSize(1920, 1080);
		setOpacity(.01f);
	}
	
	void showGUI() {
		guiRef.sgui.hideGUI();
		
		setVisible(true);
		requestFocus();
	}
	
	void hideGUI() {
		setVisible(false);
	}
	
	//NOTE: ORDER IN ARRAY IS -ALWAYS- [RAD, DEG]
	double[] calcAngles() {
		double angle[] = {0, 0};
		double aLineX = secondX - firstX;
		double aLineY = secondY - firstY;
		double aLine = Math.sqrt((Math.pow(aLineX, 2)) + (Math.pow(aLineY, 2)));
		double bLineX = secondX - thirdX;
		double bLineY = secondY - thirdY;
		double bLine = Math.sqrt((Math.pow(bLineX, 2)) + (Math.pow(bLineY, 2)));
		double aDotB = (aLineX * bLineX) + (aLineY * bLineY);
		angle[0] = Math.acos(aDotB / ((Math.abs(aLine)) * (Math.abs(bLine))));
		angle[1] = angle[0] * (180/Math.PI);
		return angle;
	}
	
	void exit() {
		guiRef.clearAngleMeas();
		numCoords = 0;
		guiRef.showMainView();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point pt = MouseInfo.getPointerInfo().getLocation();
		//RESET IF MOUSE CLICKED AFTER ANGLES CALCULATED ALREADY
		if (numCoords == 3) {
			guiRef.clearAngleMeas();
			numCoords = 0;
		}
		
		//FIRST COORDINATE
		if (numCoords == 0) {
			firstX = pt.getX();
			firstY = pt.getY();
			guiRef.updateAngleMeas(e.getPoint(), numCoords);
			numCoords++;
		}
		//SECOND COORDINATE
		else if (numCoords == 1) {
			secondX = pt.getX();
			secondY = pt.getY();
			guiRef.updateAngleMeas(e.getPoint(), numCoords);
			repaint();
			numCoords++;
		}
		//THIRD COORDINATE
		else if (numCoords == 2) {
			thirdX = pt.getX();
			thirdY = pt.getY();
			guiRef.updateAngleMeas(e.getPoint(), numCoords);
			repaint();
			numCoords++;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'q':
			exit();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
