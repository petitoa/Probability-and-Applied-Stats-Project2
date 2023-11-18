package Stocks;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The StockSmoother class provides methods for smoothing stock data using a moving window and writing the smoothed data to a CSV file.
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
            smoothedStocks.add(new Stock(stocks.get(i).getDate(), smoothedY));
        }

        return smoothedStocks;
    }

    /**
     * Writes the smoothed data to a new CSV file.
     *
     * @param inputFile   The name of the CSV file containing stock data.
     * @param windowValue The size of the moving window (number of data points on each side of the current point).
     */
    public void stockSmoothToCsv(String inputFile, int windowValue) {
        File file = new File(inputFile);

        ArrayList<Stock> stocks = new ArrayList<>();

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
                scanner.nextLine();
                stocks.add(new Stock(date, openValue));
            }

            ArrayList<Stock> smoothedStocks = new ArrayList<>(stockSmoother(stocks, windowValue));

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
    }
}
