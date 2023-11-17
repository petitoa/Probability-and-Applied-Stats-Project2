package Stocks;

public class TestStockSmoother {

    public static void main(String[] args) {
        StockSmoother test = new StockSmoother();

        test.stockSmoothToCsv("AMZN.csv", 4);
    }
}
