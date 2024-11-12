package model;

import java.util.ArrayList;

public class Polygon {

    private ArrayList<Point> points;

    public Polygon(){this.points = new ArrayList<>(); }

    public void addPoint(Point p){
        points.add(p);
    }

    public void removePoints() {
        points.clear();
    }

    public int getSize(){
        return points.size();
    }

    public Point getPoint(int index){
        return points.get(index);
    }


}
