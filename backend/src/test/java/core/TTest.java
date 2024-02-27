package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TTest \uD83C\uDF32 ")
class TTest
{
    static T testTree1;
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


    @BeforeAll
    @DisplayName("\uD83C\uDF32 T test starting")
    static void createTree()
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

        testTree1 =  new T(E.y);


    }

    @Test
    @Order(1)
    void add()
    {
        // Test that the graph's segments are correctly placed in T
        // Sweep line is at point E
        //      Insert s3

        // Sweep line is at point C
        //      Insert s2

        // Sweep line is at point G
        //      Insert s4

        // Sweep line is at point A
        //      Insert s1
        //testTree1.display();
    }
    @Test
    @Order(2)
    void remove()
    {
        // Try to remove from empty tree
        T empty = new T(E.y);
        assertThrows(Exception.class,() -> empty.remove(s3));

        // Sweep line is at point P
        //      Remove s1 then s2
        //      Add s1 then s2
        //      Check if they were correctly exchanged
    }

    @Test
    void insert()
    {
    }

    @Test
    void getNeighborhood()
    {
    }
}