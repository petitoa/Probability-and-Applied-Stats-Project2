package Stocks;

/**
 * The TestStockSmoother class serves as a test for the StockSmoother functionality.
 */
public class TestStockSmoother {

    public static void main(String[] args) {
        StockSmoother test = new StockSmoother();

        test.stockSmoothToCsv("AMZN.csv", 4);
    }
}
