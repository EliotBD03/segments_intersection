package core;

import java.util.ArrayList;
import java.util.stream.Collectors;
import static core.CDouble.*;

public class PlaneSweep
{
    private StatusQueue statusQueue;

    private   ArrayList<ComparableSegment> lower;
    private   ArrayList<ComparableSegment> inner;

    private   ArrayList<ComparableSegment> upper;

    private Point intersection;


    public PlaneSweep()
    {
        this.statusQueue = new StatusQueue();
        System.out.println("point queue at initialization");
    }

    public void next(PointQueue pointQueue) throws Exception
    {
        this.intersection = handleEventPoint(pointQueue.dequeue(), pointQueue);
    }

    private Point handleEventPoint(Point p, PointQueue pointQueue) throws Exception
    {
        System.out.println("___________________________________________");

        Point intersection = null;
        upper = p.getStartOf()
                .stream()
                .map(ComparableSegment::new)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Point: ");
        System.out.println(p);
        System.out.println("status queue before");
        statusQueue.display();
        lower = new ArrayList<>();
        inner = new ArrayList<>();
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
        {
            System.out.println("\tAdding: " + segment);
            statusQueue.add(segment, p);
            statusQueue.display();

        }
        statusQueue.display();
        if(upperInner.isEmpty())
        {
            System.out.println("what am i doing rihgt now");
            Pair<ComparableSegment, ComparableSegment> neighbours = statusQueue.getNeighbours(p);
            System.out.println("neighbours: " + neighbours);
            if(neighbours.getItem1() != null && neighbours.getItem2() != null)
                findNewEvent(neighbours.getItem1(), neighbours.getItem2(), p, pointQueue);
        }
        else
        {
           Pair<ComparableSegment, ComparableSegment> segmentPair = statusQueue.findLeftmostRightmost(p);
            System.out.println("Segment Pair: ");
           System.out.println(segmentPair);
           ComparableSegment leftSegment = statusQueue.getNeighbours(segmentPair.getItem1()).getItem1();
           ComparableSegment rightSegment = statusQueue.getNeighbours(segmentPair.getItem2()).getItem2();
           System.out.println("Left & Right Segment: ");
           System.out.println(leftSegment);
           System.out.println(rightSegment);
           System.out.println("");
           if(leftSegment != null)
               findNewEvent(leftSegment, segmentPair.getItem1(), p, pointQueue);
           if(rightSegment != null)
               findNewEvent(rightSegment, segmentPair.getItem2(), p, pointQueue);
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
                i = l1.size();
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

    private void findNewEvent(ComparableSegment sl, ComparableSegment sr, Point p, PointQueue pointQueue)
    {
        Point pp = Segment.findIntersection(sl, sr);
        System.out.println("fOUND: " + pp);
        if(pp != null)
            if(lessThan(pp.y, p.y) || (almostEqual(pp.y, p.y) && greaterThan(pp.x, p.x)))
                pointQueue.enqueue(pp);

    }

    public StatusQueue getStatusQueue()
    {
        return statusQueue;
    }

    public ArrayList<ComparableSegment> getInner()
    {
        return inner;
    }

    public ArrayList<ComparableSegment> getLower()
    {
        return lower;
    }

    public ArrayList<ComparableSegment> getUpper()
    {
        return upper;
    }

    public Point getIntersection()
    {
        return intersection;
    }
}
