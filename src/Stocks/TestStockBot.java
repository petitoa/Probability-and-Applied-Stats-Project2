package Stocks;

import java.util.ArrayList;

/**
 * The TestStockBot class serves as a test for the StockBot functionality.
 */
public class TestStockBot {
    public static void main(String[] args) {

        StockBot test = new StockBot();

        // Initialize net worth
        NetWorth netWorth = new NetWorth(10000);

        ArrayList<Stock> stocks = test.loadStocks("AMZN.csv");

        double finalNetWorth = test.completeRun(netWorth, stocks).getNetWorth();

        System.out.println("With a starting value of $10000 the finalized net worth after all runs is $" + netWorth.getNetWorth());
    }
}
