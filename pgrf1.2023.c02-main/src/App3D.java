import rasterize.*;
import renderer.WiredRenderer;
import solid.*;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * @author PGRF FIM UHK
 * @version 2023.b
 */

public class App3D {
	private final JPanel panel;
	private final RasterBufferedImage raster;
	private final WiredRenderer renderer;
	private Mat4 proj;
	private Camera camera;
	private final ArrayList<Solid> solids;
	private ArrayList<Vec3D> centers;
	private int mouseDraggedStartX, mouseDraggedStartY;

	private int selectedSolidIndex;

	public App3D(int width, int height) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		raster = new RasterBufferedImage(width, height);
		LineRasterizer lineRasterizer = new LineRasterizerGraphics(raster);
		solids = new ArrayList<>();
		renderer = new WiredRenderer(lineRasterizer, raster);
		panel = new JPanel() {
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_A){
					//camera move left
					camera = camera.left(0.2);
				}
				else if (e.getKeyCode() == KeyEvent.VK_D) {
					//camera move right
					camera = camera.right(0.2);
				}
				else if (e.getKeyCode() == KeyEvent.VK_W) {
					//camera move up
					camera = camera.up(0.2);
				}
				else if (e.getKeyCode() == KeyEvent.VK_S) {
					//camera move down
					camera = camera.down(0.2);
				}
				else if (e.getKeyCode() == KeyEvent.VK_Q) {
					//camera zoom in
					camera = camera.forward(0.2);
				}
				else if (e.getKeyCode() == KeyEvent.VK_E) {
					//camera zoom out
					camera = camera.backward(0.2);
				}
				else if (e.getKeyCode() == KeyEvent.VK_T) {
					//camera rotate over
					camera = camera.addZenith(Math.toRadians(5));
				}
				else if (e.getKeyCode() == KeyEvent.VK_F) {
					//camera rotate left
					camera = camera.addAzimuth(Math.toRadians(5));
				}
				else if (e.getKeyCode() == KeyEvent.VK_G) {
					//camera rotate under
					camera = camera.addZenith(Math.toRadians(-5));
				}
				else if (e.getKeyCode() == KeyEvent.VK_H) {
					//camera rotate right
					camera = camera.addAzimuth(Math.toRadians(-5));
				}
				else if (e.getKeyCode() == KeyEvent.VK_I) {
					//solid move up
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Transl(0, 0, 0.2)));
				}
				else if (e.getKeyCode() == KeyEvent.VK_J) {
					//solid move left
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Transl(-0.2, 0, 0)));
				}
				else if (e.getKeyCode() == KeyEvent.VK_K) {
					//solid move down
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Transl(0, 0, -0.2)));
				}
				else if (e.getKeyCode() == KeyEvent.VK_L) {
					//solid move right
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Transl(0.2, 0, 0)));
				}
				else if (e.getKeyCode() == KeyEvent.VK_U) {
					//solid move forward
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Transl(0, -0.2, 0)));
				}
				else if (e.getKeyCode() == KeyEvent.VK_O) {
					//solid move backward
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Transl(0, 0.2, 0)));
				}
				else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					//rotation Y left
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4RotY(Math.toRadians(-5))));
				}
				else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
					//rotation Y right
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4RotY(Math.toRadians(5))));
				}
				else if (e.getKeyCode() == KeyEvent.VK_HOME) {
					//rotation X backward
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4RotX(Math.toRadians(-5))));
				}
				else if (e.getKeyCode() == KeyEvent.VK_END) {
					//rotation X forward
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4RotX(Math.toRadians(5))));
				}
				else if (e.getKeyCode() == KeyEvent.VK_INSERT) {
					//rotation Z left
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4RotZ(Math.toRadians(5))));
				}
				else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
					//rotation Z right
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4RotZ(Math.toRadians(-5))));
				}
				else if (e.getKeyCode() == KeyEvent.VK_M) {
					//zoom in
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Scale(1.1)));
				}
				else if (e.getKeyCode() == KeyEvent.VK_N) {
					//zoom out
					solids.get(selectedSolidIndex).setModel(solids.get(selectedSolidIndex).getModel().mul(new Mat4Scale(0.9)));
				}
				drawScene();
			}
		});

		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDraggedStartX = e.getX();
				mouseDraggedStartY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				double dX = e.getX() - mouseDraggedStartX;
				double dY = e.getY() - mouseDraggedStartY;
				camera = camera.addAzimuth(Math.PI*(dX / raster.getWidth())/3);
				camera = camera.addZenith(Math.PI*(dY / raster.getHeight())/3);
				drawScene();
				mouseDraggedStartX = e.getX();
				mouseDraggedStartY = e.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				double dist = Integer.MAX_VALUE;
				int index = 0;
				for(int i = 0; i < centers.size(); i++){
					double dx = e.getX()-centers.get(i).getX();
					double dy = e.getY()-centers.get(i).getY();
					if((dx + dy) < dist){
						index = i;
						dist = dx + dy;
					}
				}
				selectedSolidIndex = index;
			}
		};
		panel.addMouseListener(mouseAdapter);
		panel.addMouseMotionListener(mouseAdapter);

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		panel.requestFocus();
		panel.requestFocusInWindow();

		initScene();
	}
	public void initScene(){
		Vec3D pos = new Vec3D(1.5, -7, 2.5);
		camera = new Camera(pos,
				Math.toRadians(90),
				Math.toRadians(-15),
				1, true
		);
		proj = new Mat4PerspRH(
				Math.PI/4,
				(double) raster.getHeight() / raster.getWidth(),
				0.2, 20
		);
		Cube cube = new Cube();
		Triangl triangl = new Triangl();
		AxisX axisX = new AxisX();
		AxisY axisY = new AxisY();
		AxisZ axisZ = new AxisZ();
		solids.add(cube);
		solids.add(triangl);
		solids.add(axisX);
		solids.add(axisY);
		solids.add(axisZ);
	}
	public void drawScene(){
		clear(Color.black);
		renderer.setView(camera.getViewMatrix());
		renderer.setProj(proj);
		renderer.setCenters(solids);
		centers = renderer.getCenters(solids);
		renderer.renderScene(solids);
		panel.repaint();
	}

	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}

	public void start() {
		drawScene();
	}

	public void clear(Color color) {
		raster.setClearColor(color);
		raster.clear();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new App3D(800, 600).start());
	}

}
