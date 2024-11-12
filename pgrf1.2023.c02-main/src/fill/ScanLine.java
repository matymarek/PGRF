package fill;

import model.Edge;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScanLine implements Fill {

    LineRasterizer rasterizer;
    Polygon polygon;

    public ScanLine(LineRasterizer rasterizer, Polygon polygon) {
        this.rasterizer = rasterizer;
        this.polygon = polygon;
    }

    @Override
    public void fill() {
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<ArrayList<Point>> crosses = new ArrayList<>();
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        for (int i = 0; i < polygon.getSize(); i++) {
            yMin = Math.min(yMin, polygon.getPoint(i).y);
            yMax =Math.max(yMax, polygon.getPoint(i).y);
            int index = i+1;
            if (index == polygon.getSize()) index = 0;
            Edge edge = new Edge(polygon.getPoint(i), polygon.getPoint(index));
            if (edge.isHorizontal()) continue;
            edge.orientation();
            edges.add(edge);
        }
        for (int i = yMin; i <= yMax; i++){
            ArrayList<Point> crossesOnY = new ArrayList<>();
            for(int j = 0; j < edges.size(); j++){
                Edge edge = edges.get(j);
                if (edge.isCross(i)) {
                    if (edge.cross(i) != null) crossesOnY.add(edge.cross(i));
                }
            }
            crosses.add(crossesOnY);
        }
        for(int i = 0; i < crosses.size(); i++){
            ArrayList<Point> lineCrosses = crosses.get(i);
            if (lineCrosses == null) continue;
            for (int j = 0; j < lineCrosses.size()-1; j+=2){
                int x1 = lineCrosses.get(j).x;
                int y1 = lineCrosses.get(j).y;
                int x2 = lineCrosses.get(j+1).x;
                int y2 = lineCrosses.get(j+1).y;
                rasterizer.rasterize(x1, y1, x2, y2, Color.GREEN, false, 0);
            }
        }



    }
}
