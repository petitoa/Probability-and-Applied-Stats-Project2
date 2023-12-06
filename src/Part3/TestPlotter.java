package Part3;

import javax.swing.*;

/**
 * The TestPlotter class serves as a test for the Plotter functionality.
 *
 * @author petitoa
 */
public class TestPlotter extends JFrame {
    public static void main(String[] args) {
        Plotter test = new Plotter();

        test.generateXYPlot(.2, 0, 380);

    }
}


