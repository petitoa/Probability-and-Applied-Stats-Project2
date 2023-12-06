package Part1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Plotter class provides methods for performing a mathematical function on values,
 * This creates points based on the function within a specified range, and writes the points to a CSV file.
 *
 * @author petitoa
 */
public class Plotter {

    /**
     * Performs a mathematical function on the given x-value.
     *
     * @param xValue The input x-value for the mathematical function.
     * @return The result (y-value) of the mathematical function for the given x-value.
     */
    public double performFunction(double xValue) {
        return Math.pow(xValue - 20, 2);
    }

    /**
     * Creates a list of points based on a mathematical function within a specified range.
     *
     * @param intervalBetweenPoints The interval between x-values.
     * @param lowerBound            The lower bound of x-values for the function.
     * @param upperBound            The upper bound of x-values for the function.
     * @return An ArrayList of Point objects representing the points on the function within the specified range.
     */
    ArrayList<Point> createPoints(double intervalBetweenPoints, double lowerBound, double upperBound) {
        ArrayList<Point> points = new ArrayList<>();

        double currentXValue = lowerBound;
        while (currentXValue < upperBound) {
            double yValue = performFunction(currentXValue);
            // Check if yValue is within the lower and upper bounds
            if (yValue >= lowerBound && yValue <= upperBound) {
                points.add(new Point(currentXValue, yValue));
            }

            currentXValue += intervalBetweenPoints;
        }

        return points;
    }

    /**
     * Writes ArrayList of points to a CSV file.
     *
     * @param points The ArrayList of Point objects to be written to the CSV file.
     */
    public void plotterToCsv(ArrayList<Point> points) {
        try (FileWriter fw = new FileWriter("points.csv");
             BufferedWriter bw = new BufferedWriter(fw)) {

            // Write Header
            bw.write("xValue,yValue");

            // Write Point objects data to the CSV file separated by commas
            for (Point point : points) {
                bw.newLine();
                bw.write(point.getXValue() + "," + point.getYValue());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
