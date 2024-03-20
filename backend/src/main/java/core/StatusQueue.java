package core;


import java.util.Objects;

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
        System.out.println("removing leaf");
        root = removeLeaf(root, comparableSegment);
        // Remove the inner segment
        System.out.println("removing segment");
        root = removeInner(root, comparableSegment, currPoint);
    }


    private Node<ComparableSegment> removeInner(Node<ComparableSegment> currNode, ComparableSegment segment, Point ref)
    {
        if(currNode == null)
        {
            return null;
        }
        if (currNode.getData().equals(segment))
        {
            currNode = removeRoot(currNode);
            return currNode;
        }
        //if (currNode.getData().compareToPoint(segment, ref) >= 0)
        if(statusQueueRelation(currNode.getData(), segment, ref))
        {
            currNode.setLeft(removeInner(currNode.getLeft(), segment, ref));
        }else
            currNode.setRight(removeInner(currNode.getRight(), segment, ref));
        return currNode;
    }


    /**
     * Removes the desired segment leaf inside T
     * @param currNode  The current node of the tree
     * @param segment   The segment in the leaf to remove
     * @return The resulting tree from the deletion of the leaf
     */
    private Node<ComparableSegment> removeLeaf(Node<ComparableSegment> currNode, ComparableSegment segment) throws Exception
    {
        System.out.println("curr:: " + currNode.getData());
        if(currNode == null || currNode.isLeaf())
            throw new Exception("the current AVL does not have the node with the given data : " + segment);
        if(currNode.getData().equals(segment))
        {
            // Remove the maximum element which by logic is the leaf we are looking for
            currNode.setLeft(removeMax(currNode.getLeft()).getItem1());
            currNode.balance();
            return currNode;
        }
        //if (currNode.getData().compareToPoint(segment, segment.getCurrentPoint()) >= 0)
        if(statusQueueRelation(currNode.getData(), segment, segment.getCurrentPoint()))
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
            System.out.println("tryign to insert in: " + current.getData() + " | node: " + nodeToInsert.getData());
            if(statusQueueRelation(current.getData(), nodeToInsert.getData(), currP))
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

    /**
     * Gets the left and right neighbors of a leaf in the tree
     * @param k     The segment that is contained in the leaf
     * @param ref   The current point of the graph
     * @return      The left neighbor if it exists, null otherwise. Same thing for the right one.
     */
    public Pair<ComparableSegment, ComparableSegment> getNeighbours(Segment k, Point ref)
    {

        // Cast to comparable segment
        ComparableSegment x = new ComparableSegment(k, ref);

        ComparableSegment leftN = null;
        ComparableSegment rightN = null;

        boolean goingLeft = true;
        Node<ComparableSegment> father = root;
        Node<ComparableSegment> curr = root;
        while(!curr.getData().equals(x))
        {
            System.out.println("curr: " + curr.getData());
            father = curr;
            if(statusQueueRelation(curr.getData(), x, ref) )
            {
                curr = curr.getLeft();
                goingLeft = true;
            }else{
                curr = curr.getRight();
                goingLeft = false;
            }
        }
        rightN = curr.getRight() == null ? null : curr.getRight().lookForMinimum().getData();
        if(!goingLeft)
        {
            leftN = father.getLeft().lookForMaximum().getData();
        }else
        {
            if(!curr.getLeft().isLeaf())
            {
                father = curr;
                curr = curr.getLeft();

                while (!curr.getData().equals(x))
                {
                    father = curr;
                    curr = curr.getRight();
                }
                leftN = father.getLeft().lookForMaximum().getData();
            }
        }
        return new Pair<>(leftN, rightN);
    }

    private static boolean statusQueueRelation(ComparableSegment curr, ComparableSegment other, Point ref)
    {
        // True : going left
        // False : going right
        int res = curr.compareToPoint(other, ref);
        if (res != 0)
            return res > 0;
        // Check the dist btw UP
        boolean rres = dist(curr.getUpperPoint(), ref) > dist(other.getUpperPoint(), ref);
        //System.out.println("this: " + dist(curr.getUpperPoint(), ref) + "| " + dist(other.getUpperPoint(), ref));
        //System.out.println(rres);
        return rres;
    }
    private static double dist(Point up, Point curr)
    {
        return Math.abs(up.x - curr.x);
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



    //TODO public static Segment[] find_leftmost_rightmost()

 */
}
