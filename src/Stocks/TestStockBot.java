package Stocks;

import java.util.ArrayList;

public class TestStockBot {
    public static void main(String[] args){

        StockBot test = new StockBot();

        // Initialize net worth
        NetWorth netWorth = new NetWorth(10000);

        ArrayList<Stock> stocks = test.loadStocks("AMZN.csv");

        double finalNetWorth = test.completeRun(netWorth, stocks).getNetWorth();

        System.out.println("With a starting net worth of $" + netWorth.getNetWorth() + " the finalized net worth after all runs is " + finalNetWorth);
    }
}
