package Part1;

import java.util.ArrayList;

/**
 * The TestPlotter class serves as a test for the Plotter functionality.
 *
 * @author petitoa
 */
public class TestPlotter {
    public static void main(String[] args) {
        Plotter test = new Plotter();

        ArrayList<Point> points = test.createPoints(.2, 0, 380);

        test.plotterToCsv(points);
    }
}
