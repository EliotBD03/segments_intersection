package ac.umons.be.firstg.segmentintersection.controller;

import ac.umons.be.firstg.segmentintersection.model.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import static ac.umons.be.firstg.segmentintersection.controller.utils.CDouble.*;


public class PlaneSweep
{
    private StatusQueue statusQueue;

    private   ArrayList<ComparableSegment> lower;
    private   ArrayList<ComparableSegment> inner;
    private   ArrayList<ComparableSegment> upper;
    private   Point currentPoint;

    private Point intersection;

    /**
     * Constructor of planeSweep Algo
     * Initially instance the statusQueue
     */
    public PlaneSweep()
    {
        this.statusQueue = new StatusQueue();
    }

    /***
     * apply the sub-algorithm on the next event point
     * @param pointQueue the point queue
     * @throws Exception if no more event point in the event queue
     */
    public void next(PointQueue pointQueue) throws Exception
    {
        currentPoint = pointQueue.dequeue();
        this.intersection = handleEventPoint(currentPoint, pointQueue);
    }

    /**
     * Make treatment on the current event point
     * to find potential intersections below it
     * @param p the event point
     * @param pointQueue the point queue (should be the same as the point queue which provided the event point)
     * @return an intersection if there was one, null otherwise
     * @throws Exception if illegal operations on data structures
     */

    private Point handleEventPoint(Point p, PointQueue pointQueue) throws Exception
    {

        Point intersection = null;
        upper = p.getStartOf()
                .stream()
                .map(ComparableSegment::new)
                .collect(Collectors.toCollection(ArrayList::new));
        lower = new ArrayList<>();
        inner = new ArrayList<>();
        statusQueue.findSegments(p,lower, inner);
        ArrayList<ComparableSegment> lowerInner = union(lower, inner);
        ArrayList<ComparableSegment> upperInner = union(upper, inner);
        ArrayList<ComparableSegment> upperLowerInner = union(lowerInner, upper);
        if(upperLowerInner.size() > 1)
        {
            intersection = p;
            intersection.addIntersection(upperLowerInner);
        }
        for(ComparableSegment segment : lowerInner)
            statusQueue.removeSegment(segment);
        for(ComparableSegment segment : upperInner)
            statusQueue.add(segment, p);

        if(upperInner.isEmpty())
        {
            Pair<ComparableSegment, ComparableSegment> neighbours = statusQueue.getNeighbours(p);
            if(neighbours.getItem1() != null && neighbours.getItem2() != null)
                findNewEvent(neighbours.getItem1(), neighbours.getItem2(), p, pointQueue);
        }
        else
        {
            Pair<ComparableSegment, ComparableSegment> segmentPair = new Pair<>(upperInner.getLast(), upperInner.getFirst());

            ComparableSegment leftSegment;
            ComparableSegment rightSegment;
            // If both items are the same we can only do getNeighbours once
            if(segmentPair.getItem1().equals(segmentPair.getItem2()))
            {
                Pair<ComparableSegment, ComparableSegment> segments = statusQueue.getNeighbours(segmentPair.getItem1());
                leftSegment = segments.getItem1();
                rightSegment = segments.getItem2();
            }
           else
           {
               leftSegment = statusQueue.getNeighbours(segmentPair.getItem1()).getItem1();
               rightSegment = statusQueue.getNeighbours(segmentPair.getItem2()).getItem2();
           }
           if(leftSegment != null)
               findNewEvent(leftSegment, segmentPair.getItem1(), p, pointQueue);
           if(rightSegment != null)
               findNewEvent(rightSegment, segmentPair.getItem2(), p, pointQueue);
        }
        return intersection;
    }

    /**
     * From two sorted arraylist, produce one unique sorted arraylist which is the fusion of the two
     * @param l1 the first sorted arraylist
     * @param l2 the second sorted arraylist
     * @return one arraylist which is a fusion of the two parameters
     */

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

    /**
     * Test if p is the intersection between both segments.
     * If that's the case then it will be added to the point queue.
     * @param sl the left segment (accordingly to the sweep line)
     * @param sr the right segment (accordingly to the sweep line)
     * @param p the event point supposed to be an intersection
     * @param pointQueue the point queue (should be the same as the point queue which provided the event point)
     */

    private void findNewEvent(ComparableSegment sl, ComparableSegment sr, Point p, PointQueue pointQueue)
    {
        Point pp = Segment.findIntersection(sl, sr);
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

    public Point getCurrentPoint()
    {
        return currentPoint;
    }
}
