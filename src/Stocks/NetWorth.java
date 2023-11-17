package Stocks;

public class NetWorth {
    private double netWorth;

    /**
     * Constructs a new Point object with the specified attributes.
     *
     * @param netWorth net worth of the stock portfolio.
     */
    public NetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    /**
     * Gets the x value for the point.
     *
     * @return The x value.
     */
    public double getNetWorth() {
        return netWorth;
    }

    /**
     * Gets the y value of the point.
     *
     * @return The y value.
     */

    public void updatePortfolio(int amount, double stockPrice) {
        double newNetWorth = netWorth + amount * stockPrice;

        if (newNetWorth < 0) {
            // Throw an exception if the new net worth is negative
            throw new IllegalStateException("Net worth cannot be negative.");
        }

        netWorth = newNetWorth;
    }

    public void sell(int amount, double stockPrice) {
        netWorth += amount * stockPrice;
    }

    public void buy(double amount, double stockPrice) {
        netWorth -= amount + stockPrice;
    }

}

