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

public class Plotter extends JFrame {

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

    public double performFunction(double xValue) {
        UnivariateFunction function = x -> Math.pow(x - 20, 2);

        // Calculate Y using function
        double yValue = function.value(xValue);

        return yValue;
    }


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


