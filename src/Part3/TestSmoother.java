package Part3;

import javax.swing.*;

/**
 * The TestSmoother class serves as a test for the Smoother functionality.
 *
 * @author petitoa
 */
public class TestSmoother extends JFrame {
    public static void main(String[] args) {
        Smoother test = new Smoother();
        Salt salt = new Salt();
        Plotter plotter = new Plotter();

        double[][] xYPlot = plotter.generateXYPlot(.2, 0, 380);

        double[][] saltedData = salt.generateSaltedXYPlot(xYPlot, 0, 100);

        test.smoother(saltedData, 10, 10);

    }
}
