package fill;

import rasterize.Raster;

import java.awt.*;

public class SeedFill implements Fill{

    private Raster raster;
    private Color fillColor, bgrndColor;

    private int startX, startY;

    public SeedFill(Raster raster, Color fillColor, Color bgrndColor, int startX, int startY) {
        this.raster = raster;
        this.fillColor = fillColor;
        this.bgrndColor = bgrndColor;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void fill() {
        seedFill(startX, startY);
    }

    private void seedFill(int x, int y){
        if(x < 0 || x > raster.getWidth() || y < 0 || y > raster.getHeight()) return;
        Color pixelColor = raster.getPixel(x, y);
        if(pixelColor.equals(bgrndColor)) {
            raster.setPixel(x, y, fillColor);
            seedFill(x-1, y);
            seedFill(x, y-1);
            seedFill(x+1, y);
            seedFill(x, y+1);
        }
    }
}
