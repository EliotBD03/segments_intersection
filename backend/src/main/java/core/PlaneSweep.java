package core;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("point queue at initialization");
        pointQueue.display();
    }

    public ArrayList<Point> getIntersections() throws Exception
    {
        this.intersections = new ArrayList<>();
        int index = 0; //TODO
        while(!pointQueue.isEmpty())
        {
            System.out.println("iteration " + (index++)); //TODO remove this if problem solved
            Point p = pointQueue.dequeue();
            Point intersection = handleEventPoint(p);
            if(intersection != null)
                intersections.add(intersection);
            pointQueue.display();

        }
        return intersections;
    }

    private Point handleEventPoint(Point p) throws Exception
    {
        System.out.println("___________________________________________");

        Point intersection = null;
        ArrayList<ComparableSegment> upper = p.getStartOf()
                .stream()
                .map(ComparableSegment::new)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Point: ");
        System.out.println(p);
        System.out.println("status queue before");
        statusQueue.display();
        ArrayList<ComparableSegment> lower = new ArrayList<>();
        ArrayList<ComparableSegment> inner = new ArrayList<>();
        statusQueue.findSegments(p,lower, inner);
        System.out.println("lower");
        for(Segment segment : lower)
            System.out.println(segment);
        System.out.println("inner");
        for(Segment segment: inner)
            System.out.println(segment);
        System.out.println("upper");
        for(Segment segment : upper)
            System.out.println(segment);
        ArrayList<ComparableSegment> lowerInner = union(lower, inner);
        ArrayList<ComparableSegment> upperInner = union(upper, inner);
        ArrayList<ComparableSegment> upperLowerInner = union(lowerInner, upper);
        System.out.println("upperlowerinner");
        for(Segment segment : upperLowerInner)
            System.out.println(segment);
        if(upperLowerInner.size() > 1)
        {
            System.out.println("intersection added : " + p);
            intersection = p;
            intersection.addIntersection(upperLowerInner);
        }
        System.out.println("---Removed lowerInner");
        for(ComparableSegment segment : lowerInner)
            statusQueue.removeSegment(segment);
        statusQueue.display();
        System.out.println("---Added UpperInner");
        System.out.println(upperInner);
        for(ComparableSegment segment : upperInner)
            statusQueue.add(segment, p);
        statusQueue.display();
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
        System.out.println("status queue after");
        statusQueue.display();
        return intersection;
    }

    public static ArrayList<ComparableSegment> union(ArrayList<ComparableSegment> l1, ArrayList<ComparableSegment> l2)
    {
        ArrayList<ComparableSegment> result = new ArrayList<>();
        int j = 0;
        int i = 0;
        if (l1.isEmpty())
            return l2;
        if (l2.isEmpty())
            return l1;
        while(i < l1.size() || j < l2.size())
        {
            if(i >= l1.size())
            {
                result.addAll(l2.subList(j, l2.size()));
                j = l2.size();
            }
            else if(j >= l2.size())
            {
                result.addAll(l1.subList(i, l1.size()));
                i = l2.size();
            }
            else
            {
                if (l1.get(i).getUpperPoint().compareTo(l2.get(j).getUpperPoint()) <= 0)
                {
                    result.add(l1.get(i));
                    i++;
                }
                else
                {
                    result.add(l2.get(j));
                    j++;
                }
            }
        }
        return result;
    }

    private void findNewEvent(ComparableSegment sl, ComparableSegment sr, Point p)
    {
        Point pp = Segment.findIntersection(sl, sr);
        System.out.println("fOUND: " + pp);
        if(pp != null)
            if(pp.y < p.y || (CDouble.almostEqual(pp.y, p.y) && pp.x > p.x))
                pointQueue.enqueue(pp);
    }
}
