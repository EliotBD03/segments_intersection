package ac.umons.be.firstg.segmentintersection.controller.utils;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import ac.umons.be.firstg.segmentintersection.model.Pair;
import ac.umons.be.firstg.segmentintersection.model.Segment;

/**
 * used to generate segments using randomness
 * Possibility to add a range by using object {@link Range}
 */
public class Generator
{
    private final int nbSegments;
    private Range range;

    /**
     * object Generator
     * @param nb the number of segments to generate
     * If no range object provided, default lower and upper bound : (0,0), (100, 100)
     */
    public Generator(int nb)
    {
        nbSegments = nb;
        range = new Range(new double[]{0,0}, new double[]{100, 100});
    }

    public void setRange(Range range)
    {
        this.range = range;
    }

    /**
     * Generate an arraylist of segments whose endpoint coordinates fit inside
     * a range
     * @return ArrayList of segments
     */
    public ArrayList<Segment> generate()
    {
        ArrayList<Segment> segments = new ArrayList<>();
        for(int i = 0; i < nbSegments; i++)
        {
            segments.add(new Segment(generatePoints(), "s_" + i));
        }
        return segments;
    }

    public Range getRange()
    {
        return range;
    }

    private double[] generatePoints()
    {
        double[] res = new double[4];
        for(int i = 0; i < res.length; i++)
        {
            if(i % 2 == 0)
                res[i] = ThreadLocalRandom.current().nextDouble(
                        range.getLowerBound().getItem1(),
                        range.getUpperBound().getItem1());
            else
                res[i] = ThreadLocalRandom.current().nextDouble(
                        range.getLowerBound().getItem2(),
                        range.getUpperBound().getItem2());
        }
        return res;
    }
}
