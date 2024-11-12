package renderer;

import rasterize.LineRasterizer;
import rasterize.Raster;
import solid.Solid;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.ArrayList;
import java.util.List;

public class WiredRenderer {
    private final LineRasterizer lineRasterizer;
    private Mat4 view;
    private Mat4 proj;
    private final Raster raster;

    public WiredRenderer(LineRasterizer lineRasterizer, Raster raster){
        this.lineRasterizer = lineRasterizer;
        this.raster = raster;
        this.view = new Mat4Identity();
        this.proj = new Mat4Identity();
    }

    public void render(Solid solid){
        for (int i = 0; i < solid.getIb().size(); i+=2) {
            int indexA = solid.getIb().get(i);
            int indexB = solid.getIb().get(i+1);

            Point3D pointA = solid.getVb().get(indexA);
            Point3D pointB = solid.getVb().get(indexB);

            if(!solid.isAxis()) {
                pointA = pointA.mul(solid.getModel()).mul(view).mul(proj);
                pointB = pointB.mul(solid.getModel()).mul(view).mul(proj);
            }
            else {
                pointA = pointA.mul(view).mul(proj);
                pointB = pointB.mul(view).mul(proj);
            }
            if ((!((-pointA.getW()) <= pointA.getX() && pointA.getX() <= pointA.getW())
                    || !((-pointA.getW()) <= pointA.getY() && pointA.getY() <= pointA.getW())
                    || !(0 <= pointA.getZ() && pointA.getZ() <= pointA.getW()))
                    || (!((-pointB.getW()) <= pointB.getX() && pointB.getX() <= pointB.getW())
                    || !((-pointB.getW()) <= pointB.getY() && pointB.getY() <= pointB.getW())
                    || !(0 <= pointB.getZ() && pointB.getZ() <= pointB.getW()))) { continue; }

            Vec3D vA;
            if (pointA.dehomog().isPresent()){
                 vA = transformToWindow(new Vec3D(pointA.dehomog().get()));
            }
            else continue;
            Vec3D vB;
            if (pointB.dehomog().isPresent()){
                 vB = transformToWindow(new Vec3D(pointB.dehomog().get()));
            }
            else continue;

            lineRasterizer.rasterize(
                    (int)Math.round(vA.getX()), (int)Math.round(vA.getY()),
                    (int)Math.round(vB.getX()), (int)Math.round(vB.getY()),
                    solid.getColor());
        }
    }

    private Vec3D transformToWindow(Vec3D v){
        return v.mul(new Vec3D(1, -1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((double) (raster.getWidth() - 1) /2, (double) (raster.getHeight() - 1) /2, 1));
    }

    public void renderScene(List<Solid> solids){
        for (Solid solid : solids) render(solid);
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }

    public void setCenters(List<Solid> solids){
        for(Solid solid : solids){
            if(!solid.isAxis()){
                Point3D center = solid.getCenter();
                solid.setCenter(center.mul(solid.getModel()).mul(view).mul(proj));

            }
        }
    }

    public ArrayList<Vec3D> getCenters(List<Solid> solids){
        ArrayList<Vec3D> centers = new ArrayList<>();
        for(Solid solid : solids){
            if(!solid.isAxis()){
                if (solid.getCenter().dehomog().isPresent()){
                    centers.add(transformToWindow(new Vec3D(solid.getCenter().dehomog().get())));
                }
            }
        }
        return centers;
    }


}
