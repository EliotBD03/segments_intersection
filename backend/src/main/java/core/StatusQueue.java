package core;


import java.util.ArrayList;

/**
 * class which represents a status queue.
 */
public class StatusQueue extends AVL<ComparableSegment>
{

    private Point currStatus;
    private AVL<ComparableSegment>.Node<ComparableSegment> tmp;

    /**
     * Add a segment inside the tree.
     * @param segment the segment to insert
     * @param currP the current point representing the status of the StatusQueue
     */
    public void add(Segment segment, Point currP)
    {
        currStatus = currP;
        ComparableSegment comparableSegment = new ComparableSegment(segment);
        this.insert(comparableSegment, currStatus);
    }



    /**
     * Removes both nodes containing the desired segment if it exist.
     * @param segment           The segment to remove both nodes from this tree
     * @throws Exception        If the present is not present
     */
    public void removeSegment(Segment segment) throws Exception
    {

        ComparableSegment comparableSegment = new ComparableSegment(segment);
        // Remove the leaf segment first
        root = removeLeaf(root, comparableSegment);
        // Remove the inner segment
        root = removeInner(root, comparableSegment, currStatus);
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
        if(statusQueueRelation(currNode.getData(), segment))
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
        if(statusQueueRelation(currNode.getData(), segment))
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
            if(statusQueueRelation(current.getData(), nodeToInsert.getData()))
            {
                current.setLeft(insert(current.getLeft(), nodeToInsert, currStatus));
                current.balance();
            }
            else
            {
                current.setRight(insert(current.getRight(), nodeToInsert, currStatus));
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
     * Gets the closest root containing a segment, that either
     * holds the searched point or that the point is located between both his right
     * and left children.
     * @param p The searched point
     * @return  The desired root
     */
    protected Node<ComparableSegment> getClosestRoot(Point p)
    {
        if(this.root == null)
            return null;
        Node<ComparableSegment> tmp = this.root;
        Point p2 = Segment.getPointOnXAxis(p ,tmp.getData());
        // Go as much left as possible
        while(p2.x > p.x && !tmp.isLeaf())
        {
            tmp = tmp.getLeft();
            p2 = Segment.getPointOnXAxis(p, tmp.getData());
        }
        // Go as much right as possible
        while(p2.x < p.x && (tmp.getRight() != null))
        {
            tmp = tmp.getRight();
            p2 = Segment.getPointOnXAxis(p, tmp.getData());
        }
        return tmp;
    }

    /**
     * Gets the neighboring segments of a {@link Point}.
     * This method only makes sens if the point is not part of any segment of the tree.
     * And k must be either a point that is yet to be explored on the sweep line or the current point
     * @param k The point to search respecting the defined restriction
     * @return  The neighboring segments
     */
    public Pair<ComparableSegment, ComparableSegment> getNeighbours(Point k) throws IllegalArgumentException
    {
        Node<ComparableSegment> father = getClosestRoot(k);
        // Return the left and right leaves
        System.out.println("k" + k);
        System.out.println("curr " + currStatus);
        if(root == null)
            return new Pair<>(null, null);
        return new Pair<>(  father.getLeft() != null ? father.getLeft().lookForMaximum().getData(): null,
                            father.getRight() != null ? father.getRight().lookForMinimum().getData(): null);

    }

    /**
     * Finds all the segments containing the point k, and adds them inside the given list L and C
     * @param k The point to search
     * @param L The list of segments containing k as a lower point
     * @param C The list of segments containing k inside
     */
    public void findSegments(Point k, ArrayList<ComparableSegment> L, ArrayList<ComparableSegment> C)
    {
        findSegments(root, k, L, C);
    }

    /**
     * The recursive method used to find the segments as explained in {@link StatusQueue#findSegments(Point, ArrayList, ArrayList)}
     * @param current   The current node
     * @param k         The point to search
     * @param L         The list of segments containing k as a lower point
     * @param C         The list of segments containing k inside
     */
    private void findSegments(Node<ComparableSegment> current, Point k, ArrayList<ComparableSegment> L, ArrayList<ComparableSegment> C)
    {
        System.out.println(k);
        if(current == null)
            return;
        // Only works if k, is the current point OR if a future point on the same y axis but in the future !
        // Return the left and right leaves
        ComparableSegment s = current.getData();
        Point p = ComparableSegment.getPointOnXAxis(k, s);

        if (current.isLeaf())
        {
            if (k.equals(s.getLowerPoint()))
                L.add(s);
            // We still need to check if the segment has that lower point or not
            else if (!k.equals(s.getUpperPoint()) && Segment.hasPoint(s,k))
                C.add(s);
        }else
        {
            if (p.x > k.x)
                findSegments(current.getLeft(), k, L, C);
            else if (p.x < k.x)
                findSegments(current.getRight(), k, L, C);
            else
            {
                if(s.getUpperPoint().x > k.x)
                    findSegments(current.getLeft(), k, L, C);
                else
                {
                    findSegments(current.getLeft(), k, L, C);
                    findSegments(current.getRight(), k, L, C);
                }
            }
        }
    }


    /**
     * Finds a pair of segments with the first segment being the leftmost and the second
     * the rightmost segment in this statusQueue that both contains k (if they exist)
     * @param k
     * @return  The
     */
    public Pair<ComparableSegment, ComparableSegment> findLeftmostRightmost(Point k)
    {
        // Return the left and right leaves
        Node<ComparableSegment> father = getClosestRoot(k);

        ComparableSegment sL = null;
        ComparableSegment sR = null;
        Point p;

        if (father != null)
        {
            tmp = father.getLeft();
            while (!tmp.isLeaf())
            {
                p = ComparableSegment.getPointOnXAxis(k, tmp.getData());
                if(p.x >= k.x)
                    tmp = tmp.getLeft();
                else
                    tmp = tmp.getRight();
            }
            sL = tmp.getData();
            tmp = father.getRight();
            while (tmp != null && !tmp.isLeaf())
            {
                p = ComparableSegment.getPointOnXAxis(k, tmp.getData());
                if(p.x >= k.x)
                    tmp = tmp.getLeft();
                else
                    tmp = tmp.getRight();
            }
            if (tmp != null)
                sR = tmp.getData();
        }
        return new Pair<>(sL, sR);
    }



    /**
     * Gets the left and right neighbors of a leaf in the tree
     * @param k     The segment that is contained in the leaf
     * @return      The left neighbor if it exists, null otherwise. Same thing for the right one.
     */
    public Pair<ComparableSegment, ComparableSegment> getNeighbours(Segment k)
    {

        // Cast to comparable segment
        if(k == null)
            return new Pair<>(null, null);
        ComparableSegment x = new ComparableSegment(k);

        ComparableSegment leftN = null;
        ComparableSegment rightN = null;

        boolean goingLeft = true;
        Node<ComparableSegment> father = root;
        Node<ComparableSegment> curr = root;
        // Find the inner node with its father by moving in the tree
        while(!curr.getData().equals(x))
        {
            father = curr;
            if(statusQueueRelation(curr.getData(), x))
            {
                curr = curr.getLeft();
                goingLeft = true;
            }else{
                curr = curr.getRight();
                goingLeft = false;
            }
        }
        // Thanks to the property of the graph
        // The right node is always the minimum in the right child of the inner node containing x
        rightN = curr.getRight() == null ? null : curr.getRight().lookForMinimum().getData();
        // If the last movement we did was going right, then we can easily find the left node
        // it will simply the maximum of the left child of the father of the inner node
        if(!goingLeft)
        {
            leftN = father.getLeft().lookForMaximum().getData();
        }else
        {
            // If not, we have to locate the father of the leaf containing x
            if(!curr.getLeft().isLeaf())
            {
                father = curr;
                curr = curr.getLeft();

                while (!curr.getData().equals(x))
                {
                    father = curr;
                    curr = curr.getRight();
                }
                // After it was found, we can return the maximum of the left child
                // of the father of the leaf
                leftN = father.getLeft().lookForMaximum().getData();
            }
        }
        return new Pair<>(leftN, rightN);
    }


    /**
     * Is used to navigate inside the tree
     * @param curr      The current node of the tree
     * @param other     The other node to locate
     * @return True if the other node is located to the left of the curr node,
     * False if it's located to the right.
     */
    private boolean statusQueueRelation(ComparableSegment curr, ComparableSegment other)
    {
        // True : going left
        // False : going right
        int res = curr.compareToPoint(other, currStatus);
        if (res != 0)
            return res > 0;
        // Check the dist btw the upper points
        return dist(curr.getUpperPoint(), currStatus) > dist(other.getUpperPoint(), currStatus);
    }




    private static double dist(Point up, Point curr)
    {
        return Math.abs(up.x - curr.x);
    }

}
