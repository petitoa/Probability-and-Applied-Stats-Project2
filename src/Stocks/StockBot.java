package Stocks;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * The StockBot class represents a bot for managing a simulated stock portfolio.
 * It includes methods for loading stock data from a CSV file, running a simulation, and evaluating trades by day.
 */
public class StockBot extends JFrame {

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
                double high = Double.parseDouble(scanner.next().trim());
                double low = Double.parseDouble(scanner.next().trim());
                double closeValue = Double.parseDouble(scanner.next().trim());
                scanner.nextLine();
                stocks.add(new Stock(date, openValue, closeValue));
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

        // Fill the first 15 values of rsi with zeros
        ArrayList<Double> rsi = new ArrayList<>(Collections.nCopies(15, 0.0));

        // Calculate RSI for the remaining days and add them to the existing rsi ArrayList
        rsi.addAll(calculateRsi(stocks));

        double heuristic;
        int index = 0;
        for (Stock stock : stocks) {
            // load open to the traversed stocks
            double date = stock.getDate();
            double openValue = stock.getOpenValue();
            double closeValue = stock.getCloseValue();
            traversedStocks.add(new Stock(date, openValue, closeValue));

            // calculate new heuristic and determine trade for the day
            heuristic = updateInternalData(traversedStocks);
            double rsiForDay = rsi.get(index);
            int determinedTrade = tradeEvaluator(netWorth, heuristic, openValue, rsiForDay, index);

            // update net worth accordingly
            netWorth.updatePortfolio(determinedTrade, openValue);
            index++;
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
    public int tradeEvaluator(NetWorth netWorth, double heuristic, double openValue, double rsiForDay, int index) {
        //If there are not enough prior dates to calculate rsi use heuristic instead
        if (index < 14) {
            if (openValue < heuristic) {
                // .01 (never more than one percent of portfolio in a day)
                return (int) ((0.32 * netWorth.getNetWorth()) / openValue);
            } else if (openValue > heuristic) {
                return -(int) ((0.32 * netWorth.getNetWorth()) / openValue);
            } else {
                return 0;
            }
        } else {
            double overboughtThreshold = 70;
            double oversoldThreshold = 30;

            if (openValue < heuristic && rsiForDay < oversoldThreshold) {
                // Buy condition: If the stock price is below the mean and RSI indicates oversold
                return (int) ((0.32 * netWorth.getNetWorth()) / openValue);
            } else if (openValue > heuristic && rsiForDay > overboughtThreshold) {
                // Sell condition: If the stock price is above the mean and RSI indicates overbought
                return -(int) ((0.32 * netWorth.getNetWorth()) / openValue);
            } else {
                return 0; // Hold condition: No action
            }
        }
    }

    public ArrayList<Double> calculateRsi(ArrayList<Stock> stocks) {
        int n = 14;
        ArrayList<Double> rsiValues = new ArrayList<>();

        ArrayList<Stock> traversedStocks = new ArrayList<>();

        // Stores up or down moves for the day. Depending on whether it moves up or down for the day.
        ArrayList<Double> upMoves = new ArrayList<>();
        ArrayList<Double> downMoves = new ArrayList<>();

        // Add stocks to traversed stocks
        for (Stock stock : stocks) {
            double dateStock = stock.getDate();
            double openValueStock = stock.getOpenValue();
            double closeValueStock = stock.getCloseValue();

            traversedStocks.add(new Stock(dateStock, openValueStock, closeValueStock));

            // Check if there are enough past days for the change calculation
            if (traversedStocks.size() == n) {
                double closeValue = traversedStocks.get(traversedStocks.size() - 1).getCloseValue();
                double closeValueMinus1 = traversedStocks.get(traversedStocks.size() - 2).getCloseValue();
                double change = closeValue - closeValueMinus1;

                // Add value to either upMoves or downMoves
                if (change > 0) {
                    upMoves.add(change);
                    if (upMoves.size() > n) {
                        upMoves.remove(0);  // Remove the oldest element
                    }
                } else if (change < 0) {
                    downMoves.add(Math.abs(change));
                    if (downMoves.size() > n) {
                        downMoves.remove(0);  // Remove the oldest element
                    }
                }

                //calculate avgup and avg down. Summing up divide by n.
                double avgUp = upMoves.stream().mapToDouble(Double::doubleValue).sum() / n;
                double avgDown = downMoves.stream().mapToDouble(Double::doubleValue).sum() / n;

                //Calculate RS
                double rs = (avgDown == 0) ? 0 : avgUp / avgDown;

                //Calculate RSI
                double rsi = 100 - 100 / (1 + rs);

                // Add the calculated RSI for the day to the list
                rsiValues.add(rsi);

                //remove first element from array when exceeds 14 days list since you need past n days
                traversedStocks.remove(0);
            }
        }
        return rsiValues;
    }


    public void graphRsi(ArrayList<Stock> stocks) {
        ArrayList<Double> rsiValues = new ArrayList<>(calculateRsi(stocks));

        double[][] rsi = new double[2][rsiValues.size()];

        double date = 0;
        for (int i = 0; i < rsiValues.size(); i++) {
            // Populate the 2D array with date and RSI value
            rsi[0][i] = date; // X-axis (day)
            rsi[1][i] = rsiValues.get(i); // Y-axis (RSI value)
            System.out.printf("Day: %.2f, RSI Value: %.2f%n", rsi[0][i], rsi[1][i]); //Print to terminal for debugging
            date++;
        }

        // Create a dataset with the salted data
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("RSI For Data", rsi);


        // Create the chart
        JFreeChart chart = ChartFactory.createXYLineChart("RSI For Data", "Day", "RSI Value", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        setContentPane(chartPanel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

}

