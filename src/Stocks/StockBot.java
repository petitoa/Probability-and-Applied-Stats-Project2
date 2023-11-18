package Stocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The StockBot class represents a bot for managing a simulated stock portfolio.
 * It includes methods for loading stock data from a CSV file, running a simulation, and evaluating trades by day.
 */
public class StockBot {

    /**
     * Loads stock data from a given CSV file and returns an ArrayList of Stock objects.
     *
     * @param inputFile The input file containing stock data.
     * @return An ArrayList of Stock objects representing the loaded stock data.
     * @throws RuntimeException If the file is not found.
     */
    public ArrayList<Stock> loadStocks(String inputFile) {
        File file = new File(inputFile);

        ArrayList<Stock> stocks = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            // Store and skip the header
            String header = scanner.nextLine();

            // Delimiter set to comma or new line
            scanner.useDelimiter(",|\n");

            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String grabdate = scanner.next().trim();
                double date = lineNumber;
                double openValue = Double.parseDouble(scanner.next().trim());
                scanner.nextLine();
                stocks.add(new Stock(date, openValue));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stocks;
    }

    /**
     * Runs a complete simulation of stock trading based on the provided stock data from the loaded CSV file.
     * Updates the net worth of the portfolio.
     *
     * @param netWorth The initial NetWorth object representing the portfolio's net worth.
     * @param stocks   The ArrayList of Stock objects representing the stock data.
     * @return The NetWorth object after completing the simulation.
     */
    public NetWorth completeRun(NetWorth netWorth, ArrayList<Stock> stocks) {
        ArrayList<Stock> traversedStocks = new ArrayList<>();

        double heuristic;
        for (Stock stock : stocks) {
            // load open to the traversed stocks
            double date = stock.getDate();
            double openValue = stock.getOpenValue();
            traversedStocks.add(new Stock(date, openValue));

            // calculate new heuristic and determine trade for the day
            heuristic = updateInternalData(traversedStocks);
            int determinedTrade = tradeEvaluator(netWorth, heuristic, openValue);

            // update net worth accordingly
            netWorth.updatePortfolio(determinedTrade, openValue);
        }
        return netWorth;
    }

    /**
     * Calculating the heuristic (mean) based on a specified window of past stock data.
     *
     * @param stocks The ArrayList of Stock objects representing the traversed stock data.
     * @return The calculated heuristic based on the window of past stock data.
     */
    public double updateInternalData(ArrayList<Stock> stocks) {
        // look at stocks within a certain window
        int windowSize = 5;

        // handles cases where less days than the window value have passed
        int startIndex = Math.max(0, stocks.size() - windowSize);

        double totalStocksValue = 0;
        for (int i = startIndex; i < stocks.size(); i++) {
            double openValue = stocks.get(i).getOpenValue();
            totalStocksValue += openValue;
        }

        return totalStocksValue / Math.min(windowSize, stocks.size());
    }


    /**
     * Evaluates the number of stocks to buy or sell based on the net worth, heuristic (mean), and current stock price.
     * This value never exceeds 1% of the portfolios total value
     *
     * @param netWorth  The current NetWorth object representing the portfolio's net worth.
     * @param heuristic The calculated heuristic (mean) based on recent stock data.
     * @param openValue The current stock price.
     * @return The number of stocks to buy (positive) or sell (negative) based on the evaluation.
     */
    public int tradeEvaluator(NetWorth netWorth, double heuristic, double openValue) {
        if (openValue < heuristic) {
            // .01 (never more than one percent of portfolio in a day)
            return (int) ((.01 * netWorth.getNetWorth()) / openValue);
        } else if (openValue > heuristic) {
            return -(int) ((.01 * netWorth.getNetWorth()) / openValue);

        } else {
            return 0;
        }
    }

}
