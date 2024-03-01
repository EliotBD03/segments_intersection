package core;

/**
 * Child class of the segment class that implements the method CompareTo to
 * perform comparisons inside the status queue T (cf. class T)
 */
public class ComparableSegment extends Segment implements Comparable<ComparableSegment>
{
    private double currentYAxis;

    public ComparableSegment(Segment segment, double currentYAxis)
    {
        super(segment);
        this.currentYAxis = currentYAxis;
    }

    @Override
    public int compareTo(ComparableSegment otherSegment)
    {
        Point ref = new Point(0, currentYAxis);
        return Double.compare(getPointOnXAxis(ref, otherSegment).x,getPointOnXAxis(ref, this).x);
    }
}
