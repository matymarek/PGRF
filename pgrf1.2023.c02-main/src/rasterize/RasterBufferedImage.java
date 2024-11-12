package rasterize;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster {

    private final BufferedImage img;
    private Color color;

    public BufferedImage getImg() {
        return img;
    }

    public RasterBufferedImage(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void repaint(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw(RasterBufferedImage raster) {
        Graphics graphics = getGraphics();
        graphics.setColor(color);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawImage(raster.img, 0, 0, null);
    }

    public Graphics getGraphics(){
        return img.getGraphics();
    }

    @Override
    public Color getPixel(int x, int y) {
        return new Color(img.getRGB(x, y));
    }

    @Override
    public void setPixel(int x, int y, Color color) {
           img.setRGB(x, y, color.getRGB());
    }

    @Override
    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.clearRect(0, 0, img.getWidth(), img.getHeight());
    }

    @Override
    public void setClearColor(Color color) {
        this.color = color;
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

}
