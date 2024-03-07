package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static core.AVLTestTools.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StatusQueueTest \uD83C\uDF32 ")
class StatusQueueTest
{
    static StatusQueue testTree1;

    @BeforeAll
    @DisplayName("\uD83C\uDF32 StatusQueue test starting")
    static void createTree()
    {
        testTree1 =  new StatusQueue();
    }

    @Test
    @Order(1)
    void add()
    {
        AVLTestTools<ComparableSegment> s = new AVLTestTools<>();
        // Test that the graph's segments are correctly placed in T
        // Sweep line is at point E
        //      Insert s3
        testTree1.add(s3, E);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s3, s3))));

        // Sweep line is at point C
        //      Insert s2
        testTree1.add(s2, C);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s3,s3))));
        // Sweep line is at point G
        //      Insert s4
        testTree1.add(s4, G);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s3,s3,s4,s4))));
        // Sweep line is at point A
        //      Insert s1
        testTree1.add(s1, A);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s1,s1,s2,s2,s3,s3,s4,s4))));
        //testTree1.display();
    }
    @Test
    @Order(2)
    void remove()
    {
        // Try to remove from empty tree
        StatusQueue empty = new StatusQueue();
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

    private List<ComparableSegment> convertList(List<Segment> segments)
    {
        List<ComparableSegment> res = new ArrayList<>();
        for(Segment s : segments)
        {
            res.add(new ComparableSegment(s, 0));
        }
        return res;
    }
}
