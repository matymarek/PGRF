package rasterize;

import java.awt.*;

public interface Raster {

    /**
     * Clear canvas
     */
    void clear();

    /**
     * Set clear color
     *
     * @param color
     *            clear color
     */
    void setClearColor(Color color);

    /**
     * Get horizontal size
     *
     * @return width
     */
    int getWidth() ;

    /**
     * Get vertical size
     *
     * @return height
     */
    int getHeight();

    /**
     * Get pixel color at [x,y] position
     *
     * @param x
     *            horizontal coordinate
     * @param y
     *            vertical coordinate
     * @return    pixel color
     */
    Color getPixel(int x, int y);

    /**
     * Set pixel color at [x,y] position
     *
     * @param x
     *            horizontal coordinate
     * @param y
     *            vertical coordinate
     * @param color
     *            pixel color
     */
    void setPixel(int x, int y, Color color) ;
}
