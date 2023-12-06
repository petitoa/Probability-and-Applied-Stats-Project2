package StatsLibrary;

/**
 * This class provides a collection of statistical and probability functions.
 *
 * @author petitoa
 */
public class StatsLibrary {
    /**
     * Calculates the probability of a uniform distribution within the range [a, b]
     * for a given sub-interval [c, d].
     *
     * @param a The lower bound of the entire distribution.
     * @param b The upper bound of the entire distribution.
     * @param c The lower bound of the sub-interval for which the probability is calculated.
     * @param d The upper bound of the sub-interval for which the probability is calculated.
     * @return The probability of the uniform distribution within the specified sub-interval.
     */
    public double uniformDistributionProbability(double a, double b, double c, double d) {
        double numerator = d - c;
        double denominator = b - a;
        return numerator / denominator;

    }

    /**
     * Calculates the expected value of a uniform distribution with given bounds.
     *
     * @param thetaSubOne The lower bound of the uniform distribution.
     * @param thetaSubTwo The upper bound of the uniform distribution.
     * @return The expected value of the uniform distribution.
     */
    public double uniformDistributionExpectedValue(double thetaSubOne, double thetaSubTwo) {
        double numerator = thetaSubOne + thetaSubTwo;
        double denominator = 2;
        return numerator / denominator;

    }

    /**
     * Calculates the variance of a uniform distribution with given bounds.
     *
     * @param a The lower bound of the uniform distribution.
     * @param b The upper bound of the uniform distribution.
     * @return The variance of the uniform distribution.
     */
    public double uniformDistributionVariance(double a, double b) {
        double numerator = Math.pow((b - a), 2);
        double denominator = 12;
        return numerator / denominator;

    }

    /**
     * Determines the joint probability independence based on marginal probabilities.
     * <p>
     * This method takes the marginal probabilities pXOfX, pYOfY, and the joint probability pXAndY
     * as parameters and determines whether the marginal probability functions p_x (x) and p_y (y)
     * are independent or dependent.
     *
     * @param pXOfX  The marginal probability function p_x (x).
     * @param pYOfY  The marginal probability function p_y (y).
     * @param pXAndY The joint probability function p(x, y).
     * @return A String indicating the independence or dependence of the marginal probability functions.
     */
    public String jointProbabilityIndependence(double pXOfX, double pYOfY, double pXAndY) {
        if (pXOfX * pYOfY == pXAndY) {
            return "The marginal probability functions p_x (x) and p_y (y) are independent.";
        } else {
            return "The marginal probability functions p_x (x) and p_y (y) are dependent.";
        }
    }

}
