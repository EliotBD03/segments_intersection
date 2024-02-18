package core;

import java.util.ArrayList;

/**
 * An AVL used to store endpoints of segments
 * It has a queue-like behavior.
 * Furthermore, it has no special constructor. Only a generator.
 */
public class Q extends AVL<Point>
{

    private Point head;

    /**
     * private contructor to init the head to infinity
     */
    private Q()
    {
        head = new Point(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /**
     * Update the Q's head
     */
    private void lookForNewHead()
    {
        head = root.lookForMinimum().getData();
    }

    /**
     * Q generator which is initialed with a set of endpoints from segments.
     * @param segments an arraylist of segments
     * @return Q object with the corresponding endpoints inside.
     */
    public static Q initQ(ArrayList<Segment> segments)
    {
        Q q = new Q();
        for(Segment segment : segments)
        {
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
        if(head.compareTo(point) > 0)
            head = point;
        insert(point);
    }

    /**
     * Basic dequeue method.
     * This will remove the root node from the tree
     * @throws Exception if the root does not exist. In other words, the tree is empty.
     */
    public void dequeue() throws Exception
    {
        remove(head);
        lookForNewHead();
    }
}
