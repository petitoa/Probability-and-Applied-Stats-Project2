package Part3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.Random;

/**
 * The Salt class provides methods for salting the Y values of an XY plot dataset and displaying the salted data.
 *
 * @author petitoa
 */
public class Salt extends JFrame {

    /**
     * Salts the given Y value within the specified range.
     *
     * @param yValue   The original Y value.
     * @param minRange The minimum range for salting.
     * @param maxRange The maximum range for salting.
     * @return The salted Y value.
     */
    public double saltYValue(double yValue, double minRange, double maxRange) {
        Random rng = new Random();
        //nextDouble() rng between 0.0 - 1.0, so use the difference and multiply by random add to minimum
        double saltValue = (maxRange - minRange) * rng.nextDouble() + minRange;
        double saltedYValue = (rng.nextBoolean() ? yValue - saltValue : yValue + saltValue);
        return saltedYValue;
    }

    /**
     * Generates a salted XY plot dataset based on the provided dataset and displays it using JFreeChart.
     *
     * @param xYPlotDataSet The original XY plot dataset.
     * @param minRange      The minimum range for adding salt to Y values.
     * @param maxRange      The maximum range for adding salt to Y values.
     * @return The salted XY plot dataset.
     */
    public double[][] generateSaltedXYPlot(double[][] xYPlotDataSet, double minRange, double maxRange) {
        // Salt the Y values
        double[][] saltedData = new double[2][xYPlotDataSet[0].length];

        for (int i = 0; i < xYPlotDataSet[0].length; i++) {
            saltedData[0][i] = xYPlotDataSet[0][i]; // X value
            saltedData[1][i] = saltYValue(xYPlotDataSet[1][i], minRange, maxRange); // Salt the Y value
        }

        // Create a dataset with the salted data
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Salted Data", saltedData);

        // Create the chart
        JFreeChart chart = ChartFactory.createXYLineChart("Salted Graph", "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        setContentPane(chartPanel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        return saltedData;
    }

}
