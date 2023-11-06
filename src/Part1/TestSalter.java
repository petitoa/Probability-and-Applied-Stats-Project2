package Part1;

import java.io.FileNotFoundException;

public class TestSalter {
    public static void main(String[] args) throws FileNotFoundException {
        Salter test = new Salter();

        test.saltToCsv(0, 100);
    }
}
