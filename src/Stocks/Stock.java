package Stocks;

/**
 * The Stock class represents a data point containing open value of a stock at a specific date.
 * It includes methods for constructing a new Stock object and getting the date and opening value of the stock.
 *
 * @author petitoa
 */
public class Stock {
    private double date;
    private double openValue;
    private double closeValue;

    /**
     * Constructs a new Stock object with the specified attributes.
     *
     * @param date      The x value representing the date of the stock data point.
     * @param openValue The y value representing the opening value of the stock.
     */
    public Stock(double date, double openValue, double closeValue) {
        this.date = date;
        this.openValue = openValue;
        this.closeValue = closeValue;
    }

    /**
     * Gets the date of the stock data point.
     *
     * @return The date of the stock.
     */
    public double getDate() {
        return date;
    }

    /**
     * Gets the opening value of the stock data point.
     *
     * @return The opening value of the stock.
     */
    public double getOpenValue() {
        return openValue;
    }

    /**
     * Gets the closing value of the stock data point.
     *
     * @return The opening value of the stock.
     */
    public double getCloseValue() {
        return closeValue;
    }
}

