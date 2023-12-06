package Stocks;

/**
 * The NetWorth class represents the net worth of a stock portfolio.
 * It includes methods for constructing a new NetWorth object, getting the current net worth,
 * and updating the portfolio based on stock trading.
 *
 * @author petitoa
 */
public class NetWorth {
    private double netWorth;

    private int stockQuantity;

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
        return this.netWorth;
    }

    public int getStockQuantity() {
        return this.stockQuantity;
    }

    /**
     * Calculates the new net worth after buying or selling a certain amount of stock at a given price.
     * Updates the number of stocks currently in the portfolio.
     * Throws an exception if the new net worth would be negative.
     *
     * @param stockQuantity The amount of stock bought (positive) or sold (negative).
     * @param stockPrice    The price of the stock for the transaction.
     * @throws IllegalStateException If the new net worth would be negative after the transaction.
     */
    public void updatePortfolio(int stockQuantity, double stockPrice) {
        double transactionAmount;

        // Check if there are enough stocks to make the trade
        if (stockQuantity <= this.stockQuantity) {
            transactionAmount = stockQuantity * stockPrice;
            // Subtract stocks from the portfolio
            this.stockQuantity -= stockQuantity;
        } else {
            // Sell the maximum available stocks
            transactionAmount = this.stockQuantity * stockPrice;
            // Sell all available stocks
            this.stockQuantity = 0;
        }

        double newNetWorth = this.netWorth + transactionAmount;

        if (newNetWorth < 0) {
            throw new IllegalStateException("Net worth cannot be negative.");
        }

        this.netWorth = newNetWorth;


    }
}

