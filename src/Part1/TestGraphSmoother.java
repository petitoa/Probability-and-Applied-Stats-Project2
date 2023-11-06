package Part1;

public class TestGraphSmoother {
    public static void main(String[] args) {
        GraphSmoother test = new GraphSmoother();

        test.smoothToCsv("smoothed-points.csv", 4);
    }
}
