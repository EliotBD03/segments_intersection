package core;

import java.util.ArrayList;

public class PlaneSweep
{
    private PointQueue pointQueue;
    private ArrayList<Point> intersections;
    private StatusQueue statusQueue;
    private ArrayList<Segment> segments;

    public PlaneSweep(ArrayList<Segment> segments) throws Exception
    {
        this.segments = segments;
        this.statusQueue = new StatusQueue();
        this.pointQueue = PointQueue.initQ(segments);
        this.intersections = new ArrayList<>();
        while(!pointQueue.isEmpty())
        {
            Point p = pointQueue.dequeue();
            Point intersection = handleEventPoint(p);
            if(intersection != null)
                intersections.add(intersection);
        }
    }

    public ArrayList<Point> getIntersections()
    {
        return intersections;
    }

    public Point handleEventPoint(Point p)
    {
        Point intersection = null;
        ArrayList<Segment> upper = p.getStartOf();
        ArrayList<Segment> lower = new ArrayList<>();
        ArrayList<Segment> inner = new ArrayList<>();
       //TODO statusQueue.findSegments
        return null;
    }
}
