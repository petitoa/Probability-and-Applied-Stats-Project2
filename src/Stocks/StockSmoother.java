package Stocks;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The StockSmoother class provides methods for smoothing stock data using a moving window and writing the smoothed data to a CSV file.
 * Graphs the Smoothed Stocks using JFreeChart.
 *
 * @author petitoa
 */
public class StockSmoother {

    /**
     * Smooths the given list of stock data using a moving window.
     *
     * @param stocks      The list of stock data points to be smoothed.
     * @param windowValue The size of the moving window (number of data points on each side of the current point).
     * @return The list of smoothed stock data points.
     */
    public ArrayList<Stock> stockSmoother(ArrayList<Stock> stocks, int windowValue) {
        ArrayList<Stock> smoothedStocks = new ArrayList<>();

        for (int i = 0; i < stocks.size(); i++) {
            double yValueSum = stocks.get(i).getOpenValue();

            // j = -windowValue loop will return the left and right side of the point object
            for (int j = -windowValue; j <= windowValue; j++) {
                //get next index y values
                int index = i + j;
                if (index >= 0 && index < stocks.size()) {
                    yValueSum += stocks.get(index).getOpenValue();
                }
            }

            // Calculate the average
            double smoothedY = yValueSum / (windowValue * 2);


            // Add the smoothed point to smoothedPoints
            smoothedStocks.add(new Stock(stocks.get(i).getDate(), smoothedY, stocks.get(i).getCloseValue()));
        }

        return smoothedStocks;
    }

    /**
     * Displays a graph of the smoothed stock data using JFreeChart.
     *
     * @param stocks      The list of original stock objects.
     * @param windowValue The size of the moving window.
     */
    public void graphSmoothedStocks(ArrayList<Stock> stocks, int windowValue) {
        ArrayList<Stock> smoothedValues = new ArrayList<>(stockSmoother(stocks, windowValue));

        double[][] smoothToGraph = new double[2][smoothedValues.size()];

        double date = 0;
        for (int i = 0; i < smoothedValues.size(); i++) {
            smoothToGraph[0][i] = date; // X-axis (day)
            smoothToGraph[1][i] = smoothedValues.get(i).getOpenValue(); // Use the appropriate method to get smoothed value
            date++;
        }

        // Create a dataset with the salted data
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Smoothed Stock Data", smoothToGraph);

        // Create the chart
        JFreeChart chart = ChartFactory.createXYLineChart("Smoothed Data", "Day", "Stock", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        // Set up the JFrame
        JFrame frame = new JFrame("Smoothed Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Writes the smoothed data to a new CSV file.
     *
     * @param inputFile   The name of the CSV file containing stock data.
     * @param windowValue The size of the moving window (number of data points on each side of the current point).
     */
    public ArrayList<Stock> stockSmoothToCsv(String inputFile, int windowValue) {
        File file = new File(inputFile);

        ArrayList<Stock> stocks = new ArrayList<>();

        ArrayList<Stock> smoothedStocks;
        try (Scanner scanner = new Scanner(file)) {
            // Store and skip the header
            String header = scanner.nextLine();

            // Delimiter set to comma or new line
            scanner.useDelimiter(",|\n");

            int lineNumber = 0;
            // Salt Y Values and Write point objects to CSV file separated by commas
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

            smoothedStocks = new ArrayList<>(stockSmoother(stocks, windowValue));

            try (FileWriter fw = new FileWriter("smoothed-stocks.csv");
                 BufferedWriter bw = new BufferedWriter(fw)) {

                // Write Header
                bw.write(header);

                // Write Point objects data to the CSV file separated by commas
                for (Stock stock : smoothedStocks) {
                    bw.newLine();
                    bw.write(stock.getDate() + "," + stock.getOpenValue());
                }

            } catch (IOException e) {
                throw new RuntimeException("Error while writing the smoothed data to the new CSV file", e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error while reading the CSV file", e);
        }
        return smoothedStocks;
    }
}
