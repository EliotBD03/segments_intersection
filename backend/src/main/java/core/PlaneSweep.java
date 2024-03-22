package core;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public Point handleEventPoint(Point p) throws Exception
    {
        Point intersection = null;
        ArrayList<ComparableSegment> upper = p.getStartOf()
                .stream()
                .map(ComparableSegment::new)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<ComparableSegment> lower = new ArrayList<>();
        ArrayList<ComparableSegment> inner = new ArrayList<>();
        statusQueue.findSegments(p,lower, inner);
        ArrayList<ComparableSegment> lowerInner = union(lower, inner);
        ArrayList<ComparableSegment> upperInner = union(lower, upper);
        ArrayList<ComparableSegment> upperLowerInner = union(lowerInner, upper);
        if(upperLowerInner.size() > 1)
        {
            intersection = p;
            intersection.addIntersection(upperLowerInner);
        }
        for(ComparableSegment segment : lowerInner)
            statusQueue.remove(segment);
        for(ComparableSegment segment : upperInner)
            statusQueue.add(segment, p);
        if(upperInner.isEmpty())
        {
            Pair<ComparableSegment, ComparableSegment> neighbours = statusQueue.getNeighbours(p);
            if(neighbours.getItem1() != null && neighbours.getItem2() != null)
                findNewEvent(neighbours.getItem1(), neighbours.getItem2(), p);
        }
        else
        {
           Pair<ComparableSegment, ComparableSegment> segmentPair = statusQueue.findLeftmostRightmost(p);
           ComparableSegment leftSegment = statusQueue.getNeighbours(segmentPair.getItem1()).getItem1();
           ComparableSegment rightSegment = statusQueue.getNeighbours(segmentPair.getItem2()).getItem2();
           if(leftSegment != null)
               findNewEvent(leftSegment, segmentPair.getItem1(), p);
           if(rightSegment != null)
               findNewEvent(rightSegment, segmentPair.getItem2(), p);
        }
        return intersection;
    }

    public static ArrayList<ComparableSegment> union(ArrayList<ComparableSegment> l1, ArrayList<ComparableSegment> l2)
    {
        ArrayList<ComparableSegment> result = new ArrayList<>();
        int j = 0;
        int i = 0;
        while(i < l1.size() || j < l2.size())
        {
            if(i >= l1.size())
            {

                i = l2.size();
                result.addAll(l2.subList(i, i - 1));

            }
            else if(j >= l2.size())
            {
                j = l2.size();
                result.addAll(l1.subList(j, l1.size() - 1));

            }
            else
            {
                if (l1.get(i).getUpperPoint().compareTo(l2.get(j).getUpperPoint()) <= 0) {
                    result.add(l1.get(i));
                    i++;
                } else {
                    result.add(l2.get(i));
                    j++;
                }
            }
        }
        return result;
    }

    public void findNewEvent(ComparableSegment sl, ComparableSegment sr, Point p)
    {
        Point pp = Segment.findIntersection(sl, sr);
        if(pp != null)
            if(pp.compareTo(p) <= 0)
                pointQueue.enqueue(p);
    }
}
