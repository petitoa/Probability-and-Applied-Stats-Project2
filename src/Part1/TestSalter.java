package Part1;

import java.io.FileNotFoundException;

/**
 * The TestSalter class serves as a test for the Salter functionality.
 *
 * @author petitoa
 */
public class TestSalter {
    public static void main(String[] args) throws FileNotFoundException {
        Salter test = new Salter();

        test.saltToCsv(0, 100);
    }
}
