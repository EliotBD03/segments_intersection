package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("QTest \uD83C\uDF33")
class QTest
{
    static Point A;
    static Point B;
    static Point C;
    static Point D;
    static Point E;
    static Point F;
    static Point G;
    static Point H;
    static Point I;
    static Point P;

    static Segment s1;
    static Segment s2;
    static Segment s3;
    static Segment s4;
    static Segment s5;

    static Q qTest;

    @BeforeAll
    static void initQ()
    {
        A = new Point(0,0);
        B = new Point(5,0);
        C = new Point(1,2);
        D = new Point(1,-1);
        E = new Point(2,3);
        F = new Point(3,-1);
        G = new Point(4,2);
        H = new Point(2.75d, 0);
        I = new Point(1,-2);
        P = new Point(1,0);

        s1 = new Segment(A,B,"s1");
        s2 = new Segment(C,D,"s2");
        s3 = new Segment(E,F,"s3");
        s4 = new Segment(G,H,"s4");
        s5 = new Segment(H,I,"s5");

        qTest = Q.initQ(new ArrayList<>(
                Arrays.asList(s1,s2,s3,s4,s5)
        ));
        //qTest.display();
        // Check for correct order
        assertEquals(qTest.root.getData(), A);
        assertEquals(qTest.root.getRight().getData(), D);
        assertEquals(qTest.root.getLeft().getData(), C);
        assertEquals(qTest.root.getLeft().getLeft().getData(), E);
        assertEquals(qTest.root.getLeft().getRight().getData(), G);
        assertEquals(qTest.root.getRight().getLeft().getData(), B);
        assertEquals(qTest.root.getRight().getLeft().getLeft().getData(), H);
        assertEquals(qTest.root.getRight().getRight().getData(), F);
        assertEquals(qTest.root.getRight().getRight().getRight().getData(), I);
    }


    @Test
    @Order(1)
    void UpperSegments()
    {
        // Check if segments got the correct upper segments

        assertEquals(qTest.getRootData().getStartOf(), Arrays.asList(s1));
    }

    @Test
    @Order(2)
    void dequeue()
    {
    }

    @Test
    @Order(3)
    void enqueue()
    {
        // Check for no doubles
        qTest.enqueue(E);
        qTest.enqueue(I);
        assertFalse(checkForDoubles(qTest.root, new HashSet<>()));

    }



    private boolean checkForDoubles(AVL<Point>.Node<Point> node, HashSet<Point> points)
    {
        if(node == null){
            return false;
        }
        Point rootData = node.getData();
        if(points.contains(rootData))
            return true;
        points.add(rootData);
        return checkForDoubles(node.getLeft(), points) || checkForDoubles(node.getRight(), points);
    }
}