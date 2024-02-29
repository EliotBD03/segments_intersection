package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static core.AVLTestTools.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TTest \uD83C\uDF32 ")
class TTest
{
    static T testTree1;

    @BeforeAll
    @DisplayName("\uD83C\uDF32 T test starting")
    static void createTree()
    {
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
        assertThrows(Exception.class,() -> empty.remove((ComparableSegment) s3));
    }

    @Test
    @Order(3)
    void orderSwapped()
    {
        // Sweep line is at point P
        //      Remove s1 then s2
        //      Add s1 then s2
        //      Check if they were correctly exchanged

    }
}