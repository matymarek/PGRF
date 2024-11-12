package solid;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Solid {

    protected Color color;
    protected Point3D center;

    protected boolean axis;
    protected ArrayList<Point3D> vb = new ArrayList<>();
    protected ArrayList<Integer> ib = new ArrayList<>();
    private Mat4 model = new Mat4Identity();
    public ArrayList<Point3D> getVb() {
        return vb;
    }
    public void setVb(ArrayList<Point3D> vb) {
        this.vb = vb;
    }
    public ArrayList<Integer> getIb() {
        return ib;
    }
    protected void addIndexes(Integer... indicies){
        ib.addAll(Arrays.asList(indicies));
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public Mat4 getModel() {
        return model;
    }

    public Color getColor() { return color; }

    public boolean isAxis() { return axis; }
    public Point3D getCenter() {
        return center;
    }
    public void initCenter() {
        double maxX = vb.get(0).getX();
        double maxY = vb.get(0).getY();
        double maxZ = vb.get(0).getZ();
        double minX = vb.get(0).getX();
        double minY = vb.get(0).getY();
        double minZ = vb.get(0).getZ();

        for (Point3D point: vb) {
            if (point.getX() < minX) {
                minX = point.getX();
            } else if (point.getX() > maxX) {
                maxX = point.getX();
            }
            if (point.getY() < minY) {
                minY = point.getY();
            } else if (point.getY() > maxY) {
                maxY = point.getY();
            }
            if (point.getZ() < minZ) {
                minZ = point.getZ();
            } else if (point.getZ() > maxZ) {
                maxZ = point.getZ();
            }
        }
        this.center = new Point3D(
                (maxX + minX) / 2,
                (maxY + minY) / 2,
                (maxZ + minZ) / 2
        );
    }

    public void setCenter(Point3D center){
        this.center = center;
    }

}

