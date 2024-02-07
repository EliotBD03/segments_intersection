package core;

import java.util.ArrayList;

/**
 * A segment representation with two specific endpoints:
 * - upper point
 * - lower point
 */
public class Segment implements Comparable<Segment>
{
    private Point lowerPoint, upperPoint;
    public final float a,b,c;
    private ArrayList<Point> intersections;

    /**
     * From an array of coordinates representing two endpoints
     * first endpoint : (endpoints[0],endpoints[1])
     * second endpoint : (endpoints[2],endpoints[3])
     * Notice that we cannot have two endpoints with the same coordinates.
     * @param endpoints a four-sized array of float
     * @param name1 the name of the first endpoint
     * @param name2 the name of the second endpoint
     */
    public Segment(float[] endpoints, String name1, String name2)
    {
        Point p = new Point(endpoints[0], endpoints[1], name1);
        Point q = new Point(endpoints[2], endpoints[3], name2);
        switch (p.compareTo(q))
        {
            case -1:
                upperPoint = p;
                lowerPoint = q;
                break;
            case 1:
                upperPoint = q;
                lowerPoint = p;
                break;
            default:
                throw new IllegalArgumentException("cannot have a segment with exactly the same coordinates");
        }
        a = lowerPoint.y - upperPoint.y;
        b = lowerPoint.x - upperPoint.x;
        c = lowerPoint.x * upperPoint.y - upperPoint.x * lowerPoint.y;
    }

    public Point getLowerPoint()
    {
        return lowerPoint;
    }

    public Point getUpperPoint()
    {
        return upperPoint;
    }

    /**
     * Add an intersection to the set of intersections of the segment.
     * @param intersection the intersection to put in.
     */
    public void addIntersection(Point intersection)
    {
        this.intersections.add(intersection);
    }

    /**
     * Override the method from Comparable interface.
     * The order used is the same as for two points.
     * This will be applied on the upper points of the two segments.
     * @param otherSegment the object to be compared.
     * @return -1 if current_segment < other_segment, 0 if equal or -1 otherwise.
     */
    @Override
    public int compareTo(Segment otherSegment)
    {
        return upperPoint.compareTo(otherSegment.upperPoint);
    }

    @Override
    public String toString()
    {
        return lowerPoint +"--"+ upperPoint;
    }

    /**
     * Find an intersection between two segments.
     * The mathematical formula used is the Crammer's formula.
     * @param s1 the first segment for intersection
     * @param s2 the second segment for intersection
     * @return a Point representing the intersection found, null otherwise.
     */
    public static Point findIntersection(Segment s1, Segment s2)
    {
        float denominator = s1.a * s2.b - s2.a * s1.b;
        if(denominator != 0)
        {
            float px = (s1.c * s2.b - s2.c * s1.b) / denominator;
            float py = (s1.a * s2.c - s2.a * s1.c) / denominator;
            return new Point(px, py, "intersection for " + s1 + " and " + s2);
        }
        return null;
    }
}
