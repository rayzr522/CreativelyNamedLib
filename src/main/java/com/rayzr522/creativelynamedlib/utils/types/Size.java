/**
 * 
 */
package com.rayzr522.creativelynamedlib.utils.types;

import org.apache.commons.lang.Validate;

/**
 * Represents a 2-dimensional size of <code>width</code> and <code>height</code>
 * 
 * @author Rayzr
 */
public class Size {

    public static final Size ONE = Size.of(1, 1);

    private int width;
    private int height;

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Creates
     * 
     * @param width The width of the dimension
     * @param height The height of the dimension
     */
    public static Size of(int width, int height) {
        Validate.isTrue(width >= 0, "width cannot be less than 0!");
        Validate.isTrue(height >= 0, "height cannot be less than 0!");
        return new Size(width, height);
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

}
