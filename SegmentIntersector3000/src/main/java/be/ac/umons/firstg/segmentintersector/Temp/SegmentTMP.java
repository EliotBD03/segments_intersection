package be.ac.umons.firstg.segmentintersector.Temp;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentTMP that = (SegmentTMP) o;
        return Objects.equals(point1, that.point1) && Objects.equals(point2, that.point2);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(point1, point2);
    }

    @Override
    public String toString()
    {
        return "SegmentTMP{" +
                "point1=" + point1 +
                ", point2=" + point2 +
                '}';
    }
}
