package Stocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StockBot {

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

    public NetWorth completeRun(NetWorth networth, ArrayList<Stock> stocks) {
        ArrayList<Stock> traversedStocks = new ArrayList<>();

        double heuristic;
        for (Stock stock : stocks) {
            // load open to the traversed stocks
            double date = stock.getDate();
            double openValue = stock.getOpenValue();
            traversedStocks.add(new Stock(date, openValue));

            // calculate new heuristic and determine trade for the day
            heuristic = updateInternalData(traversedStocks);
            int determinedTrade = tradeEvaluator(networth, heuristic, openValue);

            // update net worth accordingly
            networth.updatePortfolio(determinedTrade, openValue);
        }
        return networth;
    }

    public double updateInternalData(ArrayList<Stock> stocks) {
        double stocksTotal = 0;
        for (Stock stock : stocks) {
            double openValue = stock.getOpenValue();
            stocksTotal += openValue;
        }
        return stocksTotal / stocks.size();
    }

    // number of stocks to sell per day based on mean of traversed stocks so far
    public int tradeEvaluator(NetWorth netWorth, double heuristic, double openValue) {
        if (openValue < heuristic) {
            return (int) ((.01 * netWorth.getNetWorth()) / openValue);
        } else if (openValue > heuristic) {
            return -(int) ((.01 * netWorth.getNetWorth()) / openValue);

        } else {
            return 0;
        }
    }
}
