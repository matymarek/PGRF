package rasterize;

import model.Line;

import java.awt.*;

public abstract class LineRasterizer {
    Raster raster;
    Color color;

    public LineRasterizer(Raster raster){
        this.raster = raster;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = new Color(color);
    }

    public void rasterize(Line line, Color color, boolean binding, int spacing) {
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), color, binding, spacing);
    }

    public void rasterize(int x1, int y1, int x2, int y2, Color color, boolean binding, int spacing) {
        drawLine(x1, y1, x2, y2, color, binding, spacing);
    }

    public void rasterize(int x1, int y1, int x2, int y2, Color color) {
        drawLine(x1, y1, x2, y2, color);
    }

    protected void drawLine(int x1, int y1, int x2, int y2, Color color, boolean binding, int spacing) {

    }
    protected void drawLine(int x1, int y1, int x2, int y2, Color color) {

    }
}
