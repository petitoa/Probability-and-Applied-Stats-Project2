package Part1;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The Salter class provides methods for salting the y-values of points.
 * Points are read from a CSV file, salted, and written to a new CSV file.
 *
 * @author petitoa
 */
public class Salter {

    /**
     * Salts the given y-value within a specified range.
     *
     * @param yValue   The original y-value to be salted.
     * @param minRange The minimum range for the salt.
     * @param maxRange The maximum range for the salt.
     * @return The salted y-value.
     */
    public double saltYValue(double yValue, double minRange, double maxRange) {
        Random rng = new Random();
        //nextDouble() rng between 0.0 - 1.0, so use the difference and multiply by random add to minimum
        double saltValue = (maxRange - minRange) * rng.nextDouble() + minRange;
        double saltedYValue = (rng.nextBoolean() ? yValue - saltValue : yValue + saltValue);
        return saltedYValue;
    }

    /**
     * Adds random salt to the y-values of points read from a CSV file then writes the salted points to a new CSV file.
     *
     * @param minRange The minimum range for the random salt.
     * @param maxRange The maximum range for the random salt.
     */
    public void saltToCsv(double minRange, double maxRange) {
        String filename = "points.csv";
        File file = new File(filename);

        ArrayList<Point> points = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            // Store and skip the header
            String header = scanner.nextLine();

            // Delimiter set to comma or new line
            scanner.useDelimiter(",|\n");


            // Salt Y Values and Write point objects to CSV file separated by commas
            while (scanner.hasNextLine()) {
                double xValue = Double.parseDouble(scanner.next().trim());
                double yValue = Double.parseDouble(scanner.next().trim());
                double saltedYValue = saltYValue(yValue, minRange, maxRange);
                points.add(new Point(xValue, saltedYValue));
            }

            try (FileWriter fw = new FileWriter("salted-points.csv");
                 BufferedWriter bw = new BufferedWriter(fw)) {

                // Write Header
                bw.write(header);

                // Write Point objects data to the CSV file separated by commas
                for (Point point : points) {
                    bw.newLine();
                    bw.write(point.getXValue() + "," + point.getYValue());
                }

            } catch (IOException e) {
                throw new RuntimeException("Error while writing the salted data to the new CSV file", e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error while reading the CSV file", e);
        }
    }
}
