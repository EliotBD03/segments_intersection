package be.ac.umons.firstg.segmentintersector.Temp;

public class SegmentTMP
{
    private String name;
    private Point point1;
    private Point point2;

    public SegmentTMP(Point point1, Point point2)
    {
        this.point1 = point1;
        this.point2 = point2;
    }

    /**
     * Small contructor for a segment that should ONLY be used for testing !
     * @param name The name of the segment
     */
    public SegmentTMP(String name)
    {
        this.name = name;
    }

    public Point getPoint1()
    {
        return point1;
    }

    public Point getPoint2()
    {
        return point2;
    }

    public String getName()
    {
        return name;
    }
}
