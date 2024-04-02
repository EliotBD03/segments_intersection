package ac.umons.be.firstg.segmentintersection.controller;

import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.PointQueue;
import ac.umons.be.firstg.segmentintersection.model.Segment;

import java.util.ArrayList;
import java.util.Iterator;

public class PlaneSweepIterable implements Iterable<PlaneSweep>
{
    private PointQueue pointQueue;
    private PlaneSweep planeSweep;

    /**
     * iterator for the plane sweep algorithm
     * @param segments the segments to get intersections with
     * @throws Exception if no segment provided inside the arraylist
     */
    public PlaneSweepIterable(ArrayList<Segment> segments) throws Exception
    {
        this.pointQueue = PointQueue.initQ(segments);
        this.planeSweep = new PlaneSweep();
        System.out.println("point queue at initialization");
        pointQueue.display();
    }


    @Override
    public Iterator<PlaneSweep> iterator()
    {
        return new Iterator<PlaneSweep>()
        {
            /**
             * condition to iterate on the iterator
             * @return True while the pointqueue is not empty, False otherwise
             */
            @Override
            public boolean hasNext()
            {
                return !pointQueue.isEmpty();
            }

            /**
             * used to show an iteration of the plane sweep algorithm
             * @return the current step inside the plane sweep algorithm
             */
            @Override
            public PlaneSweep next()
            {
                try
                {
                    planeSweep.next(pointQueue);
                } catch (Exception e)
                {
                    throw new RuntimeException(e);
                }

                return planeSweep;

            }
        };
    }

    public ArrayList<Point> getIntersections()
    {
        ArrayList<Point> result = new ArrayList<>();
        for(PlaneSweep planeSweep : this)
        {
            if(planeSweep.getIntersection() != null)
                result.add(planeSweep.getIntersection());
        }
        return result;
    }

    public PlaneSweep getPlaneSweep()
    {
        return planeSweep;
    }
}
