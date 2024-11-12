package model;

public class Rectangle extends Polygon{

    public Rectangle(Point p1, Point p3) {
        if(p1.y > p3.y){
            Point tmp = p1;
            p1 = p3;
            p3 = tmp;
        }
        p3.y = p3.x- p1.x + p1.y;
        this.addPoint(p1);
        this.addPoint(new Point(p1.x, p3.y));
        this.addPoint(p3);
        this.addPoint(new Point(p3.x, p1.y));
    }
}
