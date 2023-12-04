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

        ArrayList<Double> testNumbers = new ArrayList<>();

        testNumbers.add(1.1);
        testNumbers.add(2.0);
        testNumbers.add(3.2);
        testNumbers.add(4.0);
        testNumbers.add(1.1);
        testNumbers.add(5.0);
        testNumbers.add(6.0);
        testNumbers.add(7.0);


        double meanResults = test.findMean(testNumbers);
        double medianResults = test.findMedian(testNumbers);
        double standardDeviation = test.standardDeviation(testNumbers);
        double modeResults = test.findMode(testNumbers);

        BigInteger factorialResults = test.factorial(5);
        BigInteger combinationResults = test.combinations(13, 5);
        BigInteger permutationResults = test.permutations(10, 5);

        double independentIntersectionResults = test.independentIntersection(0.5, 0.4);
        double dependentIntersectionResults = test.dependentIntersection(0.5, 0.4);
        double exclusiveUnionResults = test.exclusiveUnion(0.5, 0.4, 0.7);
        double notExclusiveUnionResults = test.notExclusiveUnion(0.5, 0.4, 0.7);
        String independentOrDependentResults = test.determineIndependenceOrDependent(0.3, 0.5, 0.15);

        double conditionalProbabilityResults = test.conditionalProbability(.3, .5);
        double bayesTheoremResults = test.bayesTheorem(.2, .4, .1);

        double binomialExpectedValueResults = test.binomialExpectedValue(10, .2);
        double binomialVarianceResults = test.binomialVariance(10, .2);
        double binomialDistributionResults = test.binomialDistribution(.8, 10, 7);

        double geometricExpectedValueResults = test.geometricExpectedValue(.2);
        double geometricVarianceResults = test.geometricVariance(.2);
        double geometricDistributionResults = test.geometricDistribution(.2, 8);

        double hypergeometricDistributionResults = test.hypergeometricDistribution(5, 4, 10, 4);
        double hypergeometricDistributionExpectedValueResults = test.hypergeometricDistributionExpectedValue(4, 10, 5);
        double hypergeometricDistributionVarianceResults = test.hypergeometricDistributionVariance(4, 10, 5);

        double negativeBinomialDistributionResults = test.negativeBinomialDistribution(.1, 1, 2);
        double negativeBinomialDistributionExpectedValueResults = test.negativeBinomialExpectedValue(.1, 1);
        double negativeBinomialDistributionVarianceResults = test.negativeBinomialVariance(.1, 1);

        double poissonDistributionResults = test.poissonDistribution(2, 4);
        double poissonDistributionExpectedResults = test.poissonDistributionExpectedValue(2);
        double poissonDistributionVarianceResults = test.poissonDistributionVariance(2);

        double chebyshevsResults = test.chebyshevs(179, 123, 151, 14);

        double uniformDistributionProbability = test.uniformDistributionProbability(0, 40, 0, 15);
        double uniformDistributionExpectedValue = test.uniformDistributionExpectedValue(0, 40);
        double uniformDistributionVariance = test.uniformDistributionVariance(0, 40);

        System.out.println("\nChapter 1");
        System.out.println("The mean of the test numbers: " + meanResults);
        System.out.println("The median of the test numbers: " + medianResults);
        System.out.println("The standard deviation of the test numbers: " + standardDeviation);
        if (Double.isNaN(modeResults)) {
            System.out.println("There is no mode for these test numbers");
        } else {
            System.out.println("The mode for the test numbers: " + modeResults);
        }

        System.out.println("\nChapter 2");
        System.out.println("The factorial of the test number: " + factorialResults);
        System.out.println("The number of the combination n objects r times: " + combinationResults);
        System.out.println("The number of permutations in n objects with r objects selected: " + permutationResults);

        System.out.println("The independent intersection of the test numbers: " + independentIntersectionResults);
        System.out.println("The dependent intersection of the test numbers: " + dependentIntersectionResults);
        System.out.println("The exclusive union of the test numbers: " + exclusiveUnionResults);
        System.out.println("The not exclusive union of the test numbers: " + notExclusiveUnionResults);
        System.out.println("Are the test numbers independent or dependent? " + independentOrDependentResults);

        System.out.println("The conditional probability of P(A and B) & P(B)? " + conditionalProbabilityResults);
        System.out.println("The Bayes Theorem result is? " + bayesTheoremResults);

        System.out.println("\nChapter 3");
        System.out.println("Binomial Distribution");
        System.out.println("The Binomial Distribution is: " + binomialDistributionResults);
        System.out.println("The Binomial Distribution Expected Value is: " + binomialExpectedValueResults);
        System.out.println("The Binomial Distribution Variance is: " + binomialVarianceResults);

        System.out.println("\nGeometric Distribution");
        System.out.println("The Geometric Distribution: " + geometricDistributionResults);
        System.out.println("The Geometric Distribution Expected Value is: " + geometricExpectedValueResults);
        System.out.println("The Geometric Distribution Variance is: " + geometricVarianceResults);

        System.out.println("\nHypergeometric Distribution");
        System.out.println("This is the Hypergeometric Distribution: " + hypergeometricDistributionResults);
        System.out.println("This is the Hypergeometric Distribution Expected Value: " + hypergeometricDistributionExpectedValueResults);
        System.out.println("This is the Hypergeometric Distribution Variance: " + hypergeometricDistributionVarianceResults);

        System.out.println("\nNegative Binomial Distribution");
        System.out.println("The Negative Binomial Distribution is: " + negativeBinomialDistributionResults);
        System.out.println("The Negative Binomial Expected Value is: " + negativeBinomialDistributionExpectedValueResults);
        System.out.println("The Negative Binomial Variance is: " + negativeBinomialDistributionVarianceResults);

        System.out.println("\nPoisson Distribution");
        System.out.println("The Poisson Distribution is: " + poissonDistributionResults);
        System.out.println("The Poisson Distribution Expected Value is: " + poissonDistributionExpectedResults);
        System.out.println("The Poisson Distribution Variance is: " + poissonDistributionVarianceResults);

        System.out.println("\nChebyshev's Theorem");
        System.out.println("The Chebyshev's result is: " + chebyshevsResults);

        System.out.println("\nChapter 4: \n\nUniform Distribution:");
        System.out.println("Probability of the Uniform Distribution: " + uniformDistributionProbability);
        System.out.println("Expected Value of the Uniform Distribution: " + uniformDistributionExpectedValue);
        System.out.println("Variance of the Uniform Distribution: " + uniformDistributionVariance);

    }
}

