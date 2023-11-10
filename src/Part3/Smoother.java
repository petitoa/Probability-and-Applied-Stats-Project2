package Part3;

import Part1.Point;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.Arrays;

public class Smoother extends JFrame {

    public void smoother(double[][] xYPlotDataSet, int windowValue, int smoothingIterations) {
        // Create a new array for smoothed data that contains x values
        double[][] smoothedData = new double[2][xYPlotDataSet[0].length];

        // Copy x value directly
        System.arraycopy(xYPlotDataSet[0], 0, smoothedData[0], 0, xYPlotDataSet[0].length);

        // Separate y values
        double[] yValues = new double[xYPlotDataSet[0].length];

        // Copy y values directly
        System.arraycopy(xYPlotDataSet[1], 0, yValues, 0, xYPlotDataSet[0].length);

        // Create a DescriptiveStatistics instance with the specified window size
        DescriptiveStatistics stats = new DescriptiveStatistics(windowValue);
        for (int iterations = 0; iterations < smoothingIterations; iterations++) {
            for (int k = 0; k < xYPlotDataSet[0].length; k++) {
                stats.addValue(yValues[k]);
                smoothedData[1][k] = stats.getMean();
            }
        }

        // Create the JFreeChart using the final smoothedDataset
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Smoothed Graph", smoothedData);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Smoothed Graph", "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        setContentPane(chartPanel);
    }

}