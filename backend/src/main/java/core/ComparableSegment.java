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
    public final Point currentPoint;

    /**
     * The default constructor for the ComparableSegment Class
     * @param segment       The segment to cast to a comparable Segment
     * @param currentPoint  The currentPoint used as a reference when using {@link #compareTo(ComparableSegment)}
     */
    public ComparableSegment(Segment segment, Point currentPoint)
    {
        super(segment);
        this.currentPoint = currentPoint;
    }

    /**
     * Compares two segments by comparing the x values of the two points obtained with {@link Segment#getPointOnXAxis(Point, Segment)} for this segment
     * and the otherSegment, based on the {@link #currentPoint }
     * @param otherSegment the object to be compared.
     * @return We compare both x values from two points that have the same y value. If they intersect, we return 0
     * if and only if both segments have the same cartesian equation, -1 otherwise.
     * If not we return the result of the comparison mentioned earlier.
     */
    @Override
    public int compareTo(ComparableSegment otherSegment)
    {
        Point p1 = Segment.getPointOnXAxis(otherSegment.currentPoint, this);
        Point p2 = Segment.getPointOnXAxis(otherSegment.currentPoint, otherSegment);
        //System.out.println("Compare: " + this +  " - and : " + otherSegment + " || Using: " + otherSegment.currentPoint);
        //System.out.println("P1: " + p1 + "  | P2: " + p2);
        //System.out.println(Double.compare(p1.x, p2.x) == 0 ? (this.equals(otherSegment) ? 0: -1) : Double.compare(p1.x, p2.x));
        int res = Double.compare(p1.x, p2.x);
        return res == 0 ? (this.equals(otherSegment) ? 0: -1) : res;
    }

    /**
     * Compares two segments by comparing the x values of the two points obtained with {@link #getPointOnXAxis(Point, Segment)} for this segment
     * and the otherSegment, based on the reference point provided
     * @param otherSegment  The object to be compared.
     * @param ref           The reference point.
     * @return              The comparison between the two obtained x values of the points.
     */
    public int compareToPoint(ComparableSegment otherSegment, Point ref)
    {
        Point p1 = Segment.getPointOnXAxis(ref, this);
        Point p2 = Segment.getPointOnXAxis(ref, otherSegment);
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
        return Objects.hash(currentPoint);
    }
}
