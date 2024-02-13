package core;

/**
 * Segment used in T that just implement the compareTo method.
 */
public class ComparableSegment extends Segment implements Comparable<ComparableSegment>
{
    private float currentYAxis;
    private int id;

    public ComparableSegment(Segment segment, float currentYAxis, int id)
    {
        super(segment);
        this.currentYAxis = currentYAxis;
        this.id = id;
    }

    @Override
    public int compareTo(ComparableSegment otherSegment)
    {
        Point ref = new Point(0, currentYAxis, "HUH");
        if(getPointOnXAxis(ref, this).x <= getPointOnXAxis(ref, otherSegment).x)
            return -1;
        return 1;
    }

    public String toString()
    {
        return "s"+id+":"+super.toString();
    }
}
