package StatsLibrary;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class provides a collection of statistical and probability functions.
 *
 * @author petitoa
 */
public class StatsLibrary {

    /**
     * A method to perform division operation and handle division by zero.
     *
     * @param numerator   The numerator of the division.
     * @param denominator The denominator of the division.
     * @return The result of the division.
     * @throws ArithmeticException if division by zero is detected.
     */
    public double divide(double numerator, double denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero is undefined.");
        }
        return numerator / denominator;
    }

    /**
     * A generic method that checks to make sure passed values are non-negative.
     *
     * @param values The parameters that you want to check.
     * @throws IllegalArgumentException If negative values are detected.
     */
    @SafeVarargs
    public final <T extends Number> void checkNonNegative(T... values) {
        for (T value : values) {
            if (value.doubleValue() < 0) {
                throw new IllegalArgumentException("Input and output values must be non-negative.");
            }
        }
    }

    /**
     * A generic method that checks to make sure passed values are in bounds [0, 1].
     * Used in {@link #combinations(int, int)} and {@link #permutations(int, int)}}.
     *
     * @param values The parameters that you want to check.
     * @throws IllegalArgumentException If out of bound value detected.
     */
    @SafeVarargs
    public final <T extends Number> void checkInputInBound(T... values) {
        for (T value : values) {
            if (value.doubleValue() < 0 || value.doubleValue() > 1) {
                throw new IllegalArgumentException("Input values must be non-negative.");
            }
        }
    }

    /**
     * Checks if a double result is within the distribution bounds [0, 1].
     *
     * @param result The double value to be checked.
     * @throws IllegalArgumentException If the result is outside the bounds [0, 1].
     */
    public void checkDoubleResultInBound(double result) {
        if (result < 0 || result > 1) {
            throw new IllegalArgumentException("Result is out of bounds [0, 1]");
        }
    }

    /**
     * Checks if BigInteger is non-negative.
     *
     * @param result The BigInteger value to be checked.
     * @throws IllegalArgumentException If the result is negative.
     */
    public void checkBigIntegerInBound(BigInteger result) {
        if (result.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Output values must be non-negative.");
        }
    }

    /**
     * Calculates the mean of an ArrayList of numbers.
     *
     * @param userInputNumbers The ArrayList of numbers.
     * @return The mean value.
     */
    public double findMean(ArrayList<Double> userInputNumbers) {
        double sum = 0;

        for (double singleElement : userInputNumbers) {
            sum = sum + singleElement;

        }

        double result = divide(sum, userInputNumbers.size());
        return result;
    }

    /**
     * Calculates the median of an ArrayList of numbers.
     *
     * @param userInputNumbers The ArrayList of numbers.
     * @return The median value.
     */
    public double findMedian(ArrayList<Double> userInputNumbers) {
        Collections.sort(userInputNumbers);
        int size = userInputNumbers.size();
        double median;

        // if array is even find average of middle values
        if (size % 2 == 0) {
            double middle1 = userInputNumbers.get((size / 2) + 1);
            double middle2 = userInputNumbers.get((size / 2) - 1);
            median = (middle1 + middle2) / 2;
        } else {
            // get middle value
            median = userInputNumbers.get(size / 2);
        }
        return median;
    }

    /**
     * Finds the mode of an ArrayList of numbers.
     *
     * @param userInputNumbers The ArrayList of numbers.
     * @return The mode if there is one.
     */
    public double findMode(ArrayList<Double> userInputNumbers) {
        Collections.sort(userInputNumbers);
        ArrayList<Double> modes = new ArrayList<>();

        int highestCount = 0;
        // iterate through sorted list starting at index 0
        for (Double currentNumber : userInputNumbers) {
            int count = 0;
            // compare to numbers increase count accordingly
            for (Double number : userInputNumbers) {
                if (currentNumber.equals(number)) {
                    count++;
                }
            }

            if (count > highestCount) {
                highestCount = count;
                // Clear list for the higher count
                modes.clear();
                // Add the new mode
                modes.add(currentNumber);
                //tie between the highest count and count / it's not a duplicate mode
            } else if (count == highestCount && !modes.contains(currentNumber)) {
                modes.add(currentNumber);
            }
        }

        //No numbers that occur more than once or 2 numbers occur the same amount of times greater than 1
        if (highestCount == 0 || modes.size() > 1) {
            return Double.NaN;
        }

        return modes.get(0);
    }


    /**
     * Calculates the standard deviation of a list of numbers.
     *
     * @param userInputNumbers The list of numbers.
     * @return The standard deviation value.
     */
    public double standardDeviation(ArrayList<Double> userInputNumbers) {

        //check inputs are non-negative
        for (double number : userInputNumbers) {
            checkNonNegative(number);
        }

        double mean = findMean(userInputNumbers);

        ArrayList<Double> subtractMean = new ArrayList<>();
        //Subtract mean from each data point
        for (double singleElement : userInputNumbers) {
            subtractMean.add(singleElement - mean);
        }

        ArrayList<Double> squareDeviations = new ArrayList<>();
        //Square each deviation
        for (double singleElement : subtractMean) {
            squareDeviations.add(singleElement * singleElement);
        }

        int sizeMinus1 = squareDeviations.size() - 1;
        double sum = 0;
        //Sum squared deviations
        for (double singleElement : squareDeviations) {
            sum = sum + singleElement;
        }

        // Check result is non-negative.
        checkNonNegative(Math.sqrt(divide(sum, sizeMinus1)));

        //Calculate result
        return Math.sqrt(divide(sum, sizeMinus1));
    }

    /**
     * Calculates the factorial of a given integer.
     *
     * @param num The integer for which to calculate the factorial.
     * @return The factorial value as a BigInteger.
     */
    public BigInteger factorial(int num) {
        //check input greater than zero
        checkNonNegative(num);
        if (num == 0) {
            return BigInteger.ONE;
        } else {
            BigInteger factorial = BigInteger.ONE;
            for (int i = 1; i <= num; i++) {
                factorial = factorial.multiply(BigInteger.valueOf(i));
            }
            //check result greater than zero
            checkBigIntegerInBound(factorial);
            return factorial;
        }
    }

    /**
     * Calculates the number of combinations (n choose r).
     *
     * @param objects The total number of objects (n).
     * @param times   The number of objects to choose (r).
     * @return The number of combinations as a BigInteger.
     */
    public BigInteger combinations(int objects, int times) {
        //Make sure inputs are non-negative
        checkNonNegative(objects, times);

        BigInteger objectsFactorial = factorial(objects);
        BigInteger timesFactorial = factorial(times);

        int objectsMinusTimes = objects - times;

        BigInteger objectsMinusTimesFactorial = factorial(objectsMinusTimes);

        BigInteger numerator = objectsFactorial;
        BigInteger denominator = timesFactorial.multiply(objectsMinusTimesFactorial);

        // Check result is greater than zero
        checkBigIntegerInBound(numerator.divide(denominator));

        return numerator.divide(denominator);
    }

    /**
     * Calculates the number of permutations (n P r).
     *
     * @param objects         The total number of objects (n).
     * @param objectsSelected The number of objects selected (r).
     * @return The number of permutations as a BigInteger.
     */
    public BigInteger permutations(int objects, int objectsSelected) {
        //Make sure inputs are non-negative
        checkNonNegative(objects, objectsSelected);

        BigInteger objectsFactorial = factorial(objects);

        int objectsMinusObjectsSelected = objects - objectsSelected;

        BigInteger denominatorFactorial = factorial(objectsMinusObjectsSelected);

        BigInteger numerator = objectsFactorial;
        BigInteger denominator = denominatorFactorial;

        // Check result is greater than zero
        checkBigIntegerInBound(numerator.divide(denominator));

        return numerator.divide(denominator);
    }

    /**
     * Calculates the independent intersection of two events.
     *
     * @param a The probability of event A.
     * @param b The probability of event B.
     * @return The probability of the independent intersection of events A and B.
     */
    public double independentIntersection(double a, double b) {
        return a * b;
    }

    /**
     * Calculates the dependent intersection of two probabilities event A, and event B given A.
     *
     * @param a       The probability of event A.
     * @param bGivenA The conditional probability of event B given event A.
     * @return The dependent intersection of event a and event B given A.
     */
    public double dependentIntersection(double a, double bGivenA) {
        return a * bGivenA;
    }

    /**
     * Calculates the exclusive union of two probabilities given the probability of the intersection.
     *
     * @param a     The probability of event A.
     * @param b     The probability of event B.
     * @param aAndB The probability of the intersection of events A and B.
     * @return The probability of the exclusive union.
     */
    public double exclusiveUnion(double a, double b, double aAndB) {
        double union = a + b;
        return union * (1 - aAndB);
    }

    /**
     * Calculates the probability of the union of two events, not exclusive.
     *
     * @param a     The probability of event A.
     * @param b     The probability of event B.
     * @param aAndB The probability of the intersection of events A and B.
     * @return The probability of the not exclusive union.
     */
    public double notExclusiveUnion(double a, double b, double aAndB) {

        return a + b - aAndB;
    }

    /**
     * Determines whether two events are independent or dependent based on given probabilities.
     *
     * @param a     The probability of event A.
     * @param b     The probability of event B.
     * @param aAndB The probability of the intersection of events A and B.
     * @return A string indicating whether the events are independent or dependent.
     */
    public String determineIndependenceOrDependent(double a, double b, double aAndB) {
        if (a * b == aAndB) {
            return "They are independent.";
        } else {
            return "They are dependent.";
        }

    }

    /**
     * Calculates conditional probability P(A|B) given the probability of A and the probability of the intersection P(A and B).
     *
     * @param aAndB The probability of the intersection of events A and B.
     * @param b     The probability of event B.
     * @return The conditional probability P(A|B).
     */
    public double conditionalProbability(double aAndB, double b) {
        // Check inputs in bounds
        checkInputInBound(aAndB, b);

        // Check result is in bounds.
        checkDoubleResultInBound(divide(aAndB, b));

        return divide(aAndB, b);
    }

    /**
     * Calculates the probability of event A given event B using Bayes' Theorem.
     *
     * @param a       The probability of event A.
     * @param b       The probability of event B.
     * @param bGivenA The conditional probability of B given A.
     * @return The probability of A given B using Bayes' Theorem.
     */
    public double bayesTheorem(double a, double b, double bGivenA) {
        // Check inputs in bounds
        checkInputInBound(a, b, bGivenA);

        // Check result is in bounds.
        checkDoubleResultInBound(bGivenA * divide(a, b));

        return bGivenA * divide(a, b);
    }

    /**
     * Calculates the probability of a specific outcome in a binomial distribution.
     *
     * @param p The probability of success in a single trial.
     * @param n The number of trials.
     * @param y The number of successful outcomes desired.
     * @return The probability of getting 'y' successful outcomes in 'n' trials with probability 'p'.
     */
    public double binomialDistribution(double p, int n, int y) {
        //check inputs are non negative
        checkNonNegative(p, n, y);

        // calculate p' known as q
        double q = 1 - p;

        // Calculate the combinations
        int combinations = combinations(n, y).intValue();

        double pExponentY = Math.pow(p, y);
        double qExponentNMinusY = Math.pow(q, n - y);

        // Check result is in bound
        checkDoubleResultInBound(combinations * pExponentY * qExponentNMinusY);

        // Multiply to get Binomial Distribution
        return combinations * pExponentY * qExponentNMinusY;
    }

    /**
     * Calculates the expected value (mean) of a binomial distribution.
     *
     * @param n The number of trials.
     * @param p The probability of success in a single trial.
     * @return The expected value (mean) of the binomial distribution.
     */
    public double binomialExpectedValue(int n, double p) {
        // calculate expected value for binomial distribution
        return n * p;
    }

    /**
     * Calculates the variance of a binomial distribution.
     *
     * @param n The number of trials.
     * @param p The probability of success in a single trial.
     * @return The variance of the binomial distribution.
     */
    public double binomialVariance(int n, double p) {
        // calculate p' known as q
        double q = 1 - p;

        // Check input is non-negative
        checkNonNegative(n, p);

        // Check result is non-negative
        checkNonNegative(n * p * q);

        // calculate variance for binomial distribution
        return n * p * q;
    }

    /**
     * Calculates the probability of a specific outcome in a geometric distribution.
     *
     * @param p The probability of success in a single trial.
     * @param y The number of trials until the first success.
     * @return The probability of getting the first success on the 'y'-th trial.
     */
    public double geometricDistribution(double p, int y) {
        //check inputs are non-negative
        checkNonNegative(p, y);

        // calculate p' known as q
        double q = 1 - p;

        double result = Math.pow(q, y - 1) * p;

        // Check result is in bound
        checkDoubleResultInBound(result);

        // Geometric Distribution Formula
        return result;
    }

    /**
     * Calculates the expected value (mean) of a geometric distribution.
     *
     * @param p The probability of success in a single trial.
     * @return The expected value (mean) of the geometric distribution.
     */
    public double geometricExpectedValue(double p) {
        return divide(1, p);
    }

    /**
     * Calculates the variance of a geometric distribution.
     *
     * @param p The probability of success in a single trial.
     * @return The variance of the geometric distribution.
     */
    public double geometricVariance(double p) {
        // calculate p' known as q
        double q = 1 - p;

        // Check input is non-negative
        checkNonNegative(p);

        // Check result is non-negative
        checkNonNegative(divide(q, Math.pow(p, 2)));

        return divide(q, Math.pow(p, 2));
    }

    /**
     * Calculates the Hypergeometric Distribution given N, n, r, and y.
     *
     * @param r           Available type 1 items.
     * @param y           Selected type 1 items.
     * @param totalPop    The total population you are choosing from (N).
     * @param selectedPop The selected population (n).
     * @return The Hypergeometric Distribution of the given variables.
     */
    public double hypergeometricDistribution(int r, int y, int totalPop, int selectedPop) {
        //Check inputs are non-negative
        checkNonNegative(r, y, totalPop, selectedPop);

        double rChooseY = combinations(r, y).doubleValue();
        double secondNumerator = combinations((totalPop - selectedPop), (selectedPop - y)).doubleValue();
        double totalPopChooseSelectedPop = combinations(totalPop, selectedPop).doubleValue();

        // Check result is in bound
        checkDoubleResultInBound(rChooseY * divide(secondNumerator, totalPopChooseSelectedPop));

        return rChooseY * divide(secondNumerator, totalPopChooseSelectedPop);


    }

    /**
     * Calculates the expected value (mean) of a Hypergeometric Distribution.
     *
     * @param totalPop    The total population you are choosing from (N).
     * @param selectedPop The selected population (n).
     * @param r           Available type 1 items.
     * @return The expected value of the hypergeometric distribution.
     */
    public double hypergeometricDistributionExpectedValue(int selectedPop, int totalPop, int r) {
        int numerator = selectedPop * r;
        return divide(numerator, totalPop);

    }

    /**
     * Calculates the variance of a Hypergeometric Distribution.
     *
     * @param totalPop    The total population you are choosing from (N).
     * @param selectedPop The selected population (n).
     * @param r           Available type 1 items.
     * @return The variance of the hypergeometric distribution.
     */
    public double hypergeometricDistributionVariance(int selectedPop, int totalPop, int r) {
        double term2 = divide(r, totalPop);
        double term3 = divide(totalPop - r, totalPop);
        double term4 = divide(totalPop - selectedPop, totalPop - 1);

        // Check input is non-negative
        checkNonNegative(selectedPop, totalPop, r);

        // Check result is non-negative
        checkNonNegative(selectedPop * term2 * term3 * term4);

        return selectedPop * term2 * term3 * term4;

    }

    /**
     * Calculate the probability of a negative binomial distribution.
     *
     * @param p Probability of success in each trial.
     * @param r Number of successes.
     * @param y Total number of trials.
     * @return Probability of getting 'r' successes in 'y' trials.
     */
    public double negativeBinomialDistribution(double p, int r, int y) {
        // Check inputs are non-negative
        checkNonNegative(p, r, y);

        double q = 1 - p;

        double combination = combinations((y - 1), (r - 1)).doubleValue();

        // Check result is in bounds
        double result = combination * Math.pow(p, r) * Math.pow(q, (y - r));
        checkDoubleResultInBound(result);

        return result;
    }

    /**
     * Calculate the expected value of a negative binomial distribution.
     *
     * @param p Probability of success in each trial.
     * @param r Number of successes.
     * @return Expected number of trials required to achieve 'r' successes.
     */
    public double negativeBinomialExpectedValue(double p, int r) {
        return divide(r, p);
    }

    /**
     * Calculate the variance of a negative binomial distribution.
     *
     * @param p Probability of success in each trial.
     * @param r Number of successes required.
     * @return Variance of the number of trials required to achieve 'r' successes.
     */
    public double negativeBinomialVariance(double p, int r) {
        // Check input is non-negative
        checkNonNegative(p, r);

        // Check result is non-negative
        checkNonNegative(divide((r * (1 - p)), Math.pow(p, 2)));

        return divide((r * (1 - p)), Math.pow(p, 2));
    }

    /**
     * Calculates the Poisson distribution probability for a given lambda and value y.
     *
     * @param lambda The mean rate.
     * @param y      The desired value for which the probability is calculated.
     * @return The Poisson distribution probability for the given lambda and y.
     */
    public double poissonDistribution(double lambda, int y) {

        //Check inputs are non-negative
        checkNonNegative(lambda, y);

        double numerator = Math.pow(lambda, y);
        double denominator = factorial(y).doubleValue();

        double e = Math.E;

        // Check result is in bound.
        checkDoubleResultInBound(divide(numerator, denominator) * Math.pow(e, -lambda));

        return divide(numerator, denominator) * Math.pow(e, -lambda);
    }

    /**
     * Calculate the variance of a Poisson distribution.
     *
     * @param lambda The mean rate of the Poisson distribution.
     * @return The variance of the Poisson distribution.
     */
    public double poissonDistributionVariance(double lambda) {
        // Check result is non-negative
        checkNonNegative(lambda);

        return lambda;
    }

    /**
     * Calculate the expected value of a Poisson distribution.
     *
     * @param lambda The mean rate of the Poisson distribution.
     * @return The expected value of the Poisson distribution.
     */
    public double poissonDistributionExpectedValue(double lambda) {
        // Check result is non-negative
        checkNonNegative(lambda);

        return lambda;
    }

    /**
     * Calculates the percentage of data within a specified range using Chebyshev's theorem.
     *
     * @param upperBound The upper bound of the desired range.
     * @param lowerBound The lower bound of the desired range.
     * @param mean       The mean (average) of the data.
     * @param stdDev     The standard deviation of the data.
     * @return The percentage of data within the specified range.
     * @throws IllegalArgumentException If the range is not symmetrical around the mean or if 'k' is not greater than one.
     */
    public double chebyshevs(double upperBound, double lowerBound, double mean, double stdDev) {
        double meanMinusLower = mean - lowerBound;
        double upperMinusMean = upperBound - mean;

        // Make sure they're symmetrical
        if (meanMinusLower == upperMinusMean) {
            double k = divide(meanMinusLower, stdDev);

            // Check if k is greater than one
            if (k > 1) {
                return 1 - divide(1, Math.pow(k, 2));
            } else {
                throw new IllegalArgumentException("The value of 'k' must be greater than one for Chebyshev's theorem.");
            }
        } else {
            throw new IllegalArgumentException("The provided range is not symmetrical around the mean. Chebyshev's theorem is not applicable.");
        }

    }

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
}
