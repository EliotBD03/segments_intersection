package core;


/**
 * class which represents a status queue.
 */
public class T extends AVL<ComparableSegment>
{
    private double currentYAxis;

    /**
     * constructor of the tree T which a specified y-axis to perform
     * the comparisons.
     * @param currentYAxis the value of the y-axis of the tree.
     */
    public T(double currentYAxis)
    {
        super();
        this.currentYAxis = currentYAxis;
    }

    /**
     * Add a segment inside the tree.
     * @param segment the segment to insert
     */
    public void add(Segment segment)
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis);
        this.insert(comparableSegment);
    }

    /**
     * Remove a segment from the tree.
     * @param segment the segment to remove from the tree.
     * @throws Exception if the segment does not exist.
     */
    public void remove(Segment segment) throws Exception
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis);
        super.remove(comparableSegment);
        super.remove(comparableSegment);
    }

    /**
     * insert a Node in segment in accordance with the particular behavior of the tree.
     * Another basis case is implemented -> the node is a leaf.
     * @param current the current node we are in
     * @param nodeToInsert the node to be inserted
     * @return the tree modified
     */
    private Node<ComparableSegment> insert(Node<ComparableSegment> current, Node<ComparableSegment> nodeToInsert)
    {
        if(current == null)
        {
            nodeToInsert.setLeft(new Node<ComparableSegment>(nodeToInsert));
            return nodeToInsert;
        }
        else if(current.getLeft() == null && current.getRight() == null)
        {
            Node<ComparableSegment> newNode = new Node<ComparableSegment>(nodeToInsert);
            newNode.setLeft(nodeToInsert);
            newNode.setRight(current);
            return newNode;
        }
        else if(nodeToInsert.compareTo(current) <= 0)
        {
            current.setLeft(insert(current.getLeft(), nodeToInsert));
            current.updateHeight();
            current.balance();

        }
        else
        {
            current.setRight(insert(current.getRight(), nodeToInsert));
            current.updateHeight();
            current.balance();
        }
        return current;
    }

    /**
     * Insert a comparable segment inside T
     * @param data the data to be inserted inside the tree.
     */
    @Override
    protected void insert(ComparableSegment data) {
       super.root = this.insert(super.root, new Node<ComparableSegment>(data));
    }
}
