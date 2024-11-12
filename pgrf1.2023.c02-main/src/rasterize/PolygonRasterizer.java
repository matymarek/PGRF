package rasterize;

import model.Point;
import model.Polygon;

import java.awt.*;

public class PolygonRasterizer {
    LineRasterizer lineRasterizer;

    //implementuje vykreslení polygonu

    public PolygonRasterizer(LineRasterizer lineRasterizer){
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon){
        if(polygon.getSize() > 3){
            for(int i = 0; i < polygon.getSize(); i++){
                Point a = polygon.getPoint(i);
                Point b;
                if (i+1 == polygon.getSize()) { b = polygon.getPoint(0); }
                else { b = polygon.getPoint(i+1); }
                lineRasterizer.rasterize(a.x, a.y, b.x, b.y, Color.red, false, 0);
            }
        }
    }

}
