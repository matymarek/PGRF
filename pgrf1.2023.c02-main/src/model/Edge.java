package model;

import model.Polygon;


public class Edge {
    private int x1, y1, x2, y2;

    public Edge(Point p1, Point p2) {
        x1 = p1.x;
        y1 = p1.y;
        x2 = p2.x;
        y2 = p2.y;
    }

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isHorizontal(){
        return y1==y2;
    }

    public void orientation(){
        if ( y1 > y2){
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
            tmp = x1;
            x1 = x2;
            x2 = tmp;
        }

    }

    public boolean isCross(int y){
        return (y1 <= y && y < y2);
    }

    public Point cross(int y){
        float k1 = 0;
        float k2 = (y2 - y1) / (float) (x2 - x1);
        if (k1 == k2) return null;
        int crossX = (int) Math.round(x1 + (double)(x2 - x1)/(y2-y1) * (y-y1));
        return new Point(crossX, y);
    }



}
