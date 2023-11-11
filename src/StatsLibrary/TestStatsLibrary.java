package StatsLibrary;

public class TestStatsLibrary {
    public static void main(String[] args) {
        StatsLibrary test = new StatsLibrary();

        double uniformDistributionProbability = test.uniformDistributionProbability(0, 40, 0, 15);
        double uniformDistributionExpectedValue = test.uniformDistributionExpectedValue(0, 40);
        double uniformDistributionVariance = test.uniformDistributionVariance(0, 40);

        System.out.println("Chapter 4: \n\nUniform Distribution:");
        System.out.println("Probability of the Uniform Distribution: " + uniformDistributionProbability);
        System.out.println("Expected Value of the Uniform Distribution: " + uniformDistributionExpectedValue);
        System.out.println("Variance of the Uniform Distribution: " + uniformDistributionVariance);

    }
}
