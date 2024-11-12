package solid;

import transforms.Point3D;

import java.awt.*;

public class Cube extends Solid{
    public Cube(){
        color = Color.CYAN;
        vb.add(new Point3D(0.5, 0.5, 0));
        vb.add(new Point3D(1.5, 0.5, 0));
        vb.add(new Point3D(1.5, 1.5, 0));
        vb.add(new Point3D(0.5, 1.5, 0));

        vb.add(new Point3D(0.5, 0.5, 1));
        vb.add(new Point3D(1.5, 0.5, 1));
        vb.add(new Point3D(1.5, 1.5, 1));
        vb.add(new Point3D(0.5, 1.5, 1));
        initCenter();

        addIndexes(
                0, 1,
                        0, 3,
                        1, 2,
                        3, 2,
                        4, 5,
                        4, 7,
                        5, 6,
                        6, 7,
                        0, 4,
                        1, 5,
                        2, 6,
                        3, 7);
    }
}
