package model;

public class Circle extends Polygon{
    public Circle(Point p1, Point p3){
        int width = p3.x - p1.x;
        int height = p3.y - p1.y;
        int centerX = p1.x + width / 2;
        int centerY = p1.y + height / 2;
        for (int angle = 0; angle <= 360; angle++) {
            double radians = Math.toRadians(angle);
            int x = centerX + (int) (width / 2 * Math.cos(radians));
            int y = centerY + (int) (height / 2 * Math.sin(radians));
            addPoint(new Point(x, y));
        }
    }
}