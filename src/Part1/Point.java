package Part1;

/**
 * The Point class represents a point object with an x and y value.
 *
 * @author petitoa
 */
public class Point {
    private double xValue;
    private double yValue;

    /**
     * Constructs a new Point object with the specified attributes.
     *
     * @param xValue The x (input) value of the point.
     * @param yValue The y (output) value of the point.
     */
    public Point(double xValue, double yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    /**
     * Gets the x value for the point.
     *
     * @return The x value.
     */
    public double getXValue() {
        return xValue;
    }

    /**
     * Gets the y value of the point.
     *
     * @return The y value.
     */
    public double getYValue() {
        return yValue;
    }

}