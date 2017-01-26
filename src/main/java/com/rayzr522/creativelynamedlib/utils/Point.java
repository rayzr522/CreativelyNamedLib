/**
 * 
 */
package com.rayzr522.creativelynamedlib.utils;

import com.rayzr522.creativelynamedlib.gui.GUI;

/**
 * @author Rayzr
 *
 */
public class Point {

    private int x;
    private int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new point at the given position
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The new point
     */
    public static Point at(int x, int y) {
        return new Point(x, y);
    }

    /**
     * Creates a new point at the given position rounded down
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The new point
     */
    public static Point at(double x, double y) {
        return at((int) Math.floor(x), (int) Math.floor(y));
    }

    /**
     * Creates a new point at the given position rounded down
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The new point
     */
    public static Point at(float x, float y) {
        return at((int) Math.floor(x), (int) Math.floor(y));
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Adds another point to this one and returns the result. The method does not mutate this object.
     * 
     * @param other The other point to add
     * @return The combined point
     */
    public Point add(Point other) {
        return new Point(x + other.getX(), y + other.getY());
    }

    /**
     * Subtracts another point from this one and returns the result. The method does not mutate this object.
     * 
     * @param other The other point to add
     * @return The subtracted point
     */
    public Point subtract(Point other) {
        return add(other.multiply(-1));
    }

    /**
     * Multiplies this point by a scalar quantity. The method does not mutate this object.
     * 
     * @param amount The amount to multiply
     * @return The multiplied point
     */
    public Point multiply(int amount) {
        return new Point(x * amount, y * amount);
    }

    /**
     * Checks if this point is within the bounds of the given {@link Size}
     * 
     * @param bounds The {@link Size} to check
     * @return Whether or not this point is within the bounds
     */
    public boolean isWithin(Size bounds) {
        return x >= 0 && x < bounds.getWidth() && y >= 0 && y < bounds.getHeight();
    }

    /**
     * Converts this point to a raw slot. Default columns is 9 for a custom chest {@link GUI}. For 9 columns you can also simply use {@link #toSlot()}
     * 
     * @param columns The number of columns in the target inventory you want the slot for
     * @return The slot
     */
    public int toSlot(int columns) {
        return x + y * columns;
    }

    /**
     * Alias for {@link #toSlot(int)} with a parameter of 9 (the number of columns in a custom chest {@link GUI}). <i>If you are using this with an inventory that has a number of columns <b>other</b> than 9, use {@link #toSlot(int)}</i>
     * 
     * @return The slot
     */
    public int toSlot() {
        return toSlot(9);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

}
