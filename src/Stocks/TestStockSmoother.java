package Stocks;

import java.util.ArrayList;

/**
 * The TestStockSmoother class serves as a test for the StockSmoother functionality.
 *
 * @author petitoa
 */
public class TestStockSmoother {

    public static void main(String[] args) {
        StockSmoother test = new StockSmoother();

        // Get smoothed stocks
        ArrayList<Stock> smoothedStocks = test.stockSmoothToCsv("AMZN.csv", 20);

        // Graph the smoothed stocks
        test.graphSmoothedStocks(smoothedStocks, 4);
    }
}
