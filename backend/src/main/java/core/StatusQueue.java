package core;


/**
 * class which represents a status queue.
 */
public class StatusQueue extends AVL<ComparableSegment>
{

    /**
     * Add a segment inside the tree.
     * @param segment the segment to insert
     * @param currP the current point representing the status of the StatusQueue
     */
    public void add(Segment segment, Point currP)
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currP);
        this.insert(comparableSegment, currP);
    }

    /**
     * Removes both nodes containing the desired segment if it exist.
     * @param segment           The segment to remove both nodes from this tree
     * @param currPoint         The reference point used to navigate the tree
     * @throws Exception        If the present is not present
     */
    public void remove(Segment segment, Point currPoint) throws Exception
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currPoint);
        // Remove the leaf segment first
        root = removeLeaf(root, comparableSegment);
        System.out.println("huiu");
        // Remove the inner segment
        remove(comparableSegment);
        root.balance();
    }


    /**
     * Removes the desired segment leaf inside T
     * @param currNode  The current node of the tree
     * @param segment   The segment in the leaf to remove
     * @return The resulting tree from the deletion of the leaf
     */
    private Node<ComparableSegment> removeLeaf(Node<ComparableSegment> currNode, ComparableSegment segment) throws Exception
    {
        if(currNode == null || currNode.isLeaf())
            throw new Exception("the current AVL does not have the node with the given data : " + segment);

        if(currNode.getData().equals(segment))
        {
            // Remove the maximum element which by logic is the leaf we are looking for
            currNode.setLeft(removeMax(currNode.getLeft()).getItem1());
            currNode.balance();
            return currNode;
        }
        if (currNode.getData().compareToPoint(segment, segment.currentPoint) >= 0)
        {
            currNode.setLeft(removeLeaf(currNode.getLeft(), segment));
            currNode.balance();
        }else
        {
            currNode.setRight(removeLeaf(currNode.getRight(), segment));
            currNode.balance();
        }
        return currNode;
    }

    /**
     * insert a Node in segment in accordance with the particular behavior of the tree.
     * Another basis case is implemented -> the node is a leaf.
     * @param current the current node we are in
     * @param nodeToInsert the node to be inserted
     * @param currP the current point representing the status of the StatusQueue
     * @return the tree modified
     */
    private Node<ComparableSegment> insert(Node<ComparableSegment> current, Node<ComparableSegment> nodeToInsert, Point currP)
    {
        if(current == null)
        {
            nodeToInsert.setLeft(new Node<ComparableSegment>(nodeToInsert.getData()));
            return nodeToInsert;
        }
        else if(current.isLeaf())
        {
            current.setLeft(new Node<ComparableSegment>(nodeToInsert.getData()));
            current.setRight(new Node<ComparableSegment>(current.getData()));
            current.setData(nodeToInsert.getData());
        }
        else
        {
            if(current.getData().compareToPoint(nodeToInsert.getData(), currP) >= 0)
            {
                current.setLeft(insert(current.getLeft(), nodeToInsert, currP));
                current.balance();
            }
            else
            {
                current.setRight(insert(current.getRight(), nodeToInsert, currP));
                current.balance();
            }
        }

        current.updateHeight();
        return current;
    }

    /**
     * Insert a comparable segment inside T
     * @param data the data to be inserted inside the tree.
     */

    protected void insert(ComparableSegment data, Point currentP) {
       super.root = this.insert(super.root, new Node<ComparableSegment>(data), currentP);
    }

    /**
     * find the father of a given segment inside the tree meaning
     * that one of its child is the segment
     * @param segment the segment to find the father
     * @return the father
     */
    private Node<ComparableSegment> findFather(ComparableSegment segment)
    {
        if(root.getData().compareTo(segment) == 0)
            return root;

        Node<ComparableSegment> currentNode = this.root;
        while((currentNode.getLeft() != null && currentNode.getLeft().getData().compareTo(segment) != 0) && (currentNode.getRight() != null && currentNode.getRight().getData().compareTo(segment) != 0))
        {
            if(currentNode.getData().compareTo(segment) < 0)
                currentNode = currentNode.getLeft();
            else
                currentNode = currentNode.getRight();
        }
        return currentNode;
    }


    /**
     * Get the neighborhood of a given segment which is
     * - the closest left segment to the segment
     * - the closest right segment to the segment
     * @param segment the segment to find the neighborhood
     * @param currentPoint the current point of the tree
     * @return an array of segments which is {closest left segment, closest right segment}
     */
    public Segment[] getNeighborhood(Segment segment, Point currentPoint)
    {
        Segment leftNeighbor = null;
        Segment rightNeighbor = null;
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentPoint);
        boolean flag = true;
        Node<ComparableSegment> father = findFather(comparableSegment);
        if(father.getLeft().getData().compareTo(comparableSegment) == 0)
        {
            leftNeighbor = father.getRight().lookForMinimum().getData();
            flag = false;
            System.out.println("je passe");
        }
        else
            rightNeighbor = father.getLeft().lookForMaximum().getData();
        Node<ComparableSegment> fatherFromFather = findFather(father.getData());
        if(flag)
            leftNeighbor = fatherFromFather.getRight().lookForMinimum().getData();
        else
            rightNeighbor = fatherFromFather.getLeft().lookForMaximum().getData();
        return new Segment[]{leftNeighbor, rightNeighbor};
    }

    /**
     * To test later
     * @param tree
     * @param p
     * @return
     */
    public static StatusQueue getClosestRoot(StatusQueue tree, Point p)
    {
        if(tree == null)
            return null;
        Node<ComparableSegment> father = tree.root;
        Node<ComparableSegment> tmp = tree.root.getRight();
        Point p2 = Segment.getPointOnXAxis(p ,tmp.getData());
        while(p2.x < p.x && !tmp.isLeaf())
        {
            father = tmp;
            tmp = tmp.getRight();
            p2 = Segment.getPointOnXAxis(p, tmp.getData());
        }
        tmp = tmp.getLeft();
        p2 = Segment.getPointOnXAxis(p, tmp.getData());
        while(p2.x < p.x && !tmp.isLeaf())
        {
            father = tmp;
            tmp = tmp.getRight();
            p2 = Segment.getPointOnXAxis(p, tmp.getData());
        }
        StatusQueue res = new StatusQueue();
        res.root = father;
        return res;
    }

/*
    public static void main(String[] args) throws URISyntaxException, IOException {
        Parser parser = new Parser(Parser.getPathFromResource("/cartes/fichier2.txt"));
        ArrayList<Segment> segments = parser.getSegmentsFromFile();
        T t = new T(segments.getFirst().getUpperPoint().y);
        for(int i = 0; i < segments.size(); i++)
        {
            t.add(segments.get(i));
        }
        t.display();
        Segment[] neighborhood = t.getNeighborhood(segments.get(1));
        System.out.println(segments.get(1));
        System.out.println(neighborhood[0] + " " + neighborhood[1]);


    }
    TODO in standby, waiting for unit tests
 */
}
