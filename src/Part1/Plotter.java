package Part1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Plotter {

    public double performFunction(double xValue) {
        return Math.pow(xValue - 20, 2);
    }

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
