package ac.umons.be.firstg.segmentintersection.model;

import java.util.ArrayList;
import java.util.Objects;
import static ac.umons.be.firstg.segmentintersection.controller.utils.CDouble.*;


/**
 * Class to represent a point as a couple defined on R^2
 */
public class Point implements Comparable<Point>
{
    public final double x, y;
    private ArrayList<Segment> startOf;

    private ArrayList<Segment> intersections;

    /**
     * Constructor of the Point
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y)
    {
        // Why are we doing this ? Well, in some cases java considers -0 different from 0 when
        // performing a double.compareTo (I am going insane)
        this.x = x == -0 ? 0: x;
        this.y = y == -0 ? 0: y;
        this.startOf = new ArrayList<>();
        this.intersections = new ArrayList<>();
    }

    /**
     * Add a segment to the current point which an upper point of it.
     * @param newSegment the new segment to insert.
     */
    public void addSegment(Segment newSegment)
    {
        if (newSegment.getUpperPoint().equals(this)  && !startOf.contains(newSegment))
            startOf.add(newSegment);
    }

    public ArrayList<Segment> getStartOf()
    {
        return this.startOf;
    }

    /**
     * Compare two points with the following criteria : p < q iff p.y > q.y or (p.y = q.y and p.x < q.x)
     * where p is the point of the current instance record and q the argument.
     * @param otherPoint the object q to be compared with p
     * @return -1 if p < q, 1 otherwise (notice this is a strict order)
     */
    @Override
    public int compareTo(Point otherPoint)
    {
        if(this.equals(otherPoint))
        {
            for(Segment segment : this.getStartOf())
                otherPoint.addSegment(segment);

            return 0;
        }
        return greaterThan(y, otherPoint.y) || (almostEqual(y, otherPoint.y) && lessThan(x, otherPoint.x)) ? -1 : 1;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return  almostEqual(x, point.x) && almostEqual(y, point.y);
    }



    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "("+ x + "," + y + ")";
    }

    /**
     * Add segments to the intersections set
     * @param segments the segments that intersects each other on the point
     */
    public void addIntersection(ArrayList<ComparableSegment> segments)
    {
        this.intersections.addAll(segments);
    }

    public ArrayList<Segment> getIntersections()
    {
        return intersections;
    }
}
