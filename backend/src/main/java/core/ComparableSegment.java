package core;

import java.util.Objects;

/**
 * Child class of the segment class that implements the method CompareTo to
 * perform comparisons inside a {@link StatusQueue}
 */
public class ComparableSegment extends Segment implements Comparable<ComparableSegment>
{
    /**
     * The currentPoint used as a reference when using {@link #compareTo(ComparableSegment)}
     */

    /**
     * The default constructor for the ComparableSegment Class
     * @param segment       The segment to cast to a comparable Segment
     */
    public ComparableSegment(Segment segment)
    {
        super(segment);
    }

    /**
     * Compares two segments by comparing the x values of the two points obtained with {@link Segment#getPointOnXAxis(Point, Segment)} for this segment
     * and the otherSegment
     * @param otherSegment the object to be compared.
     * @return We compare both x values from two points that have the same y value. If they intersect, we return 0
     * if and only if both segments have the same cartesian equation, -1 otherwise.
     * If not we return the result of the comparison mentioned earlier.
    */
    @Override
    public int compareTo(ComparableSegment otherSegment)
    {
        int res = compareToPoint(otherSegment, new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        return res == 0 ? (this.equals(otherSegment) ? 0: -1) : res;
    }

    /**
     * Compares two segments by comparing the x values of the two points obtained with {@link #getPointOnXAxis(Point, Segment)} for this segment
     * and the otherSegment, based on the reference point provided
     * @param otherSegment  The object to be compared.
     * @param ref           The reference point.
     * @return              The comparison between the two obtained x values of the points, obtained using {@link Double#compare(double, double)}}.
     */
    public int compareToPoint(ComparableSegment otherSegment, Point ref)
    {
        Point p1 = Segment.getPointOnXAxis(ref, this);
        Point p2 = Segment.getPointOnXAxis(ref, otherSegment);
        if (p1 == null || p2 == null)
            return 1;
        return Double.compare(p1.x, p2.x);
    }

    /**
     * Checks if this segment is the same as another
     * @param o The other object to check
     * @return  the result of {@link Segment#equals(Object)} with o.
     */
    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    // Obliged to override since we override the equals method
    @Override
    public int hashCode()
    {
        return Objects.hash(this);
    }

}
