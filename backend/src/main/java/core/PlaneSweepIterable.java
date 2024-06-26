package core;

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
                    return planeSweep;
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
