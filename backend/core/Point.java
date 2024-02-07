package core;

import java.util.ArrayList;

/**
 * Class to represent a point as a couple defined on R^2
 */
public class Point implements Comparable<Point>
{
    public final float x, y;
    private ArrayList<Segment> startOf;
    public final String name;

    /**
     * Constructor of the Point
     * @param x the x coordinate
     * @param y the y coordinate
     * @param name the name of the point
     */
    public Point(float x, float y, String name)
    {
        this.x = x;
        this.y = y;
        this.name = name;
        this.startOf = new ArrayList<>();
    }

    /**
     * Add a segment to the current point which an upper point of it.
     * @param newSegment the new segment to insert.
     */
    public void addSegment(Segment newSegment)
    {
        startOf.add(newSegment);
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
        if(y == otherPoint.y && x == otherPoint.x)
        {
            startOf.addAll(otherPoint.startOf);
            return 0;
        }
        return y > otherPoint.y || (y == otherPoint.y && x < otherPoint.x) ? -1 : 1;
    }

    @Override
    public String toString() {
        return "{"+name+" : ("+ x + "," + y + ")}";
    }
}
