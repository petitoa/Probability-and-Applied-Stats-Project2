package Stocks;

public class Stock {
    private double date;
    private double openValue;

    /**
     * Constructs a new Point object with the specified attributes.
     *
     * @param date      The x (input) value of the point.
     * @param openValue The y (output) value of the point.
     */
    public Stock(double date, double openValue) {
        this.date = date;
        this.openValue = openValue;
    }

    /**
     * Gets the x value for the point.
     *
     * @return The x value.
     */
    public double getDate() {
        return date;
    }

    /**
     * Gets the y value of the point.
     *
     * @return The y value.
     */
    public double getOpenValue() {
        return openValue;
    }

}

