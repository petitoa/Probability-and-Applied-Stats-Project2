package StatsLibrary;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * This class is used for testing the functionality of the StatsLibrary class, which provides various functions for probability and statistics.
 *
 * @author petitoa
 */
public class TestStatsLibrary {

    public static void main(String[] args) {
        StatsLibrary test = new StatsLibrary();

        double uniformDistributionProbability = test.uniformDistributionProbability(0, 40, 0, 15);
        double uniformDistributionExpectedValue = test.uniformDistributionExpectedValue(0, 40);
        double uniformDistributionVariance = test.uniformDistributionVariance(0, 40);

        String jointProbabilityIndependence = test.jointProbabilityIndependence(.3, .5, .8);

        System.out.println("\nChapter 4: \n\nUniform Distribution:");
        System.out.println("Probability of the Uniform Distribution: " + uniformDistributionProbability);
        System.out.println("Expected Value of the Uniform Distribution: " + uniformDistributionExpectedValue);
        System.out.println("Variance of the Uniform Distribution: " + uniformDistributionVariance);

        System.out.println("The determined joint probability independence: " + jointProbabilityIndependence);

    }
}

