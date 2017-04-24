package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

@SuppressWarnings("serial")
public class GUI extends Frame implements WindowListener{
	private final File output = new File("C:\\Users\\Alexander\\Programs\\MouseInfo\\Coordinates.txt");
	private static Robot r;
	private TextField tfMousePositionX;
	private TextField tfMousePositionY;
	private TextField tfColorR;
	private TextField tfColorG;
	private TextField tfColorB;
	private TextField tfColorHex;
	private BufferedImage img;
	private TextField tfFirstX;
	private TextField tfFirstY;
	private TextField tfSecondX;
	private TextField tfSecondY;
	private TextField tfThirdX;
	private TextField tfThirdY;
	private TextField tfAngleRad;
	private TextField tfAngleDeg;
	private boolean updateImg = true;
	SensorGUI sgui;
	AngleGUI ngui;

	public GUI() {
		try {
			r = new Robot();
		} catch (AWTException e) {
			System.out.println("Error with Robot");
			sgui.exit(-1);
			ngui.exit();
			System.exit(-1);
		}

		sgui = new SensorGUI(this);
		ngui = new AngleGUI(this);

		showMainView();
	}

	public static void main(String[] args) {
		new GUI();
	}

	void showMainView() {
		this.removeAll();
		updateImg = true;
		setLayout(new FlowLayout());

		add(new Label("                          *Type 'n' to measure angles.                          "));
		add(new Label("*Type 's' to save coordinates. Filename is: \"Coordinates\""));
		add(new Label("                        *Type 'q' to exit sensor screen.                        "));

		add(new Label("X-Position: "));
		tfMousePositionX = new TextField(8);
		tfMousePositionX.setEditable(false);
		add(tfMousePositionX);
		add(new Label("Y-Position: "));
		tfMousePositionY = new TextField(8);
		tfMousePositionY.setEditable(false);
		add(tfMousePositionY);

		add(new Label("Color: "));
		add(new Label("R: "));
		tfColorR = new TextField(1);
		tfColorR.setEditable(false);
		add(tfColorR);
		add(new Label("G: "));
		tfColorG = new TextField(1);
		tfColorG.setEditable(false);
		add(tfColorG);
		add(new Label("B: "));
		tfColorB = new TextField(1);
		tfColorB.setEditable(false);
		add(tfColorB);

		add(new Label("Hex: "));
		tfColorHex = new TextField(5);
		tfColorHex.setEditable(false);
		add(tfColorHex);

		add(new Label("Color Pane: "));
		img = new BufferedImage(300, 35, BufferedImage.TYPE_INT_RGB);

		addWindowListener(this);

		setTitle("Mouse Location Information");
		setSize(400, 270);
		setVisible(true);

		sgui.showGUI();
	}

	void showAngleMeasView() {
		this.removeAll();
		updateImg = false;
		setLayout(new FlowLayout());

		add(new Label("              Angles calculated with second point as vertex              "));
		add(new Label("                          *Type 'q' to return.                          "));

		add(new Label("First Coordinate:   "));
		add(new Label("X: "));
		tfFirstX = new TextField(3);
		tfFirstX.setEditable(false);
		add(tfFirstX);
		add(new Label("Y: "));
		tfFirstY = new TextField(3);
		tfFirstY.setEditable(false);
		add(tfFirstY);
		add(new Label("Second Coordinate:   "));
		add(new Label("X: "));
		tfSecondX = new TextField(3);
		tfSecondX.setEditable(false);
		add(tfSecondX);
		add(new Label("Y: "));
		tfSecondY = new TextField(3);
		tfSecondY.setEditable(false);
		add(tfSecondY);
		add(new Label("Third Coordinate:   "));
		add(new Label("X: "));
		tfThirdX = new TextField(3);
		tfThirdX.setEditable(false);
		add(tfThirdX);
		add(new Label("Y: "));
		tfThirdY = new TextField(3);
		tfThirdY.setEditable(false);
		add(tfThirdY);

		add(new Label("Calculated Angle: "));
		add(new Label("Radians: "));
		tfAngleRad = new TextField(3);
		tfAngleRad.setEditable(false);
		add(tfAngleRad);
		add(new Label("Degrees: "));
		tfAngleDeg = new TextField(3);
		tfAngleDeg.setEditable(false);
		add(tfAngleDeg);

		addWindowListener(this);

		setTitle("Angle Calculator");
		setSize(400, 220);
		setVisible(true);

		ngui.showGUI();
	}

	void updateMain(Point pt) {
		pt = MouseInfo.getPointerInfo().getLocation();
		String x = String.valueOf(pt.getX());
		tfMousePositionX.setText(x + "");
		String y = String.valueOf(pt.getY());
		tfMousePositionY.setText(y + "");

		Color color = r.getPixelColor(pt.x, pt.y);
		tfColorR.setText(String.valueOf(color.getRed()));
		tfColorG.setText(String.valueOf(color.getGreen()));
		tfColorB.setText(String.valueOf(color.getBlue()));
		tfColorHex.setText("#" + colorToHex(color.getRed()) + colorToHex(color.getGreen()) + colorToHex(color.getBlue()));
		imgReset(color.getRed(), color.getGreen(), color.getBlue());
		repaint();
	}

	void updateAngleMeas(Point pt, int coord) {
		pt = MouseInfo.getPointerInfo().getLocation();
		String x = String.valueOf(pt.getX());
		String y = String.valueOf(pt.getY());
		switch (coord) {
		case 0:
			tfFirstX.setText(x + "");
			tfFirstY.setText(y + "");
			break;
		case 1:
			tfSecondX.setText(x + "");
			tfSecondY.setText(y + "");
			break;
		case 2:
			tfThirdX.setText(x + "");
			tfThirdY.setText(y + "");
			double[] angleVals = ngui.calcAngles();
			tfAngleRad.setText(String.valueOf(angleVals[0]));
			tfAngleDeg.setText(String.valueOf(angleVals[1]));
			break;
		default:
			System.out.println("Error with AngleGUI");
			sgui.exit(-1);
			ngui.exit();
			System.exit(-1);
		}
	}

	void clearAngleMeas() {
		tfFirstX.setText("");
		tfFirstY.setText("");
		tfSecondX.setText("");
		tfSecondY.setText("");
		tfThirdX.setText("");
		tfThirdY.setText("");
		tfAngleRad.setText("");
		tfAngleDeg.setText("");
	}

	private String colorToHex(int c) {
		if (c < 0) throw new IllegalArgumentException();
		String hex = null;
		hex = Integer.toString(c, 16);
		return hex.length() == 1 ? "0" + hex : hex;
	}

	private void imgReset(int r, int g, int b) {
		if (r < 0 || g < 0 || b < 0) throw new IllegalArgumentException();
		int w = 300;
		int h = 35;
		int[] data = new int[w * h];
		int index = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (updateImg) {
					data[index++] = (r << 16) | (g << 8) | b;
				}
			}
		}
		img.setRGB(0, 0, w, h, data, 0, w);
	}

	void writeInfo() {
		try{
			PrintWriter pw = new PrintWriter(output);
			pw.println("X-Coordinate: ");
			pw.println(tfMousePositionX.getText());
			pw.println("Y-Coordinate: ");
			pw.println(tfMousePositionY.getText());
			pw.println("Color(Hex): ");
			pw.println(tfColorHex.getText());
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(-1);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 50, 215, null);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		sgui.exit(-1);
		ngui.exit();
		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
}
