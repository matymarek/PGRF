package solid;

import transforms.Point3D;

import java.awt.*;

public class AxisY extends Solid{
    public AxisY(){
        color = Color.GREEN;
        axis = true;
        vb.add(new Point3D(0, -1, 0));
        vb.add(new Point3D(0, 3, 0));

        addIndexes(0, 1);
    }
}
