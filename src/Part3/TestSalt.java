package Part3;

import javax.swing.*;

/**
 * The TestSalt class serves as a test for the Salt functionality.
 *
 * @author petitoa
 */
public class TestSalt extends JFrame {
    public static void main(String[] args) {
        Plotter plotter = new Plotter();
        Salt test = new Salt();

        double[][] xYPlotDataSet = plotter.generateXYPlot(.2, 0, 380);

        test.generateSaltedXYPlot(xYPlotDataSet, 0, 100);

    }
}
