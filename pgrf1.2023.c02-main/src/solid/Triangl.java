package solid;

import transforms.Point3D;

import java.awt.*;

public class Triangl extends Solid{
    public Triangl(){
        color = Color.YELLOW;
        vb.add(new Point3D(2, 0.5, 0));
        vb.add(new Point3D(3, 0.5, 0));
        vb.add(new Point3D(3, 1.5, 0));
        vb.add(new Point3D(2, 1.5, 0));
        vb.add(new Point3D(2.5,1, 1));
        initCenter();

        addIndexes(
                0, 1,
                0, 3,
                1, 2,
                3, 2,
                0, 4,
                1, 4,
                2, 4,
                3, 4);
    }
}
