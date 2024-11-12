import fill.ScanLine;
import fill.SeedFill;
import model.*;
import model.Point;
import model.Polygon;
import model.Rectangle;
import rasterize.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author PGRF FIM UHK
 * @version 2023.b
 */

public class App {

	private Polygon polygon;
	private Rectangle rectangle;
	private Circle circle;
	private JPanel panel;
	private RasterBufferedImage raster;
	private LineRasterizer lineRasterizer;
	//algoritmus úsečky
	private PolygonRasterizer polygonRasterizer;
	//algorytmus polygonu
	private Point lineStart;
	private Point lineEnd;
	private Point recStart;
	private Point recEnd;
	private int keyCode;
	private boolean binding;
	private int spacing;

	private static final int LINE = 76;
	private static final int POLYGON = 80;
	private static final int RECTANGLE = 84;

	public App(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());

		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		raster = new RasterBufferedImage(width, height);
		polygon = new Polygon();
		//lineRasterizer = new LineRasterizerGraphics(raster);
		lineRasterizer = new LineRasterizerTrivial(raster);
		polygonRasterizer = new PolygonRasterizer(lineRasterizer);
		keyCode = LINE;

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		panel.requestFocus();
		panel.requestFocusInWindow();

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(keyCode == POLYGON) {
					if(e.getButton() == MouseEvent.BUTTON1) {         //left
						raster.clear();
						polygon.addPoint(new Point(e.getX(), e.getY()));
						polygonRasterizer.rasterize(polygon);
					}
					else if(e.getButton() == MouseEvent.BUTTON3) {   //right
						//SeedFill seedFill = new SeedFill(raster, Color.green, raster.getPixel(e.getX(), e.getY()), e.getX(), e.getY());
						//seedFill.fill();
						ScanLine scanLine = new ScanLine(lineRasterizer, polygon);
						scanLine.fill();
						polygonRasterizer.rasterize(polygon);
					}
					panel.repaint();
				}
				else if(keyCode == RECTANGLE){
					circle = new Circle(recStart, recEnd);
					polygonRasterizer.rasterize(circle);
					panel.repaint();
					panel.repaint();
				}
			}
			@Override
			public void mousePressed(MouseEvent e){
				if(keyCode == LINE) {
					lineStart = new Point(e.getX(), e.getY());
					raster.clear();
					raster.setPixel(lineStart.x, lineStart.y, Color.red);
					panel.repaint();
				}
				else if(keyCode == RECTANGLE){
					recStart = new Point(e.getX(), e.getY());
				}
			}
		});

		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				if(keyCode == LINE) {
					raster.clear();
					lineEnd = new Point(e.getX(), e.getY());
					lineRasterizer.rasterize(new Line(lineStart, lineEnd), Color.red, binding, spacing);
					panel.repaint();
				}
				else if(keyCode == RECTANGLE){
					raster.clear();
					recEnd = new Point(e.getX(), e.getX());
					rectangle = new Rectangle(recStart, recEnd);
					polygonRasterizer.rasterize(rectangle);
					panel.repaint();
				}
			}
		});

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 67){      //c
					polygon.removePoints();
					raster.clear();
					panel.repaint();
				}
				else if(e.getKeyCode() == 16){ //shift
					binding = true;
				}
				else if(e.getKeyCode() == 76){ //l
					raster.clear();
					panel.repaint();
					spacing = 0;
					keyCode = LINE;
				}
				else if(e.getKeyCode() == 80){ //p
					raster.clear();
					panel.repaint();
					keyCode = POLYGON;
				}
				else if(e.getKeyCode() == 84){ //t
					raster.clear();
					panel.repaint();
					spacing = 5;
				}
				else if(e.getKeyCode() == 82){ //r
					raster.clear();
					panel.repaint();
					keyCode = RECTANGLE;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == 16){      //shift
					binding = false;
				}
			}
		});
	}

	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}

	public void start() {
		clear(Color.black);
		panel.repaint();
	}

	public void clear(Color color) {
		raster.setClearColor(color);
		raster.clear();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new App(800, 600).start());
	}

}
