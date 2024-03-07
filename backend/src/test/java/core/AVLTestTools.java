package core;

import core.Point;
import core.Segment;

import java.util.*;


public class AVLTestTools<T extends Comparable<T>> extends AVL<T>
{
    protected static final Point A = new Point(0,0);
    protected static final Point B = new Point(5,0);
    protected static final Point C = new Point(1,2);
    protected static final Point D = new Point(1,-1);
    protected static final Point E = new Point(2,3);
    protected static final Point F = new Point(3,-1);
    protected static final Point G = new Point(4,2);
    protected static final Point H = new Point(2.75d, 0);
    protected static final Point I = new Point(1,-2);
    protected static final Point P = new Point(1,0);

    protected static final Segment s1 = new Segment(A,B,"s1");
    protected static final Segment s2 = new Segment(C,D,"s2");
    protected static final Segment s3 = new Segment(E,F,"s3");
    protected static final Segment s4 = new Segment(G,H,"s4");
    protected static final Segment s5 = new Segment(H,I,"s5");




    /**
     * Checks if the order is respected inside the tree
     * @param node      The current node of the tree
     * @return          true if the order is respected, false otherwise
     * @param <T>       The generic type of the node
     */
    public static <T extends Comparable<T>> boolean orderIsRespected(AVL<T>.Node<T> node)
    {
        if(node == null || node.isLeaf())
            return true;

        boolean respected = (node.getLeft() != null && (node.getLeft().getData().compareTo(node.getData()) <= 0))
                || (node.getRight() != null && (node.getRight().getData().compareTo(node.getData()) >= 0));

        return respected && orderIsRespected(node.getLeft()) && orderIsRespected(node.getRight());
    }

    /**
     * Checks if the tree contains any repetitions, also fills a hashset with the nodes found
     * @param node      The current node of the tree
     * @param content   The current content covered of the tree
     * @return          true if there aren't any repetitions, false otherwise
     * @param <T> The generic type of the node
     */
    public static <T extends Comparable<T>> boolean checkForDoubles(AVL<T>.Node<T> node, HashSet<T> content)
    {
        if(node == null){
            return false;
        }
        T rootData = node.getData();
        if(content.contains(rootData))
            return true;
        content.add(rootData);
        return checkForDoubles(node.getLeft(), content) || checkForDoubles(node.getRight(), content);
    }

    public static <T extends Comparable<T>> boolean checkInorder(AVL<T>.Node<T> node, List<T> inorderList)
    {
        Queue<T> queue = new LinkedList<>(inorderList);
        // Checks that after the exploration, the queue is empty meaning there aren't any extra nodes
        return checkInorder(node, queue) && queue.isEmpty();
    }
    private static <T extends Comparable<T>> boolean checkInorder(AVL<T>.Node<T> node, Queue<T> inorderQueue)
    {
        if (node == null)
            return true;
        if(node.isLeaf())
            return checkQueue(node.getData(), inorderQueue);
        else{
            return checkInorder(node.getLeft(), inorderQueue) && checkQueue(node.getData(), inorderQueue) && checkInorder(node.getRight(), inorderQueue);
        }
    }
    private static <T extends Comparable<T>> boolean checkQueue(T current, Queue<T> queue)
    {
        return !queue.isEmpty() && queue.remove().compareTo(current) == 0;
    }


}
