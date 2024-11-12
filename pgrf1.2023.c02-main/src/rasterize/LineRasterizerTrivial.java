package rasterize;

import java.awt.*;

public class LineRasterizerTrivial extends LineRasterizer{
    public LineRasterizerTrivial(Raster raster) {
        super(raster);
    }

    //implementuje v sobě vykreslení úsečky, plně i tečkovaně, a připínání na horizontálu/vertikálu/diagonálu

    @Override
    protected void drawLine(int x1, int y1, int x2, int y2, Color color, boolean binding, int spacing) {
        float k;
        if(x1 != x2) {
            k = (y2 - y1) / (float) (x2 - x1);
            if(binding){
                if (k>2){ perpendicular(x1, y1, y2, color, spacing); return; }
                else if (k <= 2 && k > 0.5) { k = 1;}
                else if (k <= 0.5 && k > -0.5) { k = 0;}
                else if (k <= -0.5 && k > -2) { k = -1;}
                else if (k <= -2) { perpendicular(x1, y1, y2, color, spacing); return; }
            }
            float q = y1 - k*x1;
            if (k>1 || k<-1){
                if (y2<y1) {
                    int temp = y1;
                    y1=y2;
                    y2 = temp;
                }
                if(y1 < 0 ){
                    y1 = 0;
                }
                else if(y2 >= raster.getHeight()){
                    y2 = raster.getHeight()-1;
                }
                for (int y = y1; y <= y2; y += spacing+1) {
                    int x = Math.round((y-q)/k);
                    if (x <= raster.getWidth()-1 && x > 0) raster.setPixel(x, y, color);
                }
            }
            else {
                if (x2<x1) {
                    int temp = x1;
                    x1=x2;
                    x2 = temp;
                }
                if(x1 < 0){
                    x1 = 0;
                }
                else if(x2 >= raster.getWidth()){
                    x2 = raster.getWidth()-1;
                }
                for (int x = x1; x <= x2; x += spacing+1) {
                    int y = Math.round(k * x + q);
                    if (y > 0 && y <= raster.getHeight()-1) raster.setPixel(x, y, color);
                }
            }
        }
        else perpendicular(x1, y1, y2, color, spacing);


    }
    private void perpendicular(int x1, int y1, int y2, Color color, int spacing){
        if (y2<y1) {
            int temp = y1;
            y1=y2;
            y2 = temp;
        }
        if(x1 < 0){
            x1 = 0;
        }
        else if(x1 >= raster.getWidth()){
            x1 = raster.getWidth()-1;
        }
        if(y1 < 0 ){
            y1 = 0;
        }
        else if(y2 >= raster.getHeight()){
            y2 = raster.getHeight()-1;
        }
        for (int y = y1; y <= y2; y += spacing+1) {
            raster.setPixel(x1, y, color);
        }
    }

}
