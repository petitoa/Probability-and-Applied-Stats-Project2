package Part3;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Plotter class provides methods for generating and displaying XY plots using JFreeChart.
 * Uses apache commons math library for function.
 *
 * @author petitoa
 */
public class Plotter extends JFrame {

    /**
     * Generates and displays an XY plot with data points calculated based on a specified function using JFreeChart.
     *
     * @param intervalBetweenPoints The interval between consecutive x values.
     * @param lowerBound            The lower bound of the X-axis range.
     * @param upperBound            The upper bound of the X-axis range.
     * @return The generated XY plot dataset.
     */
    public double[][] generateXYPlot(double intervalBetweenPoints, double lowerBound, double upperBound) {

        double[][] xYPlotDataSet = plotPoints(intervalBetweenPoints, lowerBound, upperBound);

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Data", xYPlotDataSet);

        JFreeChart chart = ChartFactory.createXYLineChart("Plotted Graph", "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        setContentPane(chartPanel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        return xYPlotDataSet;
    }

    /**
     * Performs a mathematical function on the given X value to calculate the corresponding Y value.
     *
     * @param xValue The X value for which the Y value is calculated.
     * @return The calculated Y value.
     */
    public double performFunction(double xValue) {
        UnivariateFunction function = x -> Math.pow(x - 20, 2);

        // Calculate Y using function
        double yValue = function.value(xValue);

        return yValue;
    }


    /**
     * Calculates the x and y values for a specified function and generates data points within a given range.
     *
     * @param intervalBetweenPoints The interval between consecutive X-axis points.
     * @param lowerBound            The lower bound of the X-axis range.
     * @param upperBound            The upper bound of the X-axis range.
     * @return A 2D array representing the generated XY plot points.
     */
    public double[][] plotPoints(double intervalBetweenPoints, double lowerBound, double upperBound) {
        List<double[]> pointsList = new ArrayList<>();

        double currentXValue = lowerBound;
        while (currentXValue <= upperBound) { // Include upperBound
            double yValue = performFunction(currentXValue);
            if (yValue >= lowerBound && yValue <= upperBound) {
                double[] point = {currentXValue, yValue};
                pointsList.add(point);
            }

            currentXValue += intervalBetweenPoints;
        }

        // Convert to a 2D array
        double[][] points = new double[2][pointsList.size()];
        for (int i = 0; i < pointsList.size(); i++) {
            points[0][i] = pointsList.get(i)[0];
            points[1][i] = pointsList.get(i)[1];
        }

        return points;
    }
}


