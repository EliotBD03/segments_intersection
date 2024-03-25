package core;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A segment representation with two specific endpoints:
 * - upper point
 * - lower point
 */
public class Segment
{
    private Point lowerPoint, upperPoint;
    public final double a,b,c;

    public final String id;

    /**
     * From an array of coordinates representing two endpoints
     * first endpoint : (endpoints[0],endpoints[1])
     * second endpoint : (endpoints[2],endpoints[3])
     * Notice that we cannot have two endpoints with the same coordinates.
     * @param endpoints a four-sized array of double
     * @param id the name attributed to the segment.
     */
    public Segment(double[] endpoints, String id)
    {
        Point p = new Point(endpoints[0], endpoints[1]);
        Point q = new Point(endpoints[2], endpoints[3]);
        switch (p.compareTo(q))
        {
            case 1:
                upperPoint = q;
                lowerPoint = p;
                break;
            case -1:
                upperPoint = p;
                lowerPoint = q;
                break;
            default:
                throw new IllegalArgumentException("cannot have a segment with exactly the same coordinates");
        }
        this.a = upperPoint.y - lowerPoint.y;
        this.b = - upperPoint.x + lowerPoint.x;

        c = -(a * upperPoint.x) - (b * upperPoint.y);
        this.id = id;
    }
    /**
     * From two endpoints
     * Notice that we cannot have two endpoints with the same coordinates.
     * @param endpoint1 the first endpoint
     * @param endpoint2 the second endpoint
     * @param id the name attributed to the segment.
     */
    public Segment(Point endpoint1, Point endpoint2, String id)
    {
        this(new double[] {endpoint1.x,endpoint1.y, endpoint2.x, endpoint2.y}, id);
    }

    protected Segment(Segment segment)
    {
        this.a = segment.a;
        this.b = segment.b;
        this.c = segment.c;
        this.upperPoint = segment.upperPoint;
        this.lowerPoint = segment.lowerPoint;
        this.id = segment.id;
    }


    public Point getLowerPoint()
    {
        return lowerPoint;
    }

    public Point getUpperPoint()
    {
        return upperPoint;
    }

    @Override
    public String toString()
    {
        //return "{" + id + ":" + lowerPoint +"--"+ upperPoint + "}";
        return id;
    }

    public String getCartesianAsString()
    {
        return "(" + a + "x + " + b + "y = " + c + ")";
    }

    /**
     * Find an intersection between two segments.
     * The mathematical formula used is the Crammer's formula.
     * @param s1 the first segment for intersection
     * @param s2 the second segment for intersection
     * @return a Point representing the intersection found, null otherwise.
     */
    public static Point findIntersection(Segment s1, Segment s2) //TODO used later
    {
        double denominator = s1.a * s2.b - s2.a * s1.b;
        if(denominator != 0)
        {
            double px = (s2.c * s1.b - s1.c * s2.b) / denominator;
            double py = (s2.a * s1.c - s1.a * s2.c) / denominator;
            // We also need to check if the point is in the boundaries of both segments
            Point inter = new Point(px, py);
            return hasPoint(s2, inter) && hasPoint(s1, inter) ? inter: null;
        }
        return null;
    }

    public static boolean hasPoint(Segment segment, Point p)
    {
        Point p2 = getPointOnXAxis(p, segment);
        // Round 5 digits behind
        return p2 != null && Double.compare(p.x, Math.round(p2.x * 1.E5)/1.E5) == 0;
    }

    /**
     * Find a point which belongs to the segment with the same
     * y-coordinates as p.
     * @param p the point with the y-coordinate targeted.
     * @param segment a non-horizontal segment.
     * @return a point from the segment if it's not horizontal. Otherwise, the closest
     * point from p.
     */
    public static Point getPointOnXAxis(Point p, Segment segment)
    {
        // Check if the segment actually contains this point
        if (p.y > segment.upperPoint.y || p.y < segment.lowerPoint.y)
            return null;

        if(segment.a == 0)
        {
            if(p.x >= segment.upperPoint.x && p.x <= segment.lowerPoint.x)
                return p;
            if(segment.upperPoint.x > p.x)
                return segment.upperPoint;
            return segment.lowerPoint;
        }
        else
        {
            double x = (-segment.c - (segment.b * p.y))/ segment.a;
            return new Point(x, p.y);
        }
    }

    /**
     * Checks if the segment has the same cartesian equation than this one
     * @param o The other object to check
     * @return  true only if the object to check is the same segment
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass() && getClass() != o.getClass().getSuperclass())) return false;
        Segment segment = (Segment) o;
        return Double.compare(a, segment.a) == 0 && Double.compare(b, segment.b) == 0 && Double.compare(c, segment.c) == 0;
    }

    // Obliged to override hashcode if we override equals.
    @Override
    public int hashCode()
    {
        return Objects.hash(a, b, c);
    }
}
