package core;


import parser.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * class which represents a status queue.
 */
public class T extends AVL<ComparableSegment>
{

    /**
     * Add a segment inside the tree.
     * @param segment the segment to insert
     * @param currentYAxis the y-coordinate to perform comparisons
     */
    public void add(Segment segment, double currentYAxis)
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis);
        this.insert(comparableSegment);
    }

    /**
     * Remove a segment from the tree.
     * @param segment the segment to remove from the tree.
     * @param currentYAxis the y-coordinate to perform comparisons
     * @throws Exception if the segment does not exist.
     */
    public void remove(Segment segment, double currentYAxis) throws Exception
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
            nodeToInsert.setLeft(new Node<ComparableSegment>(nodeToInsert.getData()));
            return nodeToInsert;
        }
        else if(current.getLeft() == null && current.getRight() == null)
        {
            current.setLeft(new Node<ComparableSegment>(nodeToInsert.getData()));
            current.setRight(new Node<ComparableSegment>(current.getData()));
            current.setData(nodeToInsert.getData());

        }
        else if(current.compareTo(nodeToInsert) <= 0)
        {
            current.setLeft(insert(current.getLeft(), nodeToInsert));
            current.balance();

        }
        else
        {
            current.setRight(insert(current.getRight(), nodeToInsert));
            current.balance();
        }
        current.updateHeight();
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
     * @param currentYAxis the y-coordinate to perform comparisons
     * @return an array of segments which is {closest left segment, closest right segment}
     */
    public Segment[] getNeighborhood(Segment segment, double currentYAxis)
    {
        Segment leftNeighbor = null;
        Segment rightNeighbor = null;
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis);
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
