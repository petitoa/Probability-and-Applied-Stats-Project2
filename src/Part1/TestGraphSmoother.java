package Part1;

/**
 * The TestGraphSmoother class serves as a test for the GraphSmoother functionality.
 *
 * @author petitoa
 */
public class TestGraphSmoother {
    public static void main(String[] args) {
        GraphSmoother test = new GraphSmoother();

        test.smoothToCsv("smoothed-points.csv", 4);
    }
}
