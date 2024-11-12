package solid;

import transforms.Point3D;

import java.awt.*;

public class AxisX extends Solid{
    public AxisX(){
        color = Color.RED;
        axis = true;
        vb.add(new Point3D(-1, 0, 0));
        vb.add(new Point3D(4, 0, 0));

        addIndexes(0, 1);
    }
}
