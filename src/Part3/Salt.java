package Part3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.Random;

public class Salt extends JFrame {

    public double saltYValue(double yValue, double minRange, double maxRange) {
        Random rng = new Random();
        //nextDouble() rng between 0.0 - 1.0, so use the difference and multiply by random add to minimum
        double saltValue = (maxRange - minRange) * rng.nextDouble() + minRange;
        double saltedYValue = (rng.nextBoolean() ? yValue - saltValue : yValue + saltValue);
        return saltedYValue;
    }

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
