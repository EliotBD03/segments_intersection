package generator;

import core.Pair;

/**
 * Object range which only contains double.
 * The main goal of this is to provide upper and lower bound
 * for generator object ({@link Generator})
 */
public class Range
{
    private final Pair<Double, Double> lowerBound;
    private final Pair<Double, Double> upperBound;

    public Range(double[] lowerBound, double[] upperBound)
    {
        this.lowerBound = new Pair<>(lowerBound[0], lowerBound[1]);
        this.upperBound = new Pair<>(upperBound[0], upperBound[1]);
    }

    public Pair<Double, Double> getLowerBound()
    {
        return lowerBound;
    }

    public Pair<Double, Double> getUpperBound()
    {
        return upperBound;
    }
}
