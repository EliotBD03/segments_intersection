package core;

import java.util.ArrayList;

/**
 * An AVL used to store endpoints of segments
 * It has a queue-like behavior.
 * Furthermore, it has no special constructor. Only a generator.
 */
public class PointQueue extends AVL<Point>
{

    private Point head;

    /**
     * private contructor to init the head to infinity
     */
    private PointQueue()
    {
        head = new Point(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /**
     * Update the Q's head
     */
    private void lookForNewHead()
    {
        head = root == null ? null : root.lookForMinimum().getData();
    }

    /**
     * Q generator which is initialed with a set of endpoints from segments.
     * @param segments an arraylist of segments
     * @return Q object with the corresponding endpoints inside.
     */
    public static PointQueue initQ(ArrayList<Segment> segments) throws Exception
    {
        if(segments.isEmpty())
            throw new Exception("no segment provided");
        PointQueue q = new PointQueue();
        for(Segment segment : segments)
        {
            segment.getUpperPoint().addSegment(segment);
            q.insert(segment.getUpperPoint());
            q.insert(segment.getLowerPoint());
        }
        return q;
    }

    /**
     * Basic enqueue method.
     * This will put on the top(root) of the tree a certain given point.
     * @param point the point to enqueue.
     */
    public void enqueue(Point point)
    {
        insert(point);
    }

    /**
     * Basic dequeue method.
     * This will remove the minimum node (lower left)@ from the tree
     * @return the removed point
     * @throws Exception if the root does not exist. In other words, the tree is empty.
     */
    public Point dequeue() throws Exception
    {
        Pair<Node<Point>, Point> res = removeMin(root);
        root = res.getItem1();
        return res.getItem2();
    }

    /**
     * See if the queue is empty or not by looking at
     * the root
     * @return true if that's the case, false otherwise
     */
    public boolean isEmpty()
    {
        return root == null;
    }
}
