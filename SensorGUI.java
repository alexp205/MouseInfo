package main;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class SensorGUI extends Frame 
		implements MouseListener, MouseMotionListener, KeyListener{
	GUI guiRef;
	
	public SensorGUI(GUI gui) {
		guiRef = gui;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		setSize(screenWidth, screenHeight);
		setOpacity(.01f);
		setVisible(true);
		requestFocus();
	}
	
	void showGUI() {
		guiRef.ngui.hideGUI();
		
		setVisible(true);
		requestFocus();
	}
	
	void hideGUI() {
		setVisible(false);
	}
	
	void exit(int cmd) {
		if (cmd == -1) System.exit(0);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		guiRef.updateMain(e.getPoint());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'n':
			guiRef.showAngleMeasView();
			break;
		case 's':
			guiRef.writeInfo();
			break;
		case 'q':
			exit(-1);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
