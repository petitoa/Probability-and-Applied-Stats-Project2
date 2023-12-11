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
 * The StockBot class represents a bot for trading with a simulated stock portfolio.
 * It includes methods for loading stock data from a CSV file, running a simulation, and evaluating trades by day.
 * There are 3 different trade methods.
 *
 * @author petitoa
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
    public NetWorth completeRun(NetWorth netWorth, ArrayList<Stock> stocks, int tradeMethod) {
        ArrayList<Stock> traversedStocks = new ArrayList<>();

        // Fill the first 15 values of rsi with zeros because not enough values to determine
        ArrayList<Double> rsi = new ArrayList<>(Collections.nCopies(15, 0.0));

        // Calculate RSI for the remaining days and add them to the existing rsi ArrayList
        rsi.addAll(calculateRsi(stocks));

        double heuristic;
        int day = 0;
        for (Stock stock : stocks) {
            // load open to the traversed stocks
            double date = stock.getDate();
            double openValue = stock.getOpenValue();
            double closeValue = stock.getCloseValue();
            traversedStocks.add(new Stock(date, openValue, closeValue));

            // calculate new heuristic and determine trade for the day
            heuristic = updateInternalData(traversedStocks);
            double rsiForDay = rsi.get(day);
            int determinedTrade = determineTradeMethod(netWorth, heuristic, openValue, rsiForDay, day, tradeMethod);
            // update net worth accordingly
            netWorth.updatePortfolio(determinedTrade, openValue);
            System.out.println("Day: " + day + " Determined Trade: " + determinedTrade + " End of day networth: " + netWorth.getNetWorth() + " Num of stocks: " + netWorth.getStockQuantity() + " Trade Method: " + tradeMethod);
            day++;
        }

        return netWorth;
    }

    /**
     * Determines the trade action based on the specified trade method using int.
     *
     * @param netWorth    The current NetWorth object representing the portfolio's net worth.
     * @param heuristic   The calculated heuristic (mean) based on recent stock data.
     * @param openValue   The open value for the stock object.
     * @param rsiForDay   The RSI value for the current day.
     * @param day         The current day of the simulation.
     * @param tradeMethod The trade methods:
     *                    - 1: RSI and Heuristic Trade Evaluator
     *                    - 2: Buy and Hold
     *                    - 3: RSI and Moving Average
     * @return The number of stocks to buy (negative), sell (positive), or take no action (0)
     * based on the specified trade method.
     * @throws IllegalArgumentException If an invalid trade method code is provided.
     */
    private int determineTradeMethod(NetWorth netWorth, double heuristic, double openValue, double rsiForDay, int day, int tradeMethod) {
        return switch (tradeMethod) {
            case 1 -> RsiAndHeuristicTradeEvaluator(netWorth, heuristic, openValue, rsiForDay, day);
            case 2 -> buyAndHold(netWorth, openValue, day);
            case 3 -> rsiAndMovingAverage(netWorth, heuristic, openValue, rsiForDay);
            default -> throw new IllegalArgumentException("Invalid trade method: " + tradeMethod);
        };
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
    public int RsiAndHeuristicTradeEvaluator(NetWorth netWorth, double heuristic, double openValue, double rsiForDay, int day) {
        double portfolioPercent = .30;

        // If it's the first day, buy with half of the net worth to establish a portfolio
        if (day == 0) {
            return -(int) ((0.5 * netWorth.getNetWorth()) / openValue);
        }

        //If there are not enough prior dates to calculate rsi use heuristic instead
        if (day < 14) {
            if (openValue < heuristic) {
                // .32 (never more than thirty-two percent of portfolio in a day)
                return -(int) ((portfolioPercent * netWorth.getNetWorth()) / openValue);
            } else if (openValue > heuristic) {
                return (int) ((portfolioPercent * netWorth.getNetWorth()) / openValue);
            } else {
                return 0;
            }
        } else {
            double overboughtThreshold = 50;
            double oversoldThreshold = 30;

            if (openValue < heuristic && rsiForDay < oversoldThreshold) {
                // Buy condition: If the stock price is below the mean and RSI indicates oversold
                return -(int) ((portfolioPercent * netWorth.getNetWorth()) / openValue);
            } else if (openValue > heuristic && rsiForDay > overboughtThreshold) {
                // Sell condition: If the stock price is above the mean and RSI indicates overbought
                return (int) ((portfolioPercent * netWorth.getNetWorth()) / openValue);
            } else {
                return 0; // Hold condition: No action
            }
        }
    }

    /**
     * If it is the first day, the method buys stocks with the full portfolio value.
     * On the last day, it sells all stocks in the portfolio.
     * Otherwise, it takes no action.
     *
     * @param netWorth  The current NetWorth object representing the portfolio's net worth.
     * @param openValue The open value for the stock object.
     * @param day       The current day of the simulation.
     * @return The number of stocks to buy (negative) on the first day, sell (positive) on the last day,
     * or take no action (0) on other days.
     */
    public int buyAndHold(NetWorth netWorth, double openValue, int day) {
        //If it is the first day
        if (day == 0) {
            // buy with full portfolio value
            return -(int) ((netWorth.getNetWorth()) / openValue);
            //If it's the last day
        } else if (day == 250) {
            // Sell all stocks on the last day
            return netWorth.getStockQuantity();
        }
        return 0; // No action
    }

    /**
     * Evaluates the number of stocks to buy or sell based on the net worth, heuristic (mean), and current stock price.
     * The value of the trade never exceeds 1% of the portfolios total value.
     *
     * @param netWorth  The current NetWorth object representing the portfolio's net worth.
     * @param heuristic The calculated heuristic (mean) based on recent stock data.
     * @param openValue The open value of the stock.
     * @param rsiForDay The RSI value for the current day.
     * @return The number of stocks to buy (negative) or sell (positive) based on the evaluation.
     */
    public int rsiAndMovingAverage(NetWorth netWorth, double heuristic, double openValue, double rsiForDay) {
        double portfolioPercent = .01;
        double overboughtThreshold = 70;
        double oversoldThreshold = 30;

        if (openValue < heuristic && rsiForDay < oversoldThreshold) {
            // Buy: If the stock price is below the mean and RSI indicates oversold
            return -(int) ((portfolioPercent * netWorth.getNetWorth()) / openValue);
        } else if (openValue > heuristic && rsiForDay > overboughtThreshold) {
            // Sell: If the stock price is above the mean and RSI indicates overbought
            return (int) ((portfolioPercent * netWorth.getNetWorth()) / openValue);
        } else {
            return 0; // Hold: No action
        }
    }


    /**
     * Calculates the RSI values for each day based on the provided stock data.
     *
     * @param stocks The ArrayList of Stock objects representing the stock data.
     * @return An ArrayList of RSI values for each corresponding day.
     */
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


    /**
     * Displays the RSI values of stock objects as a graph using JFreeChart.
     *
     * @param stocks The ArrayList of Stock objects representing the stock data.
     */
    public void graphRsi(ArrayList<Stock> stocks) {
        ArrayList<Double> rsiValues = new ArrayList<>(calculateRsi(stocks));

        double[][] rsi = new double[2][rsiValues.size()];

        double date = 0;
        for (int i = 0; i < rsiValues.size(); i++) {
            // Populate the 2D array with date and RSI value
            rsi[0][i] = date; // X-axis (day)
            rsi[1][i] = rsiValues.get(i); // Y-axis (RSI value)
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

