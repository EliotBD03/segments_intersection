package ac.umons.be.firstg.segmentintersection.controller.utils;

import ac.umons.be.firstg.segmentintersection.model.Pair;

/**
 * Object range which only contains double.
 * The main goal of this is to provide upper and lower bound
 * for generator object ({@link Generator})
 */
public class Range
{
    private final Pair<Double, Double> lowerBound;
    private final Pair<Double, Double> upperBound;

    /**
     * constructor of range object
     * @param lowerBound array of double from which we won't go under
     * @param upperBound array of double from which we won't go upper
     * Utility for generator only
     */
    public Range(double[] lowerBound, double[] upperBound)
    {
        this.lowerBound = new Pair<>(lowerBound[0], lowerBound[1]);
        this.upperBound = new Pair<>(upperBound[0], upperBound[1]);
    }

    /**
     * getter of lowerBound
     * @return the lower bound attribute
     */
    public Pair<Double, Double> getLowerBound()
    {
        return lowerBound;
    }

    /**
     * getter of the upperBound
     * @return the upper bound attribute
     */
    public Pair<Double, Double> getUpperBound()
    {
        return upperBound;
    }
}
