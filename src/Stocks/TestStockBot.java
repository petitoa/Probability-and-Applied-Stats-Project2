package Stocks;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The TestStockBot class serves as a test for the StockBot functionality.
 *
 * @author petitoa
 */
public class TestStockBot extends JFrame {
    public static void main(String[] args) {

        StockBot test = new StockBot();

        ArrayList<Stock> stocks = test.loadStocks("AMZN.csv");

        // Test trade method rsiAndHeuristicTradeEvaluator
        NetWorth rsiAndHeuristicTradeEvaluator = test.completeRun(new NetWorth(10000), stocks, 1);

        // Test trade method buyAndHold
        NetWorth buyAndHold = test.completeRun(new NetWorth(10000), stocks, 2);

        // Test trade method rsiAndMovingAverage
        NetWorth rsiAndMovingAverage = test.completeRun(new NetWorth(10000), stocks, 3);

        test.graphRsi(stocks);

        System.out.println("With a starting value of $10000 the finalized net worth after using rsiAndHeuristicTradeEvaluator trade method is $" + rsiAndHeuristicTradeEvaluator.getNetWorth());
        System.out.println("With a starting value of $10000 the finalized net worth after using buyAndHold trade method is $" + buyAndHold.getNetWorth());
        System.out.println("With a starting value of $10000 the finalized net worth after using rsiAndMovingAverage trade method is $" + rsiAndMovingAverage.getNetWorth());
    }
}
