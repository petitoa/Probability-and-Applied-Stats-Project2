import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphSmoother {
    public ArrayList<Point> smoother(ArrayList<Point> points, int windowValue) {
        ArrayList<Point> smoothedPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            double yValueSum = points.get(i).getYValue();

            // j = -windowValue loop will return the left and right side of the point object
            for (int j = -windowValue; j <= windowValue; j++) {
                //get next index y values
                int index = i + j;
                if (index >= 0 && index < points.size()) {
                    yValueSum += points.get(index).getYValue();
                }
            }

            // Calculate the average
            double smoothedY = yValueSum / (windowValue * 2);


            // Add the smoothed point to smoothedPoints
            smoothedPoints.add(new Point(points.get(i).getXValue(), smoothedY));
        }

        return smoothedPoints;
    }

    public void smoothToCsv(String inputFile, int windowValue) {
        File file = new File(inputFile);

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
                points.add(new Point(xValue, yValue));
            }

            ArrayList<Point> smoothedPoints = new ArrayList<>(smoother(points, windowValue));

            try (FileWriter fw = new FileWriter("smoothed-points.csv");
                 BufferedWriter bw = new BufferedWriter(fw)) {

                // Write Header
                bw.write(header);

                // Write Point objects data to the CSV file separated by commas
                for (Point point : smoothedPoints) {
                    bw.newLine();
                    bw.write(point.getXValue() + "," + point.getYValue());
                }

            } catch (IOException e) {
                throw new RuntimeException("Error while writing the smoothed data to the new CSV file", e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error while reading the CSV file", e);
        }
    }
}
