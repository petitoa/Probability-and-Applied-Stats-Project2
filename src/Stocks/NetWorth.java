package Stocks;

/**
 * The NetWorth class represents the net worth of a stock portfolio.
 * It includes methods for constructing a new NetWorth object, getting the current net worth,
 * and updating the portfolio based on stock trading.
 */
public class NetWorth {
    private double netWorth;

    /**
     * Constructs a new Net Worth object with the specified initial net worth.
     *
     * @param netWorth The initial net worth of the stock portfolio.
     */
    public NetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    /**
     * Gets the current net worth of the stock portfolio.
     *
     * @return The current net worth.
     */
    public double getNetWorth() {
        return netWorth;
    }

    /**
     * Calculates the new net worth after buying or selling a certain amount of stock at a given price.
     * Throws an exception if the new net worth would be negative.
     *
     * @param amount     The amount of stock bought (positive) or sold (negative).
     * @param stockPrice The price of the stock for the transaction.
     * @throws IllegalStateException If the new net worth would be negative after the transaction.
     */
    public void updatePortfolio(int amount, double stockPrice) {
        double newNetWorth = netWorth + amount * stockPrice;

        if (newNetWorth < 0) {
            // Throw an exception if the new net worth is negative
            throw new IllegalStateException("Net worth cannot be negative.");
        }

        netWorth = newNetWorth;
    }

}

