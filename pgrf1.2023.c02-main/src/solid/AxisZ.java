package solid;

import transforms.Point3D;

import java.awt.*;

public class AxisZ extends Solid{
    public AxisZ(){
        color = Color.BLUE;
        axis = true;
        vb.add(new Point3D(0, 0, -1));
        vb.add(new Point3D(0, 0, 2));

        addIndexes(0, 1);
    }
}
